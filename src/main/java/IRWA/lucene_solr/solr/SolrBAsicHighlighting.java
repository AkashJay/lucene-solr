package IRWA.lucene_solr.solr;



import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.CommonParams;


public class SolrBAsicHighlighting {
	
	private static final String PRE_TEXT = "<Akash>";

	private static final String POST_TEXT = "</Akash>";

	private static SolrClient client = null;

	public static void main(String[] args) throws SolrServerException, IOException {

		client = ApacheSolrClient.getInstance().getClient();

		SolrQuery query = new SolrQuery();
		query.set(CommonParams.Q, SolrConstants.title + ":Japan");

		//set highligthin as a parameter to query
		query.setHighlight(true);
		//Add prefix to the highlight part
		query.setHighlightSimplePre(PRE_TEXT);
		query.setHighlightSimplePost(POST_TEXT);
		
		//Add the field we want to highlihgt
		query.addHighlightField(SolrConstants.title);

		System.out.println("Query " + query.toQueryString());

		QueryResponse response = client.query(query);
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		Iterator<Entry<String, Map<String, List<String>>>> iterator = highlighting.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, Map<String, List<String>>> item = iterator.next();
			String key = item.getKey();
			Map<String, List<String>> value = item.getValue();
			System.out.println("Key   " + key);
			System.out.println("Value " + value);
		}
	}

}
