package io.jawware.noty;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProductBrief extends AppCompatActivity {

    private TextView pName,pDesc,pPrize,pDis;
    private Button cShop,addtobag,addReview;
    private ImageView largeImage;

    private ArrayList<ReviewModal> reviewList;
    private EditText editQuan;
    private FirebaseAuth mAuth;
    private ListView mListView;
    private DatabaseReference mDatabase,mDaat,mDaai,mRef;

    private String pid;
    private String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_brief);
        mAuth = FirebaseAuth.getInstance();
        reviewList = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference("orders");
        mDaat = FirebaseDatabase.getInstance().getReference("reviews");
        mDaai = FirebaseDatabase.getInstance().getReference("profile");

        mListView =(ListView) findViewById(R.id.review_list);
        pName = (TextView) findViewById(R.id.prdt_nom);
        addReview = (Button) findViewById(R.id.add_review);
        pDesc = (TextView) findViewById(R.id.productdescrip);
        pPrize = (TextView) findViewById(R.id.productpricetag);
        largeImage = (ImageView) findViewById(R.id.preview_image_large);
        pDis = (TextView) findViewById(R.id.prdt_disi);
        cShop = (Button) findViewById(R.id.cshop);
        addtobag= (Button) findViewById(R.id.addtobag);
        editQuan=(EditText)findViewById(R.id.edit_quan);

        if (getIntent().getExtras() != null) {
            Bundle extras = getIntent().getExtras();
            Listdata todo = (Listdata) extras.get("stuff");
            if (todo != null) {

                Glide.with(getApplicationContext()).load(todo.getImageURL()).into(largeImage);
                pName.setText(todo.getName());
                pDesc.setText(todo.getDesc());
                pPrize.setText(todo.getPrice());
                pDis.setText(todo.getDiscount());
                pid = todo.getPid();

                mRef = FirebaseDatabase.getInstance().getReference("reviews").child(pid);
                ReviewHandler reviewHandler= new ReviewHandler();
                reviewHandler.execute();
            }
        }

        mDaai.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                username = (String) dataSnapshot.child("username").getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        addReview.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(ProductBrief.this);
                dialog.setContentView(R.layout.review_dialog);

                final EditText editText = (EditText) dialog.findViewById(R.id.user_review);
                final RatingBar ratingBar = (RatingBar) dialog.findViewById(R.id.reviewBar);
                Button dialogButton = (Button) dialog.findViewById(R.id.upload_review);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String txt = editText.getText().toString();
                        String ratings = String.valueOf(ratingBar.getRating());
                        String reviewID = mDaat.push().getKey();
                        mDaat.child(pid).child(reviewID).child("username").setValue(username);
                        mDaat.child(pid).child(reviewID).child("ratings").setValue(ratings);
                        mDaat.child(pid).child(reviewID).child("reviews").setValue(txt);
                        mDaat.child(pid).child(reviewID).child("pid").setValue(pid);

                        dialog.dismiss();

                    }
                });
                dialog.show();
            }
        });

        cShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Dashboard.class));
            }
        });

        addtobag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(editQuan.getText().toString());
                if(quantity>=1)
                {
                    String userUId = mAuth.getCurrentUser().getUid();
                    String quans = String.valueOf(quantity);
                    mDatabase.child(userUId).child(pid).child("productname").setValue(pName.getText().toString());
                    mDatabase.child(userUId).child(pid).child("productprize").setValue(pPrize.getText().toString());
                    mDatabase.child(userUId).child(pid).child("productdiscount").setValue(pDis.getText().toString());
                    mDatabase.child(userUId).child(pid).child("productquan").setValue(quans);
                    mDatabase.child(userUId).child(pid).child("productid").setValue(pid);
                    Toast.makeText(ProductBrief.this, "Added to bag", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(ProductBrief.this, "Atleast select 1 product", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private class ReviewHandler extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {

            mRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot snapshot : dataSnapshot.getChildren())
                    {

                            ReviewModal doo = snapshot.getValue(ReviewModal.class);
                            reviewList.add(doo);


                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            CustomViewAdapter customAdapter = new CustomViewAdapter(getApplicationContext(),reviewList);
          mListView.setAdapter(customAdapter);

        }
    }
}
