package com.api.road.status.response;

import com.api.road.status.deserializer.RoadResponseDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Arrays;
import java.util.Objects;

@JsonDeserialize(using = RoadResponseDeserializer.class)
public class RoadResponse {
    private String type;
    private String id;
    private String displayName;
    private String statusSeverity;
    private String statusSeverityDescription;
    private double[][] bounds;
    private double[][] envelope;
    private String url;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getStatusSeverity() {
        return statusSeverity;
    }

    public void setStatusSeverity(String statusSeverity) {
        this.statusSeverity = statusSeverity;
    }

    public String getStatusSeverityDescription() {
        return statusSeverityDescription;
    }

    public void setStatusSeverityDescription(String statusSeverityDescription) {
        this.statusSeverityDescription = statusSeverityDescription;
    }

    public double[][] getBounds() {
        return bounds;
    }

    public void setBounds(double[][] bounds) {
        this.bounds = bounds;
    }

    public double[][] getEnvelope() {
        return envelope;
    }

    public void setEnvelope(double[][] envelope) {
        this.envelope = envelope;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoadResponse that = (RoadResponse) o;
        return Objects.equals(type, that.type) && Objects.equals(id, that.id) && Objects.equals(displayName, that.displayName) && Objects.equals(statusSeverity, that.statusSeverity) && Objects.equals(statusSeverityDescription, that.statusSeverityDescription) && Arrays.equals(bounds, that.bounds) && Arrays.equals(envelope, that.envelope) && Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(type, id, displayName, statusSeverity, statusSeverityDescription, url);
        result = 31 * result + Arrays.hashCode(bounds);
        result = 31 * result + Arrays.hashCode(envelope);
        return result;
    }
}
