package com.ksintership.kozhushanmariia.fragments.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ksintership.kozhushanmariia.di.AppInjector;
import com.ksintership.kozhushanmariia.presenter.Presenter;
import com.ksintership.kozhushanmariia.presenter.PresenterOwner;

public abstract class BaseFragment<P extends Presenter> extends Fragment
        implements PresenterOwner {
    @LayoutRes
    protected final int fragmentLayout = getFragmentLayout();

    protected View rootView;

    protected P presenter;

    @LayoutRes
    protected abstract int getFragmentLayout();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getPresenterClass() != null) {
            presenter = AppInjector.getPresenterProvider().getPresenter(this, getPresenterClass());
        }
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
        if (presenter != null) {
            presenter.attach(this);
        }
        init();
        findViews(rootView);
        initViews();
    }

    protected abstract void findViews(View root);

    protected abstract void initViews();

    protected void init() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) {
            presenter.detach();
        }
    }

    @Override
    public boolean isFinalDestroy() {
        if (getActivity() == null) {
            return true;
        }
        return !getActivity().isChangingConfigurations();
    }

    @Nullable
    protected abstract Class<P> getPresenterClass();
}
