
package com.grow.derivatives.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "symbol",
    "tsInMillis",
    "open",
    "high",
    "low",
    "close",
    "dayChange",
    "dayChangePerc",
    "value"
})
public class LivePrice {

    @JsonProperty("symbol")
    private String symbol;
    @JsonProperty("tsInMillis")
    private Long tsInMillis;
    @JsonProperty("open")
    private Double open;
    @JsonProperty("high")
    private Double high;
    @JsonProperty("low")
    private Double low;
    @JsonProperty("close")
    private Double close;
    @JsonProperty("dayChange")
    private Double dayChange;
    @JsonProperty("dayChangePerc")
    private Double dayChangePerc;
    @JsonProperty("value")
    private Double value;

    @JsonProperty("symbol")
    public String getSymbol() {
        return symbol;
    }

    @JsonProperty("symbol")
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @JsonProperty("tsInMillis")
    public Long getTsInMillis() {
        return tsInMillis;
    }

    @JsonProperty("tsInMillis")
    public void setTsInMillis(Long tsInMillis) {
        this.tsInMillis = tsInMillis;
    }

    @JsonProperty("open")
    public Double getOpen() {
        return open;
    }

    @JsonProperty("open")
    public void setOpen(Double open) {
        this.open = open;
    }

    @JsonProperty("high")
    public Double getHigh() {
        return high;
    }

    @JsonProperty("high")
    public void setHigh(Double high) {
        this.high = high;
    }

    @JsonProperty("low")
    public Double getLow() {
        return low;
    }

    @JsonProperty("low")
    public void setLow(Double low) {
        this.low = low;
    }

    @JsonProperty("close")
    public Double getClose() {
        return close;
    }

    @JsonProperty("close")
    public void setClose(Double close) {
        this.close = close;
    }

    @JsonProperty("dayChange")
    public Double getDayChange() {
        return dayChange;
    }

    @JsonProperty("dayChange")
    public void setDayChange(Double dayChange) {
        this.dayChange = dayChange;
    }

    @JsonProperty("dayChangePerc")
    public Double getDayChangePerc() {
        return dayChangePerc;
    }

    @JsonProperty("dayChangePerc")
    public void setDayChangePerc(Double dayChangePerc) {
        this.dayChangePerc = dayChangePerc;
    }

    @JsonProperty("value")
    public Double getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(Double value) {
        this.value = value;
    }

}
