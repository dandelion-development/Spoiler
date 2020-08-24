package com.dandelion.udemy.spoiler.room;

// [Imports]
import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.dandelion.udemy.spoiler.retrofit.entity.MovieItemEntity;
import com.dandelion.udemy.spoiler.room.dao.MovieDao;

// [Movie Room]: database definition
@Database(entities = {MovieItemEntity.class}, version = 1, exportSchema = false)
public abstract class MovieRoom extends RoomDatabase {
    // Movie data access object
    public abstract MovieDao getMovieDao();
}
