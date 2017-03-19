package com.cuberoot.util;


import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.publisherdata.model.PublisherReport;
import com.websystique.springmvc.model.Reports;


/**
 * Utility for converting ResultSets into DTO
 */
public class DTOPopulator {
    /**
     * Populate a result set into DTO
     
     */
    public static List<PublisherReport> populateDTO(ResultSet resultSet)
            throws Exception {
       
    	   List<PublisherReport> report = new ArrayList<PublisherReport>();
           Reports reportDTO = null;
    	   String name;
           while (resultSet.next()) {
        	
        	Double impressions = null;
           	Double clicks = null;
           	Double mediacost = null;
           	Double conversions = null;
           	Double reach = null;  
        	       	   
        	int total_rows = resultSet.getMetaData().getColumnCount();
            
        	
        	PublisherReport obj = new PublisherReport();
            
        	for (int i = 0; i < total_rows; i++) {
               name = resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase();
              
               if( name.equals("count")){
                obj.setCount(resultSet.getObject(i + 1).toString());
                impressions = Double.parseDouble(resultSet.getObject(i + 1).toString());
               }
               
               if( name.equals("date"))
            	 obj.setDate(resultSet.getObject(i + 1).toString());
            
              
            
              if( name.equals("channel_name"))   
            	   obj.setChannelName(resultSet.getObject(i + 1).toString());
            
            
             
              
              
              if( name.equals("reach")){
                  obj.setReach(resultSet.getObject(i + 1).toString());              
                  reach = Double.parseDouble(resultSet.getObject(i + 1).toString());
              }
              
              
              if( name.equals("audience_segment"))
                  obj.setAudience_segment(resultSet.getObject(i + 1).toString());
              
              if( name.equals("city"))
                  obj.setCity(resultSet.getObject(i + 1).toString());
              
              if( name.equals("postal_code"))
                  obj.setPostalcode(resultSet.getObject(i + 1).toString());  
            
              if( name.equals("lat_long"))
                  obj.setLatitude_longitude(resultSet.getObject(i + 1).toString());  
              
              
             if( name.equals("os"))
                  obj.setOs(resultSet.getObject(i + 1).toString());  
             
             
             if( name.equals("browser"))
                 obj.setBrowser(resultSet.getObject(i + 1).toString());  
           
             
            
              if( name.equals("brandName"))
                  obj.setBrandname(resultSet.getObject(i + 1).toString());
              
              if( name.equals("MobileModel"))
                  obj.setMobile_device_properties(resultSet.getObject(i + 1).toString());  
                         
              if(name.equals("age"))
                  obj.setAge(resultSet.getObject(i + 1).toString());  
              
              if(name.equals("gender"))
                  obj.setGender(resultSet.getObject(i + 1).toString());  
            

    
                    
            
            
            
            
            
            }
            report.add(obj);
        
        }
        return report;
    }
    
}