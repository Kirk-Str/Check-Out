package com.kat.checkout.features.main;

import java.util.List;

import com.kat.checkout.features.base.MvpView;

public interface MainMvpView extends MvpView {

    void showPokemon(List<String> pokemon);

    void showProgress(boolean show);

    void showError(Throwable error);
}
