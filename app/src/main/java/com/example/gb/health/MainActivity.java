package com.example.gb.health;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
  //  FirebaseDatabase database;
  //  DatabaseRefferance myref;
    private FirebaseAuth mAuth;
   private FirebaseAuth.AuthStateListener authStateListener;

   private EditText emailText;
   private EditText passwordText;
   private Button logIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );


        emailText=(EditText) findViewById( R.id.user_email );
        passwordText=(EditText) findViewById( R.id.user_password );
        mAuth=FirebaseAuth.getInstance();

        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser()!=null)
                {
                    Intent intent=new Intent( MainActivity.this,PhoneActivity.class );
                    startActivity( intent );
                }

            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener( authStateListener );
    }

    public void loginButton(View view)
    {
        String email =emailText.getText().toString();
        String password=passwordText.getText().toString();

        if(TextUtils.isEmpty( email )||TextUtils.isEmpty( password ))
        {
            Toast.makeText( this,"Enter valid value",Toast.LENGTH_LONG ).show();
        }
        else
        {
            mAuth.signInWithEmailAndPassword( email,password ).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        Toast.makeText( MainActivity.this,"Incorrect user name and password",Toast.LENGTH_LONG )
                                .show();
                    }

                }
            } );
        }
    }
}
