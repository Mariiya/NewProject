package com.ksintership.kozhushanmariia.activity;


import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.MenuRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ksintership.kozhushanmariia.R;
import com.ksintership.kozhushanmariia.contract.IActivity;
import com.ksintership.kozhushanmariia.views.SearchToolbar;

public abstract class BaseActivity extends AppCompatActivity implements IActivity {

    protected Toolbar toolbar;
    private TextView toolbarTitle;
    protected SearchToolbar searchToolbar;

    private ImageButton searchAction;

    public void initToolbar(String title) {
        this.initToolbar(title, false);
    }

    public void initToolbar(String title, boolean navigationUp) {
        toolbar = findViewById(R.id.toolbar);
        toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(title);
        if (navigationUp) {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24);
            toolbar.setNavigationOnClickListener(view -> onBackPressed());
        } else {
            toolbar.setNavigationIcon(null);
        }
        searchAction = findViewById(R.id.search_action);
        searchAction.setVisibility(View.GONE);
    }

    public void initToolbar(String title,
                            @MenuRes int menuResId,
                            boolean navigationUp) {
        this.initToolbar(title, navigationUp);
        toolbar.getMenu().clear();
        toolbar.inflateMenu(menuResId);
    }

    public void initToolbarWithSearch(String title,
                                      @MenuRes int menuResId,
                                      boolean navigationUp) {
        this.initToolbar(title, menuResId, navigationUp);
        searchToolbar = findViewById(R.id.search_toolbar);
        searchAction.setVisibility(View.VISIBLE);

        searchAction.setOnClickListener(view -> searchToolbar.show(searchAction, this));
    }

    @Override
    public void onBackPressed() {
        if (searchToolbar != null && searchToolbar.isVisible()) {
            searchToolbar.hide();
            return;
        }
        super.onBackPressed();
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    @Nullable
    public SearchToolbar getSearchToolbar() {
        return searchToolbar;
    }

    @Override
    public void setToolbarVisibility(int visibility) {
        if (toolbar != null) toolbar.setVisibility(visibility);
    }
}
