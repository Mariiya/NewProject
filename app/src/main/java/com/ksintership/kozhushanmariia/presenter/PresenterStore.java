package com.ksintership.kozhushanmariia.presenter;

import androidx.annotation.Nullable;

public interface PresenterStore {

    @Nullable
    <P extends Presenter> P getPresenter(Class<P> presenterClass);

    void removePresenter(Class presenter);

    void putPresenter(Presenter presenter);

}
