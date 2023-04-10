/*
 * File Name: MainActivity.java
 * Description:   This class is responsible for handling add task and view task handler for the app.
 *
 * Project Revision:
 *      Mariana Mozzer, 2023.03.30: Created
 */

package com.example.taskmaster_v2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button addButton;
    private Button viewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the Buttons by their IDs
        addButton = findViewById(R.id.button_add_task);
        viewButton = findViewById(R.id.button_view_tasks);

        // Set click listeners for the Buttons
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateTaskActivity.class);
                startActivity(intent);
            }
        });

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ViewTaskActivity.class);
                startActivity(intent);
            }
        });

    }
}