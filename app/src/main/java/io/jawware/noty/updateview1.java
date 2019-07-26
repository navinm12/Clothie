package io.jawware.noty;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class updateview1 extends Fragment {
    public updateview1() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.updateview1, container, false);

//        Glide.with(getActivity()).load("https://archive.org/download/images_20180828/0.jpg").diskCacheStrategy(DiskCacheStrategy.ALL).crossFade().into(imageView);
        return rootView;
    }

}