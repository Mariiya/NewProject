package com.ksintership.kozhushanmariia.fragments;

import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.ksintership.kozhushanmariia.R;
import com.ksintership.kozhushanmariia.activity.BaseActivity;
import com.ksintership.kozhushanmariia.di.AppInjector;
import com.ksintership.kozhushanmariia.fragments.base.BaseFragment;
import com.ksintership.kozhushanmariia.presenter.Presenter;
import com.ksintership.kozhushanmariia.repository.TrackPreviewRepository;
import com.ksintership.kozhushanmariia.utils.PreferencesManager;

import javax.inject.Inject;

public class FragmentSettings extends BaseFragment<Presenter> {
    @Inject
    TrackPreviewRepository repository;

    private Switch switchSaveLastSearch;
    private Switch switchSaveSearchHistory;

    private TextView cacheSize;
    private View clearCacheButton;

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_settings;
    }

    @Override
    protected void findViews(View root) {
        switchSaveLastSearch = root.findViewById(R.id.switch_save_last_search);
        switchSaveSearchHistory = root.findViewById(R.id.switch_save_search_history);

        cacheSize = root.findViewById(R.id.cache_size);
        clearCacheButton = root.findViewById(R.id.clear_cache);
    }

    @Override
    protected void initViews() {
        ((BaseActivity) getActivity()).initToolbar(getContext().getString(R.string.settings), true);

        switchSaveLastSearch.setChecked(PreferencesManager.hasSaveLastSearch());
        switchSaveLastSearch.setOnClickListener(view_ ->
                PreferencesManager.setSaveLastSearch(switchSaveLastSearch.isChecked())
        );

        switchSaveSearchHistory.setChecked(PreferencesManager.hasSaveSearchHistory());
        switchSaveSearchHistory.setOnClickListener(view_ ->
                PreferencesManager.setSaveSearchHistory(switchSaveSearchHistory.isChecked())
        );

        cacheSize.setText(getCacheSizeStr());

        clearCacheButton.setOnClickListener(v -> {
            repository.clearCache();
            cacheSize.setText(getCacheSizeStr());
        });
    }

    private String getCacheSizeStr() {
        return repository.getCacheSizeInMb() + " MB";
    }

    @Override
    protected void init() {
        super.init();
        AppInjector.getAppComponent().inject(this);
    }

    @Override
    protected Class<Presenter> getPresenterClass() {
        return null;
    }
}
