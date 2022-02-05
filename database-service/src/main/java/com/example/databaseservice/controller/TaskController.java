package com.example.databaseservice.controller;

import com.example.databaseservice.model.Task;
import com.example.databaseservice.model.TaskSearchCriteria;
import com.example.databaseservice.repository.TaskCriteriaRepository;
import com.example.databaseservice.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/tasks")
public class TaskController {
    public static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    public final TaskRepository taskRepository;

    public final TaskCriteriaRepository taskCriteriaRepository;


    public TaskController(final TaskRepository taskRepository, TaskCriteriaRepository taskCriteriaRepository) {
        this.taskRepository = taskRepository;
        this.taskCriteriaRepository = taskCriteriaRepository;
    }

    @PostMapping
    ResponseEntity<?> createTask(@RequestBody @Valid Task toCreate) {
        Task result = taskRepository.save(toCreate);
        return ResponseEntity.created(URI.create("/tasks/"+ result.getId())).body(result);
    }

    @GetMapping
    ResponseEntity<Page<Task>> readAllTasks(TaskSearchCriteria taskSearchCriteria, Pageable page) {
        return new ResponseEntity<>(taskCriteriaRepository.findAllWithFilters(taskSearchCriteria, page), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<Task> readTask(@PathVariable int id) {
        return taskRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable int id, @RequestBody @Valid Task toUpdate) {
        if(!taskRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        taskRepository.findById(id)
                .ifPresent(task -> {
                    task.updateForm(toUpdate);
                });
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteTask(@PathVariable int id) {
        if(!taskRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        taskRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
