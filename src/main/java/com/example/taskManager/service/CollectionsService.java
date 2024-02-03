package com.example.taskManager.service;

import com.example.taskManager.model.Collections;
import com.example.taskManager.repository.CollectionRepository;
import com.example.taskManager.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class CollectionsService {


    private final CollectionRepository collectionsRepo;


    private final TaskRepository tasksRepo;

    public CollectionsService(CollectionRepository collectionsRepo, TaskRepository tasksRepo) {
        this.collectionsRepo = collectionsRepo;
        this.tasksRepo = tasksRepo;
    }


    public List<Collections> getCollections() {
        return collectionsRepo.findAll();
    }

    public Optional<Collections> getCollectionById(Long collectionId){
        return collectionsRepo.findById(collectionId);
    }

    @Transactional
    public Collections createCollection(String collectionName) {

        try {

            Collections newCollection = new Collections();
            newCollection.setCollectionName(collectionName);
            collectionsRepo.save(newCollection);
            return newCollection;

        } catch (Exception e) {
            throw new RuntimeException("Creating collection failed.", e);
        }
    }

    public void deleteCollection(Long collectionId) {

        Optional<Collections> optionalCollections = collectionsRepo.findById(collectionId);

        if (optionalCollections.isPresent()) {

            Collections collection = optionalCollections.get();
            tasksRepo.deleteAll(collection.getTaskToCollection());
            collectionsRepo.deleteById(collectionId);

        } else {
            throw new EntityNotFoundException("Collection with id" + collectionId + "not found");
        }
    }
    public void sortCollections(List<Collections> collectionsList,String sortOrder) {


        // getting task quantity in specific collection
        for (Collections collections : collectionsList) {
            int taskCount = collections.getTaskToCollection().size();
            collections.setTaskCount(taskCount);
        }

        if (sortOrder == null) {
            throw new NullPointerException("Sort order must be filed");
        }
        // collection sorting data
        switch (sortOrder) {
            case "nameDesc" -> collectionsList.sort(Comparator.comparing(Collections::getCollectionName).reversed());
            case "nameAsc" -> collectionsList.sort(Comparator.comparing(Collections::getCollectionName));
            case "numTaskAsc" -> collectionsList.sort(Comparator.comparingInt(Collections::getTaskCount));
            case "numTaskDesc" -> collectionsList.sort(Comparator.comparingInt(Collections::getTaskCount).reversed());
            default -> throw new IllegalArgumentException("Sort order not supported");
        }
    }



}
























