package com.fiver.movieticketapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;


public class PaymentActivity extends AppCompatActivity {
public static final int PAYPAL_REQUEST_CODE=7171;
   
private static PayPalConfiguration config=new PayPalConfiguration()
        .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(Config.Paypal_current_id);


Button btnPayNow;
EditText edtAmout;
String amount="";

    @Override
    protected void onDestroy() {
        stopService(new Intent(this,PayPalService.class));
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
    //START PAYPAL SERVICE

        Intent intent=new Intent(this,PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        startService(intent);

        Bundle bundle = getIntent().getExtras();
      String  amount = bundle.getString("price");
    btnPayNow=(Button)(findViewById(R.id.btnPayNow));
    edtAmout=(EditText)(findViewById(R.id.editAmount));
    
    processPayment(amount);
    }

    private void processPayment(String amount) {


        PayPalPayment payPalPayment =new PayPalPayment(new BigDecimal(String.valueOf(amount)),"USD","Pay For Tickets",PayPalPayment.PAYMENT_INTENT_SALE);
Intent intent=new Intent(PaymentActivity.this, com.paypal.android.sdk.payments.PaymentActivity.class);
intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
intent.putExtra(com.paypal.android.sdk.payments.PaymentActivity.EXTRA_PAYMENT,payPalPayment);
startActivityForResult(intent,PAYPAL_REQUEST_CODE);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYPAL_REQUEST_CODE) {


            if (resultCode == RESULT_OK) {

                PaymentConfirmation confirmation = data.getParcelableExtra(com.paypal.android.sdk.payments.PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null) {
                    try {
                        String paymentDetails = confirmation.toJSONObject().toString(4);
                        startActivity(new Intent(this, PaymentDetails.class).putExtra("PaymentDeatils", paymentDetails).putExtra("PaymentAmount", amount));
                        ;

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            } else if (resultCode == Activity.RESULT_CANCELED)
                Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();

        } else if (resultCode == com.paypal.android.sdk.payments.PaymentActivity.RESULT_EXTRAS_INVALID) {
            Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show();
        }
    }
}

