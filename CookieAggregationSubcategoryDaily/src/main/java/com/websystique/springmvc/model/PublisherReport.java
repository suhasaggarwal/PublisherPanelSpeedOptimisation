package com.websystique.springmvc.model;

import java.util.ArrayList;
import java.util.List;

public class PublisherReport {

	
		private String date;
		
		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
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

	
		private String channelName;
		
		private String browser;
		
		public String getBrowser() {
			return browser;
		}

		public void setBrowser(String browser) {
			this.browser = browser;
		}

		public String getChannelName() {
			return channelName;
		}

		public void setChannelName(String channelName) {
			this.channelName = channelName;
		}

		public String getMobile_device_properties() {
			return mobile_device_properties;
		}

		public void setMobile_device_properties(String mobile_device_properties) {
			this.mobile_device_properties = mobile_device_properties;
		}

		public String getBrandname() {
			return brandname;
		}

		public void setBrandname(String brandname) {
			this.brandname = brandname;
		}


		private List<PublisherReport> audience_segment_data = new ArrayList<PublisherReport>();
		
		public List<PublisherReport> getAudience_segment_data() {
			return audience_segment_data;
		}

		public void setAudience_segment_data(List<PublisherReport> audience_segment_data) {
			this.audience_segment_data = audience_segment_data;
		}

		
		private String publisher_pages;
		
		private String time_of_day;
		
		private String audience_segment;
		
		private String city;
		
		private String postalcode;
		
		private String latitude_longitude;
		
		private String os;
		
		private String mobile_device_properties;

	    private String brandname;

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
	    
	    
	   

		
	}














