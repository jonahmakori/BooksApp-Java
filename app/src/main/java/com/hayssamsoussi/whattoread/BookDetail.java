package com.hayssamsoussi.whattoread;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hayssamsoussi.whattoread.database.DatabaseHelper;

import static android.support.constraint.Constraints.TAG;

public class BookDetail extends AppCompatActivity {

    private String book_title, book_author, book_publisher, book_thumb;
    private Integer book_page;
    //Local database instance
    private DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initializing database
        myDb = new DatabaseHelper(this);


        TextView book_title_tv = (TextView) findViewById(R.id.book_title);
        TextView book_description_tv = (TextView) findViewById(R.id.book_description);
        TextView book_author_tv = (TextView) findViewById(R.id.book_author);
        TextView book_publisher_tv = (TextView) findViewById(R.id.book_publisher);
        TextView book_page_tv = (TextView) findViewById(R.id.book_pagecount);
        TextView book_isbn13_tv = (TextView) findViewById(R.id.book_isbn13);
        TextView book_isbn10_tv = (TextView) findViewById(R.id.book_isbn10);
        ImageView book_thumb_iv = (ImageView) findViewById(R.id.book_thumbnail);

        String book_description, book_isbn13, book_isbn10, book_id;
        book_id = getIntent().getExtras().getString("ID");
        book_title = getIntent().getExtras().getString("Title");
        book_author = getIntent().getExtras().getString("Author");
        book_publisher = getIntent().getExtras().getString("Publisher");
        book_thumb = getIntent().getExtras().getString("Thumbnail");
        book_description = getIntent().getExtras().getString("Description");
        book_page = getIntent().getExtras().getInt("Pages");
        book_isbn13 = getIntent().getExtras().getString("ISBN13");
        book_isbn10 = getIntent().getExtras().getString("ISBN10");

        book_title_tv.setText(book_title);
        book_description_tv.setText(book_description);
        book_author_tv.setText(book_author);
        book_publisher_tv.setText(book_publisher);
        book_page_tv.setText(book_page.toString());
        book_isbn10_tv.setText(book_isbn10);
        book_isbn13_tv.setText(book_isbn13);

        try {
            Glide.with(this)
                    .load(book_thumb)
                    .into(book_thumb_iv);
        } catch (NullPointerException e) {
            Log.d(TAG, e.toString());
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, R.string.favorites_msg, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                addToFavorites();
            }
        });
    }

    public void addToFavorites() {

        myDb.insertData(book_title,book_author,book_thumb,book_publisher);

    }

}
