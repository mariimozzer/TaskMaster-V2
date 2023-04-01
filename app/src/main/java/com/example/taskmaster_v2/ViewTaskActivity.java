package com.example.taskmaster_v2;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;


public class ViewTaskActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private ArrayList<TaskModel> eventArrayList;
    private ArrayList<TaskModel> filteredEventArrayList;
    private DatabaseHelper dbHandler;
    private TaskRVAdpter taskRVAdpter;
    private RecyclerView taskRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_task);
        eventArrayList = new ArrayList<>();
        filteredEventArrayList = new ArrayList<>();
        dbHandler = new DatabaseHelper(ViewTaskActivity.this);


        eventArrayList = dbHandler.getAllTasks();
        filteredEventArrayList.addAll(eventArrayList);
        taskRVAdpter = new TaskRVAdpter(filteredEventArrayList, ViewTaskActivity.this);
        taskRV = findViewById(R.id.idRVTasks);
        LinearLayoutManager linearLayoutManager = new
                LinearLayoutManager(ViewTaskActivity.this, RecyclerView.VERTICAL, false);
        taskRV.setLayoutManager(linearLayoutManager);
        taskRV.setAdapter(taskRVAdpter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        filteredEventArrayList.clear();
        for (TaskModel task : eventArrayList) {
            if (task.getName().toLowerCase().contains(newText.toLowerCase())) {
                filteredEventArrayList.add(task);
            }
        }
        taskRVAdpter.notifyDataSetChanged();
        return true;
    }

}
