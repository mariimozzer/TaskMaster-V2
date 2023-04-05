/*
 * File Name: Registration.java
 * Description:   This class is responsible for handling user registration in the Taskmaster_v2 app.
 *
 * Project Revision:
 *      Guilherme Bueno, 2023.04.03: Created
 */


package com.example.taskmaster_v2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {
    // Initialize UI components
    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private Button mRegisterButton;
    private DatabaseHelper mDatabaseHelper;

    /*
     * Method called when the activity is first created.
     * It initializes the UI components and sets up a listener for the registration button.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mUsernameEditText = findViewById(R.id.et_username);
        mPasswordEditText = findViewById(R.id.et_password);
        mRegisterButton = findViewById(R.id.btn_register);
        mDatabaseHelper = new DatabaseHelper(this);

        // Set up a listener for the registration button
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mUsernameEditText.getText().toString().trim();
                String password = mPasswordEditText.getText().toString().trim();
                if (!username.isEmpty() && !password.isEmpty()) {
                    if (mDatabaseHelper.registerUser(username, password)) {
                        Toast.makeText(RegistrationActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(RegistrationActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegistrationActivity.this, "Please enter a username and password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

