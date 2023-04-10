/*
 * File Name: MainActivity.java
 * Description: This activity allows the user to input task details, including name, description,
 * due date, priority.
 *
 * Project Revision:
 *      Sergio Toledo, 2023.03.30: Created
 */

package com.example.taskmaster_v2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateTaskActivity extends AppCompatActivity {

    // These private fields represent the EditText and Button views in the layout, and the DatabaseHelper for interacting with the database.
    private EditText nameEditText, descriptionEditText, dueDateEditText, priorityEditText, notesEditText;
    private Button createButton;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task);

        nameEditText = findViewById(R.id.nameEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        dueDateEditText = findViewById(R.id.dueDateEditText);
        priorityEditText = findViewById(R.id.priorityEditText);
        notesEditText = findViewById(R.id.notesEditText);
        createButton = findViewById(R.id.createButton);

        dbHelper = new DatabaseHelper(this);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTask();
            }
        });
    }

    // This function creates a new task by extracting the values from the EditText fields, creating a new instance of the TaskModel class,
    // setting its fields with the extracted values, and adding the task to the database using the DatabaseHelper field.
    // It also displays a toast message to indicate that the task has been created and finishes the activity.

    private void createTask() {
        String name = nameEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();
        String dueDate = dueDateEditText.getText().toString().trim();
        String priorityString = priorityEditText.getText().toString().trim();
        String notes = notesEditText.getText().toString().trim();

        //String used to show the validation errors
        StringBuilder errors = new StringBuilder();

        if (name.isEmpty()) {
            errors.append("- Please enter a task name\n");
        }
        if (description.isEmpty()) {
            errors.append("- Please enter a task description\n");
        }
        if (dueDate.isEmpty()) {
            errors.append("- Please enter a due date\n");
        }
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
            Toast.makeText(this, errors.toString(), Toast.LENGTH_LONG).show();
            return;
        }

        //All fields are valid, create the task
        TaskModel task = new TaskModel();
        task.setName(name);
        task.setDescription(description);
        task.setDueDate(dueDate);
        task.setPriority(Integer.parseInt(priorityString));
        task.setNotes(notes);

        dbHelper.addTask(task);

        Toast.makeText(this, "Task created successfully", Toast.LENGTH_SHORT).show();

        finish();
    }
}