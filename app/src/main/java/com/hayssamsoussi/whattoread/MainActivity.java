package com.hayssamsoussi.whattoread;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hayssamsoussi.whattoread.api.GBApi;
import com.hayssamsoussi.whattoread.api.GBApiClient;
import com.hayssamsoussi.whattoread.fragments.BooksFragment;
import com.hayssamsoussi.whattoread.fragments.CategoriesFragment;
import com.hayssamsoussi.whattoread.fragments.FavoritesFragment;
import com.hayssamsoussi.whattoread.helpers.Constants;
import com.hayssamsoussi.whattoread.models.Example;
import com.hayssamsoussi.whattoread.models.Item;
import com.hayssamsoussi.whattoread.models.VolumeInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Call<Example> mCallBooks;
    private List<Item> mItems;
    private List<VolumeInfo> mVolumeInfos;

    //layout elements
    private LinearLayout fragmentContainer;
    private ImageView mProfilePicture;
    private TextView mProfileName;
    private TextView mProfileLbl;
    private BottomNavigationView mBottomNavigationView;
    Toolbar toolbar;
    Bundle bundle;
    Fragment movieFragment, favoritesFragment, categoriesFragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    //NEW
    final Fragment fragment1 = new BooksFragment();
    final Fragment fragment2 = new CategoriesFragment();
    final Fragment fragment3 = new FavoritesFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Fragments will not be recreated
        fm.beginTransaction().add(R.id.fragment_container, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.fragment_container, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.fragment_container, fragment1, "1").commit();

        fragmentContainer = (LinearLayout) findViewById(R.id.fragment_container);
        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_main);

        // listener for botton navigation menu
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_movie:
                        fm.beginTransaction().hide(active).show(fragment1).commit();
                        active = fragment1;
                        return true;

                    case R.id.action_categories:
                        fm.beginTransaction().hide(active).show(fragment2).commit();
                        active = fragment2;
                        return true;

                    case R.id.action_favorites:
                        fm.beginTransaction().hide(active).show(fragment3).commit();
                        active = fragment3;
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void loadUpcomingMovies(){
        GBApi tmdbService = GBApiClient.getRetrofit().create(GBApi.class);
        mCallBooks = tmdbService.getBooks("a", 40, "us", 1, Constants.GB_API_KEY);
        mCallBooks.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if (!response.isSuccessful()){
                    mCallBooks = call.clone();
                    mCallBooks.enqueue(this);
                    return;
                }
                if (response.body().equals(null)){
                    System.out.println("no data");                }
                else{
                    System.out.println(response.body().getTotalItems());
                    for (Item item: response.body().getItems()){
                            mItems.add(item);
                    }
                    System.out.println(mItems.size());
                    for(int i=0; i<mItems.size(); i++){
                        System.out.println(mItems.get(i).getVolumeInfo().getTitle());
                    }
                    //mMoviesPopularAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
            }
        });

    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
    }
}
