package com.example.esliv.dotpicktr.activities;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.esliv.dotpicktr.R;


public class BoardFragment extends Fragment {
    Board board;
    final static String ARG_COLOR = "color";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_board, container, false);
        board =  v.findViewById(R.id.board);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        if (args != null) {
            updateColor(args.getInt(ARG_COLOR));
        } else if (board.getActiveColorCode() != -1) {
            updateColor(board.getActiveColorCode());
        }
    }

    private void updateColor(int color) {
        board.setActiveColorCode(color);
    }
}
