package com.api.road.status.service;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public interface RoadStatusService {

    JsonNode getRoadStatus(List<String> roadIdList, String appKey);
}
