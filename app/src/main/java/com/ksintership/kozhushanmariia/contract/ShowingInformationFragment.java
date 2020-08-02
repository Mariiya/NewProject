package com.ksintership.kozhushanmariia.contract;

import androidx.annotation.StringRes;

public interface ShowingInformationFragment {
    void showSnackbar(@StringRes int msgResId);

    void showProgressBar();

    void hideProgressBar();
}
