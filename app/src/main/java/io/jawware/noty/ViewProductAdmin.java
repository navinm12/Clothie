package io.jawware.noty;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewProductAdmin extends AppCompatActivity {

    DatabaseReference mDatabase;

    private RecyclerViewAdapterUser adapter;
    private ArrayList<Listdata> infolist;
    private RecyclerView recyclerView;
    private Context mContext;
    private String CatogType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product_admin);

        mContext = getApplicationContext();

        mDatabase = FirebaseDatabase.getInstance().getReference("products");


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                CatogType= null;
            } else {
                CatogType= extras.getString("key_type");
            }
        } else {
            CatogType= (String) savedInstanceState.getSerializable("category");
        }

        recyclerView = (RecyclerView) findViewById(R.id.product_list_admin);
        infolist = new ArrayList<>();
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setReverseLayout(true);
        llm.setStackFromEnd(true);
        recyclerView.setLayoutManager(llm);
        adapter = new RecyclerViewAdapterUser(infolist,mContext);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();



        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Listdata todo = snapshot.getValue(Listdata.class);
                    if (todo != null && todo.getCategory().equals(CatogType)) {
                        infolist.add(todo);
                    }

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
