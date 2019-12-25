package com.example.r_connect;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.r_connect.Adapters.UserAdapter;
import com.example.r_connect.models.User;
import com.example.r_connect.models.UserRepo;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class Connections extends Fragment implements View.OnClickListener {

    private RecyclerView userlist;
    private UserAdapter adapter;
    private UserRepo userRepo;
    private View v;
    RecyclerView mUserListRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_connection, container, false);
        mUserListRecyclerView = v.findViewById(R.id.user_list);
        userRepo = new UserRepo(FirebaseFirestore.getInstance());
        initUI();
        getUsers();
        return v;
    }


    private void getUsers() {

        userRepo.getUsers(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot snapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    Log.e("UserList", "Listen failed.", e);
                    return;
                }

                List<User> userList = new ArrayList<>();
                for (QueryDocumentSnapshot doc : snapshots) {
                    userList.add(new User(doc.getString("email"), doc.getString("image"), doc.getString("name"), doc.getString("user_id")));
                }

                adapter = new UserAdapter(userList, getActivity());
                userlist.setAdapter(adapter);
            }
        });
    }

    private void initUI() {

        userlist = v.findViewById(R.id.user_list);
        userlist.setLayoutManager(new LinearLayoutManager(getContext()));

    }


    @Override
    public void onClick(View v) {

    }
}
