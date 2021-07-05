package com.example.noteapp.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NoteSourceFirebaseImpl implements NoteSource {

    private static final String NOTES_COLLECTION = "notes";
    private static final String TAG = "NotesSourceFirebaseImp";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collection = db.collection(NOTES_COLLECTION);

    private List<Note> notes = new ArrayList<>();

    @Override
    public NoteSource init(final NoteSourceResponse noteSourceResponse) {
        collection.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            notes = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> doc = document.getData();
                                String id = document.getId();
                                Note note = NoteMapping.toNote(id, doc);
                                notes.add(note);
                            }
                            Log.d(TAG, "success " + notes.size() + " qnt");
                            noteSourceResponse.initialized(NoteSourceFirebaseImpl.this);
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "get failed with ", e);
                    }
                });
        return this;
    }

    @Override
    public Note getNote(int position) {
        return notes.get(position);
    }

    @Override
    public int size() {
        if (notes == null) {
            return 0;
        }
        return notes.size();
    }

    @Override
    public void deleteNote(Note note) {
        String id = note.getId();
        collection.document(id).delete();
        notes.remove(note);
    }

    @Override
    public void updateNote(int position, Note note) {
        String id = note.getId();
        collection.document(id).set(NoteMapping.toDocument(note));
    }

    @Override
    public void addNote(final Note note) {
        collection.add(NoteMapping.toDocument(note)).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                note.setId(documentReference.getId());
            }
        });
        notes.add(note);
    }
}
