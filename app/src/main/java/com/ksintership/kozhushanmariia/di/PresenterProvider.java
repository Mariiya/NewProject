package com.ksintership.kozhushanmariia.di;

import com.ksintership.kozhushanmariia.presenter.Presenter;
import com.ksintership.kozhushanmariia.presenter.PresenterOwner;

import kotlin.jvm.Throws;

public interface PresenterProvider {
    /**
     * Provides presenter implementation
     *
     * @param presenterOwner owner of presenter by his lifecycle presenter will be exist
     * @param presenterClass class of presenter that extends {@link Presenter}
     * @return instance of presenter implementation for {@param presenterClass}
     * @throws IllegalArgumentException if presenter implementation does not exist
     */
    @Throws(exceptionClasses = IllegalArgumentException.class)
    <P extends Presenter> P getPresenter(PresenterOwner presenterOwner, Class<P> presenterClass);

}
