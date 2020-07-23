package com.ksintership.kozhushanmariia.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.ksintership.kozhushanmariia.R;

public class FragmentCatalog extends Fragment {



        public FragmentCatalog() {
            // Required empty public constructor
        }

        public static FragmentCatalog newInstance() {
            FragmentCatalog fragment = new  FragmentCatalog();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (getArguments() != null) {

            }

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            return inflater.inflate(R.layout.fragment_catalog, container, false);
        }
    }

