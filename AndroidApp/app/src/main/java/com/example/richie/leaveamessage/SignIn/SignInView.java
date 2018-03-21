package com.example.richie.leaveamessage.SignIn;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.example.richie.leaveamessage.R;
import com.google.android.gms.auth.api.signin.*;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.Arrays;


/**
 * Created by Richie on 3/16/2018.
 *
 * SignInView
 */

public class SignInView extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = SignInView.class.getSimpleName();
    private static final String EMAIL = "email";
    private GoogleSignInClient mGoogleSignInClient;
    private int RC_SIGN_IN_GOOGLE = 23;
    private CallbackManager mCallbackManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_layout);
        SignInButton signInButtonGoogle = findViewById(R.id.google_sign_in_button);
        signInButtonGoogle.setSize(SignInButton.SIZE_STANDARD);
        signInButtonGoogle.setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);

        //Facebook
        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButtonFb = findViewById(R.id.fb_sign_in_button);
        loginButtonFb.setReadPermissions(Arrays.asList(EMAIL));

        loginButtonFb.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getApplicationContext(), "Logged in Baby!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), "Error :(", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//        if(account != null){
//            Toast.makeText(this,"Already Signed in", Toast.LENGTH_SHORT).show();
//        }
//
//        Use this to check for FB
//        boolean loggedIn = AccessToken.getCurrentAccessToken() == null;
//
//        TODO update ui if user is already signed in.
//        Maybe take out button and go to main activity.
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

    private void singInFb() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
        }

    private void signInGoogle(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,RC_SIGN_IN_GOOGLE );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN_GOOGLE) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
        else{
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            Toast.makeText(this,"SIGNED IN BABY!", Toast.LENGTH_LONG).show();
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(this,"Failed to Sign In.", Toast.LENGTH_LONG).show();
        }

    }
}
