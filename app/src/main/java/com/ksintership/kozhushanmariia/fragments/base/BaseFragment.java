package com.ksintership.kozhushanmariia.fragments.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public abstract class BaseFragment<VM extends ViewModel> extends Fragment {
    @LayoutRes
    protected final int fragmentLayout = getFragmentLayout();

    protected View rootView;

    protected VM viewModel;

    @LayoutRes
    protected abstract int getFragmentLayout();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getViewModelClass() != null)
            viewModel = new ViewModelProvider(this).get(getViewModelClass());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(fragmentLayout, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(rootView);
        initViews();
        init();
    }

    protected abstract void findViews(View root);

    protected abstract void initViews();

    protected void init() {
    }

    @Nullable
    protected abstract Class<VM> getViewModelClass();
}
