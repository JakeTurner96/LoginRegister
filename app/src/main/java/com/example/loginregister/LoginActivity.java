package com.example.loginregister;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private Button loginBtn;
    private TextView signUpTxt;
    private EditText emailInput;
    private EditText passwordInput;
    private DatabaseHelper myDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginBtn = findViewById(R.id.loginBtn);
        signUpTxt = findViewById(R.id.signUpTxt);
        myDbHelper = new DatabaseHelper(this);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(myDbHelper.verifyLogin(emailInput.getText().toString(), passwordInput.getText().toString(), getApplicationContext())){
                        Intent registerIntent = new Intent(LoginActivity.this, UserActivity.class);
                        registerIntent.putExtra("email", emailInput.getText().toString());
                        LoginActivity.this.startActivity(registerIntent);
                    }
            }
        });

        signUpTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Starts the register device activity.
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });
    }
}