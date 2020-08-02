package com.ksintership.kozhushanmariia.contract.listeners;

import android.animation.Animator;

public abstract class AnimationCompleteListener implements Animator.AnimatorListener {
    @Override
    final public void onAnimationStart(Animator animator) {
    }

    @Override
    public abstract void onAnimationEnd(Animator animator);

    @Override
    final public void onAnimationCancel(Animator animator) {
    }

    @Override
    final public void onAnimationRepeat(Animator animator) {
    }
}
