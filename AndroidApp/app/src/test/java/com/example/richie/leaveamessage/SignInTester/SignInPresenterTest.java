package com.example.richie.leaveamessage.SignInTester;

import com.example.richie.leaveamessage.main.UI.SignIn.SignInContract;
import com.example.richie.leaveamessage.main.UI.SignIn.SignInPresenter;


import org.junit.Before;
import org.mockito.Mockito;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SignInPresenterTest {
    private SignInPresenter signInPresenter;
    private SignInContract.ViewSignIn viewSignIn;


    @Before
    public void setup(){
    viewSignIn = Mockito.mock(SignInContract.ViewSignIn.class);
    signInPresenter = new SignInPresenter(viewSignIn);
    }


//TODO Facebook SDK is not being init. Not able to test using Junit.
//    @Test(expected = ApiException.class)
//    public void errorLoggingInGoogle(){
//        Intent emptyData = new Intent();
//        signInPresenter.onActivityResult(SignInPresenter.RC_SIGN_IN_GOOGLE,RESULT_OK,emptyData);
//       //Mockito.verify(viewSignIn, times(1)).showMessage("Already Signed in with Google!");
//    }

//    Can't Init Facebook to test Need context so, i need to refactor MVP more if I wanna test or
//    Run on android test
//    @Test
//    public void errorLoggingInFacebook(){
//        Intent emptyData = new Intent();
//        //random request Code != RC_Google so facebook CallManager is called.
//        signInPresenter.onActivityResult(9,RESULT_OK,emptyData);
//        Mockito.verify(viewSignIn, times(1)).showMessage("Failed to Log in");
//    }


}