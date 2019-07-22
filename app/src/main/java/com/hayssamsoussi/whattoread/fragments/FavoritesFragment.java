package com.hayssamsoussi.whattoread.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.hayssamsoussi.whattoread.R;
import com.hayssamsoussi.whattoread.database.DatabaseHelper;
import com.hayssamsoussi.whattoread.database.FavoriteAdapter;
import com.hayssamsoussi.whattoread.database.model.FavoriteBook;


import java.util.ArrayList;
import java.util.List;


public class FavoritesFragment extends Fragment {

    private RecyclerView myFavoriteRV;
    private FavoriteAdapter myFavoriteAdapter;
    private List<FavoriteBook> mFavorites;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favorites_fragment, container, false);

        myFavoriteRV = view.findViewById(R.id.recyclerview_id);

        //Getting the favorites from the local database
        DatabaseHelper myDb = new DatabaseHelper(getContext());
        DatabaseHelper dbhelper = new DatabaseHelper(getContext());
        mFavorites = dbhelper.getAllData();

        //Populating the data to the list view
        myFavoriteAdapter = new FavoriteAdapter(getContext(), mFavorites);
        myFavoriteRV.setLayoutManager(new GridLayoutManager(getContext(),3));
        myFavoriteRV.setAdapter(myFavoriteAdapter);

        return view;

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            //FRAGMENT NOT VISIBLE
            System.out.println("Fragment not visible");
        }
        else
        {
            //FRAGMENT IS VISIBLE

            getActivity().setTitle("Favorites");
            DatabaseHelper dbhelper = new DatabaseHelper(getContext());
            mFavorites = dbhelper.getAllData();
            myFavoriteAdapter = new FavoriteAdapter(getContext(), mFavorites);
            myFavoriteRV.setLayoutManager(new GridLayoutManager(getContext(),3));
            myFavoriteRV.setAdapter(myFavoriteAdapter);
        }
    }
}
