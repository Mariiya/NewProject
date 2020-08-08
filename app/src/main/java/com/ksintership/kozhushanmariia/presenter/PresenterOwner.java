package com.ksintership.kozhushanmariia.presenter;

import androidx.lifecycle.Lifecycle;

public interface PresenterOwner {
    Lifecycle getLifecycle();

    /**
     * @return true if view destroy by user transition, false if changed configurations
     */
    boolean isFinalDestroy();
}
