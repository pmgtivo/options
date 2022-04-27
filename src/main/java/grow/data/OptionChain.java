
package grow.data;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "strikePrice",
    "callOption",
    "putOption"
})
public class OptionChain {

    @JsonProperty("strikePrice")
    private Long strikePrice;
    @JsonProperty("callOption")
    private Option callOption;
    @JsonProperty("putOption")
    private Option putOption;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("strikePrice")
    public Long getStrikePrice() {
        return strikePrice;
    }

    @JsonProperty("strikePrice")
    public void setStrikePrice(Long strikePrice) {
        this.strikePrice = strikePrice;
    }

    @JsonProperty("callOption")
    public Option getCallOption() {
        return callOption;
    }

    @JsonProperty("callOption")
    public void setCallOption(Option callOption) {
        this.callOption = callOption;
    }

    @JsonProperty("putOption")
    public Option getPutOption() {
        return putOption;
    }

    @JsonProperty("putOption")
    public void setPutOption(Option putOption) {
        this.putOption = putOption;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
