package com.hayssamsoussi.whattoread.database;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hayssamsoussi.whattoread.R;
import com.hayssamsoussi.whattoread.database.model.FavoriteBook;


import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class CustomAdapter extends BaseAdapter {

    private ArrayList<FavoriteBook> favoritesList;
    private Context context;

    public CustomAdapter(ArrayList<FavoriteBook> list, Context cont){
        this.favoritesList = list;
        this.context = cont;
    }

    @Override
    public int getCount() {
        return this.favoritesList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.favoritesList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if(convertView == null){
            LayoutInflater inf = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.favorite_book_list_item, null);

            holder = new ViewHolder();

            holder.favoriteid = (TextView)convertView.findViewById(R.id.favorite_id);
            holder.name = (TextView)convertView.findViewById(R.id.favorite_book_name);
            holder.author = (TextView)convertView.findViewById(R.id.favorite_book_author);
            holder.thumb = (ImageView)convertView.findViewById(R.id.favorite_book_thumb);
            holder.publisher = (TextView)convertView.findViewById(R.id.favorite_book_publisher);
            holder.delete = (Button)convertView.findViewById(R.id.delete_favorite);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }

        final FavoriteBook stu = favoritesList.get(position);
        holder.name.setText(stu.getName());
        holder.author.setText(stu.getAuthor());
        holder.publisher.setText(stu.getPublisher());
        holder.favoriteid.setText(stu.getId());
        try {
            Glide.with(context) //1
                    .load(stu.getThumb())
                    .into(holder.thumb);
        } catch (NullPointerException e) {
            Log.d(TAG, e.toString());
        }

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper myDb = new DatabaseHelper(context);
                myDb.deleteData(stu.getId());
                favoritesList.remove(position);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    private static class ViewHolder{
        public TextView favoriteid;
        public TextView name;
        public TextView author;
        public ImageView thumb;
        public TextView publisher;
        public Button delete;
    }
}
