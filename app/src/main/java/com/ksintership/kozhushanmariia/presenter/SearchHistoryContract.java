package com.ksintership.kozhushanmariia.presenter;

import androidx.lifecycle.LiveData;

import com.ksintership.kozhushanmariia.model.SearchHistoryModel;
import com.ksintership.kozhushanmariia.model.TrackModel;

import java.util.List;

public interface SearchHistoryContract {
    interface View extends AbstractView {

    }

    interface Presenter extends com.ksintership.kozhushanmariia.presenter.Presenter<View> {
        LiveData<List<SearchHistoryModel>> getSearchHistoryLd();

        void reloadList();

        void removeSearchHistoryModel(SearchHistoryModel model);

        void clearSearchHistory();

    }
}
