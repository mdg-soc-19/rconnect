package com.example.r_connect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Details extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static final String TAG = "TAG";
    EditText mName, mJoin, mGrad;
    Button mSubmit;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    CheckBox checkBoxmale;
    CheckBox checkBoxfemale;
    SignUpActivity ob =new SignUpActivity();
    String branch="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        mName = findViewById(R.id.editText12);
        mJoin = findViewById(R.id.editText22);
        mGrad = findViewById(R.id.editText3);
        mSubmit = findViewById(R.id.button3);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        checkBoxmale = (CheckBox) findViewById(R.id.checkBoxM);
        final boolean checkmale = checkBoxmale.isChecked();
        checkBoxfemale = (CheckBox) findViewById(R.id.checkBoxF);
        boolean checkfemale = checkBoxfemale.isChecked();

        Spinner spinner = findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        if (fAuth.getCurrentUser() != null) {
            mSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String name = mName.getText().toString().trim();
                        final String join = mJoin.getText().toString().trim();
                    final String grad = mGrad.getText().toString().trim();

                    if (TextUtils.isEmpty(name)) {
                        mName.setError("Name is Required.");
                        return;
                    }

                    if (TextUtils.isEmpty(join)) {
                        mJoin.setError("Joining year is Required.");
                        return;
                    }
                    if (TextUtils.isEmpty(grad)) {
                        mGrad.setError("Graduation year is Required.");
                        return;
                    }
                    final String gender=(checkmale==true)?"FEMALE":"MALE";


                    // register the user in firebase



                                userID = fAuth.getCurrentUser().getUid();
                                DocumentReference documentReference = fStore.collection("users").document(userID);
                                Map<String, Object> user = new HashMap<>();
                                user.put("Name",name);
                                user.put("Gender",gender);
                                user.put("Branch",branch);
                                user.put("Joining Year",join);
                                user.put("Graduation Year",grad);

                                documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "onSuccess: user Profile is created for " + userID);
                                        openArea();
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




    }
    public void openArea() {
        Intent intent = new Intent(this, PersonalInfo.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        branch=text;
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void onCheckboxClicked(View view) {

        switch(view.getId()) {

            case R.id.checkBoxM:

                checkBoxfemale.setChecked(false);
                break;

            case R.id.checkBoxF:
                checkBoxmale.setChecked(false);
                break;
        }
    }
}
