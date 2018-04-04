package com.example.richie.leaveamessage.main.UI.SignIn;

import android.content.Intent;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.*;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

/**
 * Created by Richie on 3/16/2018.
 */

public class SignInPresenter implements SignInContract.PresenterSignIn{
    private static final String TAG = SignInPresenter.class.getSimpleName();
    private CallbackManager mCallbackManager;
    private SignInContract.ViewSignIn signInView;
    public static int RC_SIGN_IN_GOOGLE = 23;

    public SignInPresenter(final SignInContract.ViewSignIn signinView){
        mCallbackManager = CallbackManager.Factory.create();
        signInView = signinView;


        LoginManager.getInstance().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        signInView.showMessage("Logged in!");
                        signinView.startActivity();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        signInView.showMessage("Failed to Log in");
                    }
                });
    }

    @Override
    public void onStart(GoogleSignInAccount account) {
        if(account != null){
            signInView.startActivity();
            signInView.showMessage("Logged into Google");
        }

        boolean loggedIn = AccessToken.getCurrentAccessToken() != null;
        if(loggedIn){
            signInView.startActivity();
            signInView.showMessage("Logged into Facebook");
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN_GOOGLE) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = com.google.android.gms.auth.api.signin.GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
        else{
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            signInView.showMessage("Logged in!");

            // Signed in successfully, show authenticated user interface

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            signInView.showMessage("Failed to Log In");

        }

    }
}
