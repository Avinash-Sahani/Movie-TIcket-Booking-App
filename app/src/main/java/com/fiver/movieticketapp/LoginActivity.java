package com.fiver.movieticketapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private Button Login;
    private ProgressDialog progressbar;
    private EditText usernumber, userpassword;
    private TextView already;
    private DatabaseReference RootRef;
    private String parentdbname = "Users";
Button qr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intialize();
qr=(Button)findViewById(R.id.qr_code_button);
qr.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(LoginActivity.this,QRCODE.class));
    }
});
        already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AllowUserToLogin();
            }
        });
    }

    private void AllowUserToLogin() {
        progressbar = new ProgressDialog(this);
        progressbar.setTitle("Login Account");
        progressbar.setMessage("Wait while we are loging an account");
        progressbar.setCanceledOnTouchOutside(false);
        progressbar.show();

        String number = usernumber.getText().toString();
        String password = userpassword.getText().toString();

        if (((TextUtils.isEmpty(number)) | (TextUtils.isEmpty(password))))
            Toast.makeText(LoginActivity.this, "Please Fill All Details", Toast.LENGTH_SHORT).show();
        else {


            Login_Account(number, password);
        }

    }

    private void Intialize() {
        Login = (Button) findViewById(R.id.login_button);
        usernumber = (EditText) findViewById(R.id.login_email);
        userpassword = (EditText) findViewById(R.id.login_pass);
        already=(TextView)(findViewById(R.id.login_create_new));


    }

    private void Login_Account(final String number, final String password) {




        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(parentdbname).child(number).exists()) {

                    progressbar.dismiss();


                    String dnumber = (dataSnapshot.child(parentdbname).child(number).child("Number").getValue().toString());
                    String dpassword = (dataSnapshot.child(parentdbname).child(number).child("Password").getValue().toString());
                    String dname = (dataSnapshot.child(parentdbname).child(number).child("Name").getValue().toString());
                    String demail = (dataSnapshot.child(parentdbname).child(number).child("Email").getValue().toString());

                    if ((dnumber.equals(number))) {


                        if (dpassword.equals(password)) {
                            progressbar.dismiss();


                            startActivity(new Intent(LoginActivity.this,MainActivity.class));

                            Toast.makeText(LoginActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    progressbar.dismiss();
                    Toast.makeText(LoginActivity.this, "Enter All details correctly", Toast.LENGTH_SHORT).show();
                }

            }
                @Override
                public void onCancelled (@NonNull DatabaseError databaseError){

                }

            });

    }

}