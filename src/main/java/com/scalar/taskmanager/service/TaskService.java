package com.scalar.taskmanager.service;

import com.scalar.taskmanager.entities.TaskEntity;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Repository
public class TaskService {
    private ArrayList<TaskEntity> tasks = new ArrayList<>();
    private int taskId = 1;
    private final SimpleDateFormat deadlineFormatter = new SimpleDateFormat("yyyy-MM-dd");



    public TaskEntity addTask(String title, String description, String deadline) throws ParseException {
        TaskEntity task = new TaskEntity();
        task.setId(taskId);
        task.setTitle(title);
        task.setDescription(description);
        task.setDeadline(deadlineFormatter.parse(deadline));  // TODO: validate date format YYYY-MM-DD
        task.setCompleted(false);
        tasks.add(task);
        taskId++;

        return task;
    }

    public ArrayList<TaskEntity> getTasks()
    {
        return tasks;
    }

    public TaskEntity getTaskById(int id)
    {
        // New Approach
        // tasks.stream().findAny().filter(task -> task.getId() == id).orElse(null);

        // Old School Approach

        for(TaskEntity task: tasks)
        {
           if (task.getId() == id)
           {
               return task;
           }
        }
        return null;
    }

    public TaskEntity updateTask(int id, String description, String deadline, Boolean completed) throws ParseException{
        TaskEntity task = getTaskById(id);
        if(task == null)
        {
            return null;
        }
//        task.setTitle(title);
        if(description != null)
        {
            task.setDescription(description);
        }

        if (deadline != null)
        {
            task.setDeadline(deadlineFormatter.parse(deadline));
        }
        if(completed != null)
        {
            task.setCompleted(completed);
        }


        return task;

    }
}

