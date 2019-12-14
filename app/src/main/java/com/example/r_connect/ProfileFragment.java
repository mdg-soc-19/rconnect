package com.example.r_connect;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileFragment extends Fragment {
    public static final String TAG = "TAG";
    private static final int REQUEST_WRITE_PERMISSION = 786;
    ImageButton pickimage;
    ImageView imageview;
    String userID;
    static int PReqCode = 1;
    static int RequesCode = 1;
    Uri pickedImgUri;
    private FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    private EditText mname, mbhawan;
    private ProgressBar loadingProgress;
    private Button submit;
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, null);
    }


}
