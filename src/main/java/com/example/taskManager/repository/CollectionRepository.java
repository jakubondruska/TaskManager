package com.example.taskManager.repository;

import com.example.taskManager.model.Collections;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollectionRepository extends JpaRepository<Collections,Long> {
    List<Collections> findAllByOrderByCollectionNameAsc();
    List<Collections> findAllByOrderByCollectionNameDesc();
}
