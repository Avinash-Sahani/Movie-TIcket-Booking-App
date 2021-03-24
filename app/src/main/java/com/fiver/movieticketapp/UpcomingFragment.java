package com.fiver.movieticketapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.qrcode.encoder.QRCode;
import com.squareup.picasso.Picasso;

public class UpcomingFragment extends Fragment {

    private RecyclerView movieslist;
    private DatabaseReference movieref;
    String mname;
    private  View moviesview;
    private Button qr;
    private FirebaseRecyclerAdapter<Movie, UpcomingFragment.MovieViewHolder> adapter;
    public UpcomingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        moviesview = inflater.inflate(R.layout.fragment_upcoming, container, false);

        movieslist = (RecyclerView) (moviesview.findViewById(R.id.upcominglist));

        movieslist.setLayoutManager(new LinearLayoutManager(getContext()));

        movieref = FirebaseDatabase.getInstance().getReference().child("UpcomingMovies");



        return moviesview;
    }


    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions options =
                new FirebaseRecyclerOptions.Builder<Movie>()
                        .setQuery(movieref, Movie.class).build();

        adapter = new FirebaseRecyclerAdapter<Movie, UpcomingFragment.MovieViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final UpcomingFragment.MovieViewHolder holder, int position, @NonNull Movie model) {
                String moviename = getRef(position).getKey();

                movieref.child(moviename).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                       final String name = dataSnapshot.child("name").getValue().toString();
                        //Toast.makeText(getContext(),name,Toast.LENGTH_SHORT).show();
                        final String imageurl = dataSnapshot.child("image").getValue().toString();
                        final String  director = dataSnapshot.child("director").getValue().toString();
                        final String  starcast = dataSnapshot.child("starcast").getValue().toString();
                        final String  rating = dataSnapshot.child("rating").getValue().toString() + (" /10");
                        final String  releasedate = dataSnapshot.child("releasedate").getValue().toString();
                        final String  description = dataSnapshot.child("description").getValue().toString();

                        holder.tvTitle.setText(name);
                        holder.tvRating.setText(rating);
                        Picasso.get().load(imageurl).into(holder.imageView);


                        holder.btnBuyTicket.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DetailedFragmentUpcoming detailedFragment = new DetailedFragmentUpcoming();
                                Bundle bundle = new Bundle();
                                bundle.putString("movieTitle",name);
                                bundle.putString("movieRating",rating);
                                bundle.putString("movieReleaseDate",releasedate);
                                bundle.putString("movieDesc",description);
                                bundle.putString("moviePosterPath",imageurl);
                                bundle.putString("movieDirector",director);
                                bundle.putString("movieStarCast",starcast);

                                detailedFragment.setArguments(bundle);
                                getActivity().getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.fragmentContainer,detailedFragment)
                                        .addToBackStack(null)
                                        .commit();
                            }
                        });


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @NonNull
            @Override
            public UpcomingFragment.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.now_playing_movie_item, parent,false);
                UpcomingFragment.MovieViewHolder viewHolder = new UpcomingFragment.MovieViewHolder(view);
                return viewHolder;
            }
        };



        movieslist.setAdapter(adapter);
        adapter.startListening();
    }



    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        TextView tvRating, tvTitle;
        ImageView imageView;
        Button btnBuyTicket, bookmark;
        CardView cardView;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            tvRating = itemView.findViewById(R.id.movieRating);
            tvTitle = itemView.findViewById(R.id.movieTitle);
            imageView = itemView.findViewById(R.id.imgPoster);
            btnBuyTicket = itemView.findViewById(R.id.btnBuyTicket);
            btnBuyTicket.setText("Info");

        }

    }


}

