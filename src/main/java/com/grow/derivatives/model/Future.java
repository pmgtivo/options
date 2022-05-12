
package com.grow.derivatives.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "contract",
    "contractDisplayName",
    "displayName",
    "symbol",
    "growwContractId",
    "price",
    "expiry",
    "searchId",
    "livePrice"
})
public class Future {

    @JsonProperty("contract")
    private String contract;
    @JsonProperty("contractDisplayName")
    private String contractDisplayName;
    @JsonProperty("displayName")
    private String displayName;
    @JsonProperty("symbol")
    private String symbol;
    @JsonProperty("growwContractId")
    private String growwContractId;
    @JsonProperty("price")
    private Long price;
    @JsonProperty("expiry")
    private String expiry;
    @JsonProperty("searchId")
    private String searchId;
    @JsonProperty("livePrice")
    private LivePrice__Future livePrice;

    @JsonProperty("contract")
    public String getContract() {
        return contract;
    }

    @JsonProperty("contract")
    public void setContract(String contract) {
        this.contract = contract;
    }

    @JsonProperty("contractDisplayName")
    public String getContractDisplayName() {
        return contractDisplayName;
    }

    @JsonProperty("contractDisplayName")
    public void setContractDisplayName(String contractDisplayName) {
        this.contractDisplayName = contractDisplayName;
    }

    @JsonProperty("displayName")
    public String getDisplayName() {
        return displayName;
    }

    @JsonProperty("displayName")
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @JsonProperty("symbol")
    public String getSymbol() {
        return symbol;
    }

    @JsonProperty("symbol")
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @JsonProperty("growwContractId")
    public String getGrowwContractId() {
        return growwContractId;
    }

    @JsonProperty("growwContractId")
    public void setGrowwContractId(String growwContractId) {
        this.growwContractId = growwContractId;
    }

    @JsonProperty("price")
    public Long getPrice() {
        return price;
    }

    @JsonProperty("price")
    public void setPrice(Long price) {
        this.price = price;
    }

    @JsonProperty("expiry")
    public String getExpiry() {
        return expiry;
    }

    @JsonProperty("expiry")
    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    @JsonProperty("searchId")
    public String getSearchId() {
        return searchId;
    }

    @JsonProperty("searchId")
    public void setSearchId(String searchId) {
        this.searchId = searchId;
    }

    @JsonProperty("livePrice")
    public LivePrice__Future getLivePrice() {
        return livePrice;
    }

    @JsonProperty("livePrice")
    public void setLivePrice(LivePrice__Future livePrice) {
        this.livePrice = livePrice;
    }

}
