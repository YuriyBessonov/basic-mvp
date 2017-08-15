package app.warinator.basicmvp.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import app.warinator.basicmvp.R;
import app.warinator.basicmvp.data.db.DbContract;

/**
 * Created by Warinator on 10.08.2017.
 */

public class ShowsCursorAdapter extends RecyclerView.Adapter<ShowsCursorAdapter.ViewHolder> {

    private Cursor cursor;
    private Context context;

    public ShowsCursorAdapter(Context context){
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_show, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int idIndex = cursor.getColumnIndex(DbContract.ShowEntry._ID);
        int nameIndex = cursor.getColumnIndex(DbContract.ShowEntry.COLUMN_NAME);
        int originalNameIndex = cursor.getColumnIndex(DbContract.ShowEntry.COLUMN_ORIGINAL_NAME);
        int ratingIndex = cursor.getColumnIndex(DbContract.ShowEntry.COLUMN_VOTE_AVG);

        cursor.moveToPosition(position);
        int id = cursor.getInt(idIndex);
        String name = cursor.getString(nameIndex);
        String originalName = cursor.getString(originalNameIndex);
        String rating = String.valueOf(cursor.getFloat(ratingIndex));

        holder.itemView.setTag(id);
        holder.tvName.setText(name);
        holder.tvOriginalName.setText(originalName);
        holder.tvRating.setText(rating);
    }

    @Override
    public int getItemCount() {
        return cursor == null ?
                0 : cursor.getCount();
    }

    public Cursor swapCursor(Cursor c){
        if (cursor == c){
            return null;
        }
        Cursor prev = cursor;
        cursor = c;

        if (c != null){
            notifyDataSetChanged();
        }
        return prev;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        TextView tvOriginalName;
        TextView tvRating;
        ImageView ivPoster;

        public ViewHolder(View v) {
            super(v);
            tvName = v.findViewById(R.id.tv_name);
            tvOriginalName = v.findViewById(R.id.tv_original_name);
            tvRating = v.findViewById(R.id.tv_rating);
            ivPoster = v.findViewById(R.id.iv_poster);
        }
    }
}
