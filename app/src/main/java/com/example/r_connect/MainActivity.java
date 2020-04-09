package com.example.r_connect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar1 = findViewById(R.id.progressBar1);
        Button btn1 = (Button)findViewById(R.id.button1);
        Button btn2 = (Button)findViewById(R.id.button2);
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(),NavDraw.class));
            finish();
        }
        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                progressBar1.setVisibility(View.VISIBLE);
                openSignIn();

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                progressBar1.setVisibility(View.VISIBLE);
                openSignUp();


            }
        });
    }
    public void openSignIn() {
        Intent intent = new Intent(this, SignInActivity.class);
        progressBar1.setVisibility(View.INVISIBLE);
        startActivity(intent);

    }

    public void openSignUp() {
        Intent intent = new Intent(this, SignUpActivity.class);
        progressBar1.setVisibility(View.INVISIBLE);
        startActivity(intent);
    }
}
