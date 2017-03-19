package com.publisherdata.Daos;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.ImmutableSettings.Builder;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.ScriptService.ScriptType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;



public class IndexCategoriesData1 {

private static Client client;
    
    

    /**
     * This method returns ESclient instance.
     *
     * @return the es client
     * ESClient instance
     */
	public static Client getEsClient() {

		if (client == null) {
			client = getES();
		}
		return client;
	}

	/**
	 * Sets the es client.
	 *
	 * @param esClient the new es client
	 */
	public  void setEsClient(final Client esClient) {

		IndexCategoriesData1.client = esClient;
	}

	/**
	 * Gets the es.
	 *
	 * @return the es
	 */
	private static Client getES() {
		try {
			
			final Builder settings = ImmutableSettings.settingsBuilder().put("cluster.name", "elasticsearch");
			
			final TransportClient transportClient = new TransportClient(settings);
			transportClient.addTransportAddress(new InetSocketTransportAddress("10.12.2.61", 9300));
			
			return transportClient;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main (String args[]){
        IndexCategoriesData1 p = new IndexCategoriesData1();
      //  p.postElasticSearch();
    }


    public static Map<String, Object> putJsonDocument(String category, String subcategory, String content){

            Map<String, Object> jsonDocument = new HashMap<String, Object>();
            
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            String postDate = dateFormat.format(date);
            jsonDocument.put("category", category);
            jsonDocument.put("subcategory", subcategory);
            jsonDocument.put("postDate", postDate);
            jsonDocument.put("content", content);

            return jsonDocument;
    }

    //Use context of time to distinguish mixed categories

    public static void postElasticSearch(Map<String, Object> categoryDocument){

    	
         
    	String value = (String)categoryDocument.get("content");
    	
   
  //  	SearchHit[] results = searchDocumentContent(client, "dmpcategoriesdata" ,"core2" ,"content" ,value);
  //  	if(results == null || results.length == 0)
    	client.prepareIndex("dmpcategoriesdata", "core2").setSource(categoryDocument).execute().actionGet();

    }
    //If content is already there and classified, it is not further analysed

public static SearchHit[] searchEntireUserDataSorted(Client client, String index, String type,
            String field, String value){

	
	
	
	SearchResponse response = client.prepareSearch(index)
              .setTypes(type)
              .setSearchType(SearchType.QUERY_AND_FETCH)
              .setQuery(QueryBuilders.matchAllQuery())   
              .setFrom(0).setSize(60).setExplain(true)
              .addSort("postDate",org.elasticsearch.search.sort.SortOrder.DESC)
              .execute()
              .actionGet();

SearchHit[] results = response.getHits().getHits();
System.out.println("Current results: " + results.length);

return results;
}
    
    
//Will scan entire User profile database and return records one by one


public static SearchHit[] searchEntireUserData(Client client, String index, String type){


	SearchResponse response = client
			.prepareSearch(index)
			.setTypes(type)
			.setSearchType(SearchType.QUERY_AND_FETCH)
			.setPostFilter(
					FilterBuilders.rangeFilter("postDate")
							.from("now-48h").to("now"))
			.setQuery(QueryBuilders.matchAllQuery())
			.addSort(
					SortBuilders.fieldSort("postDate").order(
							SortOrder.ASC)).setFrom(0).setSize(60000)
			.setExplain(true).execute().actionGet();

SearchHit[] results = response.getHits().getHits();
System.out.println("Current results: " + results.length);

return results;
}
 
    
public static SearchHit[] searchDocumentContent(Client client, String index, String type,
        String field, String value){




SearchResponse response = client.prepareSearch(index)
          .setTypes(type)
          .setSearchType(SearchType.QUERY_AND_FETCH)
          .setQuery(QueryBuilders.matchQuery(field,value))   
          .setFrom(0).setSize(60).setExplain(true)
          .addSort("postDate",org.elasticsearch.search.sort.SortOrder.DESC)
          .execute()
          .actionGet();

SearchHit[] results = response.getHits().getHits();
System.out.println("Current results: " + results.length);

return results;
}

public static IndexResponse doIndex(Client client, String index, String type, String id, Map<String, Object> data) {

    return client.prepareIndex(index, type, id)
            .setSource(data)
            .execute()
            .actionGet();
}


public static void updateDocument(Client client, String index, String type, 

        String id, String field, String newValue){


	
System.out.println("Categorisation Data Updated"+"\n");
	
Map<String, Object> updateObject = new HashMap<String, Object>();

updateObject.put(field, newValue);

client.prepareUpdate(index, type, id)
.setScript("ctx._source." + field + "=" + field, ScriptType.INLINE)
.setScriptParams(updateObject).execute().actionGet();

}



    
}