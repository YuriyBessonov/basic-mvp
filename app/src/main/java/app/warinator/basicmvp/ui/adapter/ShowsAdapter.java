package app.warinator.basicmvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.warinator.basicmvp.R;
import app.warinator.basicmvp.data.db.model.TvShow;

/**
 * Shows  adapter
 */

public class ShowsAdapter extends RecyclerView.Adapter<ShowsAdapter.ViewHolder> {

    private List<TvShow> shows;
    private Context context;

    public ShowsAdapter(Context context) {
        this.context = context;
        shows = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_show, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TvShow show = shows.get(position);
        int id = show.getId();
        String name = show.getName();
        String originalName = show.getOriginalName();
        String rating = String.valueOf(show.getVoteAverage());

        holder.itemView.setTag(id);
        holder.tvName.setText(name);
        holder.tvOriginalName.setText(originalName);
        holder.tvRating.setText(rating);
    }

    @Override
    public int getItemCount() {
        return shows.size();
    }

    public void updateShows(List<TvShow> shows) {
        this.shows.clear();
        this.shows.addAll(shows);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
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
