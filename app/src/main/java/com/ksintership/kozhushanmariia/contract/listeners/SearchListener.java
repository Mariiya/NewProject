package com.ksintership.kozhushanmariia.contract.listeners;

public interface SearchListener {
    default void onSearchClose(){}

    void onSearch(String query);

    default void onSearchTextChanged(String query){}
}
