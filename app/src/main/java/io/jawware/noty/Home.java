package io.jawware.noty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends AppCompatActivity {

    FirebaseAuth mAuth;

    CardView ad1,ad2,ad3,ad4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button logOut = (Button) findViewById(R.id.logout);

        mAuth = FirebaseAuth.getInstance();

        ad1 = (CardView) findViewById(R.id.add_product);
        ad2 = (CardView) findViewById(R.id.view_product);
        ad3 = (CardView) findViewById(R.id.orders);
        ad4 = (CardView) findViewById(R.id.order_complete);

        ad1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AddProduct.class));
            }
        });


        ad2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ViewProduct.class));
            }
        });




        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null)
        {

        }else{
            startActivity(new Intent(new Intent(getApplicationContext(),Login.class)));
        }

    }
}
