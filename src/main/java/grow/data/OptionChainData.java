
package grow.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "optionChains",
    "expiryDetailsDto"
})
@Generated("jsonschema2pojo")
public class OptionChainData {

    @JsonProperty("optionChains")
    private List<OptionChain> optionChains = null;
    @JsonProperty("expiryDetailsDto")
    private ExpiryDetailsDto expiryDetailsDto;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("optionChains")
    public List<OptionChain> getOptionChains() {
        return optionChains;
    }

    @JsonProperty("optionChains")
    public void setOptionChains(List<OptionChain> optionChains) {
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

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
