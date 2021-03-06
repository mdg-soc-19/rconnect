package com.example.r_connect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class People extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("users");
    private Context mcontext;
    private NoteAdapter adapter;
    ProgressBar progressBar4;
    FirebaseAuth fAuth;
    Query query;
    String userID;
    List<String> group;
    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressBar4 = findViewById(R.id.progressBar4);
        setContentView(R.layout.activity_people);
        fAuth = FirebaseAuth.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        progressDialog = new ProgressDialog(People.this);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle("Let's Connect"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);
        DocumentReference documentReference = db.collection("users").document(userID);

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    //progressBar4.setVisibility(View.VISIBLE);
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        group= (List<String>) document.get("Area of Interest");
                        Log.d("LOGGER", group.toString());


                    } else {
                        Log.d("LOGGER", "No such document");
                    }
                } else {
                    Log.d("LOGGER", "get failed with ", task.getException());
                }
            }
        });
        mcontext = getApplicationContext();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setUpRecyclerView();

            }
        }, 2000);

    }

    private void setUpRecyclerView() {Log.d("LOGGER", group.toString());
        query = notebookRef.whereArrayContainsAny("Area of Interest",group);

        FirestoreRecyclerOptions<Note> options = new FirestoreRecyclerOptions.Builder<Note>()
                .setQuery(query, Note.class)
                .build();

        adapter = new NoteAdapter(options, mcontext,this);
        RecyclerView recyclerView = findViewById(R.id.recycle);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                adapter.startListening();
            }
        }, 4000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(adapter!=null)
        adapter.stopListening();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),NavDraw.class));
    }
}