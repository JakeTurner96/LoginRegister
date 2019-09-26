package com.example.loginregister;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class UserActivity extends AppCompatActivity {

    private Button deleteBtn;
    private String userEmail;
    private TextView currentUser;
    private DatabaseHelper myDbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        getSupportActionBar().hide();

        myDbhelper = new DatabaseHelper(this);
        deleteBtn = findViewById(R.id.deleteAccountBtn);
        currentUser = findViewById(R.id.currentUserTxt);

        Intent intent = getIntent();
        userEmail = intent.getStringExtra("email");
        currentUser.setText(userEmail);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDeleteUser();
            }
        });
    }

    public void confirmDeleteUser(){
        new AlertDialog.Builder(this)
                .setTitle("Warning!")
                .setMessage("Are you sure you want to delete your account?")
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Integer deletedRows = myDbhelper.deleteData(userEmail);
                        if(deletedRows > 0){
                            Toast.makeText(getApplicationContext(), "User deleted", Toast.LENGTH_SHORT).show();
                            Intent registerIntent = new Intent(UserActivity.this, LoginActivity.class);
                            UserActivity.this.startActivity(registerIntent);
                        }else {
                            Toast.makeText(getApplicationContext(), "Failed to delete user, please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .create().show();
    }
}