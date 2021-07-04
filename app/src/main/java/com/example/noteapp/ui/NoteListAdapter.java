package com.example.noteapp.ui;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteapp.R;
import com.example.noteapp.data.Note;
import com.example.noteapp.data.NoteSource;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder> {

    private final static String TAG = "NoteListAdapter";
    private NoteSource dataSource;
    private OnItemClickListener itemClickListener;

//    public NoteListAdapter() {
//        this.dataSource = dataSource;
//    }

    public void setDataSource(NoteSource dataSource) {
        this.dataSource = dataSource;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_notes_list, viewGroup, false);
        Log.d(TAG, "onCreateViewHolder");
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteListAdapter.ViewHolder viewHolder, int i) {
        viewHolder.setData(dataSource.getNote(i));
        Log.d(TAG, "onBindViewHolder");
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView noteName;
        private TextView noteDate;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            noteName = itemView.findViewById(R.id.item_of_list);
            noteDate = itemView.findViewById(R.id.item_date);
            CardView cardView = itemView.findViewById(R.id.item_card_view);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(v, getAdapterPosition());
                    }
                }
            });
        }

        public void setData(Note note) {
            noteName.setText(note.getNoteName());
            noteDate.setText(note.getNoteDate());
        }
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}

