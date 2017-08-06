package com.sw.entities;

import java.util.ArrayList;

public class Sports {
	private int id;
	private String originalName;
	private String displayName;
	private String shortName;
	private ArrayList<Game> gameList = new ArrayList<Game>();
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOriginalName() {
		return originalName;
	}
	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public ArrayList<Game> getGameList() {
		return gameList;
	}
	public void setGameList(ArrayList<Game> gameList) {
		this.gameList = gameList;
	}
	

}
