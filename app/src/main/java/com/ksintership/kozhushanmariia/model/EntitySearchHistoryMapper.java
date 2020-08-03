package com.ksintership.kozhushanmariia.model;

import com.ksintership.kozhushanmariia.contract.mappers.EntityModelMapper;

import java.util.ArrayList;
import java.util.List;

public class EntitySearchHistoryMapper implements EntityModelMapper<EntitySearchHistory, SearchHistoryModel> {
    @Override
    public SearchHistoryModel entityToModel(EntitySearchHistory entityModel) {
        return new SearchHistoryModel(entityModel.searchQuery, entityModel.searchDate);
    }

    @Override
    public List<SearchHistoryModel> entityToModel(List<EntitySearchHistory> entityModel) {
        ArrayList<SearchHistoryModel> result = new ArrayList<>();
        for (EntitySearchHistory entity : entityModel) {
            result.add(entityToModel(entity));
        }
        return result;
    }

    @Override
    public EntitySearchHistory modelToEntity(SearchHistoryModel internalModel) {
        EntitySearchHistory entity = new EntitySearchHistory();
        entity.searchDate = internalModel.getSearchDate();
        entity.searchQuery = internalModel.getHistoryQuery();
        return entity;
    }

    @Override
    public List<EntitySearchHistory> modelToEntity(List<SearchHistoryModel> internalModel) {
        ArrayList<EntitySearchHistory> result = new ArrayList<>();
        for (SearchHistoryModel model : internalModel) {
            result.add(modelToEntity(model));
        }
        return null;
    }
}
