package IRWA.lucene_solr.solr;

import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.solr.common.SolrInputDocument;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

public class SolrUtils {
	//Read json files and create solr documents in this class
	
	public static final String SOLR_OPERATION_ADD = "add";
	public static final String SOLR_OPERATION_SET = "set";
	
	//Read the json files
	public static SolrInputDocument getSolrDocumentFromFile(File file) throws FileNotFoundException {
		
		JSONObject bookJSON = (JSONObject) JSONValue.parse(new FileInputStream(file));
		return getSolrDocumentFromJSON(bookJSON);
		
	}

	//From the read json file extract solr document object and return
	private static SolrInputDocument getSolrDocumentFromJSON(JSONObject bookJSON) {
		//Document to catch all the field data
		SolrInputDocument solrDocument = new SolrInputDocument();
		
		//solrDocument.addField(name, value);                     
		solrDocument.addField(SolrConstants.pubname, (String) bookJSON.get(SolrConstants.pubname));
		solrDocument.addField(SolrConstants.isbn, (String) bookJSON.get(SolrConstants.isbn));
		solrDocument.addField(SolrConstants.pub_location, (String) bookJSON.get(SolrConstants.pub_location));
		solrDocument.addField(SolrConstants.author_firstname, (String) bookJSON.get(SolrConstants.author_firstname));
		solrDocument.addField(SolrConstants.author_lastname, (String) bookJSON.get(SolrConstants.author_lastname));
		solrDocument.addField(SolrConstants.title, (String) bookJSON.get(SolrConstants.title));
		
		
		//Category is a multivalue field. So add it to document ass a map
		Map<String, List<String>> categories = new HashMap<String, List<String>>();
		categories.put(SOLR_OPERATION_ADD, getStringListfromJsonArray((JSONArray) bookJSON.get(SolrConstants.categories)));
		solrDocument.addField(SolrConstants.categories, categories);
		
		
		//bookJSON.getAsNumber(key)
		long amazonPrice = bookJSON.getAsNumber(String.format("price_%s", SolrConstants.bookstore_amazon)).longValue();
		long flipkartPrice = bookJSON.getAsNumber(String.format("price_%s", SolrConstants.bookstore_flipkart)).longValue();
		long infybeamPrice = bookJSON.getAsNumber(String.format("price_%s", SolrConstants.bookstore_infybeam)).longValue();
		long rediffPrice = bookJSON.getAsNumber(String.format("price_%s", SolrConstants.bookstore_rediff)).longValue();
		
		//SolrConstants.wildcard_price ----> *_price     == amzon_price
		solrDocument.addField(SolrConstants.wildcard_price.replace("*", SolrConstants.bookstore_amazon), amazonPrice);
		solrDocument.addField(SolrConstants.wildcard_price.replace("*", SolrConstants.bookstore_flipkart), flipkartPrice);
		solrDocument.addField(SolrConstants.wildcard_price.replace("*", SolrConstants.bookstore_infybeam), infybeamPrice);
		solrDocument.addField(SolrConstants.wildcard_price.replace("*", SolrConstants.bookstore_rediff), rediffPrice);
		
		//Location lating
		Map<String, String> lating = new HashMap<String, String>();
		lating.put(SOLR_OPERATION_SET, bookJSON.getAsString(SolrConstants.pub_latlng));
		solrDocument.addField(SolrConstants.pub_latlng, lating);
		
		
		return solrDocument;
	}

	private static List<String> getStringListfromJsonArray(JSONArray jsonArray) {

		List<String> arr = new ArrayList<String>();
		if(jsonArray == null || jsonArray.isEmpty()) {
			return arr;
		}
		
		for(Object object : jsonArray) {
			arr.add((String) object);			
		}
		return arr;
	}
}
