
package com.grow.derivatives.model;

import javax.annotation.Nullable;

public class Company {

	private String isin;
	private String companyName;
	private String growwContractId;
	private String searchId;
	private String nseScriptCode;
	private Boolean isBuyAllowed;
	private Boolean isSellAllowed;
	private String companyShortName;
	private String companyStatus;
	private String logoUrl;
	private String equityType;
	private Long nseMarketLot;
	@Nullable
	private String isNseTradable;

	public String getGrowwContractId() {
		return growwContractId;
	}

	public void setGrowwContractId(String growwContractId) {
		this.growwContractId = growwContractId;
	}
	
	public String getIsNseTradable() {
		return isNseTradable;
	}

	public void setIsNseTradable(String isNseTradable) {
		this.isNseTradable = isNseTradable;
	}

	private AdditionalProperties additionalProperties;

	public String getIsin() {
		return isin;
	}

	public void setIsin(String isin) {
		this.isin = isin;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getSearchId() {
		return searchId;
	}

	public void setSearchId(String searchId) {
		this.searchId = searchId;
	}

	public String getNseScriptCode() {
		return nseScriptCode;
	}

	public void setNseScriptCode(String nseScriptCode) {
		this.nseScriptCode = nseScriptCode;
	}

	public Boolean getIsBuyAllowed() {
		return isBuyAllowed;
	}

	public void setIsBuyAllowed(Boolean isBuyAllowed) {
		this.isBuyAllowed = isBuyAllowed;
	}

	public Boolean getIsSellAllowed() {
		return isSellAllowed;
	}

	public void setIsSellAllowed(Boolean isSellAllowed) {
		this.isSellAllowed = isSellAllowed;
	}

	public String getCompanyShortName() {
		return companyShortName;
	}

	public void setCompanyShortName(String companyShortName) {
		this.companyShortName = companyShortName;
	}

	public String getCompanyStatus() {
		return companyStatus;
	}

	public void setCompanyStatus(String companyStatus) {
		this.companyStatus = companyStatus;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getEquityType() {
		return equityType;
	}

	public void setEquityType(String equityType) {
		this.equityType = equityType;
	}

	public Long getNseMarketLot() {
		return nseMarketLot;
	}

	public void setNseMarketLot(Long nseMarketLot) {
		this.nseMarketLot = nseMarketLot;
	}

	public AdditionalProperties getAdditionalProperties() {
		return additionalProperties;
	}

	public void setAdditionalProperties(AdditionalProperties additionalProperties) {
		this.additionalProperties = additionalProperties;
	}

}
