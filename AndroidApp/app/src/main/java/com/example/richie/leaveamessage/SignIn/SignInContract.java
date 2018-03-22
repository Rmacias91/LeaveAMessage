package com.example.richie.leaveamessage.SignIn;

import android.content.Intent;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

/**
 * Created by Richie on 3/16/2018.
 */

public interface SignInContract {

    public interface ModelSignIn{

    }

    public interface PresenterSignIn{

        void onStart(GoogleSignInAccount account);

        void onActivityResult(int requestCode, int resultCode, Intent data);
    }

    public interface ViewSignIn{

        void showMessage(String message);


    }
}
