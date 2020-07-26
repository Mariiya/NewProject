package com.ksintership.kozhushanmariia.activity;


import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.ksintership.kozhushanmariia.R;

public abstract class BaseActivity extends AppCompatActivity {

    private Toolbar toolbar;

    public void initToolbar(String title) {
        this.initToolbar(title, false);
    }

    public void initToolbar(String title, boolean navigationUp) {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        if (navigationUp) {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
            toolbar.setNavigationOnClickListener(view -> onBackPressed());
        }
    }

    public void initToolbar(String title, int menuResId, boolean navigationUp) {
        this.initToolbar(title, navigationUp);
        toolbar.inflateMenu(menuResId);
    }

    public Toolbar getToolbar() {
        return toolbar;
    }
}
