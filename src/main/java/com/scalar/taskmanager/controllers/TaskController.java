package com.scalar.taskmanager.controllers;

import com.scalar.taskmanager.dto.CreateTaskDTO;
import com.scalar.taskmanager.dto.ErrorResponseDTO;
import com.scalar.taskmanager.dto.TaskResoponseDTO;
import com.scalar.taskmanager.dto.UpdateTaskDTO;
import com.scalar.taskmanager.entities.TaskEntity;
import com.scalar.taskmanager.service.NotesService;
import com.scalar.taskmanager.service.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

// RequestMapping annotation will add global mapping
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final NotesService notesService;
    private ModelMapper modelMapper = new ModelMapper();

    public TaskController(TaskService taskService, NotesService notesService)

    {
        this.taskService = taskService;
        this.notesService = notesService;
    }
    @GetMapping("")
    public ResponseEntity<List<TaskEntity>> getTasks()
    {
        var tasks = taskService.getTasks();

        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResoponseDTO> getTaskById(@PathVariable("id") Integer id)
    {
        var task = taskService.getTaskById(id);
        var notes = notesService.getNotesForTask(id);
        if(task == null)
        {
            return ResponseEntity.notFound().build();
        }
//        task.setNotes(notes);
        var taskResponse = modelMapper.map(task, TaskResoponseDTO.class);
        taskResponse.setNotes(notes);
        return ResponseEntity.ok(taskResponse);
    }



    @PostMapping("")
    public ResponseEntity<TaskEntity> addTask(@RequestBody CreateTaskDTO body) throws ParseException {
        var task = taskService.addTask(body.getTitle(), body.getDescription(), body.getDeadline());

        return ResponseEntity.ok(task);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<TaskEntity> updateTask(@PathVariable("id") Integer id, @RequestBody UpdateTaskDTO body) throws ParseException{
        var task = taskService.updateTask(id, body.getDescription(), body.getDeadline(), body.getCompleted());
        if(task == null)
        {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(task);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleErrors(Exception e)
    {
        if(e instanceof ParseException)
        {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO("Invalid Date format"));
        }
        e.printStackTrace();
        return ResponseEntity.internalServerError().body(new ErrorResponseDTO("Internal Server Error"));
    }



}
