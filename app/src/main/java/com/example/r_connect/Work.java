package com.example.r_connect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Work extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText cg,intern,curwork;
    Button mSubmit;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    CheckBox checkBoxS,checkBoxFre,checkBoxG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);
        cg = findViewById(R.id.editText1);
        intern = findViewById(R.id.editText2);
        curwork = findViewById(R.id.editText3);
        mSubmit = findViewById(R.id.button11);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        checkBoxS = (CheckBox) findViewById(R.id.checkBoxS);
        boolean checks = checkBoxS.isChecked();
        checkBoxFre = (CheckBox) findViewById(R.id.checkBoxFre);
        boolean checkfre = checkBoxFre.isChecked();
        checkBoxG = (CheckBox) findViewById(R.id.checkBoxG);
        boolean checkgrad = checkBoxG.isChecked();
        if (fAuth.getCurrentUser() != null) {
            mSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
                            startActivity(new Intent(Work.this, NavDraw.class));
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
