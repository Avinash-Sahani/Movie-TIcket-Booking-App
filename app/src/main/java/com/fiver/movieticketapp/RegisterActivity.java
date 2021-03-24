package com.fiver.movieticketapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
private String userid;
    private Button Register;
    private ProgressDialog progressbar;
    private EditText username,usernumber,userpassword,useremail;
    private TextView already;
    private DatabaseReference RootRef;
    private String parentdbname="Users";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null)
         userid = user.getUid();
        Toast.makeText(RegisterActivity.this,userid,Toast.LENGTH_SHORT).show();
        RootRef= FirebaseDatabase.getInstance().getReference();
        Intialize();
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount();
            }
        });
already.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
    }
});
    }

    private void CreateAccount() {

        progressbar=new ProgressDialog(this);
        progressbar.setTitle("Creating Account");
        progressbar.setMessage("Wait while we are creating an account");
        progressbar.setCanceledOnTouchOutside(false);
        progressbar.show();

        String name=username.getText().toString();
        String number=usernumber.getText().toString();
        String password=userpassword.getText().toString();
        String email=useremail.getText().toString();

        if((TextUtils.isEmpty(name))| (TextUtils.isEmpty(number)) | (TextUtils.isEmpty(password)))
            Toast.makeText(RegisterActivity.this,"Please Fill All Details",Toast.LENGTH_SHORT).show();
        else
            Validate_and_Create(name,number,password,email);


    }

    private void Validate_and_Create(final String name, final String number, final String password,final String email) {

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child(parentdbname).child(number).exists()))
                {

                    HashMap<String,Object> userdata= new HashMap<>();
                    userdata.put("Email",email);
                    userdata.put("Name",name);
                    userdata.put("Number",number);
                    userdata.put("Password",password);
                    userdata.put("UserId",userid);
                    RootRef.child(parentdbname).child(number).updateChildren(userdata).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful())
                            {
                                progressbar.dismiss();
                                SendUserToLoginActivity();
                                Toast.makeText(RegisterActivity.this,"Account Created",Toast.LENGTH_SHORT).show();


                            }
                            else{
                                progressbar.dismiss();
                                Toast.makeText(RegisterActivity.this,"Network Error",Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
                else
                {
                    progressbar.dismiss();
                    Toast.makeText(RegisterActivity.this,"This number already exist",Toast.LENGTH_SHORT).show();
                    SendUserToLoginActivity();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    private void SendUserToLoginActivity() {
        Intent login=new Intent(RegisterActivity.this,LoginActivity.class);





        startActivity(login);
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

    private void Intialize() {

        Register=(Button)findViewById(R.id.register_button);
        username=(EditText)findViewById(R.id.register_name);
        usernumber=(EditText)findViewById(R.id.register_number);
        userpassword=(EditText)findViewById(R.id.register_pass);
        useremail=(EditText)findViewById(R.id.register_email);
        already=(TextView)findViewById(R.id.register_already);

    }
}
