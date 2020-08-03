package com.ksintership.kozhushanmariia.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.ksintership.kozhushanmariia.R;
import com.ksintership.kozhushanmariia.contract.BindableHistoryItem;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HistoryItemView extends ConstraintLayout implements BindableHistoryItem {

    SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE 'at' HH:mm");

    private TextView historyQuery;
    private TextView dateQuery;
    private ImageButton clearQuery;

    @Override
    public void bind(String historyQuery, long dateQuery, OnClickListener onClearClickListener) {
        this.historyQuery.setText(historyQuery);
        this.dateQuery.setText(dateFormat.format(new Date(dateQuery)));
        clearQuery.setOnClickListener(onClearClickListener);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        historyQuery = findViewById(R.id.history_query);
        dateQuery = findViewById(R.id.date);
        clearQuery = findViewById(R.id.clear);
    }

    public HistoryItemView(Context context) {
        super(context);
    }

    public HistoryItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HistoryItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
