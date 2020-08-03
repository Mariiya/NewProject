package com.ksintership.kozhushanmariia.model;

import androidx.annotation.Nullable;

public class SearchHistoryModel {
    private final String historyQuery;
    private final long searchDate;

    public SearchHistoryModel(String historyQuery, long searchDate) {
        this.historyQuery = historyQuery;
        this.searchDate = searchDate;
    }

    public long getSearchDate() {
        return searchDate;
    }

    public String getHistoryQuery() {
        return historyQuery;
    }

    @Override
    public int hashCode() {
        return (int) searchDate;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) return false;
        if (obj instanceof SearchHistoryModel) {
            return ((SearchHistoryModel) obj).searchDate == this.searchDate;
        }
        return false;
    }
}
