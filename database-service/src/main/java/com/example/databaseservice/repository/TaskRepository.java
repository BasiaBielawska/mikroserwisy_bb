package com.example.databaseservice.repository;

import com.example.databaseservice.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Integer>, JpaSpecificationExecutor<Task> {

    List<Task> findAll();

    Page<Task> findAll(Pageable page);

    boolean existsById(Integer id);

    Optional<Task> findById(Integer id);

    Task save(Task entity);

}
