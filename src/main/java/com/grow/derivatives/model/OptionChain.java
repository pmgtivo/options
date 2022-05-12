
package com.grow.derivatives.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "optionChains",
    "expiryDetailsDto"
})
public class OptionChain {

    @JsonProperty("optionChains")
    private List<OptionChainStrike> optionChains = null;
    @JsonProperty("expiryDetailsDto")
    private ExpiryDetailsDto expiryDetailsDto;

    @JsonProperty("optionChains")
    public List<OptionChainStrike> getOptionChains() {
        return optionChains;
    }

    @JsonProperty("optionChains")
    public void setOptionChains(List<OptionChainStrike> optionChains) {
        this.optionChains = optionChains;
    }

    @JsonProperty("expiryDetailsDto")
    public ExpiryDetailsDto getExpiryDetailsDto() {
        return expiryDetailsDto;
    }

    @JsonProperty("expiryDetailsDto")
    public void setExpiryDetailsDto(ExpiryDetailsDto expiryDetailsDto) {
        this.expiryDetailsDto = expiryDetailsDto;
    }

}
