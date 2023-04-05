/*
 * File Name: ViewTaskActivity.java
 * Description:   This class is responsible for handling add task and view task handler for the app.
 *
 * Project Revision:
 *      Mariana Mozzer, 2023.04.01: Created
 *      Guilherme Bueno, 2023.04.04: Revised, added filter feature
 */

package com.example.taskmaster_v2;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class ViewTaskActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private ArrayList<TaskModel> eventArrayList;    // to hold all the tasks retrieved from the database
    private ArrayList<TaskModel> filteredEventArrayList;  // to hold the tasks that match the search query
    private DatabaseHelper dbHandler;   // instance of the DatabaseHelper class to interact with the database
    private TaskRVAdpter taskRVAdpter;  // adapter for the recycler view
    private RecyclerView taskRV;    // recycler view to display the list of tasks


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_task);

        // initialize the array lists and database helper
        eventArrayList = new ArrayList<>();
        filteredEventArrayList = new ArrayList<>();
        dbHandler = new DatabaseHelper(ViewTaskActivity.this);

        // retrieve all the tasks from the database and add them to the eventArrayList
        eventArrayList = dbHandler.getAllTasks();

        // add all the tasks to the filteredEventArrayList initially
        filteredEventArrayList.addAll(eventArrayList);

        // initialize the adapter and recycler view
        taskRVAdpter = new TaskRVAdpter(filteredEventArrayList, ViewTaskActivity.this);
        taskRV = findViewById(R.id.idRVTasks);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewTaskActivity.this, RecyclerView.VERTICAL, false);
        taskRV.setLayoutManager(linearLayoutManager);
        taskRV.setAdapter(taskRVAdpter);

        // Call the method and pass in the task list
        // show a popup with tasks that have the same due date as today
        Helper.showSameDayTasksPopup(this, eventArrayList);
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
        // clear the filteredEventArrayList and add only the tasks that match the search query
        filteredEventArrayList.clear();
        for (TaskModel task : eventArrayList) {
            if (task.getName().toLowerCase().contains(newText.toLowerCase())) {
                filteredEventArrayList.add(task);
            }
        }
        // notify the adapter that the data set has changed
        taskRVAdpter.notifyDataSetChanged();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // sort the filteredEventArrayList by due date
            case R.id.action_order_by_date:
                Collections.sort(filteredEventArrayList, new Comparator<TaskModel>() {
                    @Override
                    public int compare(TaskModel t1, TaskModel t2) {
                        return t1.getDueDate().compareTo(t2.getDueDate());
                    }
                });
                // notify the adapter that the data set has changed
                taskRVAdpter.notifyDataSetChanged();
                return true;
            case R.id.action_order_by_priority:
                Collections.sort(filteredEventArrayList, new Comparator<TaskModel>() {
                    // This method is called when an item in the Options Menu is selected.
                    // It sorts the filtered task list by priority when the "Order by Priority" option is selected.
                    // It then notifies the adapter that the data has changed, and returns true to indicate that the event has been handled.
                    @Override
                    public int compare(TaskModel t1, TaskModel t2) {
                        return Integer.compare(t1.getPriority(), t2.getPriority());
                    }
                });
                taskRVAdpter.notifyDataSetChanged();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
