package com.ksintership.kozhushanmariia.di;

import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;

import com.ksintership.kozhushanmariia.presenter.Presenter;
import com.ksintership.kozhushanmariia.presenter.PresenterOwner;
import com.ksintership.kozhushanmariia.presenter.PresenterStore;
import com.ksintership.kozhushanmariia.presenter.SearchHistoryContract;
import com.ksintership.kozhushanmariia.presenter.SearchHistoryPresenter;
import com.ksintership.kozhushanmariia.presenter.SearchListContract;
import com.ksintership.kozhushanmariia.presenter.SearchListPresenter;
import com.ksintership.kozhushanmariia.presenter.TrackDetailContract;
import com.ksintership.kozhushanmariia.presenter.TrackDetailPresenter;

public class PresenterProviderImpl implements PresenterProvider {

    private final PresenterStore presenterStore;

    PresenterProviderImpl(PresenterStore presenterStore) {
        this.presenterStore = presenterStore;
    }

    @Override
    public <P extends Presenter> P getPresenter(PresenterOwner presenterOwner, Class<P> presenterClass) {
        P presenter = presenterStore.getPresenter(presenterOwner, presenterClass);
        if (presenter == null) {
            presenter = providePresenterImplementation(presenterClass);
            if (presenter == null) {
                throw new IllegalArgumentException("Cannot find implementation for " + presenterClass.getSimpleName()
                        + ". Add implementation in PresenterProvider#providePresenterImplementation");
            }
            presenterStore.putPresenter(presenterClass, presenterOwner, presenter);
        }
        return presenter;
    }

    @Nullable
    private <P extends Presenter> P providePresenterImplementation(Class<P> presenterClass) {
        if (SearchHistoryContract.Presenter.class.equals(presenterClass)) {
            return (P) new SearchHistoryPresenter();
        } else if (SearchListContract.Presenter.class.equals(presenterClass)) {
            return (P) new SearchListPresenter();
        } else if (TrackDetailContract.Presenter.class.equals(presenterClass)) {
            return (P) new TrackDetailPresenter();
        }
        return null;
    }


}
