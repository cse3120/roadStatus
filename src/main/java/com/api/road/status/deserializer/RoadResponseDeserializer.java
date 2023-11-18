package com.api.road.status.deserializer;

import com.api.road.status.response.RoadResponse;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class RoadResponseDeserializer extends StdDeserializer<RoadResponse> {

    public RoadResponseDeserializer() {
        super(RoadResponse.class);
    }

    @Override
    public RoadResponse deserialize(JsonParser p, DeserializationContext context) throws IOException {
        JsonNode node = p.readValueAsTree();
        RoadResponse roadResponse = new RoadResponse();
        roadResponse.setType(node.get("$type").asText());
        roadResponse.setId(node.get("id").asText());
        roadResponse.setDisplayName(node.get("displayName").asText());
        roadResponse.setStatusSeverity(node.get("statusSeverity").asText());
        roadResponse.setStatusSeverityDescription(node.get("statusSeverityDescription").asText());
        roadResponse.setBounds(parseDoubleArray(node.get("bounds")));
        roadResponse.setEnvelope(parseDoubleArray(node.get("envelope")));
        roadResponse.setUrl(node.get("url").asText());
        return roadResponse;
    }

    private double[][] parseDoubleArray(JsonNode node) {
        String stringArray = node.asText().replace("[[", "[").replace("]]", "]");
        String[] pairs = stringArray.split("],");
        double[][] doubleArray = new double[pairs.length][2];
        for (int i = 0; i < pairs.length; i++) {
            String[] values = pairs[i].replace("[", "").replace("]", "").split(",");
            doubleArray[i][0] = Double.parseDouble(values[0]);
            doubleArray[i][1] = Double.parseDouble(values[1]);
        }
        return doubleArray;
    }
}