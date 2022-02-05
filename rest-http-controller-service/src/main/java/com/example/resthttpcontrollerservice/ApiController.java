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

    @GetMapping
    ResponseEntity<?> readAllTasks(@RequestParam(value = "sort", required = false) String sort,
                                   @RequestParam(value = "page", required = false) String page,
                                   @RequestParam(value = "size", required = false) String size,
                                   @RequestParam(value = "description", required = false) String description,
                                   @RequestParam(value = "done", required = false) String done,
                                   @RequestParam(value = "deadlineFrom", required = false) String deadlineFrom,
                                   @RequestParam(value = "deadlineTo", required = false) String deadlineTo) {

        String url = "http://database-service/tasks";
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);

        if(sort != null) {
            builder.queryParam("sort", sort);
        }
        if(page != null) {
            builder.queryParam("page", page);
        }
        if(size != null) {
            builder.queryParam("size", size);
        }
        if(description != null) {
            builder.queryParam("description", description);
        }
        if(done != null) {
            builder.queryParam("done", done);
        }
        if(deadlineFrom != null) {
            builder.queryParam("deadlineFrom", deadlineFrom);
        }
        if(deadlineTo != null) {
            builder.queryParam("deadlineTo", deadlineTo);
        }

        return restTemplate.getForEntity(builder.toUriString(), Object.class);
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

}
