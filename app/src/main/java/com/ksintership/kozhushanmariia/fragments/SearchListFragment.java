package com.ksintership.kozhushanmariia.fragments;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ksintership.kozhushanmariia.R;
import com.ksintership.kozhushanmariia.activity.BaseActivity;
import com.ksintership.kozhushanmariia.contract.listeners.SearchListener;
import com.ksintership.kozhushanmariia.fragments.base.BaseFragment;
import com.ksintership.kozhushanmariia.utils.adapter.TrackListAdapter;
import com.ksintership.kozhushanmariia.viewmodels.SearchListViewModel;

public class SearchListFragment extends BaseFragment<SearchListViewModel> implements SearchListener {

    private RecyclerView list;
    private TrackListAdapter adapter;

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_search_list;
    }

    @Override
    protected void findViews(View root) {
        list = root.findViewById(R.id.track_list);
    }

    @Override
    protected void initViews() {
        ((BaseActivity) getActivity()).getSearchToolbar().setListener(this);
        adapter = new TrackListAdapter(getContext());
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.setAdapter(adapter);
    }

    @Override
    public void onSearchClose() {
        //TODO: not implemented yet
    }

    @Override
    public void onSearch(String query) {
        //TODO: not implemented yet
    }

    @Override
    public void onSearchTextChanged(String query) {
        //TODO: not implemented yet
    }

    @Override
    protected Class<SearchListViewModel> getViewModelClass() {
        return SearchListViewModel.class;
    }
}