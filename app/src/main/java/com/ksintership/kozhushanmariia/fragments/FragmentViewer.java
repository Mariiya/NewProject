package com.ksintership.kozhushanmariia.fragments;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.ksintership.kozhushanmariia.R;


public class FragmentViewer extends Fragment {

    private AppCompatImageView imageView;
    private AppCompatTextView nameInput;
    private AppCompatTextView yearInput;
    private AppCompatTextView genreInput;
    private AppCompatTextView descriptionInput;
    private AppCompatTextView ratingInput;

    public FragmentViewer() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_viewer, container, false);
        imageView = v.findViewById(R.id.image_view);
        nameInput = v.findViewById(R.id.input_name);
        genreInput = v.findViewById(R.id.input_genre);
        yearInput = v.findViewById(R.id.input_year);
        ratingInput = v.findViewById(R.id.input_rating);
        descriptionInput = v.findViewById(R.id.input_description);
        return v;
    }

    public void displayResource(int resId, String name, String genre, double rating, int year, String description) {
        nameInput.setText(name);
        genreInput.setText(genre);
        yearInput.setText(String.valueOf(year));
        ratingInput.setText(String.valueOf(rating));
        descriptionInput.setText(description);
        imageView.setImageResource(resId);
        descriptionInput.setMovementMethod(new ScrollingMovementMethod());
    }


}

