package com.scalar.taskmanager.entities;

import lombok.Data;

import java.util.Date;
import java.util.List;


// To generate getters and setters, use Data class mapping

@Data
public class TaskEntity {

    private int id;
    private String title;
    private String description;
    private Date deadline;
    private boolean completed;

    //    private List<NoteEntity> notes;

}
