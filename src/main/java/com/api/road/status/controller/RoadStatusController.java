package com.api.road.status.controller;

import com.api.road.status.service.RoadStatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
public class RoadStatusController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoadStatusController.class);
    private final RoadStatusService roadAPIService;

    @Autowired
    public RoadStatusController(RoadStatusService roadAPIService) {
        this.roadAPIService = roadAPIService;
    }

    @GetMapping("/roadstatus/{ids}")
    public ResponseEntity<Object> getRoadStatus(@PathVariable(name = "ids") List<String> ids, @RequestParam(name="app_key" ,required = false) String appKey) {
        LOGGER.info("Get Road");
        return new ResponseEntity<>(roadAPIService.getRoadStatus(ids,appKey), HttpStatus.OK);
    }
    @GetMapping("/roadstatus/")
    public ResponseEntity<Object> getAllRoadStatus(@RequestParam(name="app_key" ,required = false) String appKey) {
        LOGGER.info("Get Road");
        return new ResponseEntity<>(roadAPIService.getRoadStatus(Collections.emptyList(),appKey), HttpStatus.OK);
    }
}
