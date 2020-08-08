package com.ksintership.kozhushanmariia.presenter;

public interface Presenter<V> {
    void attach(V view);

    void detach();

    void clear();
}
