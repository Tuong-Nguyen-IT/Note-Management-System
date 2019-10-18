package com.example.notemanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notemanagementsystem.database.DatabaseHandler;
import com.example.notemanagementsystem.model.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    DatabaseHandler db;
    EditText txtEmail, txtPassword, txtRepassword;
    Button btnsu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        db = new DatabaseHandler(this);
        txtEmail = (EditText) findViewById(R.id.email);
        txtPassword = (EditText) findViewById((R.id.password));
        txtRepassword = (EditText) findViewById(R.id.repassword);
        btnsu = (Button) findViewById(R.id.btnsu);
        btnsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

    }

    public void signIn() {
//        Intent it = new Intent(this, LoginActivity.class);
//        startActivity(it);
        long result;
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();
        String passwordConfirm = txtRepassword.getText().toString();

        if (email.length() == 0) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() == 0) {
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (passwordConfirm.length() == 0) {
            Toast.makeText(this, "Please confirm your password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!checkEmail(email)) {
            Toast.makeText(this, "Please enter invalid email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!checkPasswordlength(password)){
            Toast.makeText(this, "Password must contain at least 8 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!passwordCompare(password, passwordConfirm)){
            Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show();
            return;
        }
        User user = new User();
        user.setEmail(email);
        user.setPwd(password);
        result = new DatabaseHandler(this).addUser(user);

        if (result != -1){
            Toast.makeText(this,"Sign Up Success", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Email is already taken", Toast.LENGTH_SHORT).show();
        }
    }

    private Boolean checkEmail(String email) {
        String emailPattern = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern regex = Pattern.compile(emailPattern);
        Matcher matcher = regex.matcher(email);
        if (matcher.find()) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkPasswordlength(String password){
        if (password.length() < 8)
            return false;
        return true;
    }

    private boolean passwordCompare(String password, String passwordConfirm){
        return password.equals(passwordConfirm);
    }
    public void SignIn(View View){
        Intent it = new Intent(this, LoginActivity.class);
        startActivity(it);
    }
}
