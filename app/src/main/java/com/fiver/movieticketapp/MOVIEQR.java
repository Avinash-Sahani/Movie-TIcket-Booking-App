package com.fiver.movieticketapp;

public class MOVIEQR {


    public MOVIEQR()
    {

    }
private String Dates;
    private String Price;
    private String SeatsBooked;

    private String Timings;
    private String TotalBill;
    private String movie;

    public String getDates() {
        return Dates;
    }

    public void setDates(String dates) {
        Dates = dates;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getSeatsBooked() {
        return SeatsBooked;
    }

    public void setSeatsBooked(String seatsBooked) {
        SeatsBooked = seatsBooked;
    }

    public String getTimings() {
        return Timings;
    }

    public void setTimings(String timings) {
        Timings = timings;
    }

    public String getTotalBill() {
        return TotalBill;
    }

    public void setTotalBill(String totalBill) {
        TotalBill = totalBill;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }


}


