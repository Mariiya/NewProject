package com.ksintership.kozhushanmariia.utils.listeners;

import android.content.Intent;

import com.ksintership.kozhushanmariia.activity.ThirdActivity;
import com.ksintership.kozhushanmariia.app.Genre;
import com.ksintership.kozhushanmariia.fragments.FragmentChooser;

public interface GenreSelectListener {

    void onCOMEDYSelected();
    void onDOCUMENTARYSelected();
    void onDRAMASelected();
    void onADVENTURESelected();
    void onTHRILLERSelected();
    void onCARTOONSelected();
    void onDETECTIVESelected();
    void onFANRASYSelected();
    void openThirdActivity();
}
