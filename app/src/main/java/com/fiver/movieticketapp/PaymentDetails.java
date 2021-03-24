package com.fiver.movieticketapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;


public class PaymentDetails extends AppCompatActivity {
    private TextView txtId,txtAmount,txtStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);


        txtId=(TextView)findViewById(R.id.txtId);
        txtAmount=(TextView)findViewById(R.id.txtAmount);
        txtStatus=(TextView)findViewById(R.id.txtStatus);

        Intent intent=getIntent();

        try {
            JSONObject jsonObject=new JSONObject(intent.getStringExtra("PaymentDetails"));

            showDeatils(jsonObject.getJSONObject("response"),intent.getStringExtra("PaymentAmount"));



        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void showDeatils(JSONObject response, String paymentAmount) {


        try {
            txtId.setText(response.getString("id"));
            txtStatus.setText(response.getString("state"));
            txtAmount.setText(response.getString(String.format("$%s",paymentAmount)));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
