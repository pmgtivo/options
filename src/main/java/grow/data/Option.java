
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
@JsonPropertyOrder({ "open", "high", "low", "close", "ltp", "dayChange", "dayChangePerc", "lowPriceRange",
		"highPriceRange", "volume", "totalBuyQty", "totalSellQty", "openInterest", "prevOpenInterest", "lastTradeQty",
		"lastTradeTime", "growwContractId", "contractDisplayName", "longDisplayName" })
public class Option {

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
	private Long openInterest;
	@JsonProperty("prevOpenInterest")
	private Long prevOpenInterest;
	@JsonProperty("lastTradeQty")
	private Long lastTradeQty;
	@JsonProperty("lastTradeTime")
	private Long lastTradeTime;
	@JsonProperty("growwContractId")
	private String growwContractId;
	@JsonProperty("contractDisplayName")
	private String contractDisplayName;
	@JsonProperty("longDisplayName")
	private String longDisplayName;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
	public Long getOpenInterest() {
		return openInterest;
	}

	@JsonProperty("openInterest")
	public void setOpenInterest(Long openInterest) {
		this.openInterest = openInterest;
	}

	@JsonProperty("prevOpenInterest")
	public Long getPrevOpenInterest() {
		return prevOpenInterest;
	}

	@JsonProperty("prevOpenInterest")
	public void setPrevOpenInterest(Long prevOpenInterest) {
		this.prevOpenInterest = prevOpenInterest;
	}

	@JsonProperty("lastTradeQty")
	public Long getLastTradeQty() {
		return lastTradeQty;
	}

	@JsonProperty("lastTradeQty")
	public void setLastTradeQty(Long lastTradeQty) {
		this.lastTradeQty = lastTradeQty;
	}

	@JsonProperty("lastTradeTime")
	public Long getLastTradeTime() {
		return lastTradeTime;
	}

	@JsonProperty("lastTradeTime")
	public void setLastTradeTime(Long lastTradeTime) {
		this.lastTradeTime = lastTradeTime;
	}

	@JsonProperty("growwContractId")
	public String getGrowwContractId() {
		return growwContractId;
	}

	@JsonProperty("growwContractId")
	public void setGrowwContractId(String growwContractId) {
		this.growwContractId = growwContractId;
	}

	@JsonProperty("contractDisplayName")
	public String getContractDisplayName() {
		return contractDisplayName;
	}

	@JsonProperty("contractDisplayName")
	public void setContractDisplayName(String contractDisplayName) {
		this.contractDisplayName = contractDisplayName;
	}

	@JsonProperty("longDisplayName")
	public String getLongDisplayName() {
		return longDisplayName;
	}

	@JsonProperty("longDisplayName")
	public void setLongDisplayName(String longDisplayName) {
		this.longDisplayName = longDisplayName;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	@Override
	public String toString() {
		return "Option [ltp=" + ltp + ", growwContractId=" + growwContractId + ", longDisplayName=" + longDisplayName
				+ "]";
	}

}
