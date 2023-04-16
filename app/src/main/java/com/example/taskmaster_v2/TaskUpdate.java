/*
 * File Name: TaskUpdate.java
 * Description: This class is responsible for handling the update, delete, and share functionalities of a task in the app.
 *
 * Project Revision:
 *      Mariana Mozzer, 2023.04.02: Created
 *      Mariana Mozzer, 2023.04.16: Validation added
 */



package com.example.taskmaster_v2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TaskUpdate extends AppCompatActivity {

    private EditText nameEditText;
    private EditText descriptionEditText;
    private EditText dueDateEditText;
    private EditText priorityEditText;
    private EditText notesEditText;
    private DatabaseHelper dbHelper;
    private TaskModel currentTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_task);


        // Create a new database helper to interact with the database
        dbHelper = new DatabaseHelper(this);

        // Get the task details passed from the previous activity through an intent
        Intent intent = getIntent();
        String taskName = intent.getStringExtra("name");
        String taskDescription= intent.getStringExtra("description");
        String taskDueDate= intent.getStringExtra("dueDate");
        int taskPriority = intent.getIntExtra("priority", -1);
        String taskNotes = intent.getStringExtra("notes");


        // Set the current task details to the UI elements for display
        nameEditText = findViewById(R.id.nameEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        dueDateEditText = findViewById(R.id.dueDateEditText);
        priorityEditText = findViewById(R.id.priorityEditText);
        notesEditText = findViewById(R.id.notesEditText);

            nameEditText.setText(taskName);
            descriptionEditText.setText(taskDescription);
            dueDateEditText.setText(taskDueDate);
            priorityEditText.setText(String.valueOf(taskPriority));
            notesEditText.setText(taskNotes);

        // Add an update button listener to update the task details in the database
        Button updateButton = findViewById(R.id.updateButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the updated task details from the UI elements
                String name = nameEditText.getText().toString();
                String description = descriptionEditText.getText().toString();
                String dueDate = dueDateEditText.getText().toString();
                String priorityString = priorityEditText.getText().toString().trim();
                String notes = notesEditText.getText().toString();

                StringBuilder errors = new StringBuilder();

                //Validate if empty
                if (name.isEmpty()) {
                    errors.append("- Please enter a task name\n");
                }
                if (description.isEmpty()) {
                    errors.append("- Please enter a task description\n");
                }

                // Validate due date format
                String datePattern = "\\d{4}-\\d{2}-\\d{2}";
                if (dueDate.isEmpty()) {
                    errors.append("- Please enter a due date\n");
                } else if (!dueDate.matches(datePattern)) {
                    errors.append("- Please enter a valid due date in the format yyyy-mm-dd\n");
                }

                //Validate if empty and lenght
                if (priorityString.isEmpty()) {
                    errors.append("- Please enter a task priority\n");
                } else {
                    try {
                        int priority = Integer.parseInt(priorityString);
                        if (priority < 1 || priority > 10) {
                            errors.append("- Priority must be between 1 and 10\n");
                        }
                    } catch (NumberFormatException e) {
                        errors.append("- Please enter a valid priority\n");
                    }
                }

                // If there are any errors, display them and return without creating the task
                if (errors.length() > 0) {
                    Toast.makeText(TaskUpdate.this, errors.toString(), Toast.LENGTH_LONG).show();
                    return;

                }


                // Update the task details in the database
                dbHelper.updateTask(taskName,name, description, dueDate, priorityString, notes);
                // Display a success message to the user
                Toast.makeText(TaskUpdate.this, "Task was successfully updated", Toast.LENGTH_SHORT).show();
                // Navigate back to the main activity
                Intent i = new Intent(TaskUpdate.this, MainActivity.class);
                startActivity(i);
            }
        });

        // Add a delete button listener to delete the task from the database
        Button deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delete the task from the database
                dbHelper.deleteTask(taskName);
                // Display a success message to the user
                Toast.makeText(TaskUpdate.this, "Task was successfully deleted", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(TaskUpdate.this, MainActivity.class);
                startActivity(i);
            }
        });

        // Add a share button listener to share the task details with other apps
        Button shareButton = findViewById(R.id.shareButton);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                String description = descriptionEditText.getText().toString();
                String dueDate = dueDateEditText.getText().toString();
                int priority = Integer.parseInt(priorityEditText.getText().toString());
                String notes = notesEditText.getText().toString();

                String shareText = "Task name: " + name
                        + "\nDescription: " + description
                        + "\nDue date: " + dueDate
                        + "\nPriority: " + priority
                        + "\nNotes: " + notes;

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Task information");
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
                startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
        });


    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
