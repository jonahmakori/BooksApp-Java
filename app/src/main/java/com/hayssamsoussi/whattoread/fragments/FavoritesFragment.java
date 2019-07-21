package com.hayssamsoussi.whattoread.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.hayssamsoussi.whattoread.R;
import com.hayssamsoussi.whattoread.database.CustomAdapter;
import com.hayssamsoussi.whattoread.database.DatabaseHelper;
import com.hayssamsoussi.whattoread.database.model.FavoriteBook;
import com.hayssamsoussi.whattoread.models.Example;
import com.hayssamsoussi.whattoread.models.Item;
import com.hayssamsoussi.whattoread.models.VolumeInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;

public class FavoritesFragment extends Fragment {

    DatabaseHelper myDb;
    ListView myListview;
    CustomAdapter myAdapter;
    ArrayList<FavoriteBook> bookList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favorites_fragment, container, false);

        myListview = view.findViewById(R.id.Favoritelv);

        //Getting the favorites from the local database
        myDb = new DatabaseHelper(getContext());
        DatabaseHelper dbhelper = new DatabaseHelper(getContext());
        bookList = dbhelper.getAllData();

        //Populating the data to the list view
        myAdapter = new CustomAdapter(bookList, getContext());
        myListview.setAdapter(myAdapter);

        return view;

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            //FRAGMENT NOT VISIBLE
        }
        else
        {
            //FRAGMENT IS VISIBLE

            getActivity().setTitle("Favorites");
            DatabaseHelper dbhelper = new DatabaseHelper(getContext());
            bookList = dbhelper.getAllData();
            myAdapter = new CustomAdapter(bookList, getContext());
            myListview.setAdapter(myAdapter);
        }
    }
}
