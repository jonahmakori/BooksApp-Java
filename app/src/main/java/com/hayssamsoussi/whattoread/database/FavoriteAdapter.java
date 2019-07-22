package com.hayssamsoussi.whattoread.database;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.hayssamsoussi.whattoread.R;
import com.hayssamsoussi.whattoread.database.model.FavoriteBook;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.MyViewHolder> {

    private Context mContext ;
    private List<FavoriteBook> mData ;

    public FavoriteAdapter(Context mContext, List<FavoriteBook> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.favorite_book_list_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.favorite_book_name.setText(mData.get(position).getName());
        try {
            Glide.with(mContext) //1
                    .load(mData.get(position).getThumb())
                    .into(holder.favorite_book_thumbnail);
        } catch (NullPointerException e) {
            Log.d(TAG, e.toString());
        }
        holder.delete_favorite_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper myDb = new DatabaseHelper(mContext);
                myDb.deleteData(mData.get(position).getId());
                mData.remove(position);
                notifyDataSetChanged();
            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //System.out.println(mData.get(position).getTitle());

//                Intent intent = new Intent(mContext, BookDetail.class);
//
//                // Passing data to book activity
//                intent.putExtra("id", mData.get(position).getId());
//                intent.putExtra("Title",mData.get(position).getVolumeInfo().getTitle());
//                intent.putExtra("Author",mData.get(position).getVolumeInfo().getAuthors().get(0));
//                intent.putExtra("Publisher", mData.get(position).getVolumeInfo().getPublisher());
//                intent.putExtra("Pages", mData.get(position).getVolumeInfo().getPageCount());
//                intent.putExtra("Thumbnail",mData.get(position).getVolumeInfo().getImageLinks().getThumbnail());
//                intent.putExtra("Description", mData.get(position).getVolumeInfo().getDescription());
//                intent.putExtra("ISBN13", mData.get(position).getVolumeInfo().getIndustryIdentifiers().get(0).getIdentifier());
//                intent.putExtra("ISBN10", mData.get(position).getVolumeInfo().getIndustryIdentifiers().get(1).getIdentifier());
//                // Start the activity
//                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView favorite_book_name;
        private ImageView favorite_book_thumbnail;
        private Button delete_favorite_book;
        private CardView cardView ;

        public MyViewHolder(View itemView) {
            super(itemView);

            favorite_book_name = (TextView) itemView.findViewById(R.id.favorite_book_name) ;
            favorite_book_thumbnail = (ImageView) itemView.findViewById(R.id.favorite_book_thumb);
            delete_favorite_book = (Button)itemView.findViewById(R.id.delete_favorite);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);
        }
    }

}


