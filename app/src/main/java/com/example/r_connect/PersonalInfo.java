package com.example.r_connect;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;

public class PersonalInfo extends AppCompatActivity {
    EditText e1,e2;
    Button b;
    public Uri downloadURL;
    public static final String TAG = "TAG";
    private static final int REQUEST_WRITE_PERMISSION = 786;
    ImageButton ib;
    String userID;
    static int PReqCode = 1;
    static int RequesCode = 1;
    Uri pickedImgUri;
    private FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        e1=findViewById(R.id.editText13);
        e2=findViewById(R.id.editText14);
        b=findViewById(R.id.button33);
        ib=findViewById(R.id.imgbut);
        imageView=findViewById(R.id.pp);
        fStore = FirebaseFirestore.getInstance();
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //IF THE ANDROID SDK UP TO MARSMALLOW BUILD NUMBER
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //START REQUEST PERMISSION
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
                } else {
                    //ELSE BELOW START OPEN PICKER
                    CropImage.startPickImageActivity(PersonalInfo.this);
                }
            }
        });
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    final String nname = e1.getText().toString();
                    final String text = e2.getText().toString();
                    final String ig = pickedImgUri.toString();
                    userID = mAuth.getCurrentUser().getUid();
                    final FirebaseUser currentUser = mAuth.getCurrentUser();
                    StorageReference mStorage = FirebaseStorage.getInstance().getReference().child(userID);
                    final StorageReference imageFilePath = mStorage.child(pickedImgUri.getLastPathSegment());
                    imageFilePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadURL=uri;
                                    Log.i("URL", uri.toString());
                                    DocumentReference documentReference = fStore.collection("users").document(userID);
                                    Map<String, Object> user = new HashMap<>();
                                    user.put("Nickname", nname);
                                    user.put("Description",text);
                                    user.put("Imageuri",downloadURL.toString());
                                    documentReference.update(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {
                                                StorageReference mStorage = FirebaseStorage.getInstance().getReference().child(userID);
                                                final StorageReference imageFilePath = mStorage.child(pickedImgUri.getLastPathSegment());
                                                imageFilePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                    @Override
                                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                                        imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                            @Override
                                                            public void onSuccess(Uri uri) {
                                                                UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder().setDisplayName(nname)
                                                                        .setPhotoUri(uri).build();

                                                                currentUser.updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {

                                                                        if (task.isSuccessful()) {
                                                                            startActivity(new Intent(PersonalInfo.this, AreaOfInterest.class));
                                                                        }


                                                                    }
                                                                });
                                                            }
                                                        });


                                                    }
                                                });
                                            }
                                        }
                                    });


                                }
                            });
                        }
                    });


                }
            });
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //THIS IS HAPPEN WHEN USER CLICK ALLOW ON PERMISSION
            //START PICK IMAGE ACTIVITY
            CropImage.startPickImageActivity(PersonalInfo.this);
        }
    }


    @SuppressLint("NewApi")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);
            Log.i("RESPONSE getPath", imageUri.getPath());
            Log.i("RESPONSE getScheme", imageUri.getScheme());
            Log.i("RESPONSE PathSegments", imageUri.getPathSegments().toString());

            //NOW CROP IMAGE URI
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setMultiTouchEnabled(true)
                    //REQUEST COMPRESS SIZE
                    .setRequestedSize(800, 800)
                    //ASPECT RATIO, DELETE IF YOU NEED CROP ANY SIZE
                    .setAspectRatio(1, 1)
                    .start(this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Log.i("RESPONSE getUri", result.getUri().toString());

                //GET CROPPED IMAGE URI AND PASS TO IMAGEVIEW
                pickedImgUri=result.getUri();
                if(pickedImgUri!=null)
                imageView.setImageURI(pickedImgUri);
            }
        }
    }
}