package com.example.notemanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notemanagementsystem.database.DatabaseHandler;
import com.example.notemanagementsystem.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.Instant;

public class LoginActivity extends AppCompatActivity {
    private TextView txtEmail;
    private TextView txtPassword;
    private CheckBox checkRemember;
    private Button btnSignIn;
    private Button btnExit;
    private FloatingActionButton fabAddAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addControls();
        addEvents();
        loadRemember();
        new DatabaseHandler(LoginActivity.this).resetActive();
    }

    private void addEvents() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = txtEmail.getText().toString();
                String password = txtPassword.getText().toString();

                User user = new DatabaseHandler(LoginActivity.this).getUser(email, password);
                if (user!=null){
                    setRemember();
                    new DatabaseHandler(LoginActivity.this).setSateActive(user);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(LoginActivity.this, "Email or password you've entered is incorrect", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(1);
            }
        });
        fabAddAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });


    }

    private void loadRemember(){
        SharedPreferences sharedPreferences = getSharedPreferences("LoginData", Context.MODE_PRIVATE);
        boolean rememberChecked = sharedPreferences.getBoolean("REMEMBER_ME", false);
        if (rememberChecked){
            String email = sharedPreferences.getString("EMAIL", "default");
            String password = sharedPreferences.getString("PASSWORD", "default");
            checkRemember.setChecked(true);
            txtEmail.setText(email);
            txtPassword.setText(password);
        }
    }

    private void setRemember() {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (checkRemember.isChecked()){
            String email = txtEmail.getText().toString();
            String password = txtPassword.getText().toString();
            editor.putBoolean("REMEMBER_ME",  true);
            editor.putString("EMAIL", email);
            editor.putString("PASSWORD", password);
            editor.commit();
        }else {
            editor.putBoolean("REMEMBER_ME", false);
            editor.remove("EMAIL");
            editor.remove("PASSWORD");
            editor.commit();
        }
    }

    private void addControls() {
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        checkRemember = findViewById(R.id.checkRemember);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnExit = findViewById(R.id.btnExit);
        fabAddAccount = findViewById(R.id.fabAddAccount);

    }
}
