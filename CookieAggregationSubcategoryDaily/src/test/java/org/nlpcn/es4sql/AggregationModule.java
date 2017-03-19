package org.nlpcn.es4sql;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.nlpcn.es4sql.TestsConstants.TEST_INDEX;

import java.io.IOException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.List;

import org.elasticsearch.action.admin.cluster.node.info.NodesInfoResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.ImmutableSettings.Builder;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.plugin.nlpcn.QueryActionElasticExecutor;
import org.elasticsearch.plugin.nlpcn.executors.CSVResult;
import org.elasticsearch.plugin.nlpcn.executors.CSVResultsExtractor;
import org.elasticsearch.plugin.nlpcn.executors.CsvExtractorException;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.elasticsearch.search.aggregations.metrics.valuecount.ValueCount;
import org.junit.Assert;
import org.nlpcn.es4sql.exception.SqlParseException;
import org.nlpcn.es4sql.query.QueryAction;
import org.nlpcn.es4sql.query.SqlElasticSearchRequestBuilder;

public class AggregationModule {



//	private static TransportClient client;
	private static SearchDao searchDao;
	private static Client client;
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

	// setUp();
//	 countTest();
//	 maxTest();
//	 countBrandNameTest1(); 
/*
	 countBrandName();
	 countBrowser();
	 countOS();
	 countModel();
	// dateHistogram();
	 countCity();
	 countfingerprint();
*/
	 }

	

    
	public final Client getEsClient() {

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
	public final void setEsClient(final Client esClient) {

		AggregationModule.client = esClient;
		searchDao = new SearchDao(client);
	}

	/**
	 * Gets the es.
	 *
	 * @return the es
	 */
	private Client getES() {
		try {
			
			final Builder settings = ImmutableSettings.settingsBuilder().put("cluster.name", "elasticsearch");
			
			final TransportClient transportClient = new TransportClient(settings);
			transportClient.addTransportAddress(new InetSocketTransportAddress("locallhost", 9300));
			
			return transportClient;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	
	
	
	

	
	
	
	/*
	public static void setUp() throws Exception {
		client = new TransportClient();
		client.addTransportAddress(getTransportAddress());

		NodesInfoResponse nodeInfos = client.admin().cluster().prepareNodesInfo().get();
		String clusterName = nodeInfos.getClusterName().value();
		System.out.println(String.format("Found cluster... cluster name: %s", clusterName));

        searchDao = new SearchDao(client);

        //refresh to make sure all the docs will return on queries
        client.admin().indices().prepareRefresh(TEST_INDEX).execute().actionGet();

		System.out.println("Finished the setup process...");
	}

*/
	public static SearchDao getSearchDao() {
		return searchDao;
	}
	
/*
	public static void countTest() throws CsvExtractorException, Exception {
		Aggregations result = query(String.format("SELECT COUNT(*),brandName,browser_name FROM enhanceduserdata group by brandName,browser_name", TEST_INDEX));
		String query = String.format("SELECT COUNT(*)as count,brandName,browser_name FROM enhanceduserdata group by brandName,browser_name","enhanceduserdata");
		CSVResult csvResult = getCsvResult(false, query);
        List<String> headers = csvResult.getHeaders();
        List<String> lines = csvResult.getLines();
        System.out.println(headers);
        System.out.println(lines);
		
		
		//		System.out.println(result.asMap().toString());
	//	ValueCount count = result.get("COUNT(*)");
	//	Assert.assertEquals(1000, count.getValue());
	}
	
	
	
	public static void maxTest() throws CsvExtractorException, Exception {
		Aggregations result = query(String.format("SELECT COUNT(*),brandName,browser_name FROM enhanceduserdata group by brandName,browser_name", TEST_INDEX));
		String query = String.format("SELECT min(request_time) FROM enhanceduserdata","enhanceduserdata");
		CSVResult csvResult = getCsvResult(false, query);
        List<String> headers = csvResult.getHeaders();
        List<String> lines = csvResult.getLines();
        System.out.println(headers);
        System.out.println(lines);
		
		
		//		System.out.println(result.asMap().toString());
	//	ValueCount count = result.get("COUNT(*)");
	//	Assert.assertEquals(1000, count.getValue());
	}

	
	
	public static void countBrandNameTest1() throws CsvExtractorException, Exception {
		Aggregations result = query(String.format("SELECT COUNT(*),brandName,browser_name FROM enhanceduserdata group by brandName,browser_name", TEST_INDEX));
		String query = String.format("SELECT COUNT(*)as count,brandName FROM enhanceduserdata group by brandName","enhanceduserdata");
		CSVResult csvResult = getCsvResult(false, query);
        List<String> headers = csvResult.getHeaders();
        List<String> lines = csvResult.getLines();
        System.out.println(headers);
        System.out.println(lines);
		
		
		//		System.out.println(result.asMap().toString());
	//	ValueCount count = result.get("COUNT(*)");
	//	Assert.assertEquals(1000, count.getValue());
	}
	
*/	
	

	
	public static void countBrandName(String startdate, String enddate) throws CsvExtractorException, Exception {
		
		
		Aggregations result = query(String.format("SELECT COUNT(*),brandName,browser_name FROM enhanceduserdata group by brandName,browser_name", TEST_INDEX));
		String query = String.format("SELECT COUNT(*)as count,date,brandName FROM enhanceduserdata where date between "+"'"+startdate+" and "+"'"+enddate+"'"+ " group by brandName,date","enhanceduserdata");
		CSVResult csvResult = getCsvResult(false, query);
        List<String> headers = csvResult.getHeaders();
        List<String> lines = csvResult.getLines();
        System.out.println(headers);
        System.out.println(lines);
		
		
		//		System.out.println(result.asMap().toString());
	//	ValueCount count = result.get("COUNT(*)");
	//	Assert.assertEquals(1000, count.getValue());
	}
	
	public static void countBrowser(String startdate,String enddate) throws CsvExtractorException, Exception {
		Aggregations result = query(String.format("SELECT COUNT(*),brandName,browser_name FROM enhanceduserdata group by brandName,browser_name", TEST_INDEX));
		String query = String.format("SELECT COUNT(*)as count,date,browser_name FROM enhanceduserdata where date between "+"'"+startdate+" and "+"'"+enddate+"'"+" group by browser_name,date","enhanceduserdata");
		CSVResult csvResult = getCsvResult(false, query);
        List<String> headers = csvResult.getHeaders();
        List<String> lines = csvResult.getLines();
        System.out.println(headers);
        System.out.println(lines);
		
		
		//		System.out.println(result.asMap().toString());
	//	ValueCount count = result.get("COUNT(*)");
	//	Assert.assertEquals(1000, count.getValue());
	}
	
	
	public static void countOS(String startdate,String enddate) throws CsvExtractorException, Exception {
		Aggregations result = query(String.format("SELECT COUNT(*),brandName,browser_name FROM enhanceduserdata group by brandName,browser_name", TEST_INDEX));
		String query = String.format("SELECT COUNT(*)as count,date,system_os FROM enhanceduserdata where date between "+"'"+startdate+" and "+"'"+enddate+"'"+" group by system_os,date","enhanceduserdata");
		CSVResult csvResult = getCsvResult(false, query);
        List<String> headers = csvResult.getHeaders();
        List<String> lines = csvResult.getLines();
        System.out.println(headers);
        System.out.println(lines);
		
		
		//		System.out.println(result.asMap().toString());
	//	ValueCount count = result.get("COUNT(*)");
	//	Assert.assertEquals(1000, count.getValue());
	}
	
	public static void countModel(String startdate,String enddate) throws CsvExtractorException, Exception {
		Aggregations result = query(String.format("SELECT COUNT(*),brandName,browser_name FROM enhanceduserdata group by brandName,browser_name", TEST_INDEX));
		String query = String.format("SELECT COUNT(*)as count,date,modelName FROM enhanceduserdata where date between "+"'"+startdate+" and "+"'"+enddate+"'"+" group by modelName,date","enhanceduserdata");
		CSVResult csvResult = getCsvResult(false, query);
        List<String> headers = csvResult.getHeaders();
        List<String> lines = csvResult.getLines();
        System.out.println(headers);
        System.out.println(lines);
		
		
		//		System.out.println(result.asMap().toString());
	//	ValueCount count = result.get("COUNT(*)");
	//	Assert.assertEquals(1000, count.getValue());
	}
	
	
	public static void countCity(String startdate,String enddate) throws CsvExtractorException, Exception {
		Aggregations result = query(String.format("SELECT COUNT(*),brandName,browser_name FROM enhanceduserdata group by brandName,browser_name", TEST_INDEX));
		String query = String.format("SELECT COUNT(*)as count,date,city FROM enhanceduserdata where date between "+"'"+startdate+" and "+"'"+enddate+"'"+" group by city,date","enhanceduserdata");
		CSVResult csvResult = getCsvResult(false, query);
        List<String> headers = csvResult.getHeaders();
        List<String> lines = csvResult.getLines();
        System.out.println(headers);
        System.out.println(lines);
		
		
		//		System.out.println(result.asMap().toString());
	//	ValueCount count = result.get("COUNT(*)");
	//	Assert.assertEquals(1000, count.getValue());
	}
	
	
	public static void countpincode(String startdate,String enddate) throws CsvExtractorException, Exception {
		Aggregations result = query(String.format("SELECT COUNT(*),brandName,browser_name FROM enhanceduserdata group by brandName,browser_name", TEST_INDEX));
		String query = String.format("SELECT COUNT(*)as count,date,city,zipcode FROM enhanceduserdata where date between "+"'"+startdate+" and "+"'"+enddate+"'"+" group by date,city,zipcode","enhanceduserdata");
		CSVResult csvResult = getCsvResult(false, query);
        List<String> headers = csvResult.getHeaders();
        List<String> lines = csvResult.getLines();
        System.out.println(headers);
        System.out.println(lines);
		
		
		//		System.out.println(result.asMap().toString());
	//	ValueCount count = result.get("COUNT(*)");
	//	Assert.assertEquals(1000, count.getValue());
	}
	
	
	
	
	
	public static void countLatLong(String startdate,String enddate) throws CsvExtractorException, Exception {
		Aggregations result = query(String.format("SELECT COUNT(*),brandName,browser_name FROM enhanceduserdata group by brandName,browser_name", TEST_INDEX));
		String query = String.format("SELECT COUNT(*)as count,date,latitude_longitude FROM enhanceduserdata where date between "+"'"+startdate+" and "+"'"+enddate+"'"+" group by latitude_longitude,date","enhanceduserdata");
		CSVResult csvResult = getCsvResult(false, query);
        List<String> headers = csvResult.getHeaders();
        List<String> lines = csvResult.getLines();
        System.out.println(headers);
        System.out.println(lines);
		
		
		//		System.out.println(result.asMap().toString());
	//	ValueCount count = result.get("COUNT(*)");
	//	Assert.assertEquals(1000, count.getValue());
	}
	
	
	
	
	
	
	public static void countfingerprint(String startdate,String enddate) throws CsvExtractorException, Exception {
		Aggregations result = query(String.format("SELECT COUNT(*),brandName,browser_name FROM enhanceduserdata group by brandName,browser_name", TEST_INDEX));
		String query = String.format("SELECT count(distinct(fingerprint_id))as reach,date"
				+ " FROM enhanceduserdata where date between "+"'"+startdate+" and "+"'"+enddate+"'"+" group by date","enhanceduserdata");
		CSVResult csvResult = getCsvResult(false, query);
        List<String> headers = csvResult.getHeaders();
        List<String> lines = csvResult.getLines();
        System.out.println(headers);
        System.out.println(lines);
		
		
		//		System.out.println(result.asMap().toString());
	//	ValueCount count = result.get("COUNT(*)");
	//	Assert.assertEquals(1000, count.getValue());
	}
	
	
	
	
	
	
	
	
	public static void dateHistogram() throws SqlParseException, SQLFeatureNotSupportedException, Exception {
        String query = String.format("select count(*),brandName,screen_properties,resolution_properties,browser_name,system_os from enhanceduserdata" +
                " group by date_histogram('field'='request_time','interval'='1d','alias'='days'),brandName,screen_properties,resolution_properties,browser_name,system_os","enhanceduserdata");
        CSVResult csvResult = getCsvResult(false, query);
        List<String> headers = csvResult.getHeaders();
        List<String> lines = csvResult.getLines();
        System.out.println(lines);
   //     Assert.assertEquals(3, lines.size());
   //     Assert.assertTrue("2014-08-14 00:00:00,477.0", lines.contains("2014-08-14 00:00:00,477.0"));
   //     Assert.assertTrue("2014-08-18 00:00:00,5664.0", lines.contains("2014-08-18 00:00:00,5664.0"));
    //    Assert.assertTrue("2014-08-22 00:00:00,3795.0", lines.contains("2014-08-22 00:00:00,3795.0"));
    }

	
	
	
	
	
	
	
	
	
	
	
	
	 private static CSVResult getCsvResult(boolean flat, String query) throws SqlParseException, SQLFeatureNotSupportedException, Exception, CsvExtractorException {
	        return getCsvResult(flat,query,false,false);
	    }

	    private static CSVResult getCsvResult(boolean flat, String query,boolean includeScore , boolean includeType) throws SqlParseException, SQLFeatureNotSupportedException, Exception, CsvExtractorException {
	        SearchDao searchDao = getSearchDao();
	        QueryAction queryAction = searchDao.explain(query);
	        Object execution =  QueryActionElasticExecutor.executeAnyAction(searchDao.getClient(), queryAction);
	        return new CSVResultsExtractor(includeScore,includeType).extractResults(execution, flat, ",");
	    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void sumTest() throws IOException, SqlParseException, SQLFeatureNotSupportedException {
	//	Aggregations result = query(String.format("SELECT SUM(balance) FROM %s/account", TEST_INDEX));
	//	Sum sum = result.get("SUM(balance)");
	//	assertThat(sum.getValue(), equalTo(25714837.0));
	}

    // script on metric aggregation tests. uncomment if your elastic has scripts enable (disabled by default)
    //todo: find a way to check if scripts are enabled
//    @Test
//    public void sumWithScriptTest() throws IOException, SqlParseException, SQLFeatureNotSupportedException {
//        Aggregations result = query(String.format("SELECT SUM(script('','doc[\\'balance\\'].value + doc[\\'balance\\'].value')) as doubleSum FROM %s/account", TEST_INDEX));
//        Sum sum = result.get("doubleSum");
//        assertThat(sum.getValue(), equalTo(25714837.0*2));
//    }
//
//    @Test
//    public void sumWithImplicitScriptTest() throws IOException, SqlParseException, SQLFeatureNotSupportedException {
//        Aggregations result = query(String.format("SELECT SUM(balance + balance) as doubleSum FROM %s/account", TEST_INDEX));
//        Sum sum = result.get("doubleSum");
//        assertThat(sum.getValue(), equalTo(25714837.0*2));
//    }
//
//    @Test
//    public void sumWithScriptTestNoAlias() throws IOException, SqlParseException, SQLFeatureNotSupportedException {
//        Aggregations result = query(String.format("SELECT SUM(balance + balance) FROM %s/account", TEST_INDEX));
//        Sum sum = result.get("SUM(script=script(balance + balance,doc('balance').value + doc('balance').value))");
//        assertThat(sum.getValue(), equalTo(25714837.0*2));
//    }
//
//    @Test
//    public void scriptedMetricAggregation() throws SQLFeatureNotSupportedException, SqlParseException {
//        Aggregations result = query ("select scripted_metric('map_script'='if(doc[\\'balance\\'].value > 49670){ if(!_agg.containsKey(\\'ages\\')) { _agg.put(\\'ages\\',doc[\\'age\\'].value); } " +
//                "else { _agg.put(\\'ages\\',_agg.get(\\'ages\\')+doc[\\'age\\'].value); }}'," +
//                "'reduce_script'='sumThem = 0; for (a in _aggs) { if(a.containsKey(\\'ages\\')){ sumThem += a.get(\\'ages\\');} }; return sumThem;') as wierdSum from " + TEST_INDEX + "/account");
//        ScriptedMetric metric = result.get("wierdSum");
//        Assert.assertEquals(136L,metric.aggregation());
//    }
//
//    @Test
//    public void scriptedMetricConcatWithStringParamAndReduceParamAggregation() throws SQLFeatureNotSupportedException, SqlParseException {
//        String query = "select scripted_metric(\n" +
//                "  'init_script' = '_agg[\"concat\"]=[] ',\n" +
//                "  'map_script'='_agg.concat.add(doc[field].value)' ,\n" +
//                "  'combine_script'='return _agg.concat.join(delim);',\t\t\t\t\n" +
//                "  'reduce_script'='_aggs.removeAll(\"\"); return _aggs.join(delim)'," +
//                "'@field' = 'name.firstname' , '@delim'=';',@reduce_delim =';' ) as all_characters \n" +
//                "from "+TEST_INDEX+"/gotCharacters";
//        Aggregations result = query (query);
//        ScriptedMetric metric = result.get("all_characters");
//        List<String> names = Arrays.asList(metric.aggregation().toString().split(";"));
//
//
//        Assert.assertEquals(4,names.size());
//        String[] expectedNames = new String[]{"brandon","daenerys","eddard","jaime"};
//        for(String name : expectedNames){
//            Assert.assertTrue("not contains:" + name,names.contains(name));
//        }
//    }
//
//    @Test
//    public void scriptedMetricAggregationWithNumberParams() throws SQLFeatureNotSupportedException, SqlParseException {
//        Aggregations result = query ("select scripted_metric('map_script'='if(doc[\\'balance\\'].value > 49670){ if(!_agg.containsKey(\\'ages\\')) { _agg.put(\\'ages\\',doc[\\'age\\'].value+x); } " +
//                "else { _agg.put(\\'ages\\',_agg.get(\\'ages\\')+doc[\\'age\\'].value+x); }}'," +
//                "'reduce_script'='sumThem = 0; for (a in _aggs) { if(a.containsKey(\\'ages\\')){ sumThem += a.get(\\'ages\\');} }; return sumThem;'" +
//                ",'@x'=3) as wierdSum from " + TEST_INDEX + "/account");
//        ScriptedMetric metric = result.get("wierdSum");
//        Assert.assertEquals(148L,metric.aggregation());
//    }
//


	private static Aggregations query(String query) throws SqlParseException, SQLFeatureNotSupportedException {
        SqlElasticSearchRequestBuilder select = getSearchRequestBuilder(query);
		return ((SearchResponse)select.get()).getAggregations();
	}

    private static SqlElasticSearchRequestBuilder getSearchRequestBuilder(String query) throws SqlParseException, SQLFeatureNotSupportedException {
        SearchDao searchDao = getSearchDao();
        return (SqlElasticSearchRequestBuilder) searchDao.explain(query).explain();
    }


    private static InetSocketTransportAddress getTransportAddress() {
		String host = System.getenv("ES_TEST_HOST");
		String port = System.getenv("ES_TEST_PORT");

		if(host == null) {
			host = "localhost";
			System.out.println("ES_TEST_HOST enviroment variable does not exist. choose default 'localhost'");
		}

		if(port == null) {
			port = "9300";
			System.out.println("ES_TEST_PORT enviroment variable does not exist. choose default '9300'");
		}

		System.out.println(String.format("Connection details: host: %s. port:%s.", host, port));
		return new InetSocketTransportAddress(host, Integer.parseInt(port));
	}





















}
