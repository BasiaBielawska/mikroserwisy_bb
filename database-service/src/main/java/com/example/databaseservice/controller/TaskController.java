package com.example.databaseservice.controller;

import com.example.databaseservice.model.Task;
import com.example.databaseservice.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    public static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    public final TaskRepository repository;

    public TaskController(final TaskRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    ResponseEntity<?> createTask(@RequestBody @Valid Task toCreate) {
        Task result = repository.save(toCreate);
        return ResponseEntity.created(URI.create("/"+ result.getId())).body(result);
    }

    @GetMapping(params = {"!sort", "!page", "!size", "!description", "!deadline", "!state", "!deadlineTimeStart", "!deadlineBefore" })
    ResponseEntity<List<Task>> readAllTasks(){
        logger.warn("Exposing all the tasks!");
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping(params = {"!description", "!state", "!deadline"})
    ResponseEntity<List<Task>> readAllTasks(Pageable page){
        logger.info("Custom pageable");
        return ResponseEntity.ok(repository.findAll(page).getContent());
    }

    @GetMapping("/{id}")
    ResponseEntity<Task> readTask(@PathVariable int id){
        return repository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable int id, @RequestBody @Valid Task toUpdate) {
        if(!repository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        repository.findById(id)
                .ifPresent(task -> {
                    task.updateForm(toUpdate);
                });
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteTask(@PathVariable int id) {
        if(!repository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(params = {"!sort", "!page", "!size", "!deadline", "!state" })
    ResponseEntity<List<Task>> findTasksByDescription(@RequestParam String description) {
        if(!repository.existsByDescriptionContaining(description)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(repository.findByDescriptionContaining(description));
    }

    @GetMapping(params = {"!sort", "!page", "!size", "!description", "!deadline"})
    ResponseEntity<List<Task>> findTasksByState(@RequestParam String state) {
        if(state.equals("true") || state.equals("false") ){
            return ResponseEntity.ok(repository.findByDone(Boolean.valueOf(state)));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping(params = {"!sort", "!page", "!size", "!description", "!state" })
    ResponseEntity<List<Task>> findTasksByDeadline(@RequestParam String deadline) {
    if(!repository.existsByDeadline(LocalDateTime.parse(deadline))) {
        return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(repository.findAllByDeadline(LocalDateTime.parse(deadline)));
    }

    @GetMapping(params = {"!sort", "!page", "!size", "!description", "!state", "!deadline", "!deadlineTimeStart"})
    ResponseEntity<List<Task>> findTasksWithDeadlineBefore(@RequestParam String deadlineBefore) {
        if(!repository.existsByDeadlineBefore(LocalDateTime.parse(deadlineBefore))) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(repository.findAllByDeadlineBefore(LocalDateTime.parse(deadlineBefore)));
    }

    @GetMapping(params = {"!sort", "!page", "!size", "!description", "!state", "!deadline"})
    ResponseEntity<List<Task>> findTasksByDeadlineBetween(@RequestParam String deadlineTimeStart, @RequestParam String deadlineTimeEnd) {
        if(!repository.existsByDeadlineBetween(LocalDateTime.parse(deadlineTimeStart), LocalDateTime.parse(deadlineTimeEnd))) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(repository.findAllByDeadlineBetween(LocalDateTime.parse(deadlineTimeStart), LocalDateTime.parse(deadlineTimeEnd)));
    }
}
