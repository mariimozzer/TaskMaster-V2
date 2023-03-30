package com.example.taskmaster_v2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateTaskActivity extends AppCompatActivity {
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

    private void createTask() {
        String name = nameEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();
        String dueDate = dueDateEditText.getText().toString().trim();
        int priority = Integer.parseInt(priorityEditText.getText().toString().trim());
        String notes = notesEditText.getText().toString().trim();

        TaskModel task = new TaskModel();
        task.setName(name);
        task.setDescription(description);
        task.setDueDate(dueDate);
        task.setPriority(priority);
        task.setNotes(notes);

        dbHelper.addTask(task);

        Toast.makeText(this, "Task created successfully", Toast.LENGTH_SHORT).show();

        finish();
    }
}