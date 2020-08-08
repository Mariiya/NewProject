package com.ksintership.kozhushanmariia.presenter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ksintership.kozhushanmariia.di.AppInjector;
import com.ksintership.kozhushanmariia.model.SearchHistoryModel;
import com.ksintership.kozhushanmariia.repository.SearchHistoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

public class SearchHistoryPresenter implements SearchHistoryContract.Presenter {

    @Inject
    SearchHistoryRepository repository;

    private ExecutorService executor;

    private MutableLiveData<List<SearchHistoryModel>> searchHistoryLd;

    private SearchHistoryContract.View view;

    public SearchHistoryPresenter() {
        AppInjector.getAppComponent().inject(this);
        executor = Executors.newSingleThreadExecutor();
        searchHistoryLd = new MutableLiveData<>();
    }

    @Override
    public void attach(SearchHistoryContract.View view) {
        this.view = view;
        reloadList();
    }

    @Override
    public LiveData<List<SearchHistoryModel>> getSearchHistoryLd() {
        return searchHistoryLd;
    }

    @Override
    public void reloadList() {
        executor.execute(() -> {
            if (repository.getAllHistory() != null) {
                searchHistoryLd.postValue(repository.getAllHistory());
            } else
                searchHistoryLd.postValue(new ArrayList<>());
        });
    }

    @Override
    public void removeSearchHistoryModel(SearchHistoryModel model) {
        repository.removeModel(model);
        reloadList();
    }

    @Override
    public void clearSearchHistory() {
        repository.clearHistory();
    }


    @Override
    public void detach() {
        view = null;
    }

    @Override
    public void clear() {
        executor.shutdownNow();
        executor = null;
        searchHistoryLd = null;
    }
}
