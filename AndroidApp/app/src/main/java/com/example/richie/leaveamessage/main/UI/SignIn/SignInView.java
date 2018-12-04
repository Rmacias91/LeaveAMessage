package com.example.richie.leaveamessage.main.UI.SignIn;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;


import com.example.richie.leaveamessage.main.UI.MapGoogle.MapView;
import com.facebook.login.LoginManager;

import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.example.richie.leaveamessage.R;
import com.google.android.gms.auth.api.signin.*;
import com.google.android.gms.common.SignInButton;

import java.util.Arrays;


/**
 * Created by Richie on 3/16/2018.
 *
 * SignInView
 */

public class SignInView extends AppCompatActivity implements View.OnClickListener, SignInContract.ViewSignIn{
    private static final String EMAIL = "email";
    private GoogleSignInClient mGoogleSignInClient;
    private SignInPresenter signInPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_layout);
        setUp();
    }

    private void setUp(){
        //Google
        SignInButton signInButtonGoogle = findViewById(R.id.google_sign_in_button);
        signInButtonGoogle.setSize(SignInButton.SIZE_STANDARD);
        signInButtonGoogle.setOnClickListener(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build();
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);

        //Facebook
        LoginButton loginButtonFb = findViewById(R.id.fb_sign_in_button);
        loginButtonFb.setReadPermissions(Arrays.asList(EMAIL));

        //Presenter
        signInPresenter = new SignInPresenter(this,getContentResolver());
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        signInPresenter.onStart(account);
    }


    private void singInFb() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
    }

    private void signInGoogle(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,SignInPresenter.RC_SIGN_IN_GOOGLE );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        signInPresenter.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.google_sign_in_button:
                signInGoogle();
                break;
            case R.id.fb_sign_in_button:
                singInFb();
                break;
        }
    }

    //Override view Implementations
    @Override
    public void showMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startActivity() {
        Intent intent = new Intent(this, MapView.class);
        startActivity(intent);
        finish();
    }
}
