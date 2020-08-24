package com.dandelion.udemy.spoiler.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.dandelion.udemy.spoiler.retrofit.entity.MovieItemEntity;

import java.util.List;

// [Movie Dao]: Movie data access object
@Dao
public interface MovieDao {
    @Query("SELECT * FROM movies")
    LiveData<List<MovieItemEntity>> getPopularMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveMovies(List<MovieItemEntity> movieList);
}
