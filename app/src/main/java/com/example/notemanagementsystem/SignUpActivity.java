package com.example.notemanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notemanagementsystem.database.DatabaseHandler;

public class SignUpActivity extends AppCompatActivity {

    DatabaseHandler db;
    EditText email, password, repassword;
    Button btnsu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        db = new DatabaseHandler(this);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById((R.id.password));
        repassword = (EditText)findViewById(R.id.repassword);
        btnsu = (Button)findViewById(R.id.btnsu);
        btnsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString();
                String Password = password.getText().toString();
                String Repassword = repassword.getText().toString();
                if (Email.equals("")||Password.equals("")||Repassword.equals("")){
                    Toast.makeText(getApplicationContext(),"Fields are empty",Toast.LENGTH_SHORT).show();
                }
                else{
                    if(Password.equals(Repassword)){
                        Boolean chkemail = db.chkemail(Email);
                        if(chkemail==true){
                            Boolean insert = db.insertSignUp(Email,Password);
                            if(insert==true){
                                Toast.makeText(getApplicationContext(),"Registered Successfully",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Email Already exists",Toast.LENGTH_SHORT).show();
                        }
                    }
                    Toast.makeText(getApplicationContext(),"password do not match",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    public void SignIn(View View){
        Intent it = new Intent(this, LoginActivity.class);
        startActivity(it);
    }
}
