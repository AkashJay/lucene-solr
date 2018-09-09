package IRWA.lucene_solr.solr;

import java.io.File;
import java.io.IOException;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.beans.DocumentObjectBinder;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.CommonParams;

public class SolrBasicIndex {
	
	//Diretory file path to read json files
	public static File solrJsonDirectory = new File("E:\\Projects\\Solr\\solr-6.0.0\\solr-bookstore-json-files");
	
	//Create a solr client object to connect to solr in local
	private static SolrClient client = null;
	
	public static void main(String[] args) throws SolrServerException, IOException {
		
		//Access the bookstore core using solr client object created above
		client = new HttpSolrClient("http://localhost:8983/solr/bookstore");
		System.out.println("Solr client created "+ ((HttpSolrClient) client).getBaseURL());
		
		//Index the documents
		index();
		
		//Then create the Query
		SolrQuery query = basicQuery("9780072231892");
		System.out.println("Query formed as "+query.toQueryString());
		
		//Then execute the query and get the Document list that match the query
		SolrDocumentList documentList = executeQuery(query);
		
		//At last print the returned documents
		printAsDocuments(documentList);
		//In here we have to print the value of the object seperatly
		//But we can use a BEAN to retrieve all the values at once
		//So we have to create a bean for that
		printAsDocumentsAsABean(documentList);
	}

	private static void printAsDocumentsAsABean(SolrDocumentList documentList) {
		
		
		for (SolrDocument doc : documentList) {
			DocumentObjectBinder bind = new DocumentObjectBinder();
			Bean bean = bind.getBean(Bean.class, doc);
			System.out.println(bean);
		}
		
	}

	private static void printAsDocuments(SolrDocumentList documentList) {
		
		for(SolrDocument document : documentList) {
			Object object = document.get(SolrConstants.isbn);
			System.out.println("ISBN value is : "+object);
		}
		
	}

	private static SolrDocumentList executeQuery(SolrQuery query) throws SolrServerException, IOException {
		
		//Limit the output
		query.setRows(10);
		//Execute the query and catch to a QueryResponse
		QueryResponse response = client.query(query);
		
		SolrDocumentList result	 = response.getResults();
		System.out.println("No of document returened : "+result.getNumFound());
		
		return result;
	}

	private static SolrQuery basicQuery(String isbn) {
		//Build the query
		
		StringBuffer buffer = new StringBuffer();
		buffer.append(SolrConstants.isbn).append(":").append(isbn);
		
		SolrQuery query = new SolrQuery();
		query.set(CommonParams.Q, buffer.toString());
		
		return query;
	}

	private static void index() throws SolrServerException, IOException {
		
		for(File file : solrJsonDirectory.listFiles()) {
			System.out.println("Indexing "+file.getName());
			//Get the document object from the input file
			SolrInputDocument document = SolrUtils.getSolrDocumentFromFile(file);
			//In each iteration add document to solr for indexing
			client.add(document);
		}
		
		client.commit();
		
		
	}

}
