package com.hayssamsoussi.whattoread.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;

import com.hayssamsoussi.whattoread.R;
import com.hayssamsoussi.whattoread.adapters.BooksAdapter;
import com.hayssamsoussi.whattoread.api.GBApi;
import com.hayssamsoussi.whattoread.api.GBApiClient;
import com.hayssamsoussi.whattoread.helpers.Constants;
import com.hayssamsoussi.whattoread.models.Example;
import com.hayssamsoussi.whattoread.models.Item;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;

public class BooksFragment extends Fragment {
    private Call<Example> mCallBooks;
    private List<Item> mItems;
    private BooksAdapter myAdapter;
    private static final String TAG = "BooksFragment";
    private ProgressBar pb;
    private static Integer startIndex = 0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.books_fragment, container, false);


        if (view == null)
            view = inflater.inflate(R.layout.books_fragment,
                    container, false
            );

        pb = (ProgressBar) view.findViewById(R.id.poster_progressBar);
        //List to store the books fetched
        mItems = new ArrayList<>();
        //The recycler view that will show the grid of the fetched books
        RecyclerView myrv = (RecyclerView) view.findViewById(R.id.recyclerview_id);
        //Adapter that will populate the recyclerview with data
        myAdapter = new BooksAdapter(getContext(), mItems);
        //Setting up the layout of the grid
        myrv.setLayoutManager(new GridLayoutManager(getContext(),3));
        //Assiging the adapter to the recyclerview
        myrv.setAdapter(myAdapter);
        return view;


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().setTitle("Home");
        //Loading the data onCreate of the fragment
        loadUpcomingMovies("android");

        //Enable the option menu in the fragment
        setHasOptionsMenu(true);

    }


    //Setting up the SearchView in the Action Bar
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        setupActionBar(menu, inflater);

    }

    private void setupActionBar(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.fragment_movies_grid, menu);
        MenuItem myActionMenuItem = menu.findItem( R.id.action_search);
        final SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                pb.setVisibility(View.VISIBLE);
                mItems.clear();
                loadUpcomingMovies(query);
                myAdapter.notifyDataSetChanged();
                InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
    }



    private void loadUpcomingMovies(final String search){
        GBApi tmdbService = GBApiClient.getRetrofit().create(GBApi.class);
        mCallBooks = tmdbService.getBooks(search, 12, Constants.GB_SOURCE_COUNTRY, startIndex, Constants.GB_API_KEY);
        mCallBooks.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if (!response.isSuccessful()){
                    mCallBooks = call.clone();
                    mCallBooks.enqueue(this);
                    return;
                }
                if (response.body() == null){
                    Log.d(TAG, "loadUpcomingMovies() No data");             }
                else{
                    mItems.addAll(response.body().getItems());
                    System.out.println(response.body().getItems().get(0).getVolumeInfo().getImageLinks().getThumbnail());

                }
                myAdapter.notifyDataSetChanged();

                startIndex = startIndex + 12;
                //loadUpcomingMovies(search);
                pb.setVisibility(GONE);
            }
            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Log.d(TAG, "loadUpcomingMovies() Failed");
            }
        });

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            //Fragment not visible
            System.out.println("Fragment not visible");
        }
        else
        {
            //FRAGMENT IS VISIBLE
            getActivity().setTitle("Home");

        }
    }
}
