package IRWA.lucene_solr.solr;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.beans.DocumentObjectBinder;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.CommonParams;

public class SolrBasicPagination {
	
	private static SolrClient client = null;
	
	public static void main(String[] args) throws SolrServerException, IOException {
		//Get the solr Instance
		client = ApacheSolrClient.getInstance().getClient();
		
		//Start from the 0 document and get 2 rows(Documents)
		int start = 0;
		int count = 2;

		
		
		while(true) {
			
			//Query all documents
			SolrQuery query = new SolrQuery();
			query.set(CommonParams.Q, "*:*");
			query.setStart(start);
			query.setRows(count);
			
			QueryResponse response = client.query(query);
			System.out.println("Query for pagination "+query.toQueryString());
			SolrDocumentList docList = response.getResults();
			
			if(docList == null || docList.isEmpty()) {
				break;
			}
			
			//Print the results
			for (SolrDocument doc : docList) {
				DocumentObjectBinder bind = new DocumentObjectBinder();
				Bean bean = bind.getBean(Bean.class, doc);
				System.out.println(bean.getPubname());
			}
			
			start += count;
			
		}
	}

}
