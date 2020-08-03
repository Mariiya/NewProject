package com.ksintership.kozhushanmariia.fragments;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ksintership.kozhushanmariia.R;
import com.ksintership.kozhushanmariia.activity.BaseActivity;
import com.ksintership.kozhushanmariia.fragments.base.BaseFragment;
import com.ksintership.kozhushanmariia.model.SearchHistoryModel;
import com.ksintership.kozhushanmariia.utils.Constants;
import com.ksintership.kozhushanmariia.utils.adapter.HistoryListAdapter;
import com.ksintership.kozhushanmariia.viewmodels.HistorySearchViewModel;

import java.util.ArrayList;

public class SearchHistoryFragment extends BaseFragment<HistorySearchViewModel>
        implements HistoryListAdapter.OnHistoryItemClickListener,
        Toolbar.OnMenuItemClickListener {

    private RecyclerView list;
    private HistoryListAdapter adapter;


    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_history_search;
    }

    @Override
    protected void init() {
        super.init();
        viewModel.init();
        viewModel.getSearchHistoryLd().observe(getViewLifecycleOwner(), (list) -> adapter.setList(list));
    }

    @Override
    protected void findViews(View root) {
        list = root.findViewById(R.id.history_search_list);
    }

    @Override
    protected void initViews() {
        ((BaseActivity) getActivity()).initToolbar("Search history", R.menu.search_history, this, true);
        ((BaseActivity) getActivity()).getToolbar().setOnMenuItemClickListener(this);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new HistoryListAdapter(getContext(), this);
        list.setAdapter(adapter);
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clear_history:
                viewModel.clearSearchHistory();
                adapter.setList(new ArrayList<>());
                return true;
        }
        return false;
    }

    @Override
    public void onClearItemClick(SearchHistoryModel model) {
        viewModel.removeSearchHistoryModel(model);
    }

    @Override
    public void onHistoryItemClick(SearchHistoryModel model) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_SEARCH_QUERY, model.getHistoryQuery());
        NavHostFragment.findNavController(this).navigate(R.id.onQueryClicked, bundle);
    }

    @Nullable
    @Override
    protected Class<HistorySearchViewModel> getViewModelClass() {
        return HistorySearchViewModel.class;
    }
}