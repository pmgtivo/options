
package grow.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "expiryDates",
    "currentExpiry",
    "expiryLotSize"
})
public class ExpiryDetailsDto {

    @JsonProperty("expiryDates")
    private List<String> expiryDates = null;
    @JsonProperty("currentExpiry")
    private String currentExpiry;
    @JsonProperty("expiryLotSize")
    private Long expiryLotSize;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("expiryDates")
    public List<String> getExpiryDates() {
        return expiryDates;
    }

    @JsonProperty("expiryDates")
    public void setExpiryDates(List<String> expiryDates) {
        this.expiryDates = expiryDates;
    }

    @JsonProperty("currentExpiry")
    public String getCurrentExpiry() {
        return currentExpiry;
    }

    @JsonProperty("currentExpiry")
    public void setCurrentExpiry(String currentExpiry) {
        this.currentExpiry = currentExpiry;
    }

    @JsonProperty("expiryLotSize")
    public Long getExpiryLotSize() {
        return expiryLotSize;
    }

    @JsonProperty("expiryLotSize")
    public void setExpiryLotSize(Long expiryLotSize) {
        this.expiryLotSize = expiryLotSize;
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
