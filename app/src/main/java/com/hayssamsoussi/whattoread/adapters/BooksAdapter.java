package com.hayssamsoussi.whattoread.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.hayssamsoussi.whattoread.BookDetail;
import com.hayssamsoussi.whattoread.R;
import com.hayssamsoussi.whattoread.models.Example;
import com.hayssamsoussi.whattoread.models.Item;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.MyViewHolder> {

    private Context mContext ;
    private List<Item> mData ;

    public BooksAdapter(Context mContext, List<Item> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardveiw_item_book,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.tv_book_title.setText(mData.get(position).getVolumeInfo().getTitle());
        try {
            Glide.with(mContext) //1
                    .load(mData.get(position).getVolumeInfo().getImageLinks().getThumbnail())
                    .into(holder.img_book_thumbnail);
        } catch (NullPointerException e) {
            Log.d(TAG, e.toString());
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(mData.get(position).getTitle());

                Intent intent = new Intent(mContext, BookDetail.class);

                // Passing data to book activity
                intent.putExtra("id", mData.get(position).getId());
                intent.putExtra("Title",mData.get(position).getVolumeInfo().getTitle());
                intent.putExtra("Author",mData.get(position).getVolumeInfo().getAuthors().get(0));
                intent.putExtra("Publisher", mData.get(position).getVolumeInfo().getPublisher());
                intent.putExtra("Pages", mData.get(position).getVolumeInfo().getPageCount());
                intent.putExtra("Thumbnail",mData.get(position).getVolumeInfo().getImageLinks().getThumbnail());
                intent.putExtra("Description", mData.get(position).getVolumeInfo().getDescription());
                intent.putExtra("ISBN13", mData.get(position).getVolumeInfo().getIndustryIdentifiers().get(0).getIdentifier());
                intent.putExtra("ISBN10", mData.get(position).getVolumeInfo().getIndustryIdentifiers().get(1).getIdentifier());
                // Start the activity
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_book_title;
        ImageView img_book_thumbnail;
        CardView cardView ;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_book_title = (TextView) itemView.findViewById(R.id.book_title_id) ;
            img_book_thumbnail = (ImageView) itemView.findViewById(R.id.book_img_id);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);
        }
    }

    public void setFilter(List<Item> dataModels){
        mData = new ArrayList<>();
        mData.addAll(dataModels);
        notifyDataSetChanged();
    }
}


