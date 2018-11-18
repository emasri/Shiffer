package com.shiffer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.shiffer.Popups.ForgetPasswordPopup;

public class StartUpActivity extends AppCompatActivity {

    Button ForgetPasswordButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);

        ForgetPasswordButton = findViewById(R.id.ForgetPasswordButton);

        ForgetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 startActivity(new Intent(StartUpActivity.this,ForgetPasswordPopup.class));

            }
        });

    }
}
