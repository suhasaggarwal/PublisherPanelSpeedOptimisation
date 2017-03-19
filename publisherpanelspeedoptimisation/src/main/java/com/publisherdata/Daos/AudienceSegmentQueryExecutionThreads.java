package com.publisherdata.Daos;

import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import javolution.util.FastMap;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.plugin.nlpcn.QueryActionElasticExecutor;
import org.elasticsearch.plugin.nlpcn.executors.CSVResult;
import org.elasticsearch.plugin.nlpcn.executors.CSVResultsExtractor;
import org.elasticsearch.plugin.nlpcn.executors.CsvExtractorException;
import org.nlpcn.es4sql.SearchDao;
import org.nlpcn.es4sql.exception.SqlParseException;
import org.nlpcn.es4sql.query.QueryAction;

import com.publisherdata.model.PublisherReport;

public class AudienceSegmentQueryExecutionThreads implements Callable<FastMap<String,Double>>{

	 public String query=null;
	 public FastMap<String,Double> fingerprintmap = new FastMap<String,Double>();
	 public FastMap<String,Double> fingerprintmap1 = new FastMap<String,Double>();
	
	 public Map<String,String> categoryMap = new HashMap<String,String>();
     
	 public  AudienceSegmentQueryExecutionThreads(String query,TransportClient client,SearchDao searchDao) {
       this.query = query;
     }

	 public FastMap<String,Double> call() {

 	    Double count0 = 0.0;
 	    Double count1 = 0.0;
 	    Double count2 = 0.0;
 	 
 	    
 	    String cookieList = "";
 	    
 	    String query1 = "SELECT SUM(contributionFactor) as count,audience_segment FROM audiencesegmentcomputeindex1 where cookie_id in ("+query+")"+" group by audience_segment limit 20000000";  
 		
 		System.out.println(query1);
 		
 		final long startTime1 = System.currentTimeMillis();
 		CSVResult csvResult = null;
		try {
			csvResult = AggregationModule.getCsvResult(false, query1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 	    
 		final long endTime1 = System.currentTimeMillis();
 		
 		System.out.println("Total cookie audience count execution time: " + (endTime1 - startTime1) );
 		
 		
 		List<String> headers = csvResult.getHeaders();
 	    List<String> lines = csvResult.getLines();
 	    List<PublisherReport> pubreport = new ArrayList();
 	    
 	 //   //System.out.println(headers);
 	//    System.out.println(lines);
 	    
 	    fingerprintmap1.put("audience_segment",1.0);
 	    
 	  
 	    for (int i = 0; i < lines.size(); i++)
 	    {
 	       
 	       String[] data11 = ((String)lines.get(i)).split(",");
 	    	
 	       if(data11.length > 1 ){
 	       
 	      
 	       
 	        if(fingerprintmap1.containsKey(data11[0])==false)
 	    	fingerprintmap1.put(data11[0].trim(),Double.parseDouble(data11[1]));
 	    	else
 	    	{
 	         count1 = fingerprintmap1.get(data11[0]);
 	         fingerprintmap1.put(data11[0].trim(),Double.parseDouble(data11[1])+count1);	
 	    	}
 	       }
 	       
 	       }
 	    
 	       lines.clear();
 	       csvResult=null;
 
	 
	  return fingerprintmap1;
	 
	 }
	    
	 
   





















/*
public class QueryExecutionThreads implements Callable<FastMap<String,Double> >{

	 public String query=null;
	 public FastMap<String,Double> fingerprintmap = new FastMap<String,Double>();
	 public FastMap<String,Double> fingerprintmap1 = new FastMap<String,Double>();
	
	 public Map<String,String> categoryMap = new HashMap<String,String>();
     public  QueryExecutionThreads(String query,TransportClient client,SearchDao searchDao) {
        this.query = query;
     }

     public void run() {
    
 	    Double count0 = 0.0;
 	    Double count1 = 0.0;
 	    Double count2 = 0.0;
 	 
 	    
 	    String cookieList = "";
 	    
 	    String query1 = "SELECT SUM(contributionFactor) as count,audience_segment FROM audiencesegmentcomputeindex1 where cookie_id in ("+query+")"+" group by audience_segment limit 20000000";  
 		
 		System.out.println(query1);
 		
 		final long startTime1 = System.currentTimeMillis();
 		CSVResult csvResult = null;
		try {
			csvResult = AggregationModule.getCsvResult(false, query1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 	    
 		final long endTime1 = System.currentTimeMillis();
 		
 		System.out.println("Total cookie audience count execution time: " + (endTime1 - startTime1) );
 		
 		
 		List<String> headers = csvResult.getHeaders();
 	    List<String> lines = csvResult.getLines();
 	    List<PublisherReport> pubreport = new ArrayList();
 	    
 	 //   //System.out.println(headers);
 	//    System.out.println(lines);
 	    
 	    
 	  
 	    for (int i = 0; i < lines.size(); i++)
 	    {
 	       
 	       String[] data11 = ((String)lines.get(i)).split(",");
 	    	
 	       if(data11.length > 1 ){
 	       
 	      
 	       
 	        if(fingerprintmap1.containsKey(data11[0])==false)
 	    	fingerprintmap1.put(data11[0].trim(),Double.parseDouble(data11[1]));
 	    	else
 	    	{
 	         count1 = fingerprintmap1.get(data11[0]);
 	         fingerprintmap1.put(data11[0].trim(),Double.parseDouble(data11[1])+count1);	
 	    	}
 	       }
 	       
 	       }
 	    
 	       lines.clear();
 	       csvResult=null;
 	    
 	 
     }
*/
     



}
