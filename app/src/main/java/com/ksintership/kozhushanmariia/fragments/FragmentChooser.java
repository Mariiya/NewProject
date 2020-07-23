package com.ksintership.kozhushanmariia.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;


import com.ksintership.kozhushanmariia.R;
import com.ksintership.kozhushanmariia.utils.listeners.GenreSelectListener;

public class FragmentChooser extends Fragment {

    private GenreSelectListener genreSelectListener;

    private AppCompatButton comedyButton;
    private AppCompatButton documentaryButton;
    private AppCompatButton dramaButton;
    private AppCompatButton adventureButton;
    private AppCompatButton thrillerButton;
    private AppCompatButton cartoonButton;
    private AppCompatButton detectiveButton;
    private AppCompatButton fantasyButton;
    private AppCompatButton favoritesButton;

    public FragmentChooser() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_chooser, container, false);


        comedyButton = v.findViewById(R.id.btn_comedy);
        documentaryButton = v.findViewById(R.id.btn_documentary);
        dramaButton = v.findViewById(R.id.btn_drama);
        adventureButton = v.findViewById(R.id.btn_adventure);
        thrillerButton = v.findViewById(R.id.btn_thriller);
        cartoonButton = v.findViewById(R.id.btn_cartoon);
        detectiveButton = v.findViewById(R.id.btn_detective);
        fantasyButton = v.findViewById(R.id.btn_fantasy);
favoritesButton=v.findViewById(R.id.favorites_btn);

        comedyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (genreSelectListener != null) {
                    genreSelectListener.onCOMEDYSelected();
                }
            }
        });

        documentaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (genreSelectListener != null) {
                    genreSelectListener.onDOCUMENTARYSelected();
                }
            }
        });

        dramaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (genreSelectListener != null) {
                    genreSelectListener.onDRAMASelected();
                }
            }
        });

        adventureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (genreSelectListener != null) {
                    genreSelectListener.onADVENTURESelected();
                }
            }
        });

        thrillerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (genreSelectListener != null) {
                    genreSelectListener.onTHRILLERSelected();
                }
            }
        });

        detectiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (genreSelectListener != null) {
                    genreSelectListener.onDETECTIVESelected();
                }
            }
        });
        cartoonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (genreSelectListener != null) {
                    genreSelectListener.onCARTOONSelected();
                }
            }
        });

        fantasyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (genreSelectListener != null) {
                    genreSelectListener.onFANRASYSelected();
                }
            }
        });

        favoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genreSelectListener.openThirdActivity();
            }
        });
        return v;
    }

    public void setPictureSelectListener(GenreSelectListener listener) {
        this.genreSelectListener = listener;
    }

}