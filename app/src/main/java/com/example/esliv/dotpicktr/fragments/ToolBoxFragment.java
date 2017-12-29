package com.example.esliv.dotpicktr.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.esliv.dotpicktr.R;


public class ToolBoxFragment extends Fragment {
    private Button colorBtn;
    public final static String ARG_COLOR = "color";
    private int currentColor =-1;

    public interface OnToolBoxSelectedListener {
        public void onToolSelected(int color);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            currentColor = savedInstanceState.getInt(ARG_COLOR);
        }

        View v =inflater.inflate(R.layout.fragment_tool_box, container, false);
        colorBtn = v.findViewById(R.id.colorBtn);
        colorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnToolBoxSelectedListener mCallback = (OnToolBoxSelectedListener) getActivity();
                mCallback.onToolSelected(currentColor);

            }
        });
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        if (args != null) {
            // Set article based on argument passed in
            updateColor(args.getInt(ARG_COLOR));
        } else if (currentColor != -1) {
            // Set article based on saved instance state defined during onCreateView
            updateColor(currentColor);
        }
    }

    public void updateColor(int color) {
        colorBtn.setBackgroundColor(color);
        currentColor = color;
    }


}