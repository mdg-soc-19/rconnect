package com.example.r_connect;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class NoteAdapter extends FirestoreRecyclerAdapter<Note, NoteAdapter.NoteHolder> {
    private Context mcontext;
    CircleImageView ppic;
    public NoteAdapter(@NonNull FirestoreRecyclerOptions<Note> options, Context context) {
        super(options);
        this.mcontext = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteHolder holder, int position, @NonNull Note model) {
        String s=model.getAreaofin();
        holder.textViewDescription1.setText(model.getName());
        holder.textViewDescription2.setText(model.getBranch());
        holder.textViewDescription3.setText(model.getYear());

        Log.d("LOGGER",s);
        holder.textViewDescription4.setText(s);
        Picasso.with(mcontext).load(Uri.parse(model.getImageuri())).into(ppic);

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


        public NoteHolder(View itemView) {
            super(itemView);
            textViewDescription1 = itemView.findViewById(R.id.name);
            textViewDescription2 = itemView.findViewById(R.id.branch);
            textViewDescription3 = itemView.findViewById(R.id.yearic);
            textViewDescription4 = itemView.findViewById(R.id.aoi);
            ppic=itemView.findViewById(R.id.cpic);
        }
    }
}