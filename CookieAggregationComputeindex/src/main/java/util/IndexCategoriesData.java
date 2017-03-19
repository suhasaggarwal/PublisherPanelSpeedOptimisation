package util;



import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.ImmutableSettings.Builder;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;



public class IndexCategoriesData {

	
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

		IndexCategoriesData.client = esClient;
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
			transportClient.addTransportAddress(new InetSocketTransportAddress("localhost", 9300));
			
			return transportClient;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	
	
	
	
	
	
	public static void main(String args[]) {
		IndexCategoriesData p = new IndexCategoriesData();
		// p.postElasticSearch();
	}

	public static Map<String, String> putCookieJsonDocument(String count,String date, String cookie_id, String channel) {

		Map<String, String> jsonDocument = new HashMap<String, String>();

		
		jsonDocument.put("count", count);
		jsonDocument.put("date",date );
		jsonDocument.put("cookie_id", cookie_id);
		jsonDocument.put("channel", channel);

		return jsonDocument;
	}

	
	public static Map<String, String> putAudienceSegmentJsonDocument(String audience_segment,
			String cookie_id, String count) {

		Map<String, String> jsonDocument = new HashMap<String, String>();

		
		jsonDocument.put("audience_segment", audience_segment);
		jsonDocument.put("cookie_id", cookie_id);
		jsonDocument.put("count", count);
		

		return jsonDocument;
	}
	
	
	public static Map<String, String> putSubcategoryJsonDocument(String subcategory,
			String cookie_id, String count) {

		Map<String, String> jsonDocument = new HashMap<String, String>();

		jsonDocument.put("subcategory", subcategory);
		jsonDocument.put("cookie_id", cookie_id);
		jsonDocument.put("count", count);

		return jsonDocument;
	}
	
	// Use context of time to distinguish mixed categories

	public static void postcookieDocumentElasticSearch(Map<String, Object> indexDocument, TransportClient client) {

		
     //   getEsClient();

		client.prepareIndex("cookieindex1", "core2")
				.setSource(indexDocument).execute().actionGet();

	}

	public static void postaudienceSegmentElasticSearch(Map<String, Object > indexDocument, TransportClient client) {

		
     //   getEsClient();

		client.prepareIndex("audiencesegmentindex1", "core2")
				.setSource(indexDocument).execute().actionGet();

	}
	
	

	public static void postaudienceSegmentcomputeElasticSearch(Map<String, Object > indexDocument, TransportClient client) {

		
     //   getEsClient();

		client.prepareIndex("audiencesegmentcomputeindex1", "core2")
				.setSource(indexDocument).execute().actionGet();

	}
	
	
	public static void postsubcategorycomputeElasticSearch(Map<String, Object> indexDocument, TransportClient client) {

		
    //    getEsClient();

		
		client.prepareIndex("subcategorycomputeindex1", "core2")
				.setSource(indexDocument).execute().actionGet();

	}
	
	
	

	public static void postsubcategoryElasticSearch(Map<String, Object> indexDocument, TransportClient client) {

		
    //    getEsClient();

		
		client.prepareIndex("subcategoryindex1", "core2")
				.setSource(indexDocument).execute().actionGet();

	}
	
	
	
	
	// If content is already there and classified, it is not further analysed

	public static SearchHit[] searchDocumentContent(Client client,
			String index, String type, String field, String value) {

		SearchResponse response = client
				.prepareSearch(index)
				.setTypes(type)
				.setSearchType(SearchType.QUERY_AND_FETCH)
				.setQuery(QueryBuilders.matchQuery(field, value))
				.setFrom(0)
				.setSize(60)
				.setExplain(true)
				.addSort("postDate",
						org.elasticsearch.search.sort.SortOrder.DESC).execute()
				.actionGet();

		SearchHit[] results = response.getHits().getHits();
		System.out.println("Current results: " + results.length);

		return results;
	}

}