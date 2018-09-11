package IRWA.lucene_solr.solr;


import java.io.IOException;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.beans.DocumentObjectBinder;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.CommonParams;


public class BasicSort {

	private static SolrClient client = null;

	public static void main(String[] args) throws SolrServerException, IOException {
		client = ApacheSolrClient.getInstance().getClient();

		SolrQuery query = new SolrQuery();
		query.set(CommonParams.Q, "*:*");
		query.addSort(SolrConstants.pubname, ORDER.asc);
		System.out.println("Query " + query.toQueryString());

		SolrDocumentList solrDocs = executeQuery(query);
		printAsBeans(solrDocs);
	}

	private static SolrDocumentList executeQuery(SolrQuery query) throws SolrServerException, IOException {
		query.setRows(10);
		QueryResponse response = client.query(query);
		SolrDocumentList results = response.getResults();
		System.out.println("No of documents found " + results.getNumFound());
		return results;
	}

	private static void printAsBeans(SolrDocumentList solrDocumentList) {
		for (SolrDocument doc : solrDocumentList) {
			DocumentObjectBinder bind = new DocumentObjectBinder();
			Bean bean = bind.getBean(Bean.class, doc);
			System.out.println(bean.getPubname());
		}
	}
}
