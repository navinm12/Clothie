package io.jawware.noty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Gracias extends AppCompatActivity {

    private DatabaseReference mDat1,mDat2;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gracias);

        mAuth = FirebaseAuth.getInstance();

        mDat1 = FirebaseDatabase.getInstance().getReference("orders");
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null)
        {
            mDat1.child(user.getUid()).setValue(null);
        }


        Button continueShop = (Button) findViewById(R.id.con);

        continueShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Dashboard.class));
            }
        });

    }
}
