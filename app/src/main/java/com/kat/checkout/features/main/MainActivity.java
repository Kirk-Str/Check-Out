package com.kat.checkout.features.main;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import com.kat.checkout.R;
import com.kat.checkout.features.base.BaseActivity;
import com.kat.checkout.features.common.ErrorView;
import com.kat.checkout.features.detail.DetailActivity;
import com.kat.checkout.injection.component.ActivityComponent;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class MainActivity extends BaseActivity implements MainMvpView, ErrorView.ErrorListener {

    private static final int POKEMON_COUNT = 20;

    @Inject
    PokemonAdapter pokemonAdapter;
    @Inject
    MainPresenter mainPresenter;

    @BindView(R.id.view_error)
    ErrorView errorView;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    @BindView(R.id.recycler_pos_lines)
    RecyclerView itemsRecycler;

    @BindView(R.id.swipe_to_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(toolbar);

        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.primary);
        swipeRefreshLayout.setColorSchemeResources(R.color.white);
        swipeRefreshLayout.setOnRefreshListener(() -> mainPresenter.getPokemon(POKEMON_COUNT));

        itemsRecycler.setLayoutManager(new GridLayoutManager(this, 3));
        itemsRecycler.setAdapter(pokemonAdapter);
        pokemonClicked();
        errorView.setErrorListener(this);

        mainPresenter.getPokemon(POKEMON_COUNT);
    }

    private void pokemonClicked() {
        Disposable disposable =
                pokemonAdapter
                        .getPokemonClick()
                        .subscribe(
                                pokemon ->
                                        startActivity(DetailActivity.getStartIntent(this, pokemon)),
                                throwable -> {
                                    Timber.e(throwable, "Pokemon click failed");
                                    Toast.makeText(
                                            this,
                                            R.string.error_something_bad_happened,
                                            Toast.LENGTH_LONG)
                                            .show();
                                });
        mainPresenter.addDisposable(disposable);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected void attachView() {
        mainPresenter.attachView(this);
    }

    @Override
    protected void detachPresenter() {
        mainPresenter.detachView();
    }

    @Override
    public void showPokemon(List<String> pokemon) {
        pokemonAdapter.setPokemon(pokemon);
        itemsRecycler.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgress(boolean show) {
        if (show) {
            if (itemsRecycler.getVisibility() == View.VISIBLE
                    && pokemonAdapter.getItemCount() > 0) {
                swipeRefreshLayout.setRefreshing(true);
            } else {
                progressBar.setVisibility(View.VISIBLE);

                itemsRecycler.setVisibility(View.GONE);
                swipeRefreshLayout.setVisibility(View.GONE);
            }

            errorView.setVisibility(View.GONE);
        } else {
            swipeRefreshLayout.setRefreshing(false);
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError(Throwable error) {
        itemsRecycler.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
        Timber.e(error, "There was an error retrieving the pokemon");
    }

    @Override
    public void onReloadData() {
        mainPresenter.getPokemon(POKEMON_COUNT);
    }
}
