package com.sw.entities;

public class Source {

	private String id;
	private String desription;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDesription() {
		return desription;
	}
	public void setDesription(String desription) {
		this.desription = desription;
	}
	public Source(String id, String desription) {
		super();
		this.id = id;
		this.desription = desription;
	}
	public Source() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
