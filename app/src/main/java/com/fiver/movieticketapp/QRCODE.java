package com.fiver.movieticketapp;

import android.graphics.Bitmap;
        import android.graphics.Color;
        import android.os.Bundle;
        import android.view.View;
import android.widget.Button;
import android.widget.EditText;
        import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
        import com.google.zxing.common.BitMatrix;
        import com.google.zxing.qrcode.QRCodeWriter;

import java.util.ArrayList;

public class QRCODE extends AppCompatActivity {
    private EditText editText;
    private ImageView imageView;
    private Button  qrbt;
    String text="";
private DatabaseReference reference;
private ArrayList<MOVIEQR> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        editText = findViewById(R.id.number);
        imageView = findViewById(R.id.imageView);
        qrbt=findViewById(R.id.button);

list=new ArrayList<>();
        QRCodeButton(imageView);

        qrbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num=editText.getText().toString();

                reference= FirebaseDatabase.getInstance().getReference().child("Users").child(num).child("Movies");

                reterivedata();

            }
        });

    }

    private void reterivedata() {



        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();

                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {

                    list.add(keyNode.getValue(MOVIEQR.class));
                }
                for (int i = 0; i < list.size(); i++)
                {

                    text=text+"Movie Name = "+list.get(i).getMovie()+"\n";
                    text=text+"Seats Booked = "+list.get(i).getSeatsBooked()+"\n";
                    text=text+"Timings : "+list.get(i).getTimings()+"\n";
                    text=text+"Date :"+list.get(i).getDates()+"\n";
                    text=text+"\n";
                    text=text+"\n";
                }

                QRCodeButton(imageView);
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }


    public void QRCodeButton(View view){
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);
            Bitmap bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.RGB_565);
            for (int x = 0; x<200; x++){
                for (int y=0; y<200; y++){
                    bitmap.setPixel(x,y,bitMatrix.get(x,y)? Color.BLACK : Color.WHITE);
                }
            }
            imageView.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}