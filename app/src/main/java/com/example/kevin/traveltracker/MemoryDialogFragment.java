package com.example.kevin.traveltracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by kevin on 5/26/15.
 */
public class MemoryDialogFragment extends DialogFragment {
    private static final String TAG = "MemoryDialogFragment";
    private static final String MEMORY_KEY = "MEMORY";

    private Memory mMemory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mMemory = (Memory)args.getSerializable(MEMORY_KEY);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Memory")
                .setMessage(mMemory.city+" "+mMemory.country)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "Clicked OK");
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "Clicked Cancel");
                    }
                });

        return builder.create();
    }

    public static MemoryDialogFragment newInstance(Memory memory){
        MemoryDialogFragment fragment = new MemoryDialogFragment();

        Bundle args = new Bundle();
        args.putSerializable(MEMORY_KEY, memory);
        fragment.setArguments(args);

        return fragment;
    }
}
