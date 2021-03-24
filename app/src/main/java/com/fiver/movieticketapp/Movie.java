package com.fiver.movieticketapp;

public class Movie {
    private String name;
private String image;
private String rating;
private String releasedate;
private String description;
private String starcast;
private String director;

public Movie()
{

}
    public Movie(String name, String image, String rating, String releasedate, String description, String starcast, String director) {
        this.name = name;
        this.image = image;
        this.rating = rating;
        this.releasedate = releasedate;
        this.description = description;
        this.starcast = starcast;
        this.director = director;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getreleasedate() {
        return releasedate;
    }

    public void setreleasedate(String releasedate) {
       this. releasedate = this.releasedate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getstarcast() {
        return starcast;
    }

    public void setstarcast(String starcast) {
        this.starcast = starcast;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }
}


