package com.api.road.status;

import com.api.road.status.service.RoadStatusService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RoadStatusIntegrationTest {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private RoadStatusService roadStatusService;

    private static HttpHeaders headers;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    public static void init() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    void testGetRoadStatus() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<JsonNode> response = testRestTemplate.exchange(
                createURLWithSingleRoad(), HttpMethod.GET, entity, JsonNode.class);
        JsonNode jsonNode = response.getBody();
        assert jsonNode != null;
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("{\"response\":\"The status of the A2 is Good and No Exceptional Delays.\"}", response.getBody().toString());
    }

    @Test
    void testGetAllRoadStatus() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<JsonNode> response = testRestTemplate.exchange(
                createURLWithAllRoad(), HttpMethod.GET, entity, JsonNode.class);
        JsonNode jsonNode = response.getBody();
        assert jsonNode != null;
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(23,response.getBody().size());
    }

    @Test
    void testGetInvalidRoadStatus() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<JsonNode> response = testRestTemplate.exchange(
                createURLWithInvalidRoad(), HttpMethod.GET, entity, JsonNode.class);
        JsonNode jsonNode = response.getBody();
        assert jsonNode != null;
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("{\"response\":\"A232 is not a valid road.\"}", response.getBody().toString());
    }

    private String createURLWithInvalidRoad() {
        return "http://localhost:" + port + "/roadstatus/A2,A232";
    }

    private String createURLWithSingleRoad() {
        return "http://localhost:" + port + "/roadstatus/A2";
    }

    private String createURLWithAllRoad() {
        return "http://localhost:" + port + "/roadstatus/";
    }
}
