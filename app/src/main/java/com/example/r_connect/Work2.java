package com.example.r_connect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Work2 extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText cg,intern,curwork;
    ProgressBar progressBar;
    Button mSubmit;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    CheckBox checkBoxS,checkBoxG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work2);
        cg = findViewById(R.id.editText1t);
        intern = findViewById(R.id.editText2t);
        curwork = findViewById(R.id.editText3t);
        mSubmit = findViewById(R.id.button11t);
        progressBar = findViewById(R.id.progressBar2);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        checkBoxS = (CheckBox) findViewById(R.id.checkBoxSt);
        boolean checks = checkBoxS.isChecked();
        checkBoxG = (CheckBox) findViewById(R.id.checkBoxGt);
        boolean checkgrad = checkBoxG.isChecked();
        if (fAuth.getCurrentUser() != null) {
            mSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressBar.setVisibility(View.VISIBLE);

                    final String campus_group = cg.getText().toString().trim();
                    final String internship = intern.getText().toString().trim();
                    final String currrent_work = curwork.getText().toString().trim();




                    // register the user in firebase



                    userID = fAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = fStore.collection("users").document(userID);
                    Map<String, Object> user = new HashMap<>();
                    user.put("Campus_Groups",campus_group);
                    user.put("Internship",internship);
                    user.put("Current_Work",currrent_work);
                    documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "onSuccess: user Profile is created for " + userID);
                            startActivity(new Intent(Work2.this, NavDraw.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: " + e.toString());
                        }
                    });

                }



            });
        }
    }}
