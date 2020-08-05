package com.ksintership.kozhushanmariia.repository;

import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;

import com.ksintership.kozhushanmariia.database.SearchHistoryDao;
import com.ksintership.kozhushanmariia.model.EntitySearchHistory;
import com.ksintership.kozhushanmariia.model.EntitySearchHistoryMapper;
import com.ksintership.kozhushanmariia.model.SearchHistoryModel;
import com.ksintership.kozhushanmariia.utils.PreferencesManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SearchHistoryRepositoryImpl implements SearchHistoryRepository {

    private SearchHistoryDao historyDao;
    private ExecutorService executor;
    private EntitySearchHistoryMapper mapper;

    private List<SearchHistoryModel> historyList;

    private boolean isCachedInRAM = false;

    public SearchHistoryRepositoryImpl(SearchHistoryDao historyDao) {
        this.historyDao = historyDao;
        historyList = new ArrayList<>();
        executor = Executors.newSingleThreadExecutor();
        mapper = new EntitySearchHistoryMapper();
    }

    @WorkerThread
    @Nullable
    @Override
    public List<SearchHistoryModel> getAllHistory() {
        if (!PreferencesManager.hasSaveSearchHistory()) {
            historyDao.clear();
            return null;
        }
        if (!isCachedInRAM) {
            List<EntitySearchHistory> entityList = historyDao.getAll();
            if (entityList == null || entityList.isEmpty()) {
                return null;
            }
            historyList = mapper.entityToModel(entityList);
            isCachedInRAM = true;
        }
        return historyList;
    }

    @Override
    public void add(SearchHistoryModel searchHistoryModel) {
        if (!PreferencesManager.hasSaveSearchHistory()) return;
        historyList.add(0, searchHistoryModel);
        executor.execute(() -> historyDao.insert(mapper.modelToEntity(searchHistoryModel)));
    }

    @Override
    public void removeModel(SearchHistoryModel model) {
        historyList.remove(model);
        executor.execute(() -> historyDao.deleteSearchQuery(mapper.modelToEntity(model)));
    }

    @Override
    public void clearHistory() {
        historyList.clear();
        executor.execute(() -> historyDao.clear());
    }
}
