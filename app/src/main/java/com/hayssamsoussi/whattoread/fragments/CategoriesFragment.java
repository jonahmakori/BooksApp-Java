package com.hayssamsoussi.whattoread.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.hayssamsoussi.whattoread.R;
import com.hayssamsoussi.whattoread.database.DatabaseHelper;
import java.util.List;

public class CategoriesFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.categories_fragment, container, false);

        //Creating categories in local database
        String[] books_cateogires = getResources().getStringArray(R.array.categories_list);


        //final String[] values = new String[] { "Action and Adventure", "Anthology" , "Classic" , "Comic and Graphic Novel","Crime and Detective",};
        final ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(getContext(), R.layout.item_list_sample, books_cateogires);
        ListView listView = (ListView) view.findViewById(R.id.Catlv);
        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {



            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {

                Fragment fragment = new BooksFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }

        });


        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseHelper myDb = new DatabaseHelper(getContext());

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            //Fragment not currently visible
            System.out.println("Fragment not visible");
        }
        else
        {
            //Fragment is visible
            getActivity().setTitle("Categories");

        }
    }
}
