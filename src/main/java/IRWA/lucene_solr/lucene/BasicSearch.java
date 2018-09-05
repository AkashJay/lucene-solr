package IRWA.lucene_solr.lucene;

import java.util.List;

import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class BasicSearch {
	
	//Index in a specific directory
	private static File _indexDir = new File("/home/akash/Documents/lucene-solr\n"); 

	public static void main(String[] args) throws IOException, ParseException {
		
		List<String> words = new ArrayList<String>();
		
		words.add("Apache Lucene");
		words.add("Apache solr");
		words.add("Java");
		words.add("J2ee");
		
		index(words);
		
		search("apache");
		
		
		
		
	}

	private static void search(String string) throws IOException, ParseException {
		System.out.println("---------------------------------------------");
		System.out.println("Searching for "+string);
		
		//Open the index
		//Create the index directory instance
		Directory indexDirectory = FSDirectory.open(Paths.get(_indexDir.getAbsolutePath()));
		
		IndexSearcher is = new IndexSearcher(DirectoryReader.open(indexDirectory));
		
		//Search for specific field
		QueryParser parser = new QueryParser("word", new SimpleAnalyzer());
		
		//create a query object with our query
		Query query = parser.parse(string);
		
		//Get the top 10 hits
		TopDocs hits = is.search(query, 10);
				
		if(hits.scoreDocs.length == 0) {
			System.out.println("No result for 0"+string);
		}else {
			for(ScoreDoc scoreDoc : hits.scoreDocs) {
				//get back the documet object for the query term
				Document doc = is.doc(scoreDoc.doc);
				                     //same id use for indexing
				String wordvalue = doc.get("word");
				
				System.out.println("Value got as ["+wordvalue+"]");
				
				
			}
		}
		
		indexDirectory.close();
		
	}

	private static void index(List<String> words) throws IOException {
		
		//Make the index directry declared above
		if(_indexDir.exists()) {
			_indexDir.mkdirs();
		}
		
		//Create the index directory instance
		Directory indexDirectory = FSDirectory.open(Paths.get(_indexDir.getAbsolutePath()));
		
		//config for the writer
		//Which basicaly has a analizer
		//Before indexing we have to pass what kind of a analizer it has to
		IndexWriterConfig iwConfig = new  IndexWriterConfig(new SimpleAnalyzer());
		
		//Index writer
		IndexWriter iw = new IndexWriter(indexDirectory, iwConfig);
		
		//If there's anything in the index delete before creating the new index
		iw.deleteAll();
		
		System.out.println("Indexing "+words);
		
		for(String word : words) {
			//Before any data has to be index we have to create DOCUMENT object
			Document doc = new Document();
			
			//Create a FIELD                   //ID of field, the value
			TextField worField = new TextField("word", word, Store.YES);
			
			//Add fields to the document
			doc.add(worField);
			
			//Then this document has to be added to our index
			iw.addDocument(doc);
		
		}
		
		iw.flush();
		iw.close();
		
		
		
		
	}
}
