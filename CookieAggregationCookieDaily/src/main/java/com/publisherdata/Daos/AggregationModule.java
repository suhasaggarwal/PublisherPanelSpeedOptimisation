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
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregations;
import org.nlpcn.es4sql.SearchDao;
import org.nlpcn.es4sql.exception.SqlParseException;
import org.nlpcn.es4sql.query.QueryAction;
import org.nlpcn.es4sql.query.SqlElasticSearchRequestBuilder;

import util.IndexCategoriesData;


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
	  
	  PrintStream out = new PrintStream(new FileOutputStream("cookiedaily.txt"));
	  System.setOut(out);
	  
	  setUp();
	  updateCookieCountDaily( "2016-11-18","2016-11-18");
	 //   updateCookieCountDaily(args[0],args[1]);
	 // aggregateCookieAudienceSegmentChannel("2016-11-01 00:00:01","2016-11-30 23:59:59");
	 // aggregateCookieSubcategoryChannel("2016-11-01 00:00:01","2016-11-30 23:59:59");
	//  
	  
	//  countfingerprintChannel("2016-08-20","2016-12-02", "Mumbai_T1_airport");
	  
	//  countAudiencesegmentChannel("2016-08-20","2016-12-02", "Mumbai_T1_airport");
	  
  }
  
  public static void setUp()
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
    Aggregations result = query(String.format("SELECT COUNT(*),brandName,browser_name FROM enhanceduserdatabeta1 group by brandName,browser_name", new Object[] { "enhancedenhanceduserdatabeta1" }));
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
    Aggregations result = query(String.format("SELECT COUNT(*),brandName,browser_name FROM enhanceduserdatabeta1 group by brandName,browser_name", new Object[] { "enhancedenhanceduserdatabeta1" }));
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
    Aggregations result = query(String.format("SELECT COUNT(*),brandName,browser_name FROM enhanceduserdatabeta1 group by brandName,browser_name", new Object[] { "enhancedenhanceduserdatabeta1" }));
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
    Aggregations result = query(String.format("SELECT COUNT(*),brandName,browser_name FROM enhanceduserdatabeta1 group by brandName,browser_name", new Object[] { "enhancedenhanceduserdatabeta1" }));
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
    Aggregations result = query(String.format("SELECT COUNT(*),brandName,browser_name FROM enhanceduserdatabeta1 group by brandName,browser_name", new Object[] { "enhancedenhanceduserdatabeta1" }));
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
    Aggregations result = query(String.format("SELECT COUNT(*),brandName,browser_name FROM enhanceduserdatabeta1 group by brandName,browser_name", new Object[] { "enhancedenhanceduserdatabeta1" }));
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
    Aggregations result = query(String.format("SELECT COUNT(*),brandName,browser_name FROM enhanceduserdatabeta1 group by brandName,browser_name", new Object[] { "enhancedenhanceduserdatabeta1" }));
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
    Aggregations result = query(String.format("SELECT COUNT(*),brandName,browser_name FROM enhanceduserdatabeta1 group by brandName,browser_name", new Object[] { "enhancedenhanceduserdatabeta1" }));
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
  
  public static void updateCookieCountDaily(String startdate, String enddate)
    throws CsvExtractorException, Exception
  {
		String query0 = "SELECT COUNT(*)as count,cookie_id FROM enhanceduserdatabeta1 where date between " + "'" + startdate + "'" + " and " + "'" + enddate +"' group by cookie_id limit 20000000";  
		CSVResult csvResult0 = getCsvResult(false, query0);
	    List<String> headers0 = csvResult0.getHeaders();
	    List<String> lines0 = csvResult0.getLines();
	    List<PublisherReport> pubreport0 = new ArrayList(); 
	    Double count0 = 0.0;
	    Double count1 = 0.0;
	    Double count2 = 0.0;
	    Map<String,Double> fingerprintmap = new HashMap<String,Double>();
	    Map<String,Double> fingerprintmap1 = new HashMap<String,Double>();
	    Map<String,Double> fingerprintmap2 = new HashMap<String,Double>();
	    String[] data0 = null;
	    String count = null;
	    String audience_Segment = null;
	    String cookie_id = null;
	    Double finalCount = 0.0;
	    //System.out.println(headers0);
	    //System.out.println(lines0);
	    SearchHit[] fingerprintDetails = null;
	    String count00 = null;
	    String fingerprintId  = null;
	    String documentId = null;
	    Map<String, Object> result =  new HashMap<String,Object>();
	    
	    Map<String,Object> cookiedata = new HashMap<String,Object>();
	    for (int i = 0; i < lines0.size(); i++)
	    {
	    
	      try{	
	       data0 = ((String)lines0.get(i)).split(",");
	    	
	       /*
	        if(fingerprintmap.containsKey(data0[1])==false)
	    	fingerprintmap.put(data0[0].trim(),Double.parseDouble(data0[1]));
	    	else
	    	{
	         count0 = fingerprintmap.get(data0[0]);
	         fingerprintmap.put(data0[0].trim(),Double.parseDouble(data0[1])+count0);	
	    	}
	    
	    
	    */
	       if(data0.length > 1){
           count00 = data0[1];
	       
	       
	       
			fingerprintDetails = null;
		
			
			fingerprintId = data0[0].trim();
		    fingerprintDetails = ElasticSearchAPIs.searchDocumentFingerprintIds(client,
						"cookieindex1", "core2",
						"cookie_id", fingerprintId);
				
	       if(fingerprintDetails!=null && fingerprintDetails.length!=0){
		    for (SearchHit hit : fingerprintDetails) {
				result = hit.getSource();
				documentId = hit.getId();
				cookie_id=(String) result.get("cookie_id"); 
				count = (String)result.get("count");
				if( count!=null && count00 !=null){
				finalCount = Double.parseDouble(count)+Double.parseDouble(count00);
		        result.put("count", finalCount);
		        IndexCategoriesData1.updateDocument(client, "cookieindex1", "core2", documentId, "count", finalCount.toString());
			   }
			 }
	       }
	       else{
	    	   
	    	   if(data0[0]!=null && data0[1]!=null && data0[0].isEmpty()==false && data0[1].isEmpty()==false){
	    	       cookiedata.put("cookie_id",data0[0]);
	    	       cookiedata.put("count",data0[1]);
	    	       }
	    	      IndexCategoriesData.postcookieDocumentElasticSearch(cookiedata,client);
	    	      cookiedata.clear();  
	    	   
	    	   
	    	   
	    	   
	    	   
	         }
	       
	       }
	      }
	       catch(Exception e){
	    	   e.printStackTrace();
	    	   continue;  
	    	  
	       }
	       
	       }
	  //     int finalCount = Integer.parseInt(count)+Integer.parseInt(count00);
	    //   String cookie_id = data0[0];
	//       String count = data0[1];
	      
  }
  
  public List<PublisherReport> countISP(String startdate, String enddate)
    throws CsvExtractorException, Exception
  {
    Aggregations result = query(String.format("SELECT COUNT(*),brandName,browser_name FROM enhanceduserdatabeta1 group by brandName,browser_name", new Object[] { "enhancedenhanceduserdatabeta1" }));
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
        obj.setISP(data[0]);
        obj.setCount(data[1]);
        pubreport.add(obj);
      }
      //System.out.println(headers);
      //System.out.println(lines);
    }
    return pubreport;
  }
  
  public List<PublisherReport> countOrg(String startdate, String enddate)
    throws CsvExtractorException, Exception
  {
    Aggregations result = query(String.format("SELECT COUNT(*),brandName,browser_name FROM enhanceduserdatabeta1 group by brandName,browser_name", new Object[] { "enhancedenhanceduserdatabeta1" }));
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
    Aggregations result = query(String.format("SELECT COUNT(*),brandName,browser_name FROM enhanceduserdatabeta1 group by brandName,browser_name", new Object[] { "enhancedenhanceduserdatabeta1" }));
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
    Aggregations result = query(String.format("SELECT COUNT(*),brandName,browser_name FROM enhanceduserdatabeta1 group by brandName,browser_name", new Object[] { "enhancedenhanceduserdatabeta1" }));
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
        if ((data1[0].length() > 3) && (data1[0].charAt(0) != '_') && (!data1[0].contains("broadband")) && (!data1[0].contains("communication")) && (!data1[0].contains("cable")) && (!data1[0].contains("telecom")) && (!data1[0].contains("network")) && (!data1[0].contains("isp")) && (!data1[0].contains("hathway")) && (!data1[0].contains("internet")) && (!data1[0].contains("Sify")) && (!data1[0].equals("_ltd")) && (!data1[0].equals("Googlebot")) && (!data1[0].equals("Bsnl")))
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
    Aggregations result = query(String.format("SELECT COUNT(*),brandName,browser_name FROM enhanceduserdatabeta1 group by brandName,browser_name", new Object[] { "enhancedenhanceduserdatabeta1" }));
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
    Aggregations result = query(String.format("SELECT COUNT(*),brandName,browser_name FROM enhanceduserdatabeta1 group by brandName,browser_name", new Object[] { "enhancedenhanceduserdatabeta1" }));
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
    Aggregations result = query(String.format("SELECT COUNT(*),brandName,browser_name FROM enhanceduserdatabeta1 group by brandName,browser_name", new Object[] { "enhancedenhanceduserdatabeta1" }));
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
    Aggregations result = query(String.format("SELECT COUNT(*),brandName,browser_name FROM enhanceduserdatabeta1 group by brandName,browser_name", new Object[] { "enhancedenhanceduserdatabeta1" }));
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
		  
		  
		  
		Aggregations result = query(String.format("SELECT COUNT(*),brandName,browser_name FROM enhanceduserdatabeta1 group by brandName,browser_name", new Object[] { "enhancedenhanceduserdatabeta1" }));
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
  
  public static void aggregateCookieDataChannel(String startdate, String enddate)
    throws CsvExtractorException, Exception
  {
 //   Aggregations result = query(String.format("SELECT COUNT(*),brandName,browser_name FROM enhanceduserdatabeta1 group by brandName,browser_name", new Object[] { "enhancedenhanceduserdatabeta1" }));
  //  String query = String.format("SELECT COUNT(*)as count,audience_segment FROM enhanceduserdatabeta1 where channel_name = '" + channel_name + "' and date between " + "'" + startdate + "'" + " and " + "'" + enddate + "'" + " group by audience_segment", new Object[] { "enhanceduserdatabeta1" });
    
//	String query0 = "SELECT COUNT(*)as count,cookie_id,date,channel_name FROM enhanceduserdatabeta1"+" where date between " + "'" + startdate + "'" + " and " + "'" + enddate + "'" +" group by cookie_id,date,channel_name limit 20000000";  
	
//	System.out.println(query0);
	
	String query1 = "SELECT COUNT(*)as count,audience_segment,cookie_id FROM enhanceduserdatabeta1"+" where date between " + "'" + startdate + "'" + " and " + "'" + enddate + "'"+" group by audience_segment,cookie_id limit 20000000";  
	
//	String query2 = "SELECT COUNT(*)as count,subcategory,cookie_id FROM enhanceduserdatabeta1"+" where date between " + "'" + startdate + "'" + " and " + "'" + enddate + "'"+" group by subcategory,cookie_id limit 20000000";  
	
/*	
	
	final long startTime = System.currentTimeMillis();
	
    CSVResult csvResult0 = getCsvResult(false, query0);
    
	
	final long endTime = System.currentTimeMillis();
	
	System.out.println("Total aggregation query execution time: " + (endTime - startTime) );
	
	
	
	
	
	List<String> headers0 = csvResult0.getHeaders();
    List<String> lines0 = csvResult0.getLines();
    List<PublisherReport> pubreport0 = new ArrayList(); 
   
    Map<String,Object> cookiedata = new HashMap<String,Object>();
   
    for (int i = 0; i < lines0.size(); i++)
    {
       try{
       String[] data0 = ((String)lines0.get(i)).split(",");
       if(i<4)
       System.out.println(lines0.get(i));	
       
       if(data0[0]!=null && data0[1]!=null && data0[2]!=null && data0[3]!=null && data0[0].isEmpty()==false && data0[1].isEmpty()==false && data0[2].isEmpty()==false && data0[3].isEmpty()==false){
       cookiedata.put("cookie_id",data0[0]);
       cookiedata.put("date",data0[1]);
       cookiedata.put("channel",data0[2]);
       cookiedata.put("count",data0[3]);
       }
      IndexCategoriesData.postcookieDocumentElasticSearch(cookiedata,client);
      cookiedata.clear();
       }
       catch(Exception e){
    	   
    	   continue;
       }
      
      
    }
    lines0.clear();
    csvResult0 = null;
    
  */  
	
    
    final long startTime1 = System.currentTimeMillis();
	CSVResult csvResult = getCsvResult(false, query1);
    
	final long endTime1 = System.currentTimeMillis();
	
	System.out.println("Total cookie audience count execution time: " + (endTime1 - startTime1) );
	
	
	List<String> headers = csvResult.getHeaders();
    List<String> lines = csvResult.getLines();
    List<PublisherReport> pubreport = new ArrayList();


    Map<String,Object> audienceSegmentdata = new HashMap<String,Object>();
    
    
    for (int i = 0; i < lines.size(); i++)
    {
    
       try{    	
       String[] data1 = ((String)lines.get(i)).split(",");
       if(i<4)
       System.out.println(lines.get(i));
      
       
       if(data1[0]!=null && data1[1]!=null && data1[2]!=null && data1[0].isEmpty()==false && data1[1].isEmpty()==false && data1[2].isEmpty()==false){
       audienceSegmentdata.put("audience_segment",data1[0]);
       audienceSegmentdata.put("cookie_id",data1[1]);
       audienceSegmentdata.put("count",data1[2]);
       audienceSegmentdata.put("date",enddate);
    //   IndexCategoriesData.postaudienceSegmentElasticSearch(audienceSegmentdata,client);
       audienceSegmentdata.clear();
        }
       }
       
       catch(Exception e){
    	   
    	   continue;
       }
    }
    lines.clear();
    
    csvResult =null;

   /*
    
    final long startTime2 = System.currentTimeMillis();
	
    
    CSVResult csvResult1 = getCsvResult(false, query2);
    
    final long endTime2 = System.currentTimeMillis();
	
    System.out.println("Total cookie subcategory count execution time: " + (endTime2 - startTime2) );
    
    List<String> headers1 = csvResult1.getHeaders();
    List<String> lines1 = csvResult1.getLines();
    List<PublisherReport> pubreport1 = new ArrayList();
    
    
    Map<String,Object> subcategorydata = new HashMap<String,Object>();
    
    for (int i = 0; i < lines1.size(); i++)
    {
    	try{    	
    	       String[] data2 = ((String)lines1.get(i)).split(",");
    	       if(i<4)
    	       System.out.println(lines1.get(i));
    	      
    	       
    if(data2[0]!=null && data2[1]!=null && data2[2]!=null && data2[0].isEmpty()==false && data2[1].isEmpty()==false && data2[2].isEmpty()==false){

        subcategorydata.put("subcategory",data2[0]);
        subcategorydata.put("cookie_id",data2[1]);
        subcategorydata.put("count",data2[2]);
        subcategorydata.put("date",enddate);
        IndexCategoriesData.postsubcategoryElasticSearch(subcategorydata,client);
    
        subcategorydata.clear();
        
           }
    	}
        catch(Exception e){
 	   
 	   continue;
    }
 }
       lines1.clear();
 
       csvResult1 =null;
    
     */
	
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
    Aggregations result = query(String.format("SELECT COUNT(*),brandName,browser_name FROM enhanceduserdatabeta1 group by brandName,browser_name", new Object[] { "enhancedenhanceduserdatabeta1" }));
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
    Aggregations result = query(String.format("SELECT COUNT(*),brandName,browser_name FROM enhanceduserdatabeta1 group by brandName,browser_name", new Object[] { "enhancedenhanceduserdatabeta1" }));
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
        obj.setISP(data[0]);
        obj.setCount(data[1]);
        obj.setChannelName(channel_name);
        pubreport.add(obj);
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
  
  private static CSVResult getCsvResult(boolean flat, String query)
    throws SqlParseException, SQLFeatureNotSupportedException, Exception, CsvExtractorException
  {
    return getCsvResult(flat, query, false, false);
  }
  
  private static CSVResult getCsvResult(boolean flat, String query, boolean includeScore, boolean includeType)
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
      System.out.println("ES_TEST_HOST enviroment variable does not exist. choose default 'localhost'");
    }
    if (port == null)
    {
      port = "9300";
      System.out.println("ES_TEST_PORT enviroment variable does not exist. choose default '9300'");
    }
    //System.out.println(String.format("Connection details: host: %s. port:%s.", new Object[] { host, port }));
    return new InetSocketTransportAddress(host, Integer.parseInt(port));
  }
}
