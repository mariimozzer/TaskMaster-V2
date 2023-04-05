/*
 * File Name: Helper.java
 * Description: This class defines a static method showSameDayTasksPopup() to display a popup dialog when a task is due on the same
 * day as the current date. The method takes a Context object and a List of TaskModel objects as input,
 * and loops through the task list to check if any task's due date is the same as the current date. If a match is found,
 * the method creates an AlertDialog with a message indicating the task due today,
 * and displays it using the create() and show() methods.
 * due date, priority.
 *
 * Project Revision:
 *      Guilherme Bueno, 2023.04.01: Created
 */


package com.example.taskmaster_v2;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Helper {

    public static void showSameDayTasksPopup(Context context, List<TaskModel> taskList) {
        // Get today's date
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String todayStr = dateFormat.format(today);

        // Loop through all tasks
        for (TaskModel task : taskList) {
            // Check if task's due date is the same as today's date
            if (task.getDueDate().equals(todayStr)) {
                // Show a popup dialog indicating that there's a task due today
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Task Due Today");
                builder.setMessage("Task '" + task.getName() + "' is due today!");
                builder.setPositiveButton("OK", null);
                builder.create().show();
            }
        }
    }
}
