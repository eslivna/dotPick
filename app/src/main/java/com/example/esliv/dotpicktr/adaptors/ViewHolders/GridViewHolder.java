package com.example.esliv.dotpicktr.adaptors.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.esliv.dotpicktr.R;
import com.example.esliv.dotpicktr.models.Grid;

import butterknife.ButterKnife;


/**
 * Created by esliv on 8/01/2018.
 */

public class GridViewHolder extends RecyclerView.ViewHolder {



    public GridViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public void setData(Grid grid) {


    }

}