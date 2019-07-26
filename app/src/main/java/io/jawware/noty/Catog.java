package io.jawware.noty;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Catog extends Fragment {

    public Catog(){}
    private Context mContext;
    private CardView cat01,cat02,cat03,cat04;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_category, container, false);

        cat01 = (CardView) rootView.findViewById(R.id.cat1_f);
        cat02 = (CardView) rootView.findViewById(R.id.cat2_f);
        cat03 = (CardView) rootView.findViewById(R.id.cat3_f);
        cat04 = (CardView) rootView.findViewById(R.id.cat4_f);

        cat01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, ViewProduct.class);
                String strName = "Top Wear";
                i.putExtra("key_type", strName);
                startActivity(i);
            }
        });

        cat02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, ViewProduct.class);
                String strName = "Bottom Wear";
                i.putExtra("key_type", strName);
                startActivity(i);
            }
        });

        cat03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, ViewProduct.class);
                String strName = "Accessories";
                i.putExtra("key_type", strName);
                startActivity(i);
            }
        });

        cat04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, ViewProduct.class);
                String strName = "Foot Wear";
                i.putExtra("key_type", strName);
                startActivity(i);
            }
        });



        return rootView;
    }
}
