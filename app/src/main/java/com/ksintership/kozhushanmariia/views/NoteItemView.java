package com.ksintership.kozhushanmariia.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.ksintership.kozhushanmariia.R;
import com.ksintership.kozhushanmariia.contract.BindableNoteItem;

public class NoteItemView extends ConstraintLayout implements BindableNoteItem {

    private TextView titleTextView;
    private TextView snippetTextView;

    public NoteItemView(Context context) {
        super(context);
    }

    public NoteItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoteItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        titleTextView = this.findViewById(R.id.title);
        snippetTextView = this.findViewById(R.id.snippet);
    }

    @Override
    public void bind(String title, String snippet) {
        titleTextView.setText(title);
        snippetTextView.setText(snippet);
    }
}
