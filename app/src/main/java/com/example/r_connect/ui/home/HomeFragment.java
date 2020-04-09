package com.example.r_connect.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.r_connect.AreaofInterest2;
import com.example.r_connect.NavDraw;
import com.example.r_connect.People;
import com.example.r_connect.R;
import com.example.r_connect.Work;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class HomeFragment extends Fragment {
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    ProgressBar progressBar1;
    View root;
    TextView t;
    Button bt;
    ImageView update;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        fAuth = FirebaseAuth.getInstance();
        root = inflater.inflate(R.layout.fragment_home, container, false);
        fStore = FirebaseFirestore.getInstance();
        progressBar1 = root.findViewById(R.id.progressBar1);
        t = root.findViewById(R.id.textv);
        bt=root.findViewById(R.id.button11);
        update=root.findViewById(R.id.change);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar1.setVisibility(View.VISIBLE);
                startActivity(new Intent(getActivity(), AreaofInterest2.class));
            }
        });
        bt.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View v) {
                                      progressBar1.setVisibility(View.VISIBLE);
                                      startActivity(new Intent(getActivity(), People.class));
                                      progressBar1.setVisibility(View.INVISIBLE);
                                  }
                              });
                userID = fAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        List<String> group = (List<String>) document.get("Area of Interest");
                        String s="";
                        for(int i=0;i<group.size();i++)
                            s=s+'\u2022'+group.get(i)+"\n";
                        t.setText(s);
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