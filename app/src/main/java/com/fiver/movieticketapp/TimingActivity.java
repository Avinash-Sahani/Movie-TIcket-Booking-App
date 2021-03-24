package com.fiver.movieticketapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class TimingActivity extends AppCompatActivity {
    private Button TimeButton;
private String moviename,number,amount;
    private ArrayList<String> Timings,Dates;
    private ArrayAdapter<String> timingadapter,dateadapter;
private String TotalPrice;
    private DatabaseReference databaseReference;

private Spinner timingspinner,datespinner,ticketspinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timing);

        Bundle bundle = getIntent().getExtras();
        moviename=bundle.getString("moviename");
        number=bundle.getString("number");
        amount=bundle.getString("price");
timingspinner=findViewById(R.id.timing);


        Timings=new ArrayList<>();
        Dates=new ArrayList<>();
        datespinner=(findViewById(R.id.days));

        ReteriveData();

        TimeButton=(Button)(findViewById(R.id.timebutton));
        TimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Toast.makeText(TimingActivity.this, "kfdsjfis", Toast.LENGTH_SHORT).show();
                SaveData();


            }
        });




        Timings.clear();
        ReteriveData();


        timingadapter=new ArrayAdapter<String>(TimingActivity.this,android.R.layout.simple_spinner_dropdown_item,Timings);

        dateadapter=new ArrayAdapter<String>(TimingActivity.this,android.R.layout.simple_spinner_dropdown_item,Dates);


        timingspinner.setAdapter(timingadapter);

        timingadapter.notifyDataSetChanged();


Dates.clear();
ReteriveDataofDates();



        datespinner.setAdapter(dateadapter);

        dateadapter.notifyDataSetChanged();


        FinalizePrice();


    }

    private void FinalizePrice() {


             databaseReference= FirebaseDatabase.getInstance().getReference().child("MovieTimings").child(moviename).child("Price");
     databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
             TotalPrice=dataSnapshot.getValue().toString();

        //     TotalPrice=String.valueOf(Integer.valueOf(TotalPrice)*(Integer.valueOf(ticketspinner.getSelectedItem().toString())));
         }

         @Override
         public void onCancelled(@NonNull DatabaseError databaseError) {

         }
     });

    }

    private void SaveData() {
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Users").child(number).child("Movies").child(moviename);

        HashMap<String,Object> userdata=new HashMap<>();
        userdata.put("Timings",timingspinner.getSelectedItem().toString());
        userdata.put("Dates",datespinner.getSelectedItem().toString());
userdata.put("Price",TotalPrice);
 databaseReference.updateChildren(userdata).addOnCompleteListener(new OnCompleteListener<Void>() {
     @Override
     public void onComplete(@NonNull Task<Void> task) {

         if(task.isSuccessful())
         {
             startActivity(new Intent(TimingActivity.this,PaymentActivity.class).putExtra("price",amount));
         }
     }
 }) ;

    }


    private void ReteriveDataofDates() {
        Dates.add("Dates ");



        databaseReference= FirebaseDatabase.getInstance().getReference().child("MovieTimings").child(moviename).child("Date");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot item:dataSnapshot.getChildren())
                {


                    if(!(Dates.contains(item.getValue().toString())))
                        Dates.add(item.getValue().toString());




                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void ReteriveData() {
        Timings.add("Timings ");






        databaseReference= FirebaseDatabase.getInstance().getReference().child("MovieTimings").child(moviename).child("Time");
databaseReference.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

        for(DataSnapshot item:dataSnapshot.getChildren())
        {


            if(!(Timings.contains(item.getValue().toString())))
            Timings.add(item.getValue().toString());




        }



    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});

    }
}


