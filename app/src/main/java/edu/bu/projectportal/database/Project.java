package edu.bu.projectportal.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


/**
 * This is a simple POJO (Pure Old Java Object) model class
 * Use @Entity to map this PoJo to a table in the database
 */
@Entity(tableName="projects")
public class Project {

    //use this as cache, so don't need to query the database always
    public static List<Project> projects = new ArrayList<>();

//    public static List<Project> projects =
//            new ArrayList<Project>(Arrays.asList(
//            new Project("Weather Forecast", "Weather Forcast is an app ..."),
//            new Project ("Connect Me", "Connect Me is an app ... "),
//            new Project("What to Eat", "What to Eat is an app ..."),
//            new Project ("Project Portal", "Project Portal is an app ...")));

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name ="project_title")
    private String title;

    @ColumnInfo(name ="project_summary")
    private String summary;

    @ColumnInfo(name ="project_favorite")
    private boolean favorite;


    public Project(@NotNull String title, @NotNull String summary) {

        this.title = title;
        this.summary = summary;
        this.favorite = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public boolean isFavorite() {return favorite;}

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @NotNull
    @Override
    public String toString() {

        return "Project{" +
                "title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                '}';
    }

}
