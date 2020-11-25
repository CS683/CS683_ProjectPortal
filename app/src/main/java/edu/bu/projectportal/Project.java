package edu.bu.projectportal;


import org.jetbrains.annotations.NotNull;


//This is a simple POJO (Pure Old Java Object) model class
public class Project {

    public final static Project[] projects = {
            new Project("Weather Forecast", "Weather Forcast is an app ..."),
            new Project ("Connect Me", "Connect Me is an app ... "),
            new Project("What to Eat", "What to Eat is an app ..."),
            new Project ("Project Portal", "Project Portal is an app ...")
    };


    private String title;
    private String summary;
    private boolean favorite;


    public Project(@NotNull String title, @NotNull String summary) {

        this.title = title;
        this.summary = summary;
        this.favorite = false;
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
