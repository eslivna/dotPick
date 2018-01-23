package com.example.esliv.dotpicktr.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.esliv.dotpicktr.DotPicktApplication;
import com.example.esliv.dotpicktr.R;
import com.example.esliv.dotpicktr.activities.views.Board;
import com.example.esliv.dotpicktr.models.Grid;
import com.squareup.leakcanary.RefWatcher;


public class BoardFragment extends Fragment {
    Board board;
    private Grid grid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_board, container, false);
        board = v.findViewById(R.id.board);
        board.setGrid(grid);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
        grid.setDrawGridLines(true);
    }

    public void redrawCanvas() {
        board.invalidate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = DotPicktApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }

}
