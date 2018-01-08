package com.example.esliv.dotpicktr.adaptors;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.esliv.dotpicktr.R;
import com.example.esliv.dotpicktr.adaptors.ViewHolders.GridViewHolder;
import com.example.esliv.dotpicktr.adaptors.ViewHolders.PlaceholderViewHolder;
import com.example.esliv.dotpicktr.models.Grid;

import java.util.Collections;
import java.util.List;


/**
 * @author Esli
 */
public class GridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Grid> items = Collections.emptyList();
    /**
     * The type for an item.
     */
    private static final int TYPE_ITEM = 0;

    /**
     * The type for the placeholder.
     */
    private static final int TYPE_PLACEHOLDER = 1;

    public GridAdapter(List<Grid> items) {
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_PLACEHOLDER) {
            return new PlaceholderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.placeholder, parent, false));
        }
        return new GridViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_grid, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof GridViewHolder) {
            ((GridViewHolder) holder).setData(items.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (items == null || items.isEmpty() || items.size() <= 0) {
            return TYPE_PLACEHOLDER;
        }
        return TYPE_ITEM;

    }

    @Override
    public int getItemCount() {
        if (items == null || items.isEmpty() || items.size() <= 0) {
            return 1;
        }
        return items.size();
    }


}
