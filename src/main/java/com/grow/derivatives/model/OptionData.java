
package com.grow.derivatives.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "optionChain",
    "company",
    "livePrice",
    "futures",
    "billingDetails",
    "optionChainConfig"
})
public class OptionData {

    @JsonProperty("optionChain")
    private OptionChain optionChain;
    @JsonProperty("company")
    private Company company;
    @JsonProperty("livePrice")
    private LivePrice livePrice;
    @JsonProperty("futures")
    private List<Future> futures = null;
    @JsonProperty("billingDetails")
    private BillingDetails billingDetails;
    @JsonProperty("optionChainConfig")
    private OptionChainConfig optionChainConfig;

    @JsonProperty("optionChain")
    public OptionChain getOptionChain() {
        return optionChain;
    }

    @JsonProperty("optionChain")
    public void setOptionChain(OptionChain optionChain) {
        this.optionChain = optionChain;
    }

    @JsonProperty("livePrice")
    public LivePrice getLivePrice() {
        return livePrice;
    }

    @JsonProperty("livePrice")
    public void setLivePrice(LivePrice livePrice) {
        this.livePrice = livePrice;
    }

    @JsonProperty("futures")
    public List<Future> getFutures() {
        return futures;
    }

    @JsonProperty("futures")
    public void setFutures(List<Future> futures) {
        this.futures = futures;
    }

    @JsonProperty("billingDetails")
    public BillingDetails getBillingDetails() {
        return billingDetails;
    }

    @JsonProperty("billingDetails")
    public void setBillingDetails(BillingDetails billingDetails) {
        this.billingDetails = billingDetails;
    }

    @JsonProperty("optionChainConfig")
    public OptionChainConfig getOptionChainConfig() {
        return optionChainConfig;
    }

    @JsonProperty("optionChainConfig")
    public void setOptionChainConfig(OptionChainConfig optionChainConfig) {
        this.optionChainConfig = optionChainConfig;
    }

}
