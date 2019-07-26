package io.jawware.noty;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.w3c.dom.Text;

public class Login extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    private EditText editEmail,editPass;
    private Button loginCustom;
    private TextView redirectText;
    private SignInButton signInButton;
    private GoogleSignInClient googleSignInClient;
    private static final int RC_SIGN_IN = 234;
    private FirebaseAuth mAuth;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {

            //Getting the GoogleSignIn Task
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                //Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);

                //authenticating with firebase
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {

                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("OAuth", "firebaseAuthWithGoogle:" + acct.getId());

        //getting the auth credential
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        //Now using firebase we are signing in the user here
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("Oauth", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

//                            Toast.makeText(OnBoarding.this, "User Signed In", Toast.LENGTH_SHORT).show();
                            if(user!=null)
                            {
                                String email = user.getEmail();
                                String name[] = email.split("@");
                                if(name[1].equals("admin"))
                                {
                                    startActivity(new Intent(new Intent(getApplicationContext(),Home.class)));
                                }else{
                                    startActivity(new Intent(new Intent(getApplicationContext(),Dashboard.class)));
                                }
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Oauth", "signInWithCredential:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        editEmail = (EditText)findViewById(R.id.edit_email);
        editPass = (EditText)findViewById(R.id.edit_password);
        loginCustom = (Button) findViewById(R.id.login);
        signInButton = findViewById(R.id.sign_in_button);




        TextView registerRedirect = (TextView) findViewById(R.id.register_label);
        registerRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(new Intent(getApplicationContext(),RegisterCustom.class)));
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("895043485073-tkvljcmju7m624c8ke7d0jn0gdedhqi0.apps.googleusercontent.com")
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);


        loginCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = editEmail.getText().toString();
                String password = editPass.getText().toString();

                if(email.isEmpty() && password.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Fields are empty", Toast.LENGTH_SHORT).show();
                }else{
                    if(password.length()>=6)
                    {
                        customLogin(email,password);
                    }else{
                        Toast.makeText(getApplicationContext(), "Password must be atleast 6 characters", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = googleSignInClient.getSignInIntent();
                startActivityForResult(intent,RC_SIGN_IN);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null)
        {
            String email = user.getEmail();
            String name[] = email.split("@");
            if(name[0].equals("admin"))
            {
                startActivity(new Intent(new Intent(getApplicationContext(),Home.class)));
            }else{
                startActivity(new Intent(new Intent(getApplicationContext(),Dashboard.class)));
            }
        }

    }

    //  custom authentication
    public void customLogin(String mail, String pass)
    {
        mAuth.signInWithEmailAndPassword(mail,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(Login.this, "Logged In", Toast.LENGTH_SHORT).show();
                FirebaseUser user =  mAuth.getCurrentUser();
            if(user!=null)
            {
                String email = user.getEmail();
                String name[] = email.split("@");
                if(name[0].equals("admin"))
                {
                    startActivity(new Intent(new Intent(getApplicationContext(),Home.class)));
                }else{
                    startActivity(new Intent(new Intent(getApplicationContext(),Dashboard.class)));
                }
            }

//                startActivity(new Intent(new Intent(getApplicationContext(),Home.class)));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Login.this, "Error "+e, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
