package com.ksintership.kozhushanmariia.presenter;

import android.os.Handler;

import androidx.annotation.Nullable;

import java.util.HashMap;

public class PresenterStoreImpl implements PresenterStore {

    private static final long DELAY_REMOVE_PRESENTER = 20000L;
    /**
     * Map of presenters
     *
     * @key canonical name of presenter interface
     * @value presenter implementation for his key
     */
    private final HashMap<String, Presenter> presenters = new HashMap<>();

    private final Handler handler = new Handler();

    private final HashMap<String, Runnable> removersInSleep = new HashMap<>();

    @Nullable
    @Override
    public <P extends Presenter> P getPresenter(Class<P> presenterClass) {
        String presenterKey = presenterClass.getCanonicalName();
        Runnable remover;
        if ((remover = removersInSleep.get(presenterKey)) != null) {
            handler.removeCallbacks(remover);
        }
        return (P) presenters.get(presenterKey);
    }

    @Override
    public void removePresenter(Class presenterClass) {
        String presenterKey = presenterClass.getCanonicalName();
        Presenter presenter = presenters.get(presenterKey);
        if (presenter != null) {
            Runnable remover = () -> {
                presenter.clear();
                presenters.remove(presenterKey);
                removersInSleep.remove(presenterKey);
            };
            removersInSleep.put(presenterKey, remover);
            handler.postDelayed(remover, DELAY_REMOVE_PRESENTER);
        }
    }

    @Override
    public void putPresenter(Presenter presenter) {
        presenters.put(presenter.getClass().getCanonicalName(), presenter);
    }

}
