package io.jawware.noty;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Slide2 extends Fragment {
    public Slide2() {
        // Required empty public constructor
    }
    public static Slide2 newInstance(String param1, String param2) {
        Slide2 fragment = new Slide2();
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
        return inflater.inflate(R.layout.fragment_slide2, container, false);
    }


}
