package com.example.resthttpcontrollerservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
                                   @RequestParam(value = "description", required = false) String description,
                                   @RequestParam(value = "done", required = false) String done,
                                   @RequestParam(value = "page", required = false) String page,
                                   @RequestParam(value = "size", required = false) String size) {
        if(sort != null){
            return restTemplate.getForEntity("lb://database-service/tasks?sort={sort}", Object.class, sort);
        }
        if(description != null){
            return restTemplate.getForEntity("lb://database-service/tasks?description={description}", Object.class, description);
        }
        if(done != null){
            return restTemplate.getForEntity("lb://database-service/tasks?done={done}", Object.class, done);
        }
        if(page != null && (size == null)){
            return restTemplate.getForEntity("lb://database-service/tasks?pageNumber={page}", Object.class, page);
        }

        if(size != null && (page == null)){
            return restTemplate.getForEntity("lb://database-service/tasks?pageSize={size}", Object.class, size);
        }

        if((page != null)) {
            String url = "http://database-service/tasks";
            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                    .queryParam("pageNumber", page)
                    .queryParam("pageSize",size);
            return restTemplate.getForEntity(builder.toUriString(), Object.class);
        }

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

}
