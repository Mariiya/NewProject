package com.ksintership.kozhushanmariia.views;

import android.animation.Animator;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.ksintership.kozhushanmariia.R;
import com.ksintership.kozhushanmariia.contract.IActivity;
import com.ksintership.kozhushanmariia.contract.listeners.AnimationCompleteListener;
import com.ksintership.kozhushanmariia.contract.listeners.SearchListener;
import com.ksintership.kozhushanmariia.utils.ViewUtil;

public class SearchToolbar extends ConstraintLayout {
    private static final long SHOW_HIDE_ANIMATION_DURATION = 400;

    private SearchListener listener;

    private IActivity iActivity;

    private EditText query;
    private ImageButton clear;
    private ImageButton close;

    private View anchorView;

    public SearchToolbar(Context context) {
        super(context);
    }

    public SearchToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SearchToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void show(View anchorView, IActivity iActivity) {
        if (isVisible()) return;
        this.anchorView = anchorView;
        this.iActivity = iActivity;
        setVisibility(VISIBLE);
        iActivity.setToolbarVisibility(INVISIBLE);
        Animator animator = ViewAnimationUtils.createCircularReveal(this,
                (int) (anchorView.getX() + anchorView.getHeight() / 2.0f),
                (int) (anchorView.getY() + anchorView.getWidth() / 2.0f),
                0,
                getWidth())
                .setDuration(SHOW_HIDE_ANIMATION_DURATION);
        animator.addListener(new AnimationCompleteListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                ViewUtil.showKeyboard(query);
            }
        });
        animator.start();
    }

    public void hide() {
        if (!isVisible()) return;
        iActivity.setToolbarVisibility(VISIBLE);
        ViewUtil.hideKeyboard(query);
        clearQuery();
        Animator animator = ViewAnimationUtils.createCircularReveal(this,
                (int) (anchorView.getX() + anchorView.getHeight() / 2.0f),
                (int) (anchorView.getY() + anchorView.getWidth() / 2.0f),
                getWidth(),
                0)
                .setDuration(SHOW_HIDE_ANIMATION_DURATION);
        animator.addListener(new AnimationCompleteListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                setVisibility(INVISIBLE);
                if (listener != null) listener.onSearchClose();
            }
        });
        animator.start();
    }

    public void setListener(@NonNull SearchListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        query = findViewById(R.id.search_text);
        clear = findViewById(R.id.search_clear);
        close = findViewById(R.id.search_back);

        close.setOnClickListener(view -> hide());

        clear.setOnClickListener(view -> clearQuery());

        query.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().isEmpty()) clear.setVisibility(GONE);
                else clear.setVisibility(VISIBLE);
                if (listener != null) listener.onSearchTextChanged(query.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        query.setOnKeyListener((view, keyCode, keyEvent) -> {
            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN &&
                    (keyCode == KeyEvent.KEYCODE_SEARCH || keyCode == KeyEvent.KEYCODE_ENTER)) {
                if (listener != null) listener.onSearch(query.getText().toString());
                ViewUtil.hideKeyboard(query);
                return true;
            }
            return false;
        });
    }

    public boolean isVisible() {
        return getVisibility() == VISIBLE;
    }

    private void clearQuery() {
        query.setText("");
    }

}
