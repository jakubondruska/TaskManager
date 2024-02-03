package com.example.taskManager.model;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "task_manager")
public class Task {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String taskName;

    @Column(name = "description")
    private String description;

    @Column(name = "date_time")
    private LocalDateTime dateAndTime;

    @Column(name = "completed")
    private Boolean completed = false;

    @Column(name = "task_priority")
    private String taskPriority;


    @ManyToOne
    @JoinColumn(name = "collection_id")
    private Collections collections;

    public String getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(String taskPriority) {
        this.taskPriority = taskPriority;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskPriority='" + taskPriority + '\'' +
                '}';
    }

    public Task(Long id, String taskName, String description, LocalDateTime dateAndTime, Boolean completed, String taskPriority, Collections collections) {
        this.id = id;
        this.taskName = taskName;
        this.description = description;
        this.dateAndTime = dateAndTime;
        this.completed = completed;
        this.taskPriority = taskPriority;
        this.collections = collections;
    }



    public Task() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(LocalDateTime dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Collections getCollections() {
        return collections;
    }

    public void setCollections(Collections collections) {
        this.collections = collections;
    }



}






