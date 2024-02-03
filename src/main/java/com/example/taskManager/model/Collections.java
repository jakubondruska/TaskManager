package com.example.taskManager.model;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "collections_db")
public class Collections {

    public Collections(Long collectionId, String collectionName, List<Task> taskToCollection) {
        this.collectionId = collectionId;
        this.collectionName = collectionName;
        this.taskToCollection = taskToCollection;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long collectionId;

    @Column(name = "collection_name")
    private String collectionName;


    @OneToMany(mappedBy = "collections", cascade = CascadeType.ALL)
    private List<Task> taskToCollection = new ArrayList<>();

    public Collections() {
    }

    public Long getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Long collectionId) {
        this.collectionId = collectionId;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }


    public List<Task> getTaskToCollection() {
        return taskToCollection;
    }

    public void setTaskToCollection(List<Task> taskToCollection) {
        this.taskToCollection = taskToCollection;
    }
    
    public void setTaskCount(int taskCount) {
    }

    public int getTaskCount() {
        return taskToCollection.size();
    }

}






