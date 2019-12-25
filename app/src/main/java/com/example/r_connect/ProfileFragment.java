package com.example.r_connect;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    View root;
    TextView t;
    TextView t1,t2,t3,t4,t5,t6,t7;
    ImageView profilepic;
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fAuth = FirebaseAuth.getInstance();
        root = inflater.inflate(R.layout.fragment_profile, container, false);
        fStore = FirebaseFirestore.getInstance();
        t1 = root.findViewById(R.id.bio);
        t2=root.findViewById(R.id.yname);
        t3=root.findViewById(R.id.nickn);
        t4=root.findViewById(R.id.semail);
        t5=root.findViewById(R.id.sbranch);
        t6=root.findViewById(R.id.syear);
        t7=root.findViewById(R.id.swoe);
        profilepic=root.findViewById(R.id.proimg);
        userID = fAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
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
                        profilepic.setImageURI(null);
                        profilepic.setImageURI(imgUri);
                    } else {
                        Log.d("LOGGER", "No such document");
                    }
                } else {
                    Log.d("LOGGER", "get failed with ", task.getException());
                }
            }
        });
        return root;
    }

}