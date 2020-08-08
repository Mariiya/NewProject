package com.ksintership.kozhushanmariia.presenter;

import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;

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

    private final HashMap<String, PresenterOwner> associates = new HashMap<>();

    private final Handler handler = new Handler();

    private final HashMap<String, Runnable> removersInSleep = new HashMap<>();

    @Nullable
    @Override
    public <P extends Presenter> P getPresenter(PresenterOwner owner, Class<P> presenterClass) {
        subscribeOnLifecycle(owner, presenterClass.getCanonicalName());
        String presenterKey = presenterClass.getCanonicalName();
        associates.put(presenterKey, owner);
        Runnable remover;
        if ((remover = removersInSleep.get(presenterKey)) != null) {
            handler.removeCallbacks(remover);
        }
        return (P) presenters.get(presenterKey);
    }

    private void removePresenter(String presenterKey) {
        Presenter presenter = presenters.get(presenterKey);
        if (presenter != null) {
            Runnable remover = () -> {
                if (associates.get(presenterKey) == null ||
                        associates.get(presenterKey).getLifecycle().getCurrentState() == Lifecycle.State.DESTROYED ||
                        associates.get(presenterKey).getLifecycle().getCurrentState() == Lifecycle.State.INITIALIZED) {
                    presenter.clear();
                    presenters.remove(presenterKey);
                    removersInSleep.remove(presenterKey);
                }
            };
            removersInSleep.put(presenterKey, remover);
            handler.postDelayed(remover, DELAY_REMOVE_PRESENTER);
        }
    }

    @Override
    public <P extends Presenter> void putPresenter(Class<P> presenterClass, PresenterOwner owner, Presenter presenter) {
        associates.put(presenterClass.getCanonicalName(), owner);
        presenters.put(presenterClass.getCanonicalName(), presenter);
    }

    private void subscribeOnLifecycle(PresenterOwner owner, String presenterKey) {
        owner.getLifecycle().addObserver((LifecycleEventObserver) (source, event) -> {
            if (event == Lifecycle.Event.ON_DESTROY && owner.isFinalDestroy()) {
                removePresenter(presenterKey);
            }
        });
    }

}
