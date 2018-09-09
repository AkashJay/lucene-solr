package IRWA.lucene_solr.solr;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.beans.Field;

public class Bean {
	
	//Getters Setters and TOString
	
	@Field(SolrConstants.isbn)
	private String isbn;
	
	@Field(SolrConstants.title)
	private String title;
	
	@Field(SolrConstants.pubname)
	private String pubname;
	
	@Field(SolrConstants.author_firstname)
	private String author_firstname;
	
	@Field(SolrConstants.author_lastname)
	private String author_lastname;
	
	@Field(SolrConstants.author_fullname)
	private List<String> author_fullname;
	
	@Field(SolrConstants.pub_location)
	private String pub_location;
	
	@Field(SolrConstants.pub_latlng)
	private String  pub_location_latlng;
	
	@Field(SolrConstants.categories)
	private List<String> categories ;
	
	@Field(SolrConstants.wildcard_price)
	private Map<String, Object> wildcard_price;

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPubname() {
		return pubname;
	}

	public void setPubname(String pubname) {
		this.pubname = pubname;
	}

	public String getPub_location() {
		return pub_location;
	}

	public void setPub_location(String pub_location) {
		this.pub_location = pub_location;
	}

	public String getPub_location_latlng() {
		return pub_location_latlng;
	}

	public void setPub_location_latlng(String pub_location_latlng) {
		this.pub_location_latlng = pub_location_latlng;
	}

	public String getAuthor_firstname() {
		return author_firstname;
	}

	public void setAuthor_firstname(String author_firstname) {
		this.author_firstname = author_firstname;
	}

	public String getAuthor_lastname() {
		return author_lastname;
	}

	public void setAuthor_lastname(String author_lastname) {
		this.author_lastname = author_lastname;
	}

	

	public List<String> getAuthor_fullname() {
		return author_fullname;
	}

	public void setAuthor_fullname(List<String> author_fullname) {
		this.author_fullname = author_fullname;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public Map<String, Object> getWildcard_price() {
		return wildcard_price;
	}

	public void setWildcard_price(Map<String, Object> wildcard_price) {
		this.wildcard_price = wildcard_price;
	}

	@Override
	public String toString() {
		return "Bean [isbn=" + isbn + ", title=" + title + ", pubname=" + pubname + ", pub_location=" + pub_location
				+ ", pub_location_latlng=" + pub_location_latlng + ", author_firstname=" + author_firstname
				+ ", author_lastname=" + author_lastname + ", author_fullname=" + author_fullname + ", categories="
				+ categories + ", wildcard_price=" + wildcard_price + "]";
	}

	
	
	

}
