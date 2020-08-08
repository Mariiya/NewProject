package com.ksintership.kozhushanmariia.presenter;

import androidx.annotation.Nullable;

public interface PresenterStore {

    @Nullable
    <P extends Presenter> P getPresenter(PresenterOwner owner, Class<P> presenterClass);

    <P extends Presenter> void putPresenter(Class<P> presenterClass, PresenterOwner owner, Presenter presenter);

}
