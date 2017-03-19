package com.websystique.springmvc.service;



import java.util.List;
import java.util.Set;

import com.publisherdata.model.PublisherReport;
import com.websystique.springmvc.model.Reports;



public interface ReportService {
	
	List<PublisherReport> extractReports(long id, String dateRange);
	
	
	List<PublisherReport> extractReportsChannel(long id, String dateRange, String channel);
	 
//	List<PublisherReport> extractReportsChannelNames(long id, String dateRange, String channelname);
	 
    Set<String> extractChannelNames(long id,String dateRange);


}
