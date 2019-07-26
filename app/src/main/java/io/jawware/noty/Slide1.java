package io.jawware.noty;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Slide1 extends Fragment {
    public Slide1() {
        // Required empty public constructor
    }
    public static Slide1 newInstance(String param1, String param2) {
        Slide1 fragment = new Slide1();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_slide1, container, false);
    }


}

