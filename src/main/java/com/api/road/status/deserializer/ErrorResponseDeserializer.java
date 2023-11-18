package com.api.road.status.deserializer;

import com.api.road.status.response.ErrorResponse;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class ErrorResponseDeserializer extends StdDeserializer<ErrorResponse> {

    protected ErrorResponseDeserializer() {
        super(ErrorResponse.class);
    }

    @Override
    public ErrorResponse deserialize(JsonParser p, DeserializationContext context) throws IOException {
        JsonNode node = p.readValueAsTree();
        ErrorResponse errorResponse=new ErrorResponse();
        errorResponse.setType(node.get("$type").asText());
        errorResponse.setTimestampUtc(node.get("timestampUtc").asText());
        errorResponse.setExceptionType(node.get("exceptionType").asText());
        errorResponse.setHttpStatusCode(node.get("httpStatusCode").asText());
        errorResponse.setHttpStatus(node.get("httpStatus").asText());
        errorResponse.setRelativeUri(node.get("relativeUri").asText());
        errorResponse.setMessage(node.get("message").asText());
        return errorResponse;
    }


}
