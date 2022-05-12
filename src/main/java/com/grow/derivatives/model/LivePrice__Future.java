
package com.grow.derivatives.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "type",
    "symbol",
    "tsInMillis",
    "open",
    "high",
    "low",
    "close",
    "ltp",
    "dayChange",
    "dayChangePerc",
    "lowPriceRange",
    "highPriceRange",
    "volume",
    "totalBuyQty",
    "totalSellQty",
    "openInterest",
    "prevOpenInterest",
    "oiDayChange",
    "oiDayChangePerc"
})
public class LivePrice__Future {

    @JsonProperty("type")
    private String type;
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
    @JsonProperty("ltp")
    private Double ltp;
    @JsonProperty("dayChange")
    private Double dayChange;
    @JsonProperty("dayChangePerc")
    private Double dayChangePerc;
    @JsonProperty("lowPriceRange")
    private Double lowPriceRange;
    @JsonProperty("highPriceRange")
    private Double highPriceRange;
    @JsonProperty("volume")
    private Long volume;
    @JsonProperty("totalBuyQty")
    private Double totalBuyQty;
    @JsonProperty("totalSellQty")
    private Double totalSellQty;
    @JsonProperty("openInterest")
    private Double openInterest;
    @JsonProperty("prevOpenInterest")
    private Double prevOpenInterest;
    @JsonProperty("oiDayChange")
    private Double oiDayChange;
    @JsonProperty("oiDayChangePerc")
    private Double oiDayChangePerc;

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

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

    @JsonProperty("ltp")
    public Double getLtp() {
        return ltp;
    }

    @JsonProperty("ltp")
    public void setLtp(Double ltp) {
        this.ltp = ltp;
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

    @JsonProperty("lowPriceRange")
    public Double getLowPriceRange() {
        return lowPriceRange;
    }

    @JsonProperty("lowPriceRange")
    public void setLowPriceRange(Double lowPriceRange) {
        this.lowPriceRange = lowPriceRange;
    }

    @JsonProperty("highPriceRange")
    public Double getHighPriceRange() {
        return highPriceRange;
    }

    @JsonProperty("highPriceRange")
    public void setHighPriceRange(Double highPriceRange) {
        this.highPriceRange = highPriceRange;
    }

    @JsonProperty("volume")
    public Long getVolume() {
        return volume;
    }

    @JsonProperty("volume")
    public void setVolume(Long volume) {
        this.volume = volume;
    }

    @JsonProperty("totalBuyQty")
    public Double getTotalBuyQty() {
        return totalBuyQty;
    }

    @JsonProperty("totalBuyQty")
    public void setTotalBuyQty(Double totalBuyQty) {
        this.totalBuyQty = totalBuyQty;
    }

    @JsonProperty("totalSellQty")
    public Double getTotalSellQty() {
        return totalSellQty;
    }

    @JsonProperty("totalSellQty")
    public void setTotalSellQty(Double totalSellQty) {
        this.totalSellQty = totalSellQty;
    }

    @JsonProperty("openInterest")
    public Double getOpenInterest() {
        return openInterest;
    }

    @JsonProperty("openInterest")
    public void setOpenInterest(Double openInterest) {
        this.openInterest = openInterest;
    }

    @JsonProperty("prevOpenInterest")
    public Double getPrevOpenInterest() {
        return prevOpenInterest;
    }

    @JsonProperty("prevOpenInterest")
    public void setPrevOpenInterest(Double prevOpenInterest) {
        this.prevOpenInterest = prevOpenInterest;
    }

    @JsonProperty("oiDayChange")
    public Double getOiDayChange() {
        return oiDayChange;
    }

    @JsonProperty("oiDayChange")
    public void setOiDayChange(Double oiDayChange) {
        this.oiDayChange = oiDayChange;
    }

    @JsonProperty("oiDayChangePerc")
    public Double getOiDayChangePerc() {
        return oiDayChangePerc;
    }

    @JsonProperty("oiDayChangePerc")
    public void setOiDayChangePerc(Double oiDayChangePerc) {
        this.oiDayChangePerc = oiDayChangePerc;
    }

}
