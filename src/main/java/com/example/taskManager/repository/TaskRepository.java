package com.example.taskManager.repository;

import com.example.taskManager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task>  findByCollectionsCollectionId(Long collectionId);


}
