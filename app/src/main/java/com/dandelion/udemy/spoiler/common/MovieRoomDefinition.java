package com.dandelion.udemy.spoiler.common;
// [Imports]

import com.dandelion.udemy.spoiler.retrofit.entity.MovieItemEntity;

// [Movie Room Definition]: defines movie room database general parameters
public class MovieRoomDefinition {
    // (Common parameters)
    public final static String ROOM_DATABASE_NAME = "ROOM_DB_MOVIES";
    public final static int ROOM_DATABASE_VERSION = 1;
    public final static Boolean ROOM_DATABASE_EXPORT_SCHEMA = false;
    // (Entities)
    public final static Object[] ROOM_DATABASE_ENTITIES = {MovieItemEntity.class};
}
