package com.example.richie.leaveamessage.main.UI.MessageList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.richie.leaveamessage.R;
import com.example.richie.leaveamessage.main.Network.MessageAPI;
import com.example.richie.leaveamessage.main.models.Message;
import com.example.richie.leaveamessage.main.UI.ReadMessage.ReadMessageView;
import com.example.richie.leaveamessage.main.UI.SignIn.SignInView;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Richie on 3/22/2018.
 */

public class ListView extends AppCompatActivity implements ListViewContract.ViewList,ListViewAdapter.ListItemClickListener {
    RecyclerView mRecyclerView;
    private static final String TAG = ListView.class.getSimpleName();
    private RecyclerView.LayoutManager mLayoutManager;
    private ListViewAdapter mAdapter;
    //Not Sure If I should have a list of data here. Not Logic so I think its okay
    private List<Message> mMessages;
    private ListViewPresenter mPresneter;
    private GoogleSignInClient mGoogleSignInClient;

    private MessageAPI messageAPI;




    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_layout);
        mMessages = new ArrayList<>();
        mPresneter = new ListViewPresenter(this);

        mRecyclerView = findViewById(R.id.recycle_list);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ListViewAdapter(this,mMessages);
        mRecyclerView.setAdapter(mAdapter);
        mPresneter.getData();

        //Used for Sign out.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build();
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);

         messageAPI= new MessageAPI();
         List<Message> onlineMessages = messageAPI.getMessages();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_setting_list,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.map_view:
                onBackPressed();
                return true;
            case R.id.sign_out:
                signOut();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Intent intent = new Intent(this, ReadMessageView.class);
        intent.putExtra(ReadMessageView.POSITION_EXTRA,clickedItemIndex);
        startActivity(intent);
    }

    @Override
    public void setData(List<Message> messages) {
        mMessages.clear();
        mMessages.addAll(messages);
        mAdapter.notifyDataSetChanged();
    }

    @SuppressLint("RestrictedApi")
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
        boolean loggedIn = AccessToken.getCurrentAccessToken() != null;
        if(loggedIn){
            LoginManager.getInstance().logOut();}
        Intent i = new Intent(this,SignInView.class);
        startActivity(i);
        finish();
    }

}
