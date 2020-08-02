package com.ksintership.kozhushanmariia.utils;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.ksintership.kozhushanmariia.R;

public class ViewUtil {

    public static long DEFAULT_ANIMATION_DURATION = 200L;

    public static void showSnackbar(View view, int resId) {
        Snackbar.make(view, resId, Snackbar.LENGTH_SHORT).show();
    }

    public static void showSnackbar(View view, String text) {
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show();
    }

    public static void showKeyboard(EditText view) {
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        ((InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE))
                .showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    public static void hideKeyboard(View view) {
        ((InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void loadImage(@NonNull ImageView target,
                                 @NonNull String imgUrl,
                                 @Nullable @DrawableRes Integer placeholder) {
        Glide.with(target.getContext())
                .load(imgUrl)
                .placeholder(placeholder == null ? R.drawable.ic_image_24 : placeholder)
                .into(target);
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
