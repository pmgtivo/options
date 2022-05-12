
package com.grow.derivatives.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "refreshEnabled",
    "refreshIntervalInSecs",
    "socketEnabled"
})
public class OptionChainConfig {

    @JsonProperty("refreshEnabled")
    private Boolean refreshEnabled;
    @JsonProperty("refreshIntervalInSecs")
    private Long refreshIntervalInSecs;
    @JsonProperty("socketEnabled")
    private Boolean socketEnabled;

    @JsonProperty("refreshEnabled")
    public Boolean getRefreshEnabled() {
        return refreshEnabled;
    }

    @JsonProperty("refreshEnabled")
    public void setRefreshEnabled(Boolean refreshEnabled) {
        this.refreshEnabled = refreshEnabled;
    }

    @JsonProperty("refreshIntervalInSecs")
    public Long getRefreshIntervalInSecs() {
        return refreshIntervalInSecs;
    }

    @JsonProperty("refreshIntervalInSecs")
    public void setRefreshIntervalInSecs(Long refreshIntervalInSecs) {
        this.refreshIntervalInSecs = refreshIntervalInSecs;
    }

    @JsonProperty("socketEnabled")
    public Boolean getSocketEnabled() {
        return socketEnabled;
    }

    @JsonProperty("socketEnabled")
    public void setSocketEnabled(Boolean socketEnabled) {
        this.socketEnabled = socketEnabled;
    }

}
