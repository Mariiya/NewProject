package com.ksintership.kozhushanmariia.contract;

import android.view.View;

public interface BindableHistoryItem {
    void bind(String historyQuery, long dateQuery, View.OnClickListener onClearClickListener);
}
