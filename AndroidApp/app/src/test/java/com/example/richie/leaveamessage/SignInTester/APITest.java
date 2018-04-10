package com.example.richie.leaveamessage.SignInTester;

import com.example.richie.leaveamessage.main.Network.MessageAPI;
import com.example.richie.leaveamessage.main.models.Message;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by Richie on 4/10/2018.
 */

public class APITest {
   //Aparently I need to use Mockito to Test Callback Answers here.
    MessageAPI messageAPI;
    @Before
    public void setup(){
        messageAPI = new MessageAPI();
    }

    @Test
    public void getMessages(){
         messageAPI.getMessages();

    }

}
