package app.warinator.basicmvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import app.warinator.basicmvp.R;
import app.warinator.basicmvp.data.db.model.TvShow;
import app.warinator.basicmvp.utils.GlideApp;
import app.warinator.basicmvp.utils.NetworkUtils;

/**
 * Shows  adapter
 */

public class ShowsAdapter extends RecyclerView.Adapter<ShowsAdapter.ViewHolder> {

    private List<TvShow> shows;
    private Context context;
    private OnShowClickListener onShowClickListener;

    public ShowsAdapter(Context context, OnShowClickListener showClickListener) {
        this.context = context;
        this.onShowClickListener = showClickListener;
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
        String rating = String.format(Locale.getDefault(), "%.1f", show.getVoteAverage());

        holder.itemView.setTag(id);
        holder.tvName.setText(name);
        holder.tvOriginalName.setText(originalName);
        holder.tvRating.setText(rating);
        GlideApp.with(context)
                .load(NetworkUtils.getPosterURL(show.getPosterPath()))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivPoster);
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

    public interface OnShowClickListener {
        void onShowClicked(int id);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName;
        TextView tvOriginalName;
        TextView tvRating;
        ImageView ivPoster;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            tvName = v.findViewById(R.id.tv_name);
            tvOriginalName = v.findViewById(R.id.tv_original_name);
            tvRating = v.findViewById(R.id.tv_rating);
            ivPoster = v.findViewById(R.id.iv_poster);
        }

        @Override
        public void onClick(View view) {
            onShowClickListener.onShowClicked(shows.get(getAdapterPosition()).getId());
        }
    }
}
