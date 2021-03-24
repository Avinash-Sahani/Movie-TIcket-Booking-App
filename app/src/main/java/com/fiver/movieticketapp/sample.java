package com.fiver.movieticketapp;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class sample extends AppCompatActivity {


    private DatabaseReference reference;
    private TextView a, b, c;
    public String s;
    private Movie movie;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference DataRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userid = user.getUid();

for(int i=0; i<4; i++)
        Toast.makeText(sample.this, userid, Toast.LENGTH_SHORT).show();


    }
}