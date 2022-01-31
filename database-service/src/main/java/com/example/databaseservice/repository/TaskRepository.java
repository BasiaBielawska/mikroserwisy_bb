package com.example.databaseservice.repository;

import com.example.databaseservice.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findAll();

    Page<Task> findAll(Pageable page);

    boolean existsById(Integer id);

    boolean existsByDescriptionContaining(String description);

    boolean existsByDeadline(LocalDateTime deadline);

    boolean existsByDeadlineBefore(LocalDateTime deadline);

    boolean existsByDeadlineBetween(LocalDateTime deadlineTimeStart, LocalDateTime deadlineTimeEnd);

    Optional<Task> findById(Integer id);

    Task save(Task entity);

    List<Task> findByDone(boolean state);

    List<Task> findByDescriptionContaining(String description);

    List<Task> findAllByDeadline(LocalDateTime deadline);

    List<Task> findAllByDeadlineBefore(LocalDateTime deadline);

    List<Task> findAllByDeadlineBetween(LocalDateTime deadlineTimeStart, LocalDateTime deadlineTimeEnd);

}
