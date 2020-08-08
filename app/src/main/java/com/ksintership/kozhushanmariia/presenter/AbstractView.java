package com.ksintership.kozhushanmariia.presenter;

public interface AbstractView {
    default void showProgressBar() {
    }

    default void hideProgressBar() {
    }

    default void showMessage(String message) {
    }
}
