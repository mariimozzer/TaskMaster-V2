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

        dbHelper = new DatabaseHelper(this);


        Intent intent = getIntent();
        String taskName = intent.getStringExtra("name");
        String taskDescription= intent.getStringExtra("description");
        String taskDueDate= intent.getStringExtra("dueDate");
        int taskPriority = intent.getIntExtra("priority", -1);
        String taskNotes = intent.getStringExtra("notes");



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


        Button updateButton = findViewById(R.id.updateButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                String description = descriptionEditText.getText().toString();
                String dueDate = dueDateEditText.getText().toString();
                int priority = Integer.parseInt(priorityEditText.getText().toString());
                String notes = notesEditText.getText().toString();



                dbHelper.updateTask(taskName,name, description, dueDate, priority, notes);
                Toast.makeText(TaskUpdate.this, "Task was successfully updated", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(TaskUpdate.this, MainActivity.class);
                startActivity(i);
            }
        });

        Button deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deleteTask(taskName);
                Toast.makeText(TaskUpdate.this, "Task was successfully deleted", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(TaskUpdate.this, MainActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
