package com.example.taskmaster_v2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class TaskRVAdpter extends RecyclerView.Adapter<TaskRVAdpter.ViewHolder> {

    private ArrayList<TaskModel> taskArray;
    private Context context;
    private final int LOW_PRIORITY = 3;
    private final int MEDIUM_PRIORITY = 7;
    private final int HIGH_PRIORITY = 10;

    private final int LOW_COLOR = Color.GREEN;
    private final int MEDIUM_COLOR = Color.YELLOW;
    private final int HIGH_COLOR = Color.RED;

    public TaskRVAdpter(ArrayList<TaskModel> taskArray, Context context) {

        this.taskArray = taskArray;
        this.context = context;

    }

    @NonNull

    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_rv_item, parent, false);
        return new ViewHolder(view);

    }

    @Override

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TaskModel modal = taskArray.get(position);
        holder.name.setText(modal.getName());
        holder.description.setText(modal.getDescription());
        holder.dueDate.setText(modal.getDueDate());
        holder.priority.setText(String.valueOf(modal.getPriority()));
        holder.notes.setText(modal.getNotes());

        int priority = modal.getPriority();
        int color = Color.GREEN; // Default color for low priority
        if (priority >= LOW_PRIORITY && priority < MEDIUM_PRIORITY) {
            color = LOW_COLOR;
        } else if (priority >= MEDIUM_PRIORITY && priority < HIGH_PRIORITY) {
            color = MEDIUM_COLOR;
        } else if (priority >= HIGH_PRIORITY) {
            color = HIGH_COLOR;
        }
        holder.priority.setBackgroundColor(color);


        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                Intent i = new Intent(context, TaskUpdate.class);
                i.putExtra("name", modal.getName());
                i.putExtra("description", modal.getDescription());
                i.putExtra("dueDate", modal.getDueDate());
                i.putExtra("priority", modal.getPriority());
                i.putExtra("notes", modal.getNotes());
                context.startActivity(i);
            }

        });

    }
    @Override

    public int getItemCount() {

        return taskArray.size();

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name, description, dueDate, priority, notes;


        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            name = itemView.findViewById(R.id.TVname);
            description = itemView.findViewById(R.id.TVDescription);
            dueDate = itemView.findViewById(R.id.TVDueDate);
            priority = itemView.findViewById(R.id.TVPriority);
            notes = itemView.findViewById(R.id.TVNotes);

        }

    }

}