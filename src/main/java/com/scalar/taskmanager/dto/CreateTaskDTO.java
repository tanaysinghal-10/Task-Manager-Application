package com.scalar.taskmanager.dto;


//DTO: Data Transfer Object


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateTaskDTO {

    String title;
    String description;
    String deadline;
}
