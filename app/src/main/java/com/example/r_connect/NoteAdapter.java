package com.example.r_connect;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class NoteAdapter extends FirestoreRecyclerAdapter<Note, NoteAdapter.NoteHolder> {
    private Context mcontext;
    private Context context;
    CircleImageView ppic;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    /*private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }*/
    public NoteAdapter(@NonNull FirestoreRecyclerOptions<Note> options, Context mcontext,Context context) {
        super(options);
        this.mcontext = mcontext;
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteHolder holder, int position, @NonNull final Note model) {
        String s=model.getAreaofin();
        holder.textViewDescription1.setText(model.getName());
        holder.textViewDescription2.setText(model.getBranch());
        holder.textViewDescription3.setText(model.getYear());
        holder.image.setImageResource(R.drawable.handshake);

        Log.d("LOGGER",s);
        holder.textViewDescription4.setText(s);
        Picasso.with(mcontext).load(Uri.parse(model.getImageuri())).into(ppic);
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, Profile.class);
                intent.putExtra("userid", model.getUserid());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);}});
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                State.area.add(model.getUserid());
                Toast.makeText(context, "User Added to Connection", Toast.LENGTH_SHORT).show();
                DocumentReference documentReference = db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
                Map<String, Object> user = new HashMap<>();
                user.put("Connection",State.area);
                documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });}});

    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card,
                parent, false);
        return new NoteHolder(v);
    }

    class NoteHolder extends RecyclerView.ViewHolder {

        TextView textViewDescription1;
        TextView textViewDescription2;
        TextView textViewDescription3;
        TextView textViewDescription4;
        CardView parentLayout;
        ImageView image;


        public NoteHolder(View itemView) {
            super(itemView);
            textViewDescription1 = itemView.findViewById(R.id.name);
            textViewDescription2 = itemView.findViewById(R.id.branch);
            textViewDescription3 = itemView.findViewById(R.id.yearic);
            textViewDescription4 = itemView.findViewById(R.id.aoi);
            image= itemView.findViewById(R.id.connect);
            ppic=itemView.findViewById(R.id.cpic);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });*/
        }
    }
}