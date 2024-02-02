package com.example.taskManager.controller;

import com.example.taskManager.model.Collections;
import com.example.taskManager.service.CollectionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
public class CollectionController {

    @Autowired
    private CollectionsService collectionsService;

    @GetMapping("/collections")
    public String getCollections(@RequestParam(name = "sortOrder", defaultValue = "nameAsc") String sortOrder, Model model) {
        List<Collections> viewAllCollections = collectionsService.getCollections();

        collectionsService.sortCollections(viewAllCollections,sortOrder);

        model.addAttribute("viewAllCollections", viewAllCollections);
        return "collections";
    }
    @GetMapping("/collections/{collectionId}/delete")
    public String deleteCollection(@PathVariable Long collectionId){
        collectionsService.deleteCollection(collectionId);
        return "redirect:/collections";
    }


    @GetMapping("/viewCollectionToCreate")
    public String getCollectionToCreate(Model model) {
        List<Collections> viewCollectionToCreate = collectionsService.getCollections();
        model.addAttribute("viewCollectionToCreate", viewCollectionToCreate);

        return "viewCollectionToCreate";


    }

    @PostMapping("/viewCollectionToCreate")
    public String createCollection(@RequestParam String collectionName,
                                                         RedirectAttributes redirectAttributes){

        Collections createCollection = collectionsService.createCollection(collectionName);
        redirectAttributes.addFlashAttribute("successMessage", "Collection added successfully");

        Long collectionId = createCollection.getCollectionId();


        return "redirect:/collections/" + collectionId + "/viewAllTasksInCollection";

    }





}
