package com.cuberoot.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.publisherdata.model.PublisherReport;
import com.websystique.springmvc.model.Reports;

public class AudienceSegmentPopulator {

	public static List<PublisherReport> FilterReportDTO(List<PublisherReport> Report
			) {
			
		  List<PublisherReport> finalDTO = new ArrayList<PublisherReport>();
          String audience_segment = null;
		  String [] subcategory = null;
 	      
		  for(int i=0; i<Report.size(); i++){
	    	  
	    	  audience_segment = Report.get(i).getAudience_segment();
	    	  
	    	  subcategory = audience_segment.split("/");
	    	  if(subcategory.length > 2)
	    	  {
	    		  for(int j=0; j<Report.size(); j++){
	    		  
	    			 if(Report.get(j).getAudience_segment().equals("/"+subcategory[1])){
	    				 
	    				 Report.get(j).getAudience_segment_data().add(Report.get(i));
	    				 
	    			 }
	    		  }
	    	  }
	    	  
	    	}
		
 	     
 	      for(int k=0; k<Report.size(); k++){
 	    	 audience_segment = Report.get(k).getAudience_segment();
 	    	 subcategory = audience_segment.split("/");
	    	 
 	    	   if(subcategory.length == 2)
	    	  {
 	             finalDTO.add(Report.get(k));
 	      } 
 	      
 	      }	   
 	    	   return finalDTO;	
	
//	 return Report;
	
	}
		






}
