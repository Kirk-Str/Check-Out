package com.kat.checkout.features.detail;

import com.kat.checkout.data.model.response.Pokemon;
import com.kat.checkout.data.model.response.Statistic;
import com.kat.checkout.features.base.MvpView;

public interface DetailMvpView extends MvpView {

    void showPokemon(Pokemon pokemon);

    void showStat(Statistic statistic);

    void showProgress(boolean show);

    void showError(Throwable error);
}
