package com.example.esliv.dotpicktr.fragments;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.content.CursorLoader;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.esliv.dotpicktr.DotPicktApplication;
import com.example.esliv.dotpicktr.R;
import com.example.esliv.dotpicktr.adapters.GridAdapter;
import com.example.esliv.dotpicktr.persistence.GridContract;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by esliv on 22/01/2018.
 */

public class GalleryFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, GridAdapter.OnRowClickListener {

    private RecyclerView recyclerView;
    private GridAdapter adapter;

    private GalleryFragmentListener fragmentListener;

    public static final String URI_KEY = "uri";
    private static final int LOADER_ID = 1;

    public GalleryFragment() {
    }

    public interface GalleryFragmentListener {
        void displayMainActivity(Bundle bundle);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() instanceof GalleryFragmentListener) {
            fragmentListener = (GalleryFragmentListener) getActivity();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gallery, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(LOADER_ID, null, this);

        adapter = new GridAdapter(getContext(), null, this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                GridContract.GridEntry._ID,
                GridContract.GridEntry.COLUMN_SIZE,
                GridContract.GridEntry.COLUMN_GRIDLINES,
                GridContract.GridEntry.COLUMN_GRID,
                GridContract.GridEntry.COLUMN_NAME
        };

        String sortOrder = GridContract.GridEntry.COLUMN_NAME + " ASC";

        return new CursorLoader(getContext(), GridContract.GridEntry.CONTENT_URI, projection, null, null, sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.getCount() > 0) {
            adapter.swapCursor(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);

    }

    @Override
    public void onRowClick(Uri uri) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(URI_KEY, uri);
        displayDetailsFragment(bundle);
    }

    private void displayDetailsFragment(Bundle bundle) {
        if (fragmentListener != null) {
            fragmentListener.displayMainActivity(bundle);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = DotPicktApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }


}
