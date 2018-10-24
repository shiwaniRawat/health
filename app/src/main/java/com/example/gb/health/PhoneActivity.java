package com.example.gb.health;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneActivity extends AppCompatActivity {

    EditText editTextPhone,editTextCode;
    FirebaseAuth mAuth;
    String codeSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_phone );

        mAuth=FirebaseAuth.getInstance();
        editTextPhone=findViewById( R.id.editText_Phone );
        editTextCode=findViewById( R.id.edittextcode );

        findViewById( R.id.btn_get_verification_code ).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendVerificationCode();
                //openActivity();

            }
        } );

        findViewById( R.id.btn_verification ).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                verifySignInCode();

            }
        } );
    }

    //private void openActivity() {
       // Intent intent=new Intent( PhoneActivity.this,Map.class );
     //   startActivity( intent );
   // }

    private void verifySignInCode()
        {

            String code=editTextCode.getText().toString();
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSend, code);
            signInWithPhoneAuthCredential( credential );
        }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //here new activity
                           // startActivity( new Intent( PhoneActivity.this,Map.class ) );
                           Toast.makeText( getApplicationContext(),"ur number is verified",Toast.LENGTH_LONG )
                                   .show();
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText( getApplicationContext(),"Enter correct code",Toast.LENGTH_LONG )
                                        .show();
                            }

                        }
                    }
                });
    }
    private void sendVerificationCode() {

        String phoneNumber=editTextPhone.getText().toString();

        if(phoneNumber.isEmpty()){
            editTextPhone.setError( "Phone number is required" );
            editTextPhone.requestFocus();
            return;
        }


        if(phoneNumber.length()<=10)
        {
            editTextPhone.setError( "Enter valid Phone" );
            editTextPhone.requestFocus();
            return;
        }

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks

    }


PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
    @Override
    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

    }

    @Override
    public void onVerificationFailed(FirebaseException e) {

    }

    @Override
    public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
        super.onCodeSent( s, forceResendingToken );
        codeSend=s;


    }
};


}
