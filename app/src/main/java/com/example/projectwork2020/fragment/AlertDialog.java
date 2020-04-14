package com.example.projectwork2020.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AlertDialog extends DialogFragment {
    String mTitle, mMessage;

    public AlertDialog(String aTitle, String aMessage)
    {
        mTitle = aTitle;
        mMessage = aMessage;
    }

    public interface IAlertDialog
    {
        void onResponse(boolean aResponse);
    }

    IAlertDialog mListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        android.app.AlertDialog.Builder vBuilder = new android.app.AlertDialog.Builder(getActivity());
        vBuilder.setTitle(mTitle)
                .setMessage(mMessage)
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onResponse(true);
                        dismiss();
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onResponse(false);
                        dismiss();
                    }
                });
        return vBuilder.create();
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        if(activity instanceof IAlertDialog)
        {
            mListener = (IAlertDialog) activity;
        }
    }
}
