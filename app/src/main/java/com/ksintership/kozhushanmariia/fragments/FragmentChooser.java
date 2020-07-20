package com.ksintership.kozhushanmariia.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.ksintership.kozhushanmariia.utils.PictureSelectListener;

import com.ksintership.kozhushanmariia.R;

public class FragmentChooser extends Fragment {

    private PictureSelectListener pictureSelectListener;

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
        return v;
    }



}