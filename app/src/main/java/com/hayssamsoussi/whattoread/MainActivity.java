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

import com.hayssamsoussi.whattoread.fragments.BooksFragment;
import com.hayssamsoussi.whattoread.fragments.CategoriesFragment;
import com.hayssamsoussi.whattoread.fragments.FavoritesFragment;
import com.hayssamsoussi.whattoread.models.Example;
import com.hayssamsoussi.whattoread.models.Item;

import java.util.List;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

    private Call<Example> mCallBooks;
    private List<Item> mItems;

    //layout elements
    private LinearLayout fragmentContainer;
    private ImageView mProfilePicture;
    private TextView mProfileName;
    private TextView mProfileLbl;
    private Toolbar toolbar;
    private Bundle bundle;
    private Fragment movieFragment, favoritesFragment, categoriesFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

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
        fm.beginTransaction().add(R.id.fragment_container, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.fragment_container, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.fragment_container, fragment1, "1").commit();

        fragmentContainer = (LinearLayout) findViewById(R.id.fragment_container);
        BottomNavigationView mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_main);

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



    private void setupToolbar() {
        setSupportActionBar(toolbar);
    }
}
