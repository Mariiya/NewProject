package com.ksintership.kozhushanmariia.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.StringRes;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.ksintership.kozhushanmariia.R;
import com.ksintership.kozhushanmariia.contract.ShowingInformationFragment;
import com.ksintership.kozhushanmariia.utils.ViewUtil;


public class MainActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener, ShowingInformationFragment {

    private NavController navController;
    private ProgressBar progressBar;
    private View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rootView = findViewById(R.id.root_view);
        progressBar = findViewById(R.id.progress_horizontal);

        initToolbarWithSearch(getString(R.string.app_name), R.menu.main_menu, false);

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        initListeners();
    }

    @Override
    public void showSnackbar(@StringRes int msg) {
        ViewUtil.showSnackbar(rootView, msg);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (navController.getCurrentDestination().getId() == R.id.searchListFragment) {
            initToolbarWithSearch(getString(R.string.app_name), R.menu.main_menu, false);
        }
    }

    private void initListeners() {
        getToolbar().setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                navController.navigate(R.id.toSettingsFragment);
                toolbar.getMenu().clear();
                return true;
        }
        return false;
    }
}