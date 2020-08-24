package com.dandelion.udemy.spoiler.ui;
// [Imports]
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dandelion.udemy.spoiler.R;
import com.dandelion.udemy.spoiler.common.APICommon;
import com.dandelion.udemy.spoiler.common.AppCommon;

// [Movie Detail]: movie detailed information activity
public class MovieDetailActivity extends AppCompatActivity {
    // (Vars)
    private ImageView movieCover;
    private TextView movieTitle;
    private RatingBar ratingBarVotes;
    private TextView movieOverview;
    private static final int RATE_MIN_VALUE = 0;
    private static final int RATE_MAX_VALUE = 10;
    private static final int RATE_STARS_NUM = 5;
    // [Methods]: overridable methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        // (Action Bar): hides action bar to get full screen
        getSupportActionBar().hide();

        // (Movie Details): sets activity controls to show movie detailed information

        // Controls instance
        movieCover = findViewById(R.id.imageViewDetailCover);
        movieTitle = findViewById(R.id.textViewMovieTitle);
        ratingBarVotes = findViewById(R.id.ratingBarMovie);
        movieOverview = findViewById(R.id.textViewMovieSynopsis);

        // Gets intent object
        Intent intentData = getIntent();

        // Loads data over activity controls

        // Cover
        String cover_image = intentData.getStringExtra(AppCommon.SHARE_DATA_MOVIE_COVER_LABEL);
        Glide.with(getApplicationContext())
                .load(APICommon.API_IMAGE_URL_W500 + cover_image)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(movieCover);

        // Title
        String movie_title = intentData.getStringExtra(AppCommon.SHARE_DATA_MOVIE_TITLE_LABEL);
        movieTitle.setText(movie_title);

        // Votes rating
        float movie_rating = intentData.getFloatExtra(AppCommon.SHARE_DATA_MOVIE_RATE_LABEL,0);

        ratingBarVotes.setIsIndicator(true);
        ratingBarVotes.setNumStars(RATE_STARS_NUM);
        ratingBarVotes.setMax(RATE_MAX_VALUE);
        ratingBarVotes.setStepSize(0.1f);
        ratingBarVotes.setRating(getRate(movie_rating));

        // Synopsis
        String movie_overview = intentData.getStringExtra(AppCommon.SHARE_DATA_MOVIE_OVERVIEW_LABEL);
        movieOverview.setText(movie_overview);
    }

    // [Methods]: custom
    private float getRate(float rateValue) {
        float ratio = (RATE_MAX_VALUE / RATE_STARS_NUM);
        if (rateValue < RATE_MIN_VALUE) {rateValue = 0;}
        else if (rateValue > RATE_MAX_VALUE) {rateValue = 10;}
        else {
            rateValue = (rateValue / ratio);
        }
        return rateValue;
    }
}