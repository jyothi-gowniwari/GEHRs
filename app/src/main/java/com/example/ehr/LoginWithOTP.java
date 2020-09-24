package com.example.ehr;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.mukesh.OtpView;

import java.util.concurrent.TimeUnit;

public class LoginWithOTP extends Activity {

    OtpView pinFromUser;
    String codeBySystem;
    TextView verify_code;
    private boolean otpsuccess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_with_otp);

        pinFromUser=findViewById(R.id.pin_view);
        verify_code=findViewById(R.id.verify_code);

        String _phoneNo=getIntent().getStringExtra("phoneNo");
        verify_code.setText(verify_code.getText()+_phoneNo);

        sendVerficationCodeToUser(_phoneNo);

    }

    private void sendVerficationCodeToUser(String phoneNo) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNo,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                TaskExecutors.MAIN_THREAD,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    codeBySystem=s;
                }

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    String code =phoneAuthCredential.getSmsCode();
                    if(code!=null){
                        pinFromUser.setText(code);
                        verifyCode(code);
                    }

                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {

                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
            };

            private void verifyCode(String code) {

                PhoneAuthCredential credential=PhoneAuthProvider.getCredential(codeBySystem,code);
                signInWithPhoneAuthCredential(credential);

            }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            otpsuccess=true;
                            Toast.makeText(getApplicationContext(),"Verification Completed!",Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(getApplicationContext(),RegistrationActivity.class);
                            intent.putExtra("otpsuccess",otpsuccess);
                            startActivity(intent);

                        } else {

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                                Toast.makeText(getApplicationContext(),"Verification Not completed! Try Again",Toast.LENGTH_LONG).show();
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

            public void callNextScreenFromOTP(View view){

                String code=pinFromUser.getText().toString();
                if(!code.isEmpty()){
                    verifyCode(code);
                }

            }


}
