package com.ksintership.kozhushanmariia.contract.listeners;

import android.animation.Animator;

public interface AnimationCompleteListener extends Animator.AnimatorListener {
    @Override
    default void onAnimationStart(Animator animator) {
    }

    @Override
    void onAnimationEnd(Animator animator);

    @Override
    default void onAnimationCancel(Animator animator) {
    }

    @Override
    default void onAnimationRepeat(Animator animator) {
    }
}
