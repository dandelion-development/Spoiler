package com.dandelion.udemy.spoiler.ui;
// [Imports]
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dandelion.udemy.spoiler.R;
import com.dandelion.udemy.spoiler.data.viewmodel.MovieViewModel;
import com.dandelion.udemy.spoiler.network.Resource;
import com.dandelion.udemy.spoiler.retrofit.entity.MovieItemEntity;

import java.util.ArrayList;
import java.util.List;

// [Movies]: Fragment to list movies
public class MovieFragment extends Fragment {
    // (Vars)
    private List<MovieItemEntity> movieCollection;
    private MyMovieRecyclerViewAdapter movieAdapter;
    private ViewModelProvider viewModelProvider;
    private MovieViewModel movieViewModel;
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 2;

    // (Constructor)
    public MovieFragment() {

    }

    // (Instance)

    public static MovieFragment newInstance(int columnCount) {
        MovieFragment fragment = new MovieFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    //[Methods]: overridable methods

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        // Gets an instance of ViewModel
        viewModelProvider = new ViewModelProvider(getActivity());
        movieViewModel = viewModelProvider.get(MovieViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;

            // Defines GridLayoutManager as default
            adaptToScreen();
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            int widthPixels = getResources().getDisplayMetrics().widthPixels;
            int heightPixels = getResources().getDisplayMetrics().heightPixels;
            int densityDpi = getResources().getDisplayMetrics().densityDpi;

            // Makes recycler view adapter
            movieAdapter = new MyMovieRecyclerViewAdapter(getActivity(),movieCollection);
            recyclerView.setAdapter(movieAdapter);
            loadPopularMovies();
        }
        return view;
    }

    // [Methods]: custom methods

    // Retrieves movies list data and observes data to notify changes
    private void loadPopularMovies() {
        movieViewModel.getPopularMovies().observe(getActivity(), new Observer<Resource<List<MovieItemEntity>>>() {
            @Override
            public void onChanged(Resource<List<MovieItemEntity>> listResource) {
                movieCollection =  listResource.data;       // Updates movies data from resource data
                movieAdapter.updateData(movieCollection);   // Updates data over adapter
            }
        });
    }

    // Adapts columns number depending on screen
    private void adaptToScreen() {
        // Gets dimension parameters
        int widthPixels = getResources().getDisplayMetrics().widthPixels;
        float image_view_width = getResources().getDimension(R.dimen.animal_image_view_width);
        // Sets column count based on dimensions
        mColumnCount = Math.round((widthPixels) / (image_view_width));
    }
}