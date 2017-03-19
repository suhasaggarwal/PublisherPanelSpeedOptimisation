package com.publisherdata.Daos;

import com.publisherdata.model.PublisherReport;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javolution.util.FastMap;

import org.elasticsearch.action.admin.cluster.node.info.NodesInfoRequestBuilder;
import org.elasticsearch.action.admin.cluster.node.info.NodesInfoResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.ClusterAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.ClusterName;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.plugin.nlpcn.QueryActionElasticExecutor;
import org.elasticsearch.plugin.nlpcn.executors.CSVResult;
import org.elasticsearch.plugin.nlpcn.executors.CSVResultsExtractor;
import org.elasticsearch.plugin.nlpcn.executors.CsvExtractorException;
import org.elasticsearch.search.aggregations.Aggregations;
import org.nlpcn.es4sql.SearchDao;
import org.nlpcn.es4sql.exception.SqlParseException;
import org.nlpcn.es4sql.query.QueryAction;
import org.nlpcn.es4sql.query.SqlElasticSearchRequestBuilder;

public class AggregationModule
{
  private static TransportClient client;
  private static SearchDao searchDao;
  private static AggregationModule INSTANCE;
  
  
  
  public static AggregationModule getInstance()
  {
    if (INSTANCE == null) {
      return new AggregationModule();
    }
    return INSTANCE;
  }
  
  public static void main(String[] args)
    throws Exception
  {
	  
	
	//  setUp();
	  
	//  countfingerprintChannel("2016-08-20","2016-12-02", "Mumbai_T1_airport");
	  
	//  countAudiencesegmentChannel("2016-08-20","2016-12-02", "Mumbai_T1_airport");
	
	  
	//  Integer countv1 = 500000;
	  
	  /*
	  
	  if(countv1 >= 500000)
	    {
	    double total_length = countv1 - 0;
	    double subrange_length = total_length/30;	
	    
	    double current_start = 0;
	    for (int i = 0; i < 20; ++i) {
	      System.out.println("Smaller range: [" + current_start + ", " + (current_start + subrange_length) + "]");
	      current_start += subrange_length;
	    }
	   }
  */
	  
	  
	  
	    Double countv1 = Double.parseDouble("90000");
	    
	    Double n = 0.0;
	    if(countv1 >= 250000)
	       n=50.0;
	    
	    if(countv1 >= 100000 && countv1 <= 250000 )
	       n=20.0;
	    
	    if(countv1 < 100000)
           n=50.0;	    
	   
	    Double total_length = countv1 - 0;
	    Double subrange_length = total_length/n;	
	    String startdate= "Startdate";
	    String enddate= "endDate";
	    Double current_start = 0.0;
	    for (int i = 0; i < n; ++i) {
	      System.out.println("Smaller range: [" + current_start + ", " + (current_start + subrange_length) + "]");
	      Double startlimit = current_start;
	      Double finallimit = current_start + subrange_length;
	      Double index = startlimit +1;
	      String query = "SELECT DISTINCT(cookie_id) FROM enhanceduserdatabeta1 where date between " + "'" + startdate + "'" + " and " + "'" + enddate +"' limit "+index.intValue()+","+finallimit.intValue();  	
		  System.out.println(query);
	  //    Query.add(query);
	      current_start += subrange_length;
	    //  Query.add(query);
	     
	    }
	    
	    
  
  }
  
  public void setUp()
    throws Exception
  {
    if (client == null)
    {
      client = new TransportClient();
      client.addTransportAddress(getTransportAddress());
      
      NodesInfoResponse nodeInfos = (NodesInfoResponse)client.admin().cluster().prepareNodesInfo(new String[0]).get();
      String clusterName = nodeInfos.getClusterName().value();
      //System.out.println(String.format("Found cluster... cluster name: %s", new Object[] { clusterName }));
      
      searchDao = new SearchDao(client);
    }
    //System.out.println("Finished the setup process...");
  }
  
  public static SearchDao getSearchDao()
  {
    return searchDao;
  }
  
  public List<PublisherReport> countBrandName(String startdate, String enddate)
    throws CsvExtractorException, Exception
  {
    Aggregations result = query(String.format("SELECT COUNT(*),brandName,browser_name FROM enhanceduserdatabeta1 group by brandName,browser_name", new Object[] { "enhanceduserprofilestore" }));
    String query = String.format("SELECT COUNT(*)as count,brandName FROM enhanceduserdatabeta1 where date between '" + startdate + "'" + " and " + "'" + enddate + "'" + " group by brandName", new Object[] { "enhanceduserdatabeta1" });
    CSVResult csvResult = getCsvResult(false, query);
    List<String> headers = csvResult.getHeaders();
    List<String> lines = csvResult.getLines();
    List<PublisherReport> pubreport = new ArrayList();
    if (lines.size() > 0) {
      for (int i = 0; i < lines.size(); i++)
      {
        PublisherReport obj = new PublisherReport();
        
        String[] data = ((String)lines.get(i)).split(",");
        if(data[0].trim().toLowerCase().contains("logitech")==false && data[0].trim().toLowerCase().contains("mozilla")==false && data[0].trim().toLowerCase().contains("web_browser")==false && data[0].trim().toLowerCase().contains("microsoft")==false && data[0].trim().toLowerCase().contains("opera")==false && data[0].trim().toLowerCase().contains("epiphany")==false){ 
        obj.setBrandname(data[0]);
        obj.setCount(data[1]);
        pubreport.add(obj);
       }
      }
    }
    //System.out.println(headers);
    //System.out.println(lines);
    
    return pubreport;
  }
  
  public List<PublisherReport> countBrowser(String startdate, String enddate)
    throws CsvExtractorException, Exception
  {
    Aggregations result = query(String.format("SELECT COUNT(*),brandName,browser_name FROM enhanceduserdatabeta1 group by brandName,browser_name", new Object[] { "enhanceduserprofilestore" }));
    String query = String.format("SELECT COUNT(*)as count,browser_name FROM enhanceduserdatabeta1 where date between '" + startdate + "'" + " and " + "'" + enddate + "'" + " group by browser_name", new Object[] { "enhanceduserdatabeta1" });
    CSVResult csvResult = getCsvResult(false, query);
    List<String> headers = csvResult.getHeaders();
    List<String> lines = csvResult.getLines();
    List<PublisherReport> pubreport = new ArrayList();
    if ((lines != null) && (!lines.isEmpty()) && (!((String)lines.get(0)).isEmpty())) {
      for (int i = 0; i < lines.size(); i++)
      {
        PublisherReport obj = new PublisherReport();
        
        String[] data = ((String)lines.get(i)).split(",");
       
        obj.setBrowser(data[0]);
        obj.setCount(data[1]);
        pubreport.add(obj);
      }
    }
    //System.out.println(headers);
    //System.out.println(lines);
    
    return pubreport;
  }
  
  public List<PublisherReport> countOS(String startdate, String enddate)
    throws CsvExtractorException, Exception
  {
    Aggregations result = query(String.format("SELECT COUNT(*),brandName,browser_name FROM enhanceduserdatabeta1 group by brandName,browser_name", new Object[] { "enhanceduserprofilestore" }));
    String query = String.format("SELECT COUNT(*)as count,system_os FROM enhanceduserdatabeta1 where date between '" + startdate + "'" + " and " + "'" + enddate + "'" + " group by system_os", new Object[] { "enhanceduserdatabeta1" });
    CSVResult csvResult = getCsvResult(false, query);
    List<String> headers = csvResult.getHeaders();
    List<String> lines = csvResult.getLines();
    //System.out.println(headers);
    //System.out.println(lines);
    List<PublisherReport> pubreport = new ArrayList();
    if ((lines != null) && (!lines.isEmpty()) && (!((String)lines.get(0)).isEmpty())) {
      for (int i = 0; i < lines.size(); i++)
      {
        PublisherReport obj = new PublisherReport();
        
        String[] data = ((String)lines.get(i)).split(",");
        obj.setOs(data[0]);
        obj.setCount(data[1]);
        pubreport.add(obj);
      }
    }
    return pubreport;
  }
  
  public List<PublisherReport> countModel(String startdate, String enddate)
    throws CsvExtractorException, Exception
  {
    Aggregations result = query(String.format("SELECT COUNT(*),brandName,browser_name FROM enhanceduserdatabeta1 group by brandName,browser_name", new Object[] { "enhanceduserprofilestore" }));
    String query = String.format("SELECT COUNT(*)as count,modelName FROM enhanceduserdatabeta1 where date between '" + startdate + "'" + " and " + "'" + enddate + "'" + " group by modelName", new Object[] { "enhanceduserdatabeta1" });
    CSVResult csvResult = getCsvResult(false, query);
    List<String> headers = csvResult.getHeaders();
    List<String> lines = csvResult.getLines();
    List<PublisherReport> pubreport = new ArrayList();
    if ((lines != null) && (!lines.isEmpty()) && (!((String)lines.get(0)).isEmpty())) {
      for (int i = 0; i < lines.size(); i++)
      {
        PublisherReport obj = new PublisherReport();
        
        String[] data = ((String)lines.get(i)).split(",");
        if(data[0].trim().toLowerCase().contains("logitech_revue")==false && data[0].trim().toLowerCase().contains("mozilla_firefox")==false && data[0].trim().toLowerCase().contains("apple_safari")==false && data[0].trim().toLowerCase().contains("generic_web")==false && data[0].trim().toLowerCase().contains("google_compute")==false && data[0].trim().toLowerCase().contains("microsoft_xbox")==false && data[0].trim().toLowerCase().contains("google_chromecast")==false && data[0].trim().toLowerCase().contains("opera")==false && data[0].trim().toLowerCase().contains("epiphany")==false && data[0].trim().toLowerCase().contains("laptop")==false){    
        obj.setMobile_device_properties(data[0]);
        obj.setCount(data[1]);
        pubreport.add(obj);
        }
        
        }
    }
    return pubreport;
  }
  
  public List<PublisherReport> countCity(String startdate, String enddate)
    throws CsvExtractorException, Exception
  {
    Aggregations result = query(String.format("SELECT COUNT(*),brandName,browser_name FROM enhanceduserdatabeta1 group by brandName,browser_name", new Object[] { "enhanceduserprofilestore" }));
    String query = String.format("SELECT COUNT(*)as count,city FROM enhanceduserdatabeta1 where date between '" + startdate + "'" + " and " + "'" + enddate + "'" + " group by city", new Object[] { "enhanceduserdatabeta1" });
    CSVResult csvResult = getCsvResult(false, query);
    List<String> headers = csvResult.getHeaders();
    List<String> lines = csvResult.getLines();
    List<PublisherReport> pubreport = new ArrayList();
    if ((lines != null) && (!lines.isEmpty()) && (!((String)lines.get(0)).isEmpty())) {
      for (int i = 0; i < lines.size(); i++)
      {
        PublisherReport obj = new PublisherReport();
        
        String[] data = ((String)lines.get(i)).split(",");
        obj.setCity(data[0]);
        obj.setCount(data[1]);
        pubreport.add(obj);
      }
    }
    return pubreport;
  }
  
  public List<PublisherReport> countPinCode(String startdate, String enddate)
    throws CsvExtractorException, Exception
  {
    Aggregations result = query(String.format("SELECT COUNT(*),brandName,browser_name FROM enhanceduserdatabeta1 group by brandName,browser_name", new Object[] { "enhanceduserprofilestore" }));
    String query = String.format("SELECT COUNT(*)as count,postalcode FROM enhanceduserdatabeta1 where date between '" + startdate + "'" + " and " + "'" + enddate + "'" + " group by postalcode", new Object[] { "enhanceduserdatabeta1" });
    CSVResult csvResult = getCsvResult(false, query);
    List<String> headers = csvResult.getHeaders();
    List<String> lines = csvResult.getLines();
    //System.out.println(headers);
    //System.out.println(lines);
    List<PublisherReport> pubreport = new ArrayList();
    if ((lines != null) && (!lines.isEmpty()) && (!((String)lines.get(0)).isEmpty())) {
      for (int i = 0; i < lines.size(); i++)
      {
        PublisherReport obj = new PublisherReport();
        
        String[] data = ((String)lines.get(i)).split(",");
        obj.setPostalcode(data[0]);
        obj.setCount(data[1]);
        pubreport.add(obj);
      }
    }
    return pubreport;
  }
  
  public List<PublisherReport> countLatLong(String startdate, String enddate)
    throws CsvExtractorException, Exception
  {
    Aggregations result = query(String.format("SELECT COUNT(*),brandName,browser_name FROM enhanceduserdatabeta1 group by brandName,browser_name", new Object[] { "enhanceduserprofilestore" }));
    String query = String.format("SELECT COUNT(*)as count,latitude_longitude FROM enhanceduserdatabeta1 where date between '" + startdate + "'" + " and " + "'" + enddate + "'" + " group by latitude_longitude", new Object[] { "enhanceduserdatabeta1" });
    CSVResult csvResult = getCsvResult(false, query);
    List<String> headers = csvResult.getHeaders();
    List<String> lines = csvResult.getLines();
    //System.out.println(headers);
    //System.out.println(lines);
    List<PublisherReport> pubreport = new ArrayList();
    if ((lines != null) && (!lines.isEmpty()) && (!((String)lines.get(0)).isEmpty())) {
      for (int i = 0; i < lines.size(); i++)
      {
        PublisherReport obj = new PublisherReport();
        
        String[] data = ((String)lines.get(i)).split(",");
        String[] dashcount = data[0].split("_");
        if ((dashcount.length == 3) && (data[0].charAt(data[0].length() - 1) != '_') && 
          (!dashcount[2].isEmpty()))
        {
          obj.setLatitude_longitude(data[0]);
          obj.setCount(data[1]);
          pubreport.add(obj);
        }
      }
    }
    return pubreport;
  }
  
  public List<PublisherReport> countfingerprint(String startdate, String enddate)
    throws CsvExtractorException, Exception
  {
    Aggregations result = query(String.format("SELECT COUNT(*),brandName,browser_name FROM enhanceduserdatabeta1 group by brandName,browser_name", new Object[] { "enhanceduserprofilestore" }));
    String query = String.format("SELECT count(distinct(cookie_id))as reach,date FROM enhanceduserdatabeta1 where date between '" + 
      startdate + "'" + " and " + "'" + enddate + "'" + " group by date", new Object[] {"enhanceduserdatabeta1" });
    CSVResult csvResult = getCsvResult(false, query);
    List<String> headers = csvResult.getHeaders();
    List<String> lines = csvResult.getLines();
    List<PublisherReport> pubreport = new ArrayList();
    if ((lines != null) && (!lines.isEmpty()) && (!((String)lines.get(0)).isEmpty())) {
      for (int i = 0; i < lines.size(); i++)
      {
        PublisherReport obj = new PublisherReport();
        
        String[] data = ((String)lines.get(i)).split(",");
        obj.setDate(data[0]);
        obj.setReach(data[1]);
        pubreport.add(obj);
      }
    }
    return pubreport;
  }
  
  public List<PublisherReport> countAudienceSegment(String startdate, String enddate)
    throws CsvExtractorException, Exception
  {
		
	  List<PublisherReport> pubreport = new ArrayList(); 
	  
	  String querya1 = "Select Count(DISTINCT(cookie_id)) FROM enhanceduserdatabeta1 where date between " + "'" + startdate + "'" + " and " + "'" + enddate +"' limit 20000000";  
	  
	    //Divide count in different limits 
	
	  
	  List<String> Query = new ArrayList();
	  


	    System.out.println(querya1);
	    
	    final long startTime2 = System.currentTimeMillis();
		
	    
	    CSVResult csvResult1 = null;
		try {
			csvResult1 = AggregationModule.getCsvResult(false, querya1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    final long endTime2 = System.currentTimeMillis();
		
	    List<String> headers = csvResult1.getHeaders();
	    List<String> lines = csvResult1.getLines();
	    
	    
	    String count = lines.get(0);
	    Double countv1 = Double.parseDouble(count);
	    Double n = 0.0;
	    if(countv1 >= 250000)
	       n=50.0;
	    
	    if(countv1 >= 100000 && countv1 <= 250000 )
	       n=20.0;
	    
	    if(countv1 < 100000)
           n=10.0;	    
	   
	    Double total_length = countv1 - 0;
	    Double subrange_length = total_length/n;	
	    
	    Double current_start = 0.0;
	    for (int i = 0; i < n; ++i) {
	      System.out.println("Smaller range: [" + current_start + ", " + (current_start + subrange_length) + "]");
	      Double startlimit = current_start;
	      Double finallimit = current_start + subrange_length;
	      Double index = startlimit +1;
	      String query = "SELECT DISTINCT(cookie_id) FROM enhanceduserdatabeta1 where date between " + "'" + startdate + "'" + " and " + "'" + enddate +"' Order by cookie_id limit "+index.intValue()+","+finallimit.intValue();  	
		  System.out.println(query);
	  //    Query.add(query);
	      current_start += subrange_length;
	      Query.add(query);
	     
	    }
	    
	    
	    	
	    
	  
	  ExecutorService executorService = Executors.newFixedThreadPool(20);
        
       List<Callable<FastMap<String,Double>>> lst = new ArrayList<Callable<FastMap<String,Double>>>();
    
       for(int i=0 ; i < Query.size(); i++ ){
       lst.add(new AudienceSegmentQueryExecutionThreads(Query.get(i),client,searchDao));
    /*   lst.add(new AudienceSegmentQueryExecutionThreads(query1,client,searchDao));
       lst.add(new AudienceSegmentQueryExecutionThreads(query2,client,searchDao));
       lst.add(new AudienceSegmentQueryExecutionThreads(query3,client,searchDao));
       lst.add(new AudienceSegmentQueryExecutionThreads(query4,client,searchDao));*/
        
       // returns a list of Futures holding their status and results when all complete
       lst.add(new SubcategoryQueryExecutionThreads(Query.get(i),client,searchDao));
   /*    lst.add(new SubcategoryQueryExecutionThreads(query6,client,searchDao));
       lst.add(new SubcategoryQueryExecutionThreads(query7,client,searchDao));
       lst.add(new SubcategoryQueryExecutionThreads(query8,client,searchDao));
       lst.add(new SubcategoryQueryExecutionThreads(query9,client,searchDao)); */
       }
       
       
       List<Future<FastMap<String,Double>>> maps = executorService.invokeAll(lst);
        
       System.out.println(maps.size() +" Responses recieved.\n");
        
       for(Future<FastMap<String,Double>> task : maps)
       {
    	   try{
           if(task!=null)
    	   System.out.println(task.get().toString());
    	   }
    	   catch(Exception e)
    	   {
    		   e.printStackTrace();
    		   continue;
    	   }
    	    
    	   
    	   }
        
       /* shutdown your thread pool, else your application will keep running */
       executorService.shutdown();
	  
	
	  //  //System.out.println(headers1);
	 //   //System.out.println(lines1);
	    
	    
       
       FastMap<String,Double> audiencemap = new FastMap<String,Double>();
       
       FastMap<String,Double> subcatmap = new FastMap<String,Double>();
       
       Double count1 = 0.0;
       
       Double count2 = 0.0;
       
       String key ="";
       String key1 = "";
       Double value = 0.0;
       Double vlaue1 = 0.0;
       
	    for (int i = 0; i < maps.size(); i++)
	    {
	    
	    	if(maps!=null && maps.get(i)!=null){
	        FastMap<String,Double> map = (FastMap<String, Double>) maps.get(i).get();
	    	
	       if(map.size() > 0){
	       
	       if(map.containsKey("audience_segment")==true){
	       for (Map.Entry<String, Double> entry : map.entrySet())
	    	 {
	    	  key = entry.getKey();
	    	  key = key.trim();
	    	  value=  entry.getValue();
	    	if(key.equals("audience_segment")==false) { 
	    	if(audiencemap.containsKey(key)==false)
	    	audiencemap.put(key,value);
	    	else
	    	{
	         count1 = audiencemap.get(key);
	         if(count1!=null)
	         audiencemap.put(key,count1+value);	
	    	}
	      }
	    }
	  }   

	       if(map.containsKey("subcategory")==true){
	       for (Map.Entry<String, Double> entry : map.entrySet())
	    	 {
	    	   key = entry.getKey();
	    	   key = key.trim();
	    	   value=  entry.getValue();
	    	if(key.equals("subcategory")==false) {    
	    	if(subcatmap.containsKey(key)==false)
	    	subcatmap.put(key,value);
	    	else
	    	{
	         count1 = subcatmap.get(key);
	         if(count1!=null)
	         subcatmap.put(key,count1+value);	
	    	}
	    }  
	    	
	   }
	      
	     	       }
	           
	       } 
	    
	    	} 	
	   }    
	    
	    String subcategory = null;
	   
	    if(audiencemap.size()>0){
	   
	    	for (Map.Entry<String, Double> entry : audiencemap.entrySet()) {
	    	//System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
	    

	        PublisherReport obj = new PublisherReport();
	        
	   //     String[] data = ((String)lines.get(i)).split(",");
	        
	     //   if(data[0].trim().toLowerCase().contains("festivals"))
	      //  obj.setAudience_segment("");
	      //  else
	        obj.setAudience_segment( entry.getKey());	
	        obj.setCount(String.valueOf(entry.getValue()));
	      
	        if ((!entry.getKey().equals("tech")) && (!entry.getKey().equals("india")) && (!entry.getKey().trim().toLowerCase().equals("foodbeverage")) )
	        {
	         for (Map.Entry<String, Double> entry1 : subcatmap.entrySet()) {
	        	 
	        	    
	        	 
	        	 PublisherReport obj1 = new PublisherReport();
	            
	           
	            if (entry1.getKey().contains(entry.getKey()))
	            {
	              String substring = "_" + entry.getKey() + "_";
	              subcategory = entry1.getKey().replace(substring, "");
	           //   if(data[0].trim().toLowerCase().contains("festivals"))
	           //   obj1.setAudience_segment("");
	           //   else
	        
	              //System.out.println(" \n\n\n Key : " + subcategory + " Value : " + entry1.getValue());  
	              obj1.setAudience_segment(subcategory);
	              obj1.setCount(String.valueOf(entry1.getValue()));
	              obj.getAudience_segment_data().add(obj1);
	            }
	          }
	          pubreport.add(obj);
	        }
	      
	    }
	    }
	    return pubreport;
 
   
  }
  
  public List<PublisherReport> countISP(String startdate, String enddate)
    throws CsvExtractorException, Exception
  {
    Aggregations result = query(String.format("SELECT COUNT(*),brandName,browser_name FROM enhanceduserdatabeta1 group by brandName,browser_name", new Object[] { "enhanceduserprofilestore" }));
    String query = String.format("SELECT COUNT(*)as count,ISP FROM enhanceduserdatabeta1 where date between '" + startdate + "'" + " and " + "'" + enddate + "'" + " group by ISP", new Object[] { "enhanceduserdatabeta1" });
    CSVResult csvResult = getCsvResult(false, query);
    List<String> headers = csvResult.getHeaders();
    List<String> lines = csvResult.getLines();
    List<PublisherReport> pubreport = new ArrayList();
    if ((lines != null) && (!lines.isEmpty()) && (!((String)lines.get(0)).isEmpty()))
    {
      for (int i = 0; i < lines.size(); i++)
      {
        PublisherReport obj = new PublisherReport();
        
        String[] data = ((String)lines.get(i)).split(",");
        if(data[0].trim().toLowerCase().equals("_ltd")==false){ 
        obj.setISP(data[0]);
        obj.setCount(data[1]);
        pubreport.add(obj);
       }
      }
      //System.out.println(headers);
      //System.out.println(lines);
    }
    return pubreport;
  }
  
  public List<PublisherReport> countOrg(String startdate, String enddate)
    throws CsvExtractorException, Exception
  {
    Aggregations result = query(String.format("SELECT COUNT(*),brandName,browser_name FROM enhanceduserdatabeta1 group by brandName,browser_name", new Object[] { "enhanceduserprofilestore" }));
    String query = String.format("SELECT organisation FROM enhanceduserdatabeta1 where date between '" + startdate + "'" + " and " + "'" + enddate + "'" + " and organisation NOT IN (Select DISTINCT(ISP) FROM enhanceduserdatabeta1)", new Object[] { "enhanceduserdatabeta1" });
    CSVResult csvResult = getCsvResult(false, query);
    List<String> headers = csvResult.getHeaders();
    List<String> lines = csvResult.getLines();
    List<PublisherReport> pubreport = new ArrayList();
    if ((lines != null) && (!lines.isEmpty()) && (!((String)lines.get(0)).isEmpty()))
    {
      for (int i = 0; i < lines.size(); i++)
      {
        PublisherReport obj = new PublisherReport();
        
        String[] data = ((String)lines.get(i)).split(",");
        obj.setOrganisation(data[0]);
        obj.setCount(data[1]);
        pubreport.add(obj);
      }
      //System.out.println(headers);
      //System.out.println(lines);
    }
    return pubreport;
  }
  
  public Set<String> getChannelList(String startdate, String enddate)
    throws CsvExtractorException, Exception
  {
    String query = String.format("SELECT channel_name FROM enhanceduserdatabeta1 where date between '" + startdate + "'" + " and " + "'" + enddate + "'" + " Group by channel_name", new Object[] { "enhanceduserdatabeta1" });
    CSVResult csvResult = getCsvResult(false, query);
    List<String> headers = csvResult.getHeaders();
    List<String> lines = csvResult.getLines();
    List<String> finallines = new ArrayList();
    Set<String> data = new HashSet();
    data.addAll(lines);
    
    //System.out.println(headers);
    //System.out.println(lines);
    
    return data;
  }
  
  public List<PublisherReport> gettimeofdayQuarter(String startdate, String enddate)
    throws SQLFeatureNotSupportedException, SqlParseException, CsvExtractorException, Exception
  {
    String query = "Select count(*) from enhanceduserdatabeta1 WHERE date between '" + startdate + "'" + " and " + "'" + enddate + "' GROUP BY HOUR(request_time)";
    
    CSVResult csvResult = getCsvResult(false, query);
    List<String> headers = csvResult.getHeaders();
    List<String> lines = csvResult.getLines();
    List<PublisherReport> pubreport = new ArrayList();
    if ((lines != null) && (!lines.isEmpty()) && (!((String)lines.get(0)).isEmpty()))
    {
      for (int i = 0; i < lines.size(); i++)
      {
        PublisherReport obj = new PublisherReport();
        
        String[] data = ((String)lines.get(i)).split(",");
        obj.setTime_of_day(data[0]);
        obj.setCount(data[1]);
        pubreport.add(obj);
      }
      //System.out.println(headers);
      //System.out.println(lines);
    }
    return pubreport;
  }
  
  public List<PublisherReport> gettimeofdayDaily(String startdate, String enddate)
    throws SQLFeatureNotSupportedException, SqlParseException, CsvExtractorException, Exception
  {
    String query = "Select count(*) from enhanceduserdatabeta1 WHERE date between '" + startdate + "'" + " and " + "'" + enddate + "' GROUP BY date_histogram(field='request_time','interval'='1d')";
    CSVResult csvResult = getCsvResult(false, query);
    List<String> headers = csvResult.getHeaders();
    List<String> lines = csvResult.getLines();
    List<PublisherReport> pubreport = new ArrayList();
    if ((lines != null) && (!lines.isEmpty()) && (!((String)lines.get(0)).isEmpty()))
    {
      for (int i = 0; i < lines.size(); i++)
      {
        PublisherReport obj = new PublisherReport();
        
        String[] data = ((String)lines.get(i)).split(",");
        obj.setTime_of_day(data[0]);
        obj.setCount(data[1]);
        pubreport.add(obj);
      }
      //System.out.println(headers);
      //System.out.println(lines);
    }
    return pubreport;
  }
  
  public List<PublisherReport> gettimeofday(String startdate, String enddate)
    throws SQLFeatureNotSupportedException, SqlParseException, CsvExtractorException, Exception
  {
    String query = "Select count(*) from enhanceduserdatabeta1 WHERE date between '" + startdate + "'" + " and " + "'" + enddate + "' GROUP BY date_histogram(field='request_time','interval'='1h')";
    CSVResult csvResult = getCsvResult(false, query);
    List<String> headers = csvResult.getHeaders();
    List<String> lines = csvResult.getLines();
    List<PublisherReport> pubreport = new ArrayList();
    if ((lines != null) && (!lines.isEmpty()) && (!((String)lines.get(0)).isEmpty()))
    {
      for (int i = 0; i < lines.size(); i++)
      {
        PublisherReport obj = new PublisherReport();
        
        String[] data = ((String)lines.get(i)).split(",");
        obj.setTime_of_day(data[0]);
        obj.setCount(data[1]);
        pubreport.add(obj);
      }
      //System.out.println(headers);
      //System.out.println(lines);
    }
    return pubreport;
  }
  
  public List<PublisherReport> countGender(String startdate, String enddate)
    throws CsvExtractorException, Exception
  {
    Aggregations result = query(String.format("SELECT COUNT(*),brandName,browser_name FROM enhanceduserdatabeta1 group by brandName,browser_name", new Object[] { "enhanceduserprofilestore" }));
    String query = String.format("SELECT COUNT(*)as count,gender FROM enhanceduserdatabeta1 where date between '" + startdate + "'" + " and " + "'" + enddate + "'" + " group by gender", new Object[] { "enhanceduserdatabeta1" });
    CSVResult csvResult = getCsvResult(false, query);
    List<String> headers = csvResult.getHeaders();
    List<String> lines = csvResult.getLines();
    List<PublisherReport> pubreport = new ArrayList();
    
    //System.out.println(headers);
    //System.out.println(lines);
    if ((lines != null) && (!lines.isEmpty()) && (!((String)lines.get(0)).isEmpty()))
    {
      for (int i = 0; i < lines.size(); i++)
      {
        PublisherReport obj = new PublisherReport();
        
        String[] data = ((String)lines.get(i)).split(",");
        obj.setGender(data[0]);
        obj.setCount(data[1]);
        pubreport.add(obj);
      }
      //System.out.println(headers);
      //System.out.println(lines);
    }
    return pubreport;
  }
  
  public List<PublisherReport> countAgegroup(String startdate, String enddate)
    throws CsvExtractorException, Exception
  {
    Aggregations result = query(String.format("SELECT COUNT(*),brandName,browser_name FROM enhanceduserdatabeta1 group by brandName,browser_name", new Object[] { "enhanceduserprofilestore" }));
    String query = String.format("SELECT COUNT(*)as count,agegroup FROM enhanceduserdatabeta1 where date between '" + startdate + "'" + " and " + "'" + enddate + "'" + " group by agegroup", new Object[] { "enhanceduserdatabeta1" });
    CSVResult csvResult = getCsvResult(false, query);
    List<String> headers = csvResult.getHeaders();
    List<String> lines = csvResult.getLines();
    List<PublisherReport> pubreport = new ArrayList();
    
    //System.out.println(headers);
    //System.out.println(lines);
    if ((lines != null) && (!lines.isEmpty()) && (!((String)lines.get(0)).isEmpty()))
    {
      for (int i = 0; i < lines.size(); i++)
      {
        PublisherReport obj = new PublisherReport();
        
        String[] data = ((String)lines.get(i)).split(",");
        obj.setAge(data[0]);
        obj.setCount(data[1]);
        pubreport.add(obj);
      }
      //System.out.println(headers);
      //System.out.println(lines);
    }
    return pubreport;
  }
  
  public List<PublisherReport> getOrg(String startdate, String enddate)
    throws SQLFeatureNotSupportedException, SqlParseException, CsvExtractorException, Exception
  {
    String query1 = "Select count(*),organisation from enhanceduserdatabeta1 where date between '" + startdate + "'" + " and " + "'" + enddate + "'" + " GROUP BY organisation";
    CSVResult csvResult1 = getCsvResult(false, query1);
    List<String> headers1 = csvResult1.getHeaders();
    List<String> lines1 = csvResult1.getLines();
    List<PublisherReport> pubreport = new ArrayList();
    if ((lines1 != null) && (!lines1.isEmpty()) && (!((String)lines1.get(0)).isEmpty()))
    {
      for (int i = 0; i < lines1.size(); i++)
      {
        PublisherReport obj = new PublisherReport();
        
        String[] data1 = ((String)lines1.get(i)).split(",");
        if ((data1[0].length() > 3) && (data1[0].charAt(0) != '_') && (!data1[0].contains("broadband")) && (!data1[0].contains("communication")) && (!data1[0].contains("cable")) && (!data1[0].contains("telecom")) && (!data1[0].contains("network")) && (!data1[0].contains("isp")) && (!data1[0].contains("hathway")) && (!data1[0].contains("internet")) && (!data1[0].contains("Sify")) && (!data1[0].toLowerCase().equals("_ltd")) && (!data1[0].equals("Googlebot")) && (!data1[0].equals("Bsnl")))
        {
          obj.setOrganisation(data1[0]);
          obj.setCount(data1[1]);
          
          pubreport.add(obj);
        }
      }
      //System.out.println(headers1);
      //System.out.println(lines1);
    }
    return pubreport;
  }
  
  public List<PublisherReport> getdayQuarterdata(String startdate, String enddate)
    throws SQLFeatureNotSupportedException, SqlParseException, CsvExtractorException, Exception
  {
    String query = "Select count(*),QuarterValue from enhanceduserdatabeta1 where date between '" + startdate + "'" + " and " + "'" + enddate + "'" + " GROUP BY QuarterValue";
    CSVResult csvResult = getCsvResult(false, query);
    List<String> headers = csvResult.getHeaders();
    List<String> lines = csvResult.getLines();
    List<PublisherReport> pubreport = new ArrayList();
    if ((lines != null) && (!lines.isEmpty()) && (!((String)lines.get(0)).isEmpty()))
    {
      for (int i = 0; i < lines.size(); i++)
      {
        PublisherReport obj = new PublisherReport();
        
        String[] data = ((String)lines.get(i)).split(",");
        if (data[0].equals("quarter1")) {
          data[0] = "quarter1 (00 - 04 AM)";
        }
        if (data[0].equals("quarter2")) {
          data[0] = "quarter2 (04 - 08 AM)";
        }
        if (data[0].equals("quarter3")) {
          data[0] = "quarter3 (08 - 12 AM)";
        }
        if (data[0].equals("quarter4")) {
          data[0] = "quarter4 (12 - 16 PM)";
        }
        if (data[0].equals("quarter5")) {
          data[0] = "quarter5 (16 - 20 PM)";
        }
        if (data[0].equals("quarter6")) {
          data[0] = "quarter6 (20 - 24 PM)";
        }
        obj.setTime_of_day(data[0]);
        obj.setCount(data[1]);
        
        pubreport.add(obj);
      }
      //System.out.println(headers);
      //System.out.println(lines);
    }
    return pubreport;
  }
  
  public List<PublisherReport> countBrandNameChannel(String startdate, String enddate, String channel_name)
    throws CsvExtractorException, Exception
  {
    String query = "SELECT COUNT(*)as count,brandName FROM enhanceduserdatabeta1 where channel_name = '" + channel_name + "' and date between " + "'" + startdate + "'" + " and " + "'" + enddate + "'" + " group by brandName";
    //System.out.println(query);
    CSVResult csvResult = getCsvResult(false, query);
    List<String> headers = csvResult.getHeaders();
    List<String> lines = csvResult.getLines();
    List<PublisherReport> pubreport = new ArrayList();
    if ((lines != null) && (!lines.isEmpty()) && (!((String)lines.get(0)).isEmpty()))
    {
      for (int i = 0; i < lines.size(); i++)
      {
        PublisherReport obj = new PublisherReport();
        
        String[] data = ((String)lines.get(i)).split(",");
        if(data[0].trim().toLowerCase().contains("logitech")==false && data[0].trim().toLowerCase().contains("mozilla")==false && data[0].trim().toLowerCase().contains("web_browser")==false && data[0].trim().toLowerCase().contains("microsoft")==false && data[0].trim().toLowerCase().contains("opera")==false && data[0].trim().toLowerCase().contains("epiphany")==false){ 
        obj.setBrandname(data[0]);
        obj.setCount(data[1]);
        obj.setChannelName(channel_name);
        pubreport.add(obj);
        } 
       }
  //    //System.out.println(headers);
  //    //System.out.println(lines);
    }
    return pubreport;
  }
  
  public List<PublisherReport> countBrowserChannel(String startdate, String enddate, String channel_name)
    throws CsvExtractorException, Exception
  {
    Aggregations result = query(String.format("SELECT COUNT(*),brandName,browser_name FROM enhanceduserdatabeta1 group by brandName,browser_name", new Object[] { "enhanceduserprofilestore" }));
    String query = "SELECT COUNT(*)as count,browser_name FROM enhanceduserdatabeta1 where channel_name ='" + channel_name + "' and date between " + "'" + startdate + "'" + " and " + "'" + enddate + "'" + " group by browser_name";
    CSVResult csvResult = getCsvResult(false, query);
    List<String> headers = csvResult.getHeaders();
    List<String> lines = csvResult.getLines();
    List<PublisherReport> pubreport = new ArrayList();
    if ((lines != null) && (!lines.isEmpty()) && (!((String)lines.get(0)).isEmpty()))
    {
      for (int i = 0; i < lines.size(); i++)
      {
        PublisherReport obj = new PublisherReport();
        
        String[] data = ((String)lines.get(i)).split(",");
        obj.setBrowser(data[0]);
        obj.setCount(data[1]);
        obj.setChannelName(channel_name);
        pubreport.add(obj);
      }
      //System.out.println(headers);
      //System.out.println(lines);
    }
    return pubreport;
  }
  
  public List<PublisherReport> countOSChannel(String startdate, String enddate, String channel_name)
    throws CsvExtractorException, Exception
  {
    Aggregations result = query(String.format("SELECT COUNT(*),brandName,browser_name FROM enhanceduserdatabeta1 group by brandName,browser_name", new Object[] { "enhanceduserprofilestore" }));
    String query = String.format("SELECT COUNT(*)as count,system_os FROM enhanceduserdatabeta1 where channel_name = '" + channel_name + "' and date between " + "'" + startdate + "'" + " and " + "'" + enddate + "'" + " group by system_os", new Object[] { "enhanceduserdatabeta1" });
    CSVResult csvResult = getCsvResult(false, query);
    List<String> headers = csvResult.getHeaders();
    List<String> lines = csvResult.getLines();
  //  //System.out.println(headers);
  //  //System.out.println(lines);
    List<PublisherReport> pubreport = new ArrayList();
    if ((lines != null) && (!lines.isEmpty()) && (!((String)lines.get(0)).isEmpty())) {
      for (int i = 0; i < lines.size(); i++)
      {
        PublisherReport obj = new PublisherReport();
        
        String[] data = ((String)lines.get(i)).split(",");
        obj.setOs(data[0]);
        obj.setCount(data[1]);
        obj.setChannelName(channel_name);
        pubreport.add(obj);
      }
    }
    return pubreport;
  }
  
  public List<PublisherReport> countModelChannel(String startdate, String enddate, String channel_name)
    throws CsvExtractorException, Exception
  {
    Aggregations result = query(String.format("SELECT COUNT(*),brandName,browser_name FROM enhanceduserdatabeta1 group by brandName,browser_name", new Object[] { "enhanceduserprofilestore" }));
    String query = String.format("SELECT COUNT(*)as count,modelName FROM enhanceduserdatabeta1 where channel_name = '" + channel_name + "' and date between " + "'" + startdate + "'" + " and " + "'" + enddate + "'" + " group by modelName", new Object[] { "enhanceduserdatabeta1" });
    CSVResult csvResult = getCsvResult(false, query);
    List<String> headers = csvResult.getHeaders();
    List<String> lines = csvResult.getLines();
    List<PublisherReport> pubreport = new ArrayList();
    if ((lines != null) && (!lines.isEmpty()) && (!((String)lines.get(0)).isEmpty())) {
      for (int i = 0; i < lines.size(); i++)
      {
        PublisherReport obj = new PublisherReport();
        
        String[] data = ((String)lines.get(i)).split(",");

        if(data[0].trim().toLowerCase().contains("logitech_revue")==false && data[0].trim().toLowerCase().contains("mozilla_firefox")==false && data[0].trim().toLowerCase().contains("apple_safari")==false && data[0].trim().toLowerCase().contains("generic_web")==false && data[0].trim().toLowerCase().contains("google_compute")==false && data[0].trim().toLowerCase().contains("microsoft_xbox")==false && data[0].trim().toLowerCase().contains("google_chromecast")==false && data[0].trim().toLowerCase().contains("opera")==false && data[0].trim().toLowerCase().contains("epiphany")==false && data[0].trim().toLowerCase().contains("laptop")==false){    
        obj.setMobile_device_properties(data[0]);
        obj.setCount(data[1]);
        obj.setChannelName(channel_name);
        pubreport.add(obj);
      }
        
        }
    }
    return pubreport;
  }
  
  public List<PublisherReport> countCityChannel(String startdate, String enddate, String channel_name)
    throws CsvExtractorException, Exception
  {
    Aggregations result = query(String.format("SELECT COUNT(*),brandName,browser_name FROM enhanceduserdatabeta1 group by brandName,browser_name", new Object[] { "enhanceduserprofilestore" }));
    String query = String.format("SELECT COUNT(*)as count,city FROM enhanceduserdatabeta1 where channel_name = '" + channel_name + "' and date between " + "'" + startdate + "'" + " and " + "'" + enddate + "'" + " group by city", new Object[] { "enhanceduserdatabeta1" });
    CSVResult csvResult = getCsvResult(false, query);
    List<String> headers = csvResult.getHeaders();
    List<String> lines = csvResult.getLines();
    List<PublisherReport> pubreport = new ArrayList();
    if ((lines != null) && (!lines.isEmpty()) && (!((String)lines.get(0)).isEmpty())) {
      for (int i = 0; i < lines.size(); i++)
      {
        PublisherReport obj = new PublisherReport();
        
        String[] data = ((String)lines.get(i)).split(",");
        obj.setCity(data[0]);
        obj.setCount(data[1]);
        obj.setChannelName(channel_name);
        pubreport.add(obj);
      }
    }
    return pubreport;
  }
  
  public List<PublisherReport> countfingerprintChannel(String startdate, String enddate, String channel_name)
    throws CsvExtractorException, Exception
  {
	  
	  
	  System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream("output.txt"))));
	  
    
	  String query00 = "SELECT cookie_id FROM enhanceduserdatabeta1 where channel_name = '" + 
		      channel_name + "' and date between " + "'" + startdate + "'" + " and " + "'" + enddate + "'" +"group by cookie_id limit 20000000";
	  
		 CSVResult csvResult00 = getCsvResult(false, query00);
		 List<String> headers00 = csvResult00.getHeaders();
		 List<String> lines00 = csvResult00.getLines();
		 List<PublisherReport> pubreport00 = new ArrayList();  
			  
		//  //System.out.println(headers00);
		//  //System.out.println(lines00);  
		  
		  for (int i = 0; i < lines00.size(); i++)
	      {
	       
	        String[] data = ((String)lines00.get(i)).split(",");
	  //      //System.out.println(data[0]);
	      }
		  
		  
		  
		Aggregations result = query(String.format("SELECT COUNT(*),brandName,browser_name FROM enhanceduserdatabeta1 group by brandName,browser_name", new Object[] { "enhanceduserprofilestore" }));
	    String query = "SELECT count(distinct(cookie_id))as reach,date FROM enhanceduserdatabeta1 where channel_name = '" + 
	      channel_name + "' and date between " + "'" + startdate + "'" + " and " + "'" + enddate + "'" + " group by date";
	    CSVResult csvResult = getCsvResult(false, query);
	    List<String> headers = csvResult.getHeaders();
	    List<String> lines = csvResult.getLines();
	    List<PublisherReport> pubreport = new ArrayList();
	    if ((lines != null) && (!lines.isEmpty()) && (!((String)lines.get(0)).isEmpty())) {
	      for (int i = 0; i < lines.size(); i++)
	      {
	        PublisherReport obj = new PublisherReport();
	        
	        String[] data = ((String)lines.get(i)).split(",");
	        obj.setDate(data[0]);
	        obj.setReach(data[1]);
	        obj.setChannelName(channel_name);
	        pubreport.add(obj);
	      }
	    }
	    
    return pubreport;
  }
  
  public List<PublisherReport> countAudiencesegmentChannel(String startdate, String enddate, String channel_name)
    throws CsvExtractorException, Exception
  {
 //   Aggregations result = query(String.format("SELECT COUNT(*),brandName,browser_name FROM enhanceduserdatabeta1 group by brandName,browser_name", new Object[] { "enhanceduserprofilestore" }));
  //  String query = String.format("SELECT COUNT(*)as count,audience_segment FROM enhanceduserdatabeta1 where channel_name = '" + channel_name + "' and date between " + "'" + startdate + "'" + " and " + "'" + enddate + "'" + " group by audience_segment", new Object[] { "enhanceduserdatabeta1" });
    
	String query0 = "SELECT DISTINCT(cookie_id) FROM enhanceduserdatabeta1 where channel_name = '" + channel_name + "' and date between " + "'" + startdate + "'" + " and " + "'" + enddate +"' limit 20000000";  
	
//	System.out.println(query0);
	final long startTime = System.currentTimeMillis();
	
//	CSVResult csvResult0 = getCsvResult(false, query0);
    
	final long endTime = System.currentTimeMillis();
	
	System.out.println("Total cookie count execution time: " + (endTime - startTime) );
	
	
  //  List<String> headers0 = csvResult0.getHeaders();
  //  List<String> lines0 = csvResult0.getLines();
    List<PublisherReport> pubreport0 = new ArrayList(); 
    Double count0 = 0.0;
    Double count1 = 0.0;
    Double count2 = 0.0;
    FastMap<String,Double> fingerprintmap = new FastMap<String,Double>();
    FastMap<String,Double> fingerprintmap1 = new FastMap<String,Double>();
    FastMap<String,Double> fingerprintmap2 = new FastMap<String,Double>();
    
    String cookieList = "";
    
    
 //   //System.out.println(headers0);
 //   //System.out.println(lines0);
    
   /*
    for (int i = 0; i < lines0.size(); i++)
    {
    
       String data0 = (String)lines0.get(i);
   
       if(data0 != null && data0.isEmpty()==false){
        if(cookieList.equals(""))
        cookieList = "'"+data0+"'";
        else
        cookieList=cookieList+","+"'"+data0+"'"; 	
       }
    }
    */
  //     lines0.clear();
   //    csvResult0=null;
	  
	String query = "SELECT SUM(contributionFactor) as count,audience_segment FROM audiencesegmentcomputeindex1 where cookie_id in ("+query0+")"+" group by audience_segment limit 20000000";  
	
//	System.out.println(query);
	
	final long startTime1 = System.currentTimeMillis();
	CSVResult csvResult = getCsvResult(false, query);
    
	final long endTime1 = System.currentTimeMillis();
	
	System.out.println("Total cookie audience count execution time: " + (endTime1 - startTime1) );
	
	
	List<String> headers = csvResult.getHeaders();
    List<String> lines = csvResult.getLines();
    List<PublisherReport> pubreport = new ArrayList();
    
 //   //System.out.println(headers);
 //   //System.out.println(lines);
    
    
  
    for (int i = 0; i < lines.size(); i++)
    {
       
       String[] data11 = ((String)lines.get(i)).split(",");
    	
       if(data11.length > 1){
       
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
    
    String query1 = "SELECT SUM(contributionFactor) as count,subcategory FROM subcategorycomputeindex1 where cookie_id in ("+query0+")"+ " group by subcategory limit 20000000";
    
  //  System.out.println(query1);
    
    final long startTime2 = System.currentTimeMillis();
	
    
    CSVResult csvResult1 = getCsvResult(false, query1);
    
    final long endTime2 = System.currentTimeMillis();
	
    System.out.println("Total cookie subcategory count execution time: " + (endTime2 - startTime2) );
    
    
    
    List<String> headers1 = csvResult1.getHeaders();
    List<String> lines1 = csvResult1.getLines();
    List<PublisherReport> pubreport1 = new ArrayList();
    
  //  //System.out.println(headers1);
 //   //System.out.println(lines1);
    
    
    for (int i = 0; i < lines1.size(); i++)
    {
    
       String[] data12 = ((String)lines1.get(i)).split(",");
    	
       if(data12.length > 1){
       
        if(fingerprintmap2.containsKey(data12[0])==false)
    	fingerprintmap2.put(data12[0].trim(),Double.parseDouble(data12[1]));
    	else
    	{
         count2 = fingerprintmap2.get(data12[0]);
         fingerprintmap2.put(data12[0].trim(),Double.parseDouble(data12[1])+count2);	
    	}
     }
       
       
       }
    
      lines1.clear();
      csvResult1 = null;
    
    String subcategory = null;
   
    if(fingerprintmap1.size()>0){
   
    	for (Map.Entry<String, Double> entry : fingerprintmap1.entrySet()) {
    	//System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
    

        PublisherReport obj = new PublisherReport();
        
   //     String[] data = ((String)lines.get(i)).split(",");
        
     //   if(data[0].trim().toLowerCase().contains("festivals"))
      //  obj.setAudience_segment("");
      //  else
        obj.setAudience_segment( entry.getKey());	
        obj.setCount(String.valueOf(entry.getValue()));
        obj.setChannelName(channel_name);
        if ((!entry.getKey().equals("tech")) && (!entry.getKey().equals("india")) && (!entry.getKey().trim().toLowerCase().equals("foodbeverage")) )
        {
         for (Map.Entry<String, Double> entry1 : fingerprintmap2.entrySet()) {
        	 
        	    
        	 
        	 PublisherReport obj1 = new PublisherReport();
            
           
            if (entry1.getKey().contains(entry.getKey()))
            {
              String substring = "_" + entry.getKey() + "_";
              subcategory = entry1.getKey().replace(substring, "");
           //   if(data[0].trim().toLowerCase().contains("festivals"))
           //   obj1.setAudience_segment("");
           //   else
        
              //System.out.println(" \n\n\n Key : " + subcategory + " Value : " + entry1.getValue());  
              obj1.setAudience_segment(subcategory);
              obj1.setCount(String.valueOf(entry1.getValue()));
              obj1.setChannelName(channel_name);
              obj.getAudience_segment_data().add(obj1);
            }
          }
          pubreport.add(obj);
        }
      
    }
    }
    return pubreport;
  }
  
  public List<PublisherReport> gettimeofdayChannel(String startdate, String enddate, String channel_name)
    throws SQLFeatureNotSupportedException, SqlParseException, CsvExtractorException, Exception
  {
    String query = "Select count(*) from enhanceduserdatabeta1 where channel_name = '" + channel_name + "' and date between " + "'" + startdate + "'" + " and " + "'" + enddate + "'" + " GROUP BY date_histogram(field='request_time','interval'='1h')";
    CSVResult csvResult = getCsvResult(false, query);
    List<String> headers = csvResult.getHeaders();
    List<String> lines = csvResult.getLines();
    List<PublisherReport> pubreport = new ArrayList();
    if ((lines != null) && (!lines.isEmpty()) && (!((String)lines.get(0)).isEmpty()))
    {
      for (int i = 0; i < lines.size(); i++)
      {
        PublisherReport obj = new PublisherReport();
        
        String[] data = ((String)lines.get(i)).split(",");
        obj.setTime_of_day(data[0]);
        obj.setCount(data[1]);
        obj.setChannelName(channel_name);
        pubreport.add(obj);
      }
      //System.out.println(headers);
      //System.out.println(lines);
    }
    return pubreport;
  }
  
  public List<PublisherReport> countPinCodeChannel(String startdate, String enddate, String channel_name)
    throws CsvExtractorException, Exception
  {
    Aggregations result = query(String.format("SELECT COUNT(*),brandName,browser_name FROM enhanceduserdatabeta1 group by brandName,browser_name", new Object[] { "enhanceduserprofilestore" }));
    String query = String.format("SELECT COUNT(*)as count,postalcode FROM enhanceduserdatabeta1 where channel_name = '" + channel_name + "' and date between " + "'" + startdate + "'" + " and " + "'" + enddate + "'" + " group by postalcode", new Object[] { "enhanceduserdatabeta1" });
    CSVResult csvResult = getCsvResult(false, query);
    List<String> headers = csvResult.getHeaders();
    List<String> lines = csvResult.getLines();
    //System.out.println(headers);
    //System.out.println(lines);
    List<PublisherReport> pubreport = new ArrayList();
    if ((lines != null) && (!lines.isEmpty()) && (!((String)lines.get(0)).isEmpty())) {
      for (int i = 0; i < lines.size(); i++)
      {
        PublisherReport obj = new PublisherReport();
        
        String[] data = ((String)lines.get(i)).split(",");
        obj.setPostalcode(data[0]);
        obj.setCount(data[1]);
        obj.setChannelName(channel_name);
        pubreport.add(obj);
      }
    }
    return pubreport;
  }
  
  public List<PublisherReport> countLatLongChannel(String startdate, String enddate, String channel_name)
    throws CsvExtractorException, Exception
  {
    Aggregations result = query(String.format("SELECT COUNT(*),brandName,browser_name FROM enhanceduserdatabeta1 group by brandName,browser_name", new Object[] { "enhanceduserprofilestore" }));
    String query = String.format("SELECT COUNT(*)as count,latitude_longitude FROM enhanceduserdatabeta1 where channel_name = '" + channel_name + "' and date between " + "'" + startdate + "'" + " and " + "'" + enddate + "'" + " group by latitude_longitude", new Object[] { "enhanceduserdatabeta1" });
    CSVResult csvResult = getCsvResult(false, query);
    List<String> headers = csvResult.getHeaders();
    List<String> lines = csvResult.getLines();
    //System.out.println(headers);
    //System.out.println(lines);
    List<PublisherReport> pubreport = new ArrayList();
    if ((lines != null) && (!lines.isEmpty()) && (!((String)lines.get(0)).isEmpty())) {
      for (int i = 0; i < lines.size(); i++)
      {
        PublisherReport obj = new PublisherReport();
        
        String[] data = ((String)lines.get(i)).split(",");
        String[] dashcount = data[0].split("_");
        if ((dashcount.length == 3) && (data[0].charAt(data[0].length() - 1) != '_'))
        {
          if (!dashcount[2].isEmpty())
          {
            obj.setLatitude_longitude(data[0]);
            obj.setCount(data[1]);
            obj.setChannelName(channel_name);
          }
          pubreport.add(obj);
        }
      }
    }
    return pubreport;
  }
  
  public List<PublisherReport> gettimeofdayQuarterChannel(String startdate, String enddate, String channel_name)
    throws SQLFeatureNotSupportedException, SqlParseException, CsvExtractorException, Exception
  {
    String query = "Select count(*) from enhanceduserdatabeta1 where channel_name = '" + channel_name + "' and date between " + "'" + startdate + "'" + " and " + "'" + enddate + "'" + " GROUP BY date_histogram(field='request_time','interval'='4h')";
    CSVResult csvResult = getCsvResult(false, query);
    List<String> headers = csvResult.getHeaders();
    List<String> lines = csvResult.getLines();
    List<PublisherReport> pubreport = new ArrayList();
    if ((lines != null) && (!lines.isEmpty()) && (!((String)lines.get(0)).isEmpty()))
    {
      for (int i = 0; i < lines.size(); i++)
      {
        PublisherReport obj = new PublisherReport();
        
        String[] data = ((String)lines.get(i)).split(",");
        obj.setTime_of_day(data[0]);
        obj.setCount(data[1]);
        obj.setChannelName(channel_name);
        pubreport.add(obj);
      }
      //System.out.println(headers);
      //System.out.println(lines);
    }
    return pubreport;
  }
  
  public List<PublisherReport> gettimeofdayDailyChannel(String startdate, String enddate, String channel_name)
    throws SQLFeatureNotSupportedException, SqlParseException, CsvExtractorException, Exception
  {
    String query = "Select count(*) from enhanceduserdatabeta1 where channel_name = '" + channel_name + "' and date between " + "'" + startdate + "'" + " and " + "'" + enddate + "'" + " GROUP BY date_histogram(field='request_time','interval'='1d')";
    CSVResult csvResult = getCsvResult(false, query);
    List<String> headers = csvResult.getHeaders();
    List<String> lines = csvResult.getLines();
    List<PublisherReport> pubreport = new ArrayList();
    if ((lines != null) && (!lines.isEmpty()) && (!((String)lines.get(0)).isEmpty()))
    {
      for (int i = 0; i < lines.size(); i++)
      {
        PublisherReport obj = new PublisherReport();
        
        String[] data = ((String)lines.get(i)).split(",");
        obj.setTime_of_day(data[0]);
        obj.setCount(data[1]);
        obj.setChannelName(channel_name);
        pubreport.add(obj);
      }
      //System.out.println(headers);
      //System.out.println(lines);
    }
    return pubreport;
  }
  
  public List<PublisherReport> getdayQuarterdataChannel(String startdate, String enddate, String channel_name)
    throws SQLFeatureNotSupportedException, SqlParseException, CsvExtractorException, Exception
  {
    String query = "Select count(*),QuarterValue from enhanceduserdatabeta1 where channel_name = '" + channel_name + "' and date between " + "'" + startdate + "'" + " and " + "'" + enddate + "'" + " GROUP BY QuarterValue";
    CSVResult csvResult = getCsvResult(false, query);
    List<String> headers = csvResult.getHeaders();
    List<String> lines = csvResult.getLines();
    List<PublisherReport> pubreport = new ArrayList();
    if ((lines != null) && (!lines.isEmpty()) && (!((String)lines.get(0)).isEmpty()))
    {
      for (int i = 0; i < lines.size(); i++)
      {
        PublisherReport obj = new PublisherReport();
        
        String[] data = ((String)lines.get(i)).split(",");
        if (data[0].equals("quarter1")) {
          data[0] = "quarter1 (00 - 04 AM)";
        }
        if (data[0].equals("quarter2")) {
          data[0] = "quarter2 (04 - 08 AM)";
        }
        if (data[0].equals("quarter3")) {
          data[0] = "quarter3 (08 - 12 AM)";
        }
        if (data[0].equals("quarter4")) {
          data[0] = "quarter4 (12 - 16 PM)";
        }
        if (data[0].equals("quarter5")) {
          data[0] = "quarter5 (16 - 20 PM)";
        }
        if (data[0].equals("quarter6")) {
          data[0] = "quarter6 (20 - 24 PM)";
        }
        obj.setTime_of_day(data[0]);
        obj.setCount(data[1]);
        obj.setChannelName(channel_name);
        pubreport.add(obj);
      }
      //System.out.println(headers);
      //System.out.println(lines);
    }
    return pubreport;
  }
  
  public List<PublisherReport> getGenderChannel(String startdate, String enddate, String channel_name)
    throws SQLFeatureNotSupportedException, SqlParseException, CsvExtractorException, Exception
  {
    String query = "Select count(*),gender from enhanceduserdatabeta1 where channel_name = '" + channel_name + "' and date between " + "'" + startdate + "'" + " and " + "'" + enddate + "'" + " GROUP BY gender";
    CSVResult csvResult = getCsvResult(false, query);
    List<String> headers = csvResult.getHeaders();
    List<String> lines = csvResult.getLines();
    List<PublisherReport> pubreport = new ArrayList();
    
    //System.out.println(headers);
    //System.out.println(lines);
    if ((lines != null) && (!lines.isEmpty()) && (!((String)lines.get(0)).isEmpty()))
    {
      for (int i = 0; i < lines.size(); i++)
      {
        PublisherReport obj = new PublisherReport();
        
        String[] data = ((String)lines.get(i)).split(",");
        obj.setGender(data[0]);
        obj.setCount(data[1]);
        obj.setChannelName(channel_name);
        pubreport.add(obj);
      }
      //System.out.println(headers);
      //System.out.println(lines);
    }
    return pubreport;
  }
  
  public List<PublisherReport> getAgegroupChannel(String startdate, String enddate, String channel_name)
    throws SQLFeatureNotSupportedException, SqlParseException, CsvExtractorException, Exception
  {
    String query = "Select count(*),agegroup from enhanceduserdatabeta1 where channel_name = '" + channel_name + "' and date between " + "'" + startdate + "'" + " and " + "'" + enddate + "'" + " GROUP BY agegroup";
    CSVResult csvResult = getCsvResult(false, query);
    List<String> headers = csvResult.getHeaders();
    List<String> lines = csvResult.getLines();
    List<PublisherReport> pubreport = new ArrayList();
    if ((lines != null) && (!lines.isEmpty()) && (!((String)lines.get(0)).isEmpty()))
    {
      for (int i = 0; i < lines.size(); i++)
      {
        PublisherReport obj = new PublisherReport();
        
        String[] data = ((String)lines.get(i)).split(",");
        obj.setAge(data[0]);
        obj.setCount(data[1]);
        obj.setChannelName(channel_name);
        pubreport.add(obj);
      }
      //System.out.println(headers);
      //System.out.println(lines);
    }
    return pubreport;
  }
  
  public List<PublisherReport> getISPChannel(String startdate, String enddate, String channel_name)
    throws SQLFeatureNotSupportedException, SqlParseException, CsvExtractorException, Exception
  {
    String query = "Select count(*),ISP from enhanceduserdatabeta1 where channel_name = '" + channel_name + "' and date between " + "'" + startdate + "'" + " and " + "'" + enddate + "'" + " GROUP BY ISP";
    CSVResult csvResult = getCsvResult(false, query);
    List<String> headers = csvResult.getHeaders();
    List<String> lines = csvResult.getLines();
    List<PublisherReport> pubreport = new ArrayList();
    if ((lines != null) && (!lines.isEmpty()) && (!((String)lines.get(0)).isEmpty()))
    {
      for (int i = 0; i < lines.size(); i++)
      {
        PublisherReport obj = new PublisherReport();
        
        String[] data = ((String)lines.get(i)).split(",");
        if(data[0].trim().toLowerCase().equals("_ltd")==false){ 
        obj.setISP(data[0]);
        obj.setCount(data[1]);
        obj.setChannelName(channel_name);
        pubreport.add(obj);
         }
        }
      //System.out.println(headers);
      //System.out.println(lines);
    }
    return pubreport;
  }
  
  public List<PublisherReport> getOrgChannel(String startdate, String enddate, String channel_name)
    throws SQLFeatureNotSupportedException, SqlParseException, CsvExtractorException, Exception
  {
    String query1 = "Select count(*),organisation from enhanceduserdatabeta1 where channel_name = '" + channel_name + "' and date between " + "'" + startdate + "'" + " and " + "'" + enddate + "'" + " GROUP BY organisation";
    CSVResult csvResult1 = getCsvResult(false, query1);
    List<String> headers1 = csvResult1.getHeaders();
    List<String> lines1 = csvResult1.getLines();
    List<PublisherReport> pubreport = new ArrayList();
    if ((lines1 != null) && (!lines1.isEmpty()) && (!((String)lines1.get(0)).isEmpty()))
    {
      for (int i = 0; i < lines1.size(); i++)
      {
        PublisherReport obj = new PublisherReport();
        
        String[] data1 = ((String)lines1.get(i)).split(",");
        if ((data1[0].length() > 3) && (data1[0].charAt(0) != '_') && (!data1[0].trim().toLowerCase().contains("broadband")) && (!data1[0].trim().toLowerCase().contains("communication")) && (!data1[0].trim().toLowerCase().contains("cable")) && (!data1[0].trim().toLowerCase().contains("telecom")) && (!data1[0].trim().toLowerCase().contains("network")) && (!data1[0].trim().toLowerCase().contains("isp")) && (!data1[0].trim().toLowerCase().contains("hathway")) && (!data1[0].trim().toLowerCase().contains("internet")) && (!data1[0].trim().toLowerCase().equals("_ltd")) && (!data1[0].trim().toLowerCase().contains("googlebot")) && (!data1[0].trim().toLowerCase().contains("sify")) && (!data1[0].trim().toLowerCase().contains("bsnl")) && (!data1[0].trim().toLowerCase().contains("reliance")) && (!data1[0].trim().toLowerCase().contains("broadband")) && (!data1[0].trim().toLowerCase().contains("tata")) && (!data1[0].trim().toLowerCase().contains("nextra")))
        {
          obj.setOrganisation(data1[0]);
          obj.setCount(data1[1]);
          obj.setChannelName(channel_name);
          pubreport.add(obj);
        }
      }
      //System.out.println(headers1);
      //System.out.println(lines1);
    }
    return pubreport;
  }
  
  public static CSVResult getCsvResult(boolean flat, String query)
    throws SqlParseException, SQLFeatureNotSupportedException, Exception, CsvExtractorException
  {
    return getCsvResult(flat, query, false, false);
  }
  
  public static CSVResult getCsvResult(boolean flat, String query, boolean includeScore, boolean includeType)
    throws SqlParseException, SQLFeatureNotSupportedException, Exception, CsvExtractorException
  {
    SearchDao searchDao = getSearchDao();
    QueryAction queryAction = searchDao.explain(query);
    Object execution = QueryActionElasticExecutor.executeAnyAction(searchDao.getClient(), queryAction);
    return new CSVResultsExtractor(includeScore, includeType).extractResults(execution, flat, ",");
  }
  
  public static void sumTest()
    throws IOException, SqlParseException, SQLFeatureNotSupportedException
  {}
  
  private static Aggregations query(String query)
    throws SqlParseException, SQLFeatureNotSupportedException
  {
    SqlElasticSearchRequestBuilder select = getSearchRequestBuilder(query);
    return ((SearchResponse)select.get()).getAggregations();
  }
  
  private static SqlElasticSearchRequestBuilder getSearchRequestBuilder(String query)
    throws SqlParseException, SQLFeatureNotSupportedException
  {
    SearchDao searchDao = getSearchDao();
    return (SqlElasticSearchRequestBuilder)searchDao.explain(query).explain();
  }
  
  private static InetSocketTransportAddress getTransportAddress()
  {
    String host = System.getenv("ES_TEST_HOST");
    String port = System.getenv("ES_TEST_PORT");
    if (host == null)
    {
      host = "localhost";
      //System.out.println("ES_TEST_HOST enviroment variable does not exist. choose default 'localhost'");
    }
    if (port == null)
    {
      port = "9300";
      //System.out.println("ES_TEST_PORT enviroment variable does not exist. choose default '9300'");
    }
    //System.out.println(String.format("Connection details: host: %s. port:%s.", new Object[] { host, port }));
    return new InetSocketTransportAddress(host, Integer.parseInt(port));
  }
}
