package pl.lborowy.retrofit2film.Model;

import java.util.List;

/**
 * Created by RENT on 2017-07-07.
 */

public class Film {

    private float rating;
    private String story;
    private String movie;
    private String image;
    private int year;
    private String director;
    private String tagline;
    private String duration;
    private List<Cast> castList;


    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public List<Cast> getCastList() {
        return castList;
    }

    public void setCastList(List<Cast> castList) {
        this.castList = castList;
    }


    public static class Cast {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
