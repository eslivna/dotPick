package com.example.esliv.dotpicktr.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.esliv.dotpicktr.R;
import com.example.esliv.dotpicktr.persistence.GridContract;
import com.example.esliv.dotpicktr.utils.BoardThumbnail;
import com.example.esliv.dotpicktr.utils.CursorRecyclerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by esliv on 22/01/2018.
 */

public class GridAdapter extends CursorRecyclerAdapter<GridAdapter.GridViewHolder> {

    private OnRowClickListener mClickListener;
    private Context context;

    public interface OnRowClickListener {
        void onRowClick(Uri uri);
    }

    public interface OnEditClickListener {
        void onEditClick(String answer, int id);
    }


    public GridAdapter(Context context, Cursor cursor, OnRowClickListener clickListener) {
        super(cursor);
        this.context = context;
        mClickListener = clickListener;
    }

    @Override
    public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GridViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_grid_list, parent, false));
    }


    @Override
    public void onBindViewHolder(GridViewHolder holder, Cursor cursor) {
        holder.setData(cursor, context);
    }

    public class GridViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.name)
        TextView name;

        @BindView(R.id.size)
        TextView sizeView;

        @BindView(R.id.board_thumbnail)
        BoardThumbnail board;

        private Context context;
        private int id;

        GridViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }


        public void setData(Cursor cursor, Context context) {
            this.context = context;
            id = cursor.getInt(cursor.getColumnIndex(GridContract.GridEntry._ID));
            name.setText(cursor.getString(cursor.getColumnIndex(GridContract.GridEntry.COLUMN_NAME)));
            int size = cursor.getInt(cursor.getColumnIndex(GridContract.GridEntry.COLUMN_SIZE));
            int[][] grid = getGrid(cursor.getString(cursor.getColumnIndex(GridContract.GridEntry.COLUMN_GRID)), size);
            board.setGrid(grid);
            sizeView.setText(String.format("%d x %d", size, size));
        }


        @Override
        public void onClick(View v) {

            if (mClickListener != null) {
                if (mCursor.moveToPosition(getAdapterPosition())) {
                    int columnIdIndex = mCursor.getColumnIndex(GridContract.GridEntry._ID);
                    Uri uri = GridContract.GridEntry.buildRowUri(mCursor.getInt(columnIdIndex));
                    mClickListener.onRowClick(uri);
                }
            }
        }

        private int[][] getGrid(String gridString, int size) {
            int[][] result = new int[size][size];
            String[] array = gridString.split(",");
            int counter = 0;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    result[i][j] = Integer.parseInt(array[counter]);
                    counter++;
                }
            }
            return result;
        }


        @OnClick(R.id.editName)
        public void click(ImageButton button) {
            AlertDialog.Builder builder = new AlertDialog.Builder(button.getContext());
            builder.setTitle("Edit Title");

            final EditText input = new EditText(button.getContext());
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            input.setText(name.getText().toString());
            builder.setView(input);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String answer = input.getText().toString();
                    OnEditClickListener callback = (OnEditClickListener)context;
                    callback.onEditClick(answer, id);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        }
    }
}
