package com.publisherdata.model;

import java.util.ArrayList;
import java.util.List;

import com.websystique.springmvc.model.Reports;

//Publisher Report Structure Defining Publisher Report Fields

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

		
		   public String getISP() {
				return ISP;
			}

			public void setISP(String iSP) {
				ISP = iSP;
			}

			public String getOrganisation() {
				return organisation;
			}

			public void setOrganisation(String organisation) {
				this.organisation = organisation;
			}


			private String ISP;
		    
		    private String organisation;
		
		
	
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

	    public String getReach() {
			return reach;
		}

		public void setReach(String reach) {
			this.reach = reach;
		}


		private String reach;
	    
		public String getPublisher_pages() {
			return publisher_pages;
		}

		public void setPublisher_pages(String publisher_pages) {
			this.publisher_pages = publisher_pages;
		}

		public String getTime_of_day() {
			return time_of_day;
		}

		public void setTime_of_day(String time_of_day) {
			this.time_of_day = time_of_day;
		}

		public String getPostalcode() {
			return postalcode;
		}

		public void setPostalcode(String postalcode) {
			this.postalcode = postalcode;
		}

		public String getLatitude_longitude() {
			return latitude_longitude;
		}

		public void setLatitude_longitude(String latitude_longitude) {
			this.latitude_longitude = latitude_longitude;
		}

		public String getCount() {
			return count;
		}

		public void setCount(String count) {
			this.count = count;
		}


		private String count;
	    
	    
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














