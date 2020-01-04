package com.example.r_connect;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.logging.Logger;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    TextView t;
    TextView t1,t2,t3,t4,t5,t6,t7;
    CircleImageView profilepic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        t1 = findViewById(R.id.bio);
        t2=findViewById(R.id.yname);
        t3=findViewById(R.id.nickn);
        t4=findViewById(R.id.semail);
        t5=findViewById(R.id.sbranch);
        t6=findViewById(R.id.syear);
        t7=findViewById(R.id.swoe);
        profilepic=findViewById(R.id.proimg);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userID = extras.getString("userid");
            Log.d("Logger",userID);
            //The key argument here must match that used in the other activity
        }
        Query capitalCities = fStore.collection("users").whereEqualTo("userid", userID);
        capitalCities.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String bio=document.get("Description").toString();
                        String name= document.get("Name").toString();
                        String nick=document.get("Nickname").toString();
                        String email=document.get("email").toString();
                        String branch=document.get("Branch").toString();
                        String gradyear=document.get("Graduation Year").toString();
                        String s1=document.get("Campus_Groups").toString();
                        String s2=document.get("Internship").toString();
                        String s3=document.get("Current_Work").toString();String work="";
                        if(s1!="")
                            work+=s1+"  ";
                        if(s2!="")
                            work+=s2+"  ";
                        if(s3!="")
                            work+=s3;

                        t1.setText(bio);
                        t2.setText(name);
                        t3.setText(nick);
                        t4.setText(email);
                        t5.setText(branch);
                        t6.setText(gradyear);
                        t7.setText(work);
                        Uri imgUri=Uri.parse(document.get("Imageuri").toString());
                        Picasso.with(Profile.this).load(imgUri).into(profilepic);
                    }
                }
            }
        });

    }

}