package com.example.loginregister;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText confirmEmail;
    private EditText password;
    private EditText confirmPassword;
    private DatabaseHelper myDbHelper;
    private Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        firstName = findViewById(R.id.nameInput);
        lastName = findViewById(R.id.surnameInput);
        email = findViewById(R.id.emailInput);
        confirmEmail = findViewById(R.id.confirmEmailInput);
        password = findViewById(R.id.passwordInput);
        confirmPassword = findViewById(R.id.confirmPasswordInput);
        myDbHelper = new DatabaseHelper(this);
        registerBtn = findViewById(R.id.submitBtn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                if(validateText(firstName) && validateText(lastName) && validateEmail(email) && validateEmail(confirmEmail) &&  validatePassword(password) && validatePassword(confirmPassword)){
                    if(myDbHelper.insertData(email.getText().toString(), firstName.getText().toString(), lastName.getText().toString(), password.getText().toString())){
                        Toast.makeText(getApplicationContext(), "Account created", Toast.LENGTH_SHORT).show();
                        Intent registerIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                        RegisterActivity.this.startActivity(registerIntent);
                    }else{
                        Toast.makeText(getApplicationContext(), "Failed to create account, please try again", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public boolean validateEmail(EditText input){

        // TODO check email is not already registered

        String emailInput = input.getText().toString().trim();

        if(emailInput.isEmpty()){
            input.setError("Field can't be empty");
            return false;
        } else if(!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            input.setError("Please enter a valid email address");
            return false;
        } else if(!checkMatch(email, confirmEmail)){
            email.setError("Emails don't match");
            confirmEmail.setError("Emails don't match");
            return false;
        } else {
            input.setError(null);
            return true;
        }
    }

    public boolean validatePassword(EditText input){

        // TODO password pattern constraints

        String passwordInput = input.getText().toString().trim();

        if(passwordInput.isEmpty()){
            input.setError("Field can't be empty");
            return false;
        }else if(!checkMatch(password, confirmPassword)) {
            password.setError("Passwords don't match");
            confirmPassword.setError("Passwords don't match");
            return false;
        } else{
            input.setError(null);
            return true;
        }
    }

    public boolean validateText(EditText input){
        String textInput = input.getText().toString().trim();

        if(textInput.isEmpty()){
            input.setError("Field can't be empty");
            return false;
        }else {
            input.setError(null);
            return true;
        }
    }

    public boolean checkMatch(EditText input1, EditText input2){
        if(!input1.getText().toString().equals(input2.getText().toString())) {
            return false;
        } else{
            return true;
        }
    }
}