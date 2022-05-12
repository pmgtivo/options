
package com.grow.derivatives.model;

public class AdditionalProperties {

    private Object segment;
    private Object strikePrice;
    private String underlyingId;
    private Object underlyingAssetType;
    private Object expiry;
    private Object symbolDisplayName;
    private Object optionType;

    public Object getSegment() {
        return segment;
    }

    public void setSegment(Object segment) {
        this.segment = segment;
    }

    public Object getStrikePrice() {
        return strikePrice;
    }

    public void setStrikePrice(Object strikePrice) {
        this.strikePrice = strikePrice;
    }

    public String getUnderlyingId() {
        return underlyingId;
    }

    public void setUnderlyingId(String underlyingId) {
        this.underlyingId = underlyingId;
    }

    public Object getUnderlyingAssetType() {
        return underlyingAssetType;
    }

    public void setUnderlyingAssetType(Object underlyingAssetType) {
        this.underlyingAssetType = underlyingAssetType;
    }

    public Object getExpiry() {
        return expiry;
    }

    public void setExpiry(Object expiry) {
        this.expiry = expiry;
    }

    public Object getSymbolDisplayName() {
        return symbolDisplayName;
    }

    public void setSymbolDisplayName(Object symbolDisplayName) {
        this.symbolDisplayName = symbolDisplayName;
    }

    public Object getOptionType() {
        return optionType;
    }

    public void setOptionType(Object optionType) {
        this.optionType = optionType;
    }

}
