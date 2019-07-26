package io.jawware.noty;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyOrders extends Fragment {

    public MyOrders(){}

    private Context mContext;
    private RecyclerViewAdapterOrders adapter;
    private ArrayList<OrderModal> orderist;
    private RecyclerView recyclerView;
    private TextView totalAmount;
    private ArrayList<Integer> total;
    private int amount=0;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private Button paymentGo;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_my_orders, container, false);

        totalAmount = (TextView) rootView.findViewById(R.id.totalamount);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.order_list);
        paymentGo = (Button) rootView.findViewById(R.id.select_paay);
        orderist = new ArrayList<>();
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setReverseLayout(true);
        llm.setStackFromEnd(true);
        recyclerView.setLayoutManager(llm);
        adapter = new RecyclerViewAdapterOrders(orderist,mContext);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        total = new ArrayList<>();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        OrderModal go = snapshot.getValue(OrderModal.class);
                        orderist.add(go);
                }

                for(int i=0;i<orderist.size();i++)
                {
                    total.add(Integer.parseInt(orderist.get(i).getProductprize()));
                }

                for(Integer d : total)
                    amount += d;

                totalAmount.setText(String.valueOf(amount));

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if(totalAmount.getText().toString().equals("0"))
        {
            paymentGo.setClickable(false);
        }else {
            paymentGo.setClickable(true);
        }

        paymentGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(amount!=0) {
                    Intent i = new Intent(getContext(), PaymentDash.class);
                    i.putExtra("key_amount", totalAmount.getText().toString());
                    startActivity(i);
                }
            }
        });

                return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("orders").child(mAuth.getCurrentUser().getUid());

    }


}
