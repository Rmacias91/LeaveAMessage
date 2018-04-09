package com.example.richie.leaveamessage.main.Network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Richie on 4/9/2018.
 */

public class MessageAPI {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://Default-Environment.dber9pmkbe.us-east-2.elasticbeanstalk.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    MessageAPIService service = retrofit.create(MessageAPIService.class);
}
