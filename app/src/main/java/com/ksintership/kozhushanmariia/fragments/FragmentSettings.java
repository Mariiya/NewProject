package com.ksintership.kozhushanmariia.fragments;

import android.view.View;
import android.widget.Switch;

import androidx.lifecycle.ViewModel;

import com.ksintership.kozhushanmariia.R;
import com.ksintership.kozhushanmariia.activity.BaseActivity;
import com.ksintership.kozhushanmariia.fragments.base.BaseFragment;
import com.ksintership.kozhushanmariia.presenter.Presenter;
import com.ksintership.kozhushanmariia.utils.PreferencesManager;

public class FragmentSettings extends BaseFragment<Presenter> {
    private Switch switchSaveLastSearch;
    private Switch switchSaveSearchHistory;

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_settings;
    }

    @Override
    protected void findViews(View root) {
        switchSaveLastSearch = rootView.findViewById(R.id.switch_save_last_search);
        switchSaveSearchHistory = rootView.findViewById(R.id.switch_save_search_history);
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
    }

    @Override
    protected Class<Presenter> getPresenterClass() {
        return null;
    }
}
