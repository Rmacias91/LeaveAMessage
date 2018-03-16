package com.example.richie.leaveamessage.SignIn;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.example.richie.leaveamessage.R;

/**
 * Created by Richie on 3/16/2018.
 *
 * SignInView
 */

public class SignInView extends AppCompatActivity {
    private Button mGoogleSignInBut;
    private Button mFbSigInBut;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_layout);
        mGoogleSignInBut = findViewById(R.id.google_sign_but);
        mFbSigInBut = findViewById(R.id.fb_sign_but);


    }
}
