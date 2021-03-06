package com.example.richie.leaveamessage.main.UI.WriteMessage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.richie.leaveamessage.R;
import com.example.richie.leaveamessage.main.UI.MapGoogle.MapView;

/**
 * Created by Richie on 4/8/2018.
 */

public class WriteMessageView extends AppCompatActivity implements WriteMessageContract.view {
    private Button mLeaveMessageBut;
    private EditText mMessageEdit;
    private double mLat;
    private double mLon;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_activity_layout);

        Intent intent = getIntent();
        mLat = intent.getDoubleExtra(MapView.LAST_KNOWN_LAT,0.00);
        mLon = intent.getDoubleExtra(MapView.LAST_KNOWN_LON,0.00);

        final WriteMessagePresenter presenter = new WriteMessagePresenter(this,getContentResolver());

        mLeaveMessageBut = findViewById(R.id.write_message_but);
        mMessageEdit = findViewById(R.id.write_edit);
        mLeaveMessageBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(mMessageEdit.getText())) {
                    presenter.saveMessage(mMessageEdit.getText().toString(),
                            mLat,
                            mLon);
                }
            }
        });

    }

    @Override
    public void activityFinish() {
        Intent data = new Intent();
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(WriteMessageView.this, message, Toast.LENGTH_SHORT).show();
    }
}
