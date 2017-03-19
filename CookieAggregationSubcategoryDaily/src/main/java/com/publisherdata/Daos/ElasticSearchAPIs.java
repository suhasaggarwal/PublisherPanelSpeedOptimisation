package com.publisherdata.Daos;




import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;



public class ElasticSearchAPIs {

public static SearchHit[] searchDocumentFingerprintIds(Client client, String index, String type,
            String field, String value){

	SearchHit[] results = null;
	System.out.println("Searching Records: for fingerprints");
//	FieldSortBuilder request_time_sort  = SortBuilders.fieldSort("request_time").order(SortOrder.ASC);
	try{
	SearchResponse response = client.prepareSearch(index)
              .setTypes(type)
              .setSearchType(SearchType.QUERY_AND_FETCH)
              .setQuery(QueryBuilders.matchQuery(field,value))   
              .setExplain(true)
              .execute()
              .actionGet();
	
	results = response.getHits().getHits();
	System.out.println("Current results: " + results.length);
	/*
	for (SearchHit hit : results) {
	System.out.println("------------------------------");
	Map<String,Object> result = hit.getSource();   

	System.out.println(result);
	return result;
	*/
	return results;
	}
	catch(Exception e){
		System.out.println("Exception in searching for fingerprint. "+e);
	}
	return results;
}

public static SearchHit[] searchDocumentCampaignIds(Client client, String index, String type,
        String field, String value){


	

SearchResponse response = client.prepareSearch(index)
          .setTypes(type)
          .setSearchType(SearchType.QUERY_AND_FETCH)
          .setQuery(QueryBuilders.matchQuery(field,value))   
          .addSort(SortBuilders.fieldSort("created_date_time"))
          .setFrom(0).setSize(10).setExplain(true)
          .execute()
          .actionGet();

SearchHit[] results = response.getHits().getHits();
//System.out.println("Current results: " + results.length);
/*
for (SearchHit hit : results) {
System.out.println("------------------------------");
Map<String,Object> result = hit.getSource();   

System.out.println(result);
return result;
*/
return results;
}

}





