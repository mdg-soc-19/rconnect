package com.example.r_connect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class VerifyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        Button btn1 = (Button) findViewById(R.id.button4);

        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openPassword();

            }
        });
        Button btn2 = (Button) findViewById(R.id.button5);

        btn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openManApp();

            }
        });
    }
    public void openPassword() {
        Intent intent = new Intent(this, PasswordActivitry.class);
        startActivity(intent);
    }
    public void openManApp() {
        Intent intent = new Intent(this, ManAppActivity.class);
        startActivity(intent);
    }
}
