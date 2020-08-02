package com.ksintership.kozhushanmariia.contract;

import com.ksintership.kozhushanmariia.contract.listeners.SearchListener;

public interface IActivity {
    void setToolbarVisibility(int visibility);

    void hideSearch();

    void setSearchListener(SearchListener searchListener);
}
