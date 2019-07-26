package io.jawware.noty;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class userProfile extends Fragment {

    private EditText editUsername,editUserAddress,editUserMobile;
    private TextView emailUser,idUser;
    private Button saveProfile;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    public userProfile(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_user_profile, container, false);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("profile");

        FirebaseUser user = mAuth.getCurrentUser();


        editUsername = (EditText) rootView.findViewById(R.id.user_name);
        editUserAddress = (EditText) rootView.findViewById(R.id.user_address);
        editUserMobile = (EditText) rootView.findViewById(R.id.user_mobile);
        emailUser = (TextView) rootView.findViewById(R.id.user_email);
        idUser = (TextView) rootView.findViewById(R.id.user_id);
        saveProfile = (Button) rootView.findViewById(R.id.save_profile);
        if(user!=null)
        {
            emailUser.setText(user.getEmail());
            idUser.setText(user.getUid());
        }
        saveProfile.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                String email = emailUser.getText().toString();
                String id = idUser.getText().toString();
                String address = editUserAddress.getText().toString();
                String mob = editUserMobile.getText().toString();
                String name = editUsername.getText().toString();

                mDatabase.child(id).child("username").setValue(name);
                mDatabase.child(id).child("useremail").setValue(email);
                mDatabase.child(id).child("usermob").setValue(mob);
                mDatabase.child(id).child("useraddress").setValue(address);

                Toast.makeText(getContext(), "Profile Updated", Toast.LENGTH_SHORT).show();


            }
        });


        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
}
