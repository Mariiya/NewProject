package com.ksintership.kozhushanmariia.contract.listeners;

public interface SearchListener {
    void onSearchClose();

    void onSearch(String query);

    void onSearchTextChanged(String query);
}
