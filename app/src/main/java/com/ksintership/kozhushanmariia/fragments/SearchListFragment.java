package com.ksintership.kozhushanmariia.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ksintership.kozhushanmariia.R;
import com.ksintership.kozhushanmariia.activity.BaseActivity;
import com.ksintership.kozhushanmariia.activity.MainActivity;
import com.ksintership.kozhushanmariia.contract.IActivity;
import com.ksintership.kozhushanmariia.contract.ShowingInformationFragment;
import com.ksintership.kozhushanmariia.contract.listeners.SearchListener;
import com.ksintership.kozhushanmariia.fragments.base.BaseFragment;
import com.ksintership.kozhushanmariia.model.TrackModel;
import com.ksintership.kozhushanmariia.utils.Constants;
import com.ksintership.kozhushanmariia.utils.adapter.TrackListAdapter;
import com.ksintership.kozhushanmariia.viewmodels.SearchListViewModel;

public class SearchListFragment extends BaseFragment<SearchListViewModel> implements SearchListener, TrackListAdapter.OnTrackItemClickListener {

    private RecyclerView list;
    private TrackListAdapter adapter;

    private IActivity iActivity;
    private ShowingInformationFragment showingInfoFragment;

    private NavController navController;

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
        ((BaseActivity) getActivity()).initToolbarWithSearch(getString(R.string.app_name),
                R.menu.main_menu,
                (MainActivity) getActivity(),
                false);
        adapter = new TrackListAdapter(getContext(), this);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.setAdapter(adapter);

        list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            LinearLayoutManager manager = (LinearLayoutManager) list.getLayoutManager();

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (manager.findLastVisibleItemPosition() == manager.getItemCount() - 4) {
                    viewModel.reloadTrackList();
                }
            }
        });
    }

    @Override
    protected void init() {
        super.init();
        iActivity = (IActivity) getActivity();
        showingInfoFragment = (ShowingInformationFragment) getActivity();
        navController = NavHostFragment.findNavController(this);

        iActivity.setSearchListener(this);

        viewModel.init(showingInfoFragment);

        String historySearchQuery = null;
        if (getArguments() != null) {
            historySearchQuery = getArguments().getString(Constants.BUNDLE_SEARCH_QUERY);
        }
        if (historySearchQuery != null) {
            onSearch(historySearchQuery);
            getArguments().clear();
        } else {
            viewModel.loadCachedTrack();
        }

        viewModel.getTrackListLd().observe(getViewLifecycleOwner(),
                trackModels -> {
                    adapter.setTracks(trackModels);
                    if (trackModels == null) {
                        //TODO: realize view stub for empty list
                    }
                });
        viewModel.getShowErrorMessageLd().observe(getViewLifecycleOwner(),
                showMsg -> {
                    if (showMsg)
                        ((ShowingInformationFragment) getActivity()).showSnackbar(R.string.search_failure);
                });
    }

    @Override
    public void onItemClick(TrackModel trackModel) {
        iActivity.hideSearch();
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.BUNDLE_TRACK_ID, trackModel.getId());
        navController.navigate(R.id.toTrackDetailFragment, bundle);
    }

    @Override
    public void onSearch(String query) {
        viewModel.search(query);
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