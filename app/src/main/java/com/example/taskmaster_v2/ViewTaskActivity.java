package com.example.taskmaster_v2;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;


public class ViewTaskActivity extends AppCompatActivity {

    private ArrayList<TaskModel> eventArrayList;
    private DatabaseHelper dbHandler;
    private TaskRVAdpter taskRVAdpter;
    private RecyclerView taskRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_task);
        eventArrayList = new ArrayList<>();
        dbHandler = new DatabaseHelper(ViewTaskActivity.this);


        eventArrayList = dbHandler.getAllTasks();
        taskRVAdpter = new TaskRVAdpter(eventArrayList, ViewTaskActivity.this);
        taskRV = findViewById(R.id.idRVTasks);
        LinearLayoutManager linearLayoutManager = new
                LinearLayoutManager(ViewTaskActivity.this, RecyclerView.VERTICAL, false);
        taskRV.setLayoutManager(linearLayoutManager);
        taskRV.setAdapter(taskRVAdpter);
    }

}
