package com.sw.entities;

import com.google.gson.annotations.SerializedName;

public class Leagues {

	@SerializedName("Name")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Leagues(String name) {
		super();
		this.name = name;
	}

	public Leagues() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
