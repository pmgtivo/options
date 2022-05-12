
package com.grow.derivatives.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "strikePrice",
    "callOption",
    "putOption"
})
public class OptionChainStrike {

    @JsonProperty("strikePrice")
    private Long strikePrice;
    @JsonProperty("callOption")
    private CallOption callOption;
    @JsonProperty("putOption")
    private PutOption putOption;

    @JsonProperty("strikePrice")
    public Long getStrikePrice() {
        return strikePrice;
    }

    @JsonProperty("strikePrice")
    public void setStrikePrice(Long strikePrice) {
        this.strikePrice = strikePrice;
    }

    @JsonProperty("callOption")
    public CallOption getCallOption() {
        return callOption;
    }

    @JsonProperty("callOption")
    public void setCallOption(CallOption callOption) {
        this.callOption = callOption;
    }

    @JsonProperty("putOption")
    public PutOption getPutOption() {
        return putOption;
    }

    @JsonProperty("putOption")
    public void setPutOption(PutOption putOption) {
        this.putOption = putOption;
    }

}
