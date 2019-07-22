package com.hayssamsoussi.whattoread;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import com.hayssamsoussi.whattoread.fragments.BooksFragment;
import com.hayssamsoussi.whattoread.fragments.CategoriesFragment;
import com.hayssamsoussi.whattoread.fragments.FavoritesFragment;


public class MainActivity extends AppCompatActivity {

    //NEW
    private final Fragment fragment1 = new BooksFragment();
    private final Fragment fragment2 = new CategoriesFragment();
    private final Fragment fragment3 = new FavoritesFragment();
    private final FragmentManager fm = getSupportFragmentManager();
    private Fragment active = fragment1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Fragments will not be recreated
        fm.beginTransaction().add(R.id.fragment_container, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.fragment_container, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.fragment_container, fragment1, "1").commit();

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

                    default:
                        return false;

                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
