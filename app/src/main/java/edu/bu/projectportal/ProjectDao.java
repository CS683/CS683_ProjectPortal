package edu.bu.projectportal;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProjectDao {
    @Query("SELECT * FROM projects")
    List<Project> getAll();

    @Query("SELECT * FROM projects WHERE id IN (:projectIds)")
    List<Project> loadAllByIds(long[] projectIds);

    @Query("SELECT * FROM projects WHERE project_title LIKE :title")
    List<Project> loadAllByTitle(String title);

    @Query("SELECT * FROM projects WHERE id = :projectId")
    Project findById(long projectId);

    @Query("SELECT * FROM projects WHERE project_title LIKE :title LIMIT 1 ")
    Project findByTitle(String title);

    @Query("SELECT * FROM projects WHERE project_favorite = :favorite")
    List<Project> findFavorite(boolean favorite);

    @Insert
    long[] insertAll(Project... projects);

    @Insert
    long insertProject(Project project);

    @Delete
    void delete(Project project);

    @Update
    void update(Project project);
}