package com.ksintership.kozhushanmariia.utils;

import android.content.res.Resources;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class ViewUtil {

    public static long DEFAULT_ANIMATION_DURATION = 200L;

    public static void showSnackbar(View view, int resId) {
        Snackbar.make(view, resId, Snackbar.LENGTH_SHORT).show();
    }

    public static void showSnackbar(View view, String text) {
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show();
    }

    public static void animateShow(View view) {
        if (view.getVisibility() == View.VISIBLE) return;
        view.setAlpha(0f);
        view.setVisibility(View.VISIBLE);
        view.animate()
                .alpha(1f)
                .setDuration(DEFAULT_ANIMATION_DURATION)
                .start();
    }

    public static void animateHide(View view) {
        if (view.getVisibility() != View.VISIBLE) return;
        view.setAlpha(1f);
        view.animate()
                .alpha(0f)
                .setDuration(DEFAULT_ANIMATION_DURATION)
                .withEndAction(() -> view.setVisibility(View.INVISIBLE))
                .start();
    }

    public static int dpToPx(float dp) {
        return Math.round(dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
