package com.example.noteapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

public class DialogDeleteNoteFragment extends DialogFragment {

    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final View contentView = requireActivity().getLayoutInflater().inflate(R.layout.dialog_delete_note, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity())
                .setTitle(R.string.dialog_title_text)
                .setView(contentView)
                .setPositiveButton(R.string.dialog_text_yes_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dismiss();
                        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                        ((MainFragment) fragmentManager.findFragmentByTag("TAG")).onDialogResult(true);
                    }
                })
                .setNegativeButton(R.string.dialog_text_no_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dismiss();
                        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                        ((MainFragment) fragmentManager.findFragmentByTag("TAG")).onDialogResult(false);
                    }
                });
        return builder.create();
    }
}

