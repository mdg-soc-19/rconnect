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

import com.example.r_connect.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AreaOfInterest extends AppCompatActivity {
    public static final String TAG = "TAG";
    CheckBox c1,c2,c3,c4,c5,c6,c7,c8,c9;
    Button aSubmit;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_of_interest);
        aSubmit = findViewById(R.id.button34);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        c1=(CheckBox)findViewById(R.id.checkBox1);
        c2=(CheckBox)findViewById(R.id.checkBox2);
        c3=(CheckBox)findViewById(R.id.checkBox3);
        c4=(CheckBox)findViewById(R.id.checkBox4);
        c5=(CheckBox)findViewById(R.id.checkBox5);
        c6=(CheckBox)findViewById(R.id.checkBox6);
        c7=(CheckBox)findViewById(R.id.checkBox7);
        c8=(CheckBox)findViewById(R.id.checkBox8);
        c9=(CheckBox)findViewById(R.id.checkBox9);
        final List<String> area = new ArrayList<>();


        if (fAuth.getCurrentUser() != null) {
            aSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(c1.isChecked())
                        area.add("Core");
                    if(c2.isChecked())
                        area.add("Competitive Programming");
                    if(c3.isChecked())
                        area.add("Web Development");
                    if(c4.isChecked())
                        area.add("Data Science/AI/Analytics");
                    if(c5.isChecked())
                        area.add("UI//UX/Designing");
                    if(c6.isChecked())
                        area.add("Finance");
                    if(c7.isChecked())
                        area.add("Consultancy");
                    if(c8.isChecked())
                        area.add("Other");
                    if(c9.isChecked())
                        area.add("Entrepreneurship");


                    // register the user in firebase



                    userID = fAuth.getCurrentUser().getUid();


                    DocumentReference documentReference = fStore.collection("users").document(userID);
                    Map<String, Object> user = new HashMap<>();
                    user.put("Area of Interest",area);
                    documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "onSuccess: user Profile is created for " + userID);
                            startActivity(new Intent(AreaOfInterest.this, Work.class));
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
