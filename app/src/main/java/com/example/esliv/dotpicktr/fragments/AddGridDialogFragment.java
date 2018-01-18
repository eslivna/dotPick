package com.example.esliv.dotpicktr.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.example.esliv.dotpicktr.R;

/**
 * Created by esliv on 8/01/2018.
 */

public class AddGridDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose size canvas")
                .setItems(R.array.size_names, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int position) {
                        OnSelectionClickListener callback = (OnSelectionClickListener) getActivity();
                        callback.onGridSize(position);
                    }
                });
        return builder.create();
    }

    public interface OnSelectionClickListener {
        void onGridSize(int position);
    }


}
