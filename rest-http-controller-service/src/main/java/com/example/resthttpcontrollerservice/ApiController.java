package com.example.resthttpcontrollerservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/tasks")
public class ApiController {

    @Autowired
    private final RestTemplate restTemplate;

    public ApiController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping
    ResponseEntity<?> createTask(@RequestBody Object request) {
        return restTemplate.postForEntity("lb://database-service/tasks", request, Object.class);
    }

    @GetMapping(params = {"!sort", "!page", "!size", "!description", "!deadline", "!state", "!deadlineTimeStart", "!deadlineBefore" })
    ResponseEntity<?> readAllTasks() {
        return restTemplate.getForEntity("lb://database-service/tasks", Object.class);
    }

    @GetMapping("/{id}")
    ResponseEntity<?> readTask(@PathVariable int id) {
        return restTemplate.getForEntity("lb://database-service/tasks/{id}", Object.class, id);
    }

    @PutMapping("/{id}")
    void updateTask(@PathVariable int id, @RequestBody Object request) {
        restTemplate.put("lb://database-service/tasks/{id}", request, id);
    }

    @DeleteMapping("/{id}")
    void deleteTask(@PathVariable int id) {
        restTemplate.delete("lb://database-service/tasks/{id}", id);
    }

    @GetMapping(params = {"!sort", "!page", "!size", "!deadline", "!state" })
    ResponseEntity<?> findTasksByDescription(@RequestParam String description) {
        return restTemplate.getForEntity("lb://database-service/tasks?description={description}", Object.class, description);
    }

    @GetMapping(params = {"!sort", "!page", "!size", "!description", "!deadline"})
    ResponseEntity<?> findTasksByState(@RequestParam String state) {
        return restTemplate.getForEntity("lb://database-service/tasks?state={state}", Object.class, state);
    }

    @GetMapping(params = {"!sort", "!page", "!size", "!description", "!state" })
    ResponseEntity<?> findTasksByDeadline(@RequestParam String deadline) {
        return restTemplate.getForEntity("lb://database-service/tasks?deadline={deadline}", Object.class, deadline);
    }

    @GetMapping(params = {"!sort", "!page", "!size", "!description", "!state", "!deadline", "!deadlineTimeStart"})
    ResponseEntity<?> findTasksWithDeadlineBefore(@RequestParam String deadlineBefore) {
        return restTemplate.getForEntity("lb://database-service/tasks?deadlineBefore={deadlineBefore}", Object.class, deadlineBefore);
    }

    @GetMapping(params = {"!description", "!state", "!deadline", "!deadlineTimeStart"})
    ResponseEntity<?> pagingAllTasks(@RequestParam int page, @RequestParam int size) {
        String url = "http://database-service/tasks";
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                .queryParam("page", page)
                .queryParam("size",size);
        return restTemplate.getForEntity(builder.toUriString(), Object.class);
    }

    @GetMapping(params = {"!sort", "!page", "!size", "!description", "!state", "!deadline"})
    ResponseEntity<?> findTasksByDeadlineBetween(@RequestParam String deadlineTimeStart, @RequestParam String deadlineTimeEnd) {
        String url = "http://database-service/tasks";
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                .queryParam("deadlineTimeStart", deadlineTimeStart)
                .queryParam("deadlineTimeEnd",deadlineTimeEnd);
        return restTemplate.getForEntity(builder.toUriString(), Object.class);
    }

    @GetMapping(params = {"!description", "!state", "!deadline", "!deadlineTimeStart","!page", "!size"})
    ResponseEntity<?> sortAllTasks(@RequestParam String sort) {
        return restTemplate.getForEntity("lb://database-service/tasks?sort={sort}", Object.class, sort);
    }

}
