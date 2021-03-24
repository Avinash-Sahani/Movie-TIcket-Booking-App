package com.fiver.movieticketapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import java.util.List;

public class BookingSeat extends AppCompatActivity implements View.OnClickListener {
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
    String already_booked;
    String TotalPrice;
    private EditText phonenumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_seat);
        Intialize();

        seatsdone=findViewById(R.id.selectbutton);
        layout = findViewById(R.id.SeatsLayout);
        Bundle bundle = getIntent().getExtras();
        moviename = bundle.getString("moviename");
        already_booked=bundle.getString("seatsbooked");
        if(already_booked!=null  || already_booked!="") {


            String[] arr = already_booked.split(" ");
            for (int i = 0; i < arr.length; i++) {
                int index = Integer.valueOf(arr[i]);
                char[] v = seats.toCharArray();
                if (index < seats.length() && index >= 0)
                    v[index] = 'R';
                seats = String.valueOf(v);


            }
        }
        phonenumber=findViewById(R.id.number_verify);
        seatsdone.setEnabled(false);

        phonenumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                seatsdone.setEnabled(true);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



















        seatno=new ArrayList<>();
        seats = "/" + seats;

        LinearLayout layoutSeat = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutSeat.setOrientation(LinearLayout.VERTICAL);
        layoutSeat.setLayoutParams(params);
        layoutSeat.setPadding(8 * seatGaping, 8 * seatGaping, 8 * seatGaping, 8 * seatGaping);
        layout.addView(layoutSeat);

        LinearLayout layout = null;

        int count = 0;

        for (int index = 0; index < seats.length(); index++) {
            if (seats.charAt(index) == '/') {
                layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.HORIZONTAL);
                layoutSeat.addView(layout);
            } else if (seats.charAt(index) == 'U') {
                count++;
                TextView view = new TextView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                view.setLayoutParams(layoutParams);
                view.setPadding(0, 0, 0, 2 * seatGaping);
                view.setId(count);
                view.setGravity(Gravity.CENTER);
                view.setBackgroundResource(R.drawable.ic_seats_booked);
                view.setTextColor(Color.WHITE);
                view.setTag(STATUS_BOOKED);
                view.setText(count + "");
                view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
                layout.addView(view);
                seatViewList.add(view);
                view.setOnClickListener(this);
            } else if (seats.charAt(index) == 'A') {
                count++;
                TextView view = new TextView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                view.setLayoutParams(layoutParams);
                view.setPadding(0, 0, 0, 2 * seatGaping);
                view.setId(count);
                view.setGravity(Gravity.CENTER);
                view.setBackgroundResource(R.drawable.ic_seats_book);
                view.setText(count + "");
                view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
                view.setTextColor(Color.BLACK);
                view.setTag(STATUS_AVAILABLE);
                layout.addView(view);
                seatViewList.add(view);
                view.setOnClickListener(this);
            } else if (seats.charAt(index) == 'R') {
                count++;
                TextView view = new TextView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                view.setLayoutParams(layoutParams);
                view.setPadding(0, 0, 0, 2 * seatGaping);
                view.setId(count);
                view.setGravity(Gravity.CENTER);
                view.setBackgroundResource(R.drawable.ic_seats_reserved);
                view.setText(count + "");
                view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
                view.setTextColor(Color.WHITE);
                view.setTag(STATUS_RESERVED);
                layout.addView(view);
                seatViewList.add(view);
                view.setOnClickListener(this);
            } else if (seats.charAt(index) == '_') {
                TextView view = new TextView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                view.setLayoutParams(layoutParams);
                view.setBackgroundColor(Color.TRANSPARENT);
                view.setText("");
                layout.addView(view);
            }
        }
        seatsdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Login_Account(phonenumber.getText().toString());



            }
        });

    }




    private void Login_Account(final String number) {


        DatabaseReference RootRef;


        RootRef = FirebaseDatabase.getInstance().getReference().child("Users");
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child(number).exists()) {



                    String dnumber = (dataSnapshot.child(number).child("Number").getValue().toString());


                    if ((dnumber.equals(number))) {

                        SendDataToDatabase();

                    }
                } else {

                    Toast.makeText(BookingSeat.this, "Number is Incorrect"
                            , Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onCancelled (@NonNull DatabaseError databaseError){

            }

        });

    }









    private void SendDataToDatabase() {
        for(int i=0; i<seatno.size(); i++)
        {
            bookedseats=bookedseats+ (seatno.get(i));
            bookedseats=bookedseats+" ";


        }

        databaseReference= FirebaseDatabase.getInstance().getReference().child("MovieTimings").child(moviename);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("Price").getValue()!=null) {
                    int ticketprice = Integer.valueOf(dataSnapshot.child("Price").getValue().toString());

                    TotalPrice = String.valueOf(seatno.size() * ticketprice);

                }
                doit();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    private void doit() {






        databaseReference= FirebaseDatabase.getInstance().getReference().child("Users").child(phonenumber.getText().toString());
        HashMap<String,Object> userdata=new HashMap<>();
        userdata.put("movie",moviename);
        userdata.put("SeatsBooked",bookedseats);
        userdata.put("Total Bill",TotalPrice);

        databaseReference.child("Movies").child(moviename).updateChildren(userdata).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    startActivity(new Intent(BookingSeat.this,TimingActivity.class).putExtra("moviename",moviename).putExtra("price",TotalPrice).putExtra("number",phonenumber.getText().toString()));
                }
                else
                {
                    Toast.makeText(BookingSeat.this,task.getException().toString() , Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    private void Intialize() {
    }

    @Override
    public void onClick(View view) {
        if ((int) view.getTag() == STATUS_AVAILABLE) {
            if (selectedIds.contains(view.getId() + ",")) {


                selectedIds = selectedIds.replace(+view.getId() + ",", "");
                view.setBackgroundResource(R.drawable.ic_seats_book);
            } else {

                selectedIds = selectedIds + view.getId() + ",";
                view.setBackgroundResource(R.drawable.ic_seats_selected);
                seatno.add(view.getId());
            }
        } else if ((int) view.getTag() == STATUS_BOOKED) {
            Toast.makeText(this, "Seat " + view.getId() + " is Booked", Toast.LENGTH_SHORT).show();
        } else if ((int) view.getTag() == STATUS_RESERVED) {
            Toast.makeText(this, "Seat " + view.getId() + " is Reserved", Toast.LENGTH_SHORT).show();
        }
    }


}
