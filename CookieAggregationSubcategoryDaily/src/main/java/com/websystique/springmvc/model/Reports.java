package com.websystique.springmvc.model;

import java.util.ArrayList;
import java.util.List;



public class Reports {

	private String date;
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCampaign_id() {
		return campaign_id;
	}

	public void setCampaign_id(String campaign_id) {
		this.campaign_id = campaign_id;
	}

	public String getImpressions() {
		return impressions;
	}

	public void setImpressions(String impressions) {
		this.impressions = impressions;
	}

	public String getClicks() {
		return clicks;
	}

	public void setClicks(String clicks) {
		this.clicks = clicks;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getAudience_segment() {
		return audience_segment;
	}

	public void setAudience_segment(String audience_segment) {
		this.audience_segment = audience_segment;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getDevice_type() {
		return device_type;
	}

	public void setDevice_type(String device_type) {
		this.device_type = device_type;
	}

	private String campaign_id;
	
	private String impressions;
	
	private String clicks;
	
	private String channel;
	
	private List<Reports> audience_segment_data = new ArrayList<Reports>();
	
	public List<Reports> getAudience_segment_data() {
		return audience_segment_data;
	}

	public void setAudience_segment_data(List<Reports> audience_segment_data) {
		this.audience_segment_data = audience_segment_data;
	}

	private String audience_segment;
	
	private String city;
	
	private String os;
	
	private String device_type;

    private String conversions;
    
    private String reach = "0";
   
    public String getReach() {
		return reach;
	}

	public void setReach(String reach) {
		this.reach = reach;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	private String age;
    
    private String gender;
    
    
    public Double getCpm() {
		return cpm;
	}

	public void setCpm(Double cpm) {
		this.cpm = cpm;
	}

	public Double getCpc() {
		return cpc;
	}

	public void setCpc(Double cpc) {
		this.cpc = cpc;
	}

	public Double getCPConversion() {
		return cpconversion;
	}

	public void setCPConversion(Double cpconversion) {
		this.cpconversion = cpconversion;
	}

	private Double cpm = 0.0;
    
    private Double cpc = 0.0;
    
    private Double cpconversion = 0.0;
    
    private Double cpp = 0.0;
    
	
	public Double getCpp() {
		return cpp;
	}

	public void setCpp(Double cpp) {
		this.cpp = cpp;
	}

	public String getConversions() {
		return conversions;
	}

	public void setConversions(String conversions) {
		this.conversions = conversions;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	private String cost;
}
