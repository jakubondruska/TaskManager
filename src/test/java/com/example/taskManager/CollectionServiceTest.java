package com.example.taskManager;

import com.example.taskManager.model.Collections;
import com.example.taskManager.model.Task;
import com.example.taskManager.service.CollectionsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class CollectionServiceTest {

    @Autowired
    public  CollectionsService collectionsService;


    @Test
    public void sortCollectionEmpty() {
        List<Collections> collectionsList = new ArrayList<>();
        String sortOrder = "nameDesc";



        collectionsService.sortCollections(collectionsList,sortOrder);

        Assertions.assertEquals(collectionsList, new ArrayList<Collections>());
    }

    @Test
    public void sortCollectionEmptySortOrderAsc() {
        List<Collections> collectionsList = new ArrayList<>
                (Arrays.asList(
                        new Collections(1L, "Zakub",Arrays.asList
                        (new Task(1L, "ok", "nook",
                                LocalDateTime.of(1010,5,5,1,1,1,5),true,"high",null))),

                        new Collections(1L, "peto", Arrays.asList
                                (new Task(1L, "okay", "nook",
                        LocalDateTime.of(1010,5,5,1,1,1,5),true,"high",null)))));

        List<Collections> collectionsExpectedList = new ArrayList<>
                (Arrays.asList(
                        new Collections(1L, "peto",Arrays.asList
                                (new Task(1L, "ok", "nook",
                                        LocalDateTime.of(1010,5,5,1,1,1,5),true,"high",null))),

                        new Collections(1L, "Zakub", Arrays.asList
                                (new Task(1L, "okay", "nook",
                                        LocalDateTime.of(1010,5,5,1,1,1,5),true,"high",null)))));

        String sortOrder = "nameAsc";


        collectionsService.sortCollections(collectionsList,sortOrder);

        Assertions.assertEquals(collectionsList, collectionsExpectedList);
    }

}
