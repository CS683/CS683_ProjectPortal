package edu.bu.projectportal;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Project.class}, version = 1, exportSchema = false)
public abstract class ProjectPortalDatabase extends RoomDatabase {

    //    singleton instance of Database
    private static ProjectPortalDatabase INSTANCE;

    public static synchronized ProjectPortalDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, ProjectPortalDatabase.class, "project_portal_database")
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    .allowMainThreadQueries()
                    .build();
        }

        return INSTANCE;
    }

    //    List all daos here
    public abstract ProjectDao projectDao();

}
