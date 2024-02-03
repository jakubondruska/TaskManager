package com.example.taskManager.service;

import com.example.taskManager.model.Collections;
import com.example.taskManager.model.Task;
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
public class TaskService {

    public static final String HIGH = "high";
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CollectionRepository collectionRepository;


    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(Long taskId) {
        return taskRepository.findById(taskId);
    }

    @Transactional
    public void createTask(Long collectionId, Task task) {

        try {

            Collections collection = collectionRepository.findById(collectionId)
                    .orElseThrow(() -> new EntityNotFoundException("Collection not found with id: " + collectionId));

            task.setCollections(collection);
            task.setTaskName(task.getTaskName());
            task.setCompleted(task.getCompleted());
            task.setDescription(task.getDescription());
            task.setDateAndTime(task.getDateAndTime());

            task.setTaskPriority(task.getTaskPriority());

            taskRepository.save(task);

        } catch (Exception e) {
            throw new RuntimeException("Creating task failed.", e);
        }
    }

    public List<Task> getTasksByCollectionId(Long collectionId) {
        return taskRepository.findByCollectionsCollectionId(collectionId);
    }

    public void updateTask(Long taskId, Task updatedTask) {
        Task existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + taskId));
        existingTask.setTaskName(updatedTask.getTaskName());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setDateAndTime(updatedTask.getDateAndTime());
        taskRepository.save(existingTask);
    }

    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }


    public Optional<Task> editTask(Long taskId) {
        return taskRepository.findById(taskId);
    }


    public void updateMarkTaskAsCompleted(Long taskId) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);

        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setCompleted(!task.getCompleted());
            taskRepository.save(task);
        } else {
            throw new EntityNotFoundException("Task not found with id" + taskId);
        }
    }

    public void sortTasks(String sortOrder,List<Task> taskList) {


        switch (sortOrder) {
            case "nameDesc" ->
                    taskList.sort(Comparator.comparing(Task::getTaskName, Comparator.nullsLast(Comparator.reverseOrder())));
            case "nameAsc" ->
                    taskList.sort(Comparator.comparing(Task::getTaskName, Comparator.nullsLast(Comparator.naturalOrder())));
            case "priority" -> taskList.sort(Comparator.comparing(Task::getTaskPriority, (priority1, priority2) -> {
                if (HIGH.equalsIgnoreCase(priority1) && !HIGH.equalsIgnoreCase(priority2)) {
                    return -1;
                } else if (!HIGH.equalsIgnoreCase(priority1) && HIGH.equalsIgnoreCase(priority2)) {
                    return 1;
                } else {
                    return 0;
                }
            }));
        }
    }
}

