package IRWA.lucene_solr.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;

public class ApacheSolrClient {
	
	//Create a singelton instance of solr client
	private static ApacheSolrClient instance = new ApacheSolrClient();
	
	private SolrClient client;
	
	private ApacheSolrClient() {
		client = new HttpSolrClient.Builder("http://localhost:8983/solr/bookstore").build();
		System.out.println("Solr client created "+ ((HttpSolrClient) client).getBaseURL());
		
	}
	
	public static ApacheSolrClient getInstance() {
		return instance;
	}
	
	public SolrClient getClient() {
		
		return client;
	}

}
