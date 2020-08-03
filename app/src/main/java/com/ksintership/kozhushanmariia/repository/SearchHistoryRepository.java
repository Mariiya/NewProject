package com.ksintership.kozhushanmariia.repository;

import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;

import com.ksintership.kozhushanmariia.model.SearchHistoryModel;

import java.util.List;

public interface SearchHistoryRepository {
    @WorkerThread
    @Nullable
    List<SearchHistoryModel> getAllHistory();

    void add(SearchHistoryModel searchHistoryModel);

    void removeModel(SearchHistoryModel model);

    void clearHistory();
}
