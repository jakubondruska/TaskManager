package com.example.taskManager.controller;

import com.example.taskManager.model.Task;
import com.example.taskManager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class TaskController {

    @Autowired
    private TaskService taskService;


    @GetMapping("/collections/{collectionId}/viewAllTasksInCollection")
    public String getAllTasks(@PathVariable Long collectionId,
                              @RequestParam(name = "sortOrder", defaultValue = "priority")
                              String sortOrder, Model model) {

        List<Task> viewAllTasksInCollection = taskService.getTasksByCollectionId(collectionId);


        taskService.sortTasks(sortOrder, viewAllTasksInCollection);

        model.addAttribute("viewAllTasksInCollection", viewAllTasksInCollection);
        model.addAttribute("collectionId", collectionId);

        return "viewAllTasksInCollection";
    }

    @GetMapping("/collections/{collectionId}/tasks")
    public String getTaskToCreate(@PathVariable Long collectionId, Model model) {
        List<Task> viewTaskToCreate = taskService.getTasksByCollectionId(collectionId);


        model.addAttribute("viewTaskToCreate", viewTaskToCreate);
        model.addAttribute("collectionId", collectionId.toString());
        model.addAttribute("task", new Task());

        return "tasks";
    }
    @GetMapping("/collectionBack")
    public String redirectToCollections() {
        return "redirect:/collections";
    }


    @PostMapping("/collections/{collectionId}/tasks")
    public String createTaskInCollection(@PathVariable Long collectionId,
                                         @ModelAttribute Task task) {

        taskService.createTask(collectionId,task);

        return "redirect:/collections/" +collectionId+ "/viewAllTasksInCollection";

    }

    @PostMapping("/collections/{collectionId}/tasks/markAsCompleted")
    public String toggleCompletion(@RequestParam(name = "completed", required = false)
                                   List<Long> completed, @PathVariable Long collectionId) {
        if (completed != null && !completed.isEmpty()) {
            for (Long taskId : completed) {
                taskService.updateMarkTaskAsCompleted(taskId);
            }
        }
        return "redirect:/collections/" + collectionId.toString() + "/viewAllTasksInCollection";
    }


    @GetMapping("collections/{collectionId}/tasks/{taskId}")
    public String editTaskInCollection(@PathVariable Long collectionId,
                                       @PathVariable Long taskId, Model model) {

        Optional<Task> editTaskInCollection = taskService.editTask(taskId);
        if (editTaskInCollection.isPresent()) {
            model.addAttribute("editTaskInCollection", editTaskInCollection.get());
            return "editTask";
        } else {
            return "redirect:/collections/" + collectionId + "/viewAllTasksInCollection";

        }
    }

    @PostMapping("/collections/{collectionId}/tasks/{taskId}")
    public String updateTaskInCollection(@PathVariable Long collectionId,
                                         @PathVariable Long taskId,
                                         @ModelAttribute Task updatedTask) {
        taskService.updateTask(taskId, updatedTask);
        return "redirect:/collections/" + collectionId + "/viewAllTasksInCollection";
    }


    @GetMapping("/collections/{collectionId}/tasks/{taskId}/delete")
    public String deleteTaskInCollection(@PathVariable Long collectionId,
                                         @PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return "redirect:/collections/" + collectionId.toString() + "/viewAllTasksInCollection";
    }

}