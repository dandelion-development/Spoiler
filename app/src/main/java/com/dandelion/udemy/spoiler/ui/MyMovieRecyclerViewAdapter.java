package com.dandelion.udemy.spoiler.ui;
// [Imports]
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dandelion.udemy.spoiler.R;
import com.dandelion.udemy.spoiler.common.APICommon;
import com.dandelion.udemy.spoiler.common.AppCommon;
import com.dandelion.udemy.spoiler.retrofit.entity.MovieItemEntity;

import java.util.List;

// [Movie Recycler View Adapter]
public class MyMovieRecyclerViewAdapter extends RecyclerView.Adapter<MyMovieRecyclerViewAdapter.ViewHolder> {
    // (Vars)
    private Context appContext;
    private List<MovieItemEntity> mValues;
    // (Constructor)
    public MyMovieRecyclerViewAdapter(Context context, List<MovieItemEntity> items) {
        appContext = context;
        mValues = items;
    }

    // [Methods]: overridable
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        // (Movie Cover)
        // Cover image load
        Glide.with(appContext)
                .load(APICommon.API_IMAGE_URL_W500 + holder.mItem.getPosterPath())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .centerCrop()
                .into(holder.imageViewCover);
        // Movie cover click event > move to details
        holder.imageViewCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveToDetails = new Intent(appContext,MovieDetailActivity.class);
                // Prepares movie detail data to be send as parameter
                moveToDetails.putExtra(AppCommon.SHARE_DATA_MOVIE_ID_LABEL,holder.mItem.getId());
                moveToDetails.putExtra(AppCommon.SHARE_DATA_MOVIE_COVER_LABEL,holder.mItem.getPosterPath());
                moveToDetails.putExtra(AppCommon.SHARE_DATA_MOVIE_TITLE_LABEL,holder.mItem.getTitle());
                moveToDetails.putExtra(AppCommon.SHARE_DATA_MOVIE_RATE_LABEL,holder.mItem.getVoteAverage());
                moveToDetails.putExtra(AppCommon.SHARE_DATA_MOVIE_RELEASE_LABEL,holder.mItem.getReleaseDate());
                moveToDetails.putExtra(AppCommon.SHARE_DATA_MOVIE_OVERVIEW_LABEL,holder.mItem.getOverview());
                // Goes to detail activity
                appContext.startActivity(moveToDetails);
            }
        });
    }

    @Override
    public int getItemCount() {
        int listSize = 0;
        if (mValues != null) {listSize = mValues.size();}
        return listSize;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView imageViewCover;
        public MovieItemEntity mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            imageViewCover = (ImageView) view.findViewById(R.id.imageViewCover);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mItem.getTitle() + "'";
        }
    }

    // [Methods]: custom methods

    // Updates data
    public void updateData(List<MovieItemEntity> updatedMovies) {
        this.mValues = updatedMovies;
        this.notifyDataSetChanged();
    }
}