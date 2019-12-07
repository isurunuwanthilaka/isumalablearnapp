package com.isumalab.learn.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AppCompatDialogFragment;
import android.text.Html;

public class ContinueDialogFragment extends DialogFragment {

    public interface ContinueDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    ContinueDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (ContinueDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException("Activity must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(Html.fromHtml("<font color='#000'>Do you want to continue?</font>"));
        builder.setPositiveButton(Html.fromHtml("<font color='#000'>Yes</font>"), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // yes
                        mListener.onDialogPositiveClick(ContinueDialogFragment.this);
                    }
                })
                .setNegativeButton(Html.fromHtml("<font color='#000'>No</font>"), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // no
                        mListener.onDialogNegativeClick(ContinueDialogFragment.this);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}