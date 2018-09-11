package IRWA.lucene_solr.solr;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import javax.management.Query;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.CommonParams;
import org.apache.solr.common.params.FacetParams;

public class SolrBasicFacet {
	
	private static SolrClient client = null;
	
	public static void main(String[] args) throws SolrServerException, IOException {
		
		//Get the solr Instance
		client = ApacheSolrClient.getInstance().getClient();
				
		SolrQuery query = new SolrQuery();
		query.set(CommonParams.Q, "*:*");
		
		//Set the grouping(faceting) true
		query.setFacet(true);
		
		query.set(FacetParams.FACET_FIELD, SolrConstants.categories);
		
		//Do not return any document to this query
		query.setRows(0);
		
		System.out.println("Query for faceting : "+query.toQueryString());
		
		QueryResponse queryResponse = client.query(query);
		List<FacetField> facetFields = queryResponse.getFacetFields();
		
		
		//facetFields --->>> [categories:[Book (7), Computer (4), Educational (4)]]
		for(FacetField facetField : facetFields) {
			
			System.out.println("Feild name : "+facetField.getName()+ "  count : "+facetField.getValueCount());
			
			List<Count> value = facetField.getValues();
			
			for(Count c : value) {
				System.out.println("Key : "+c.getName()+" value : "+c.getCount());
			}
		}
		
		
	}

}
