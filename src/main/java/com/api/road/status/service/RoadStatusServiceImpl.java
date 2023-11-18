package com.api.road.status.service;

import com.api.road.status.configuration.RoadStatusConfiguration;
import com.api.road.status.response.ErrorResponse;
import com.api.road.status.response.RoadResponse;
import com.api.road.status.response.RoadStatusResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoadStatusServiceImpl implements RoadStatusService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoadStatusServiceImpl.class);
    private static final String RESPONSE_STRING = "response";
    private final RoadStatusConfiguration roadAPIConfiguration;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public RoadStatusServiceImpl(RoadStatusConfiguration roadAPIConfiguration, RestTemplate restTemplate,
                                 ObjectMapper objectMapper) {
        this.roadAPIConfiguration = roadAPIConfiguration;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public JsonNode getRoadStatus(List<String> roadIdList, String appKey) {
        try {
            if (appKey == null) {
                appKey = roadAPIConfiguration.getAppKey();
            }
            String appURL = roadAPIConfiguration.getAppURL();
            String roadURL = UriComponentsBuilder.fromUriString(appURL)
                    .buildAndExpand(roadIdList != null ? String.join(",", roadIdList) : null, appKey)
                    .toUriString();
            LOGGER.info("Invoking Road API");
            ResponseEntity<String> response = restTemplate.exchange(roadURL, HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                LOGGER.info("Receiving successful response");
                TypeReference<List<RoadResponse>> typeReference = new TypeReference<>() {
                };
                return mapRoadResponse(objectMapper.readValue(response.getBody(), typeReference));
            }
        } catch (HttpClientErrorException httpClientErrorException) {
            return processNotFoundMessage(httpClientErrorException);
        } catch (JsonProcessingException e) {
            LOGGER.error("Exception while reading road api response");
            return getObjectNodeForException();
        }
        return null;
    }

    private ObjectNode getObjectNodeForException() {
        ObjectNode objectNode = objectMapper.createObjectNode();
        String responseValue = String.join("", "Road API response is not proper.");
        objectNode.put(RESPONSE_STRING, responseValue);
        return objectNode;
    }

    private ObjectNode processNotFoundMessage(HttpClientErrorException httpClientErrorException) {
        try {
            if (httpClientErrorException.getStatusCode() == HttpStatus.NOT_FOUND) {
                ErrorResponse errorResponse = objectMapper.readValue(httpClientErrorException.getResponseBodyAsString(), ErrorResponse.class);
                if (errorResponse.getMessage() != null) {
                    String[] splitMessage = errorResponse.getMessage().trim().split(":");
                    if (splitMessage != null && splitMessage.length > 1) {
                        String roadId = splitMessage[1].trim();
                        return getJsonNode(roadId, " is not a valid road.");
                    }
                }
            }
        } catch (JsonProcessingException e) {
            LOGGER.error("Exception while reading road api response");
            return getObjectNodeForException();
        }
        return null;
    }

    private ObjectNode getJsonNode(String roadId, String responseString) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        String responseValue = String.join("", String.join(",", roadId), responseString);
        objectNode.put(RESPONSE_STRING, responseValue);
        return objectNode;
    }

    private JsonNode mapRoadResponse(List<RoadResponse> roadResponseList) {
        if (roadResponseList != null && !roadResponseList.isEmpty()) {
            List<RoadStatusResponse> roadStatusResponseList = new ArrayList<>();
            for (RoadResponse roadResponse : roadResponseList) {
                RoadStatusResponse roadStatusResponse = new RoadStatusResponse();
                roadStatusResponse.setDisplayName(roadResponse.getDisplayName());
                roadStatusResponse.setStatusSeverity(roadResponse.getStatusSeverity());
                roadStatusResponse.setStatusSeverityDescription(roadResponse.getStatusSeverityDescription());
                roadStatusResponseList.add(roadStatusResponse);
            }
            if (roadStatusResponseList.size() == 1) {
                return getJsonObjectNode(roadStatusResponseList.get(0));
            } else {
                return buildResponseString(roadStatusResponseList);
            }
        }
        return null;
    }

    private ArrayNode buildResponseString(List<RoadStatusResponse> roadStatusResponseList) {
        ArrayNode arrayNode = objectMapper.createArrayNode();
        for (RoadStatusResponse roadStatusResponse : roadStatusResponseList) {
            ObjectNode responseObjectNode = getJsonObjectNode(roadStatusResponse);
            arrayNode.add(responseObjectNode);
        }
        return arrayNode;
    }

    private ObjectNode getJsonObjectNode(RoadStatusResponse roadStatusResponse) {
        ObjectNode responseObjectNode = objectMapper.createObjectNode();
        String responseString = "The status of the " +
                roadStatusResponse.getDisplayName() +
                " is " +
                roadStatusResponse.getStatusSeverity() +
                " and " +
                roadStatusResponse.getStatusSeverityDescription() +
                ".";
        responseObjectNode.put(RESPONSE_STRING, responseString);
        return responseObjectNode;
    }
}