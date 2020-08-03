package com.ksintership.kozhushanmariia.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ksintership.kozhushanmariia.di.AppInjector;
import com.ksintership.kozhushanmariia.model.SearchHistoryModel;
import com.ksintership.kozhushanmariia.repository.SearchHistoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

public class HistorySearchViewModel extends ViewModel {

    @Inject
    SearchHistoryRepository repository;

    private ExecutorService executor;

    private MutableLiveData<List<SearchHistoryModel>> searchHistoryLd = new MutableLiveData<>();

    public void init() {
        AppInjector.getAppComponent().inject(this);
        executor = Executors.newSingleThreadExecutor();
        reloadList();
    }

    public LiveData<List<SearchHistoryModel>> getSearchHistoryLd() {
        return searchHistoryLd;
    }

    public void removeSearchHistoryModel(SearchHistoryModel model) {
        repository.removeModel(model);
        reloadList();
    }

    public void reloadList() {
        executor.execute(() -> {
            if (repository.getAllHistory() != null) {
                searchHistoryLd.postValue(repository.getAllHistory());
            } else
                searchHistoryLd.postValue(new ArrayList<>());
        });
    }

    public void clearSearchHistory() {
        repository.clearHistory();
    }
}
