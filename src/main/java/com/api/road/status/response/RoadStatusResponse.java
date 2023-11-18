package com.api.road.status.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

public class RoadStatusResponse {
    @Schema(defaultValue = "A2")
    private String displayName;
    @Schema(defaultValue = "Good")
    private String statusSeverity;
    @Schema(defaultValue = "No Exceptional Delays")
    private String statusSeverityDescription;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoadStatusResponse that = (RoadStatusResponse) o;
        return Objects.equals(displayName, that.displayName) && Objects.equals(statusSeverity, that.statusSeverity) && Objects.equals(statusSeverityDescription, that.statusSeverityDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(displayName, statusSeverity, statusSeverityDescription);
    }
}