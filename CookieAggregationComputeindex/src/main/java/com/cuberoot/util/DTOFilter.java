package com.cuberoot.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.websystique.springmvc.model.Reports;

public class DTOFilter {

	public static List<Reports> FilterReportDTO(List<Reports> Report,
			String dateStart, String dateEnd, int reportCode) {
			
		  List<Reports> finalDTO = new ArrayList<Reports>();
          Map<String,List<String>> campDateMap = new HashMap<String,List<String>>();
		  String campaignId = null;
		  String date;
		//  List<Str>
		  List <String> dates=null;
		  String impressions = null;
		  
	      for(int i=0; i<Report.size(); i++){
	    	  
	    	  campaignId = Report.get(i).getCampaign_id();
	    	  date = Report.get(i).getDate();
	    	  
	    	  
	    	  if(campDateMap.containsKey(campaignId)==false){
	    	  dates = new ArrayList<String>();
	    	  dates.add(date);
	    	  campDateMap.put(campaignId,dates);
	    	  finalDTO.add(Report.get(i));
	    	  }
	    	  else{
	    		dates = new ArrayList<String>();
	    		dates = campDateMap.get(campaignId);  
	    		if(dates.contains(date)==true){
//	    			Report.remove(i);
	    		}
	    	
	    		else{
	    		dates.add(date);
	    		campDateMap.put(campaignId,dates);  
	    		finalDTO.add(Report.get(i));
	    		}
	    		
	        }
	    	  
	    	  
	   }	
	
//	 return Report;
	 return finalDTO;
	}
	
}