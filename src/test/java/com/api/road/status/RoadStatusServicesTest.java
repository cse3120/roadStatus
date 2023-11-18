package com.api.road.status;

import com.api.road.status.configuration.RoadStatusConfiguration;
import com.api.road.status.response.ErrorResponse;
import com.api.road.status.response.RoadResponse;
import com.api.road.status.service.RoadStatusServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class RoadStatusServicesTest {

    @InjectMocks
    RoadStatusServiceImpl roadStatusService;
    @Mock
    RestTemplate testRestTemplate;

    @Mock
    RoadStatusConfiguration roadStatusConfiguration;
    @Mock
    ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        when(roadStatusConfiguration.getAppKey()).thenReturn("t12344");
        when(roadStatusConfiguration.getAppURL()).thenReturn("/rrr/eee");
    }

    @Test
    void testGetRoadStatusSuccess() throws JsonProcessingException {
        String response = "[\n" +
                "    {\n" +
                "        \"$type\": \"Tfl.Api.Presentation.Entities.RoadCorridor, Tfl.Api.Presentation.Entities\",\n" +
                "        \"id\": \"a2\",\n" +
                "        \"displayName\": \"A2\",\n" +
                "        \"statusSeverity\": \"Good\",\n" +
                "        \"statusSeverityDescription\": \"No Exceptional Delays\",\n" +
                "        \"bounds\": \"[[-0.0857,51.44091],[0.17118,51.49438]]\",\n" +
                "        \"envelope\": \"[[-0.0857,51.44091],[-0.0857,51.49438],[0.17118,51.49438],[0.17118,51.44091],[-0.0857,51.44091]]\",\n" +
                "        \"url\": \"/Road/a2\"\n" +
                "    }\n" +
                "]";
        ResponseEntity<String> stringResponseEntity=new ResponseEntity<>(response,HttpStatus.OK);
        when(testRestTemplate.exchange(anyString(),eq(HttpMethod.GET), any(HttpEntity.class), eq(String.class))).thenReturn(stringResponseEntity);
        List<RoadResponse> roadResponseList=new ArrayList<>();
        RoadResponse roadResponse=new RoadResponse();
        roadResponse.setDisplayName("A2");
        roadResponse.setStatusSeverity("Good");
        roadResponse.setDisplayName("No Exceptional Delays");
        roadResponseList.add(roadResponse);
        when(objectMapper.readValue(anyString(), any(TypeReference.class))).thenReturn(roadResponseList);
        when(objectMapper.createObjectNode()).thenReturn(new ObjectNode(new JsonNodeFactory(false)));
        when(objectMapper.createArrayNode()).thenReturn(new ArrayNode(new JsonNodeFactory(false)));
        List<String> roadIdList=new ArrayList<>();
        roadIdList.add("A2");
        roadIdList.add("A3");
        JsonNode objectNode = roadStatusService.getRoadStatus(roadIdList, null);
        assertEquals(1, objectNode.size());
    }


    @Test
    void testGetRoadFailure() throws JsonProcessingException {
        when(testRestTemplate.exchange(anyString(),eq(HttpMethod.GET), any(HttpEntity.class), eq(String.class))).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));
        when(objectMapper.createObjectNode()).thenReturn(new ObjectNode(new JsonNodeFactory(false)));
        List<String> roadIdList=new ArrayList<>();
        roadIdList.add("A2");
        roadIdList.add("A132");
        ErrorResponse errorResponse=new ErrorResponse();
        errorResponse.setType("Tfl.Api.Presentation.Entities.ApiError, Tfl.Api.Presentation.Entities");
        errorResponse.setTimestampUtc("2023-11-18T13:51:17.7662747Z");
        errorResponse.setHttpStatusCode("404");
        errorResponse.setHttpStatus("NotFound");
        errorResponse.setRelativeUri("/Road/A2,A132?app_key=aaddd");
        errorResponse.setMessage("The following road id is not recognised: A132");
        when(objectMapper.readValue(anyString(), eq(ErrorResponse.class))).thenReturn(errorResponse);
        JsonNode objectNode = roadStatusService.getRoadStatus(roadIdList, null);
        assertNotNull(objectNode);
    }
}