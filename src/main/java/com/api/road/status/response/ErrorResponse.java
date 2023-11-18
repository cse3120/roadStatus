package com.api.road.status.response;

import com.api.road.status.deserializer.ErrorResponseDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Objects;

@JsonDeserialize(using = ErrorResponseDeserializer.class)
public class ErrorResponse {
    private String type;
    private String timestampUtc;
    private String exceptionType;
    private String httpStatusCode;
    private String httpStatus;
    private String relativeUri;
    private String message;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTimestampUtc() {
        return timestampUtc;
    }

    public void setTimestampUtc(String timestampUtc) {
        this.timestampUtc = timestampUtc;
    }

    public String getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
    }

    public String getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(String httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public String getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(String httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getRelativeUri() {
        return relativeUri;
    }

    public void setRelativeUri(String relativeUri) {
        this.relativeUri = relativeUri;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorResponse that = (ErrorResponse) o;
        return Objects.equals(type, that.type) && Objects.equals(timestampUtc, that.timestampUtc) && Objects.equals(exceptionType, that.exceptionType) && Objects.equals(httpStatusCode, that.httpStatusCode) && Objects.equals(httpStatus, that.httpStatus) && Objects.equals(relativeUri, that.relativeUri) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, timestampUtc, exceptionType, httpStatusCode, httpStatus, relativeUri, message);
    }
}
