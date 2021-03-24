package com.fiver.movieticketapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DetailedFragmentUpcoming extends Fragment {
    ViewGroup layout;

    private Button seatsdone;

    String bookedseats="";


    String moviename;

    private ArrayList<Integer> seatno;

    String seats = "_AAAAAAAAAAAAAAA_/"
            + "_________________/"
            + "AA__AAAAAAAAA__AA/"
            + "AA__AAAAAAAAA__AA/"
            + "AA__AAAAAAAAA__AA/"
            + "AA__AAAAAAAAA__AA/"
            + "AA__AAAA_AAAA__AA/"
            + "AA__AAAA_AAAA__AA/"
            + "AA__AAAA_AAAA__AA/"
            + "AA__AAAA_AAAA__AA/"
            + "_________________/"
            + "AA_AAAAAAAAAAA_AA/"
            + "AA_AAAAAAAAAAA_AA/"
            + "AA_AAAAAAAAAAA_AA/"
            + "AA_AAAAAAAAAAA_AA/"
            + "_________________/";

    private DatabaseReference databaseReference;

    List<TextView> seatViewList = new ArrayList<>();
    int seatSize = 100;
    int seatGaping = 10;


    int STATUS_AVAILABLE = 1;
    int STATUS_BOOKED = 2;
    int STATUS_RESERVED = 3;
    String selectedIds = "";
    int TotalPrice=0;
    String already_booked="";
    private EditText phonenumber;

    TextView tvTitle,tvDesc,tvRating,tvReleaseDate,tvDirector,tvStarCast;
    Button btnTicketBooking;
    ImageView movieImage;

    String movieTitle,movieRating,movieReleaseDate,movieDesc,movieImagePath,movieDirector,movieStarcast;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detailed_upcoming,container,false);
        tvTitle = view.findViewById(R.id.movieTitle);
        tvDesc = view.findViewById(R.id.movieDesc);
        tvRating = view.findViewById(R.id.movieRating);
        tvReleaseDate = view.findViewById(R.id.movieReleaseDate);
tvDirector=view.findViewById(R.id.movieDirector);
tvStarCast=view.findViewById(R.id.movieStarCast);

        movieImage = view.findViewById(R.id.imgPoster);

        Bundle bundle = getArguments();

        movieTitle = bundle.getString("movieTitle");
        movieRating = bundle.getString("movieRating");
        movieDesc = bundle.getString("movieDesc");
        movieReleaseDate = bundle.getString("movieReleaseDate");
        movieImagePath = bundle.getString("moviePosterPath");
        movieDirector=bundle.getString("movieDirector");
        movieStarcast=bundle.getString("movieStarCast");




        tvTitle.setText(movieTitle);
        tvRating.setText(movieRating);
        tvDesc.setText(movieDesc);
        tvReleaseDate.setText(movieReleaseDate);
       tvDirector.setText(movieDirector);
        tvStarCast.setText(movieStarcast);
       Picasso.get().load(movieImagePath).into(movieImage);





        return view;
    }
    public String extractInt(String str)
    {
        // Replacing every non-digit number
        // with a space(" ")
        str = str.replaceAll("[^\\d]", " ");

        // Remove extra spaces from the beginning
        // and the ending of the string
        str = str.trim();

        // Replace all the consecutive white
        // spaces with a single space
        str = str.replaceAll(" +", " ");

        if (str.equals(""))
            return "-1";

        return str;
    }
}
