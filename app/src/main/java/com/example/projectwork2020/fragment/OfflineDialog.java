package com.example.projectwork2020.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class OfflineDialog extends DialogFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        android.app.AlertDialog.Builder vBuilder = new android.app.AlertDialog.Builder(getActivity());
        vBuilder.setTitle("SEI OFFLINE")
                .setMessage("Il primo accesso necessità di internet, attiva la connessione e ricarica la pagina scrollando verso il basso")
                .setPositiveButton("Ok, capito", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });
        return vBuilder.create();
    }
}
