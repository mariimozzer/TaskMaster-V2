/*
 * File Name: LoginActivity.java
 * Description:  is responsible for handling user authentication in the Taskmaster_v2 app.
 * It contains two input fields for the username and password, and two buttons -
 * one for logging in and another for registering a new user.
 *
 * Project Revision:
 *      Sergio Toledo, 2023.04.02: Created
 */


package com.example.taskmaster_v2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText usernameInput, passwordInput;
    Button loginButton, registrationButton;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // set up the input fields and buttons
        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        registrationButton = findViewById(R.id.registrationButton);

        // create a new database helper instance
        dbHelper = new DatabaseHelper(this);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get the username and password from the input fields
                String username = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();

                // check if the user credentials are valid
                if (dbHelper.checkUser(username, password)) {
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);

                } else {
                    // if the user credentials are invalid, display an error message
                    Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go to the registration activity
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

    }

}
