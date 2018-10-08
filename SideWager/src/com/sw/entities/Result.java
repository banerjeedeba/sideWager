package com.sw.entities;

import com.google.gson.annotations.SerializedName;

public class Result {

	@SerializedName("ID")
	private String id;
	@SerializedName("HomeScore")
	private String homeScore;
	@SerializedName("AwayScore")
	private String awayScore;
	@SerializedName("OddType")
	private String oddType;
	@SerializedName("Final")
	private boolean finalValue;
	@SerializedName("TennisFinalScore")
	private String tennisFinalScore;
	@SerializedName("AETScore")
	private String aetScore;
	@SerializedName("PKScore")
	private String pkScore;
	@SerializedName("FinalType")
	private String finalType;
	@SerializedName("Name")
	private String name;
	@SerializedName("Position")
	private String position;
	@SerializedName("FinalPosition")
	private String finalPosition;
	@SerializedName("BinaryScore")
	private String binaryScore;
	@SerializedName("EventID")
	private String eventId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHomeScore() {
		return homeScore;
	}
	public void setHomeScore(String homeScore) {
		this.homeScore = homeScore;
	}
	public String getAwayScore() {
		return awayScore;
	}
	public void setAwayScore(String awayScore) {
		this.awayScore = awayScore;
	}
	public String getOddType() {
		return oddType;
	}
	public void setOddType(String oddType) {
		this.oddType = oddType;
	}
	public boolean isFinalValue() {
		return finalValue;
	}
	public void setFinalValue(boolean finalValue) {
		this.finalValue = finalValue;
	}
	public String getTennisFinalScore() {
		return tennisFinalScore;
	}
	public void setTennisFinalScore(String tennisFinalScore) {
		this.tennisFinalScore = tennisFinalScore;
	}
	public String getAetScore() {
		return aetScore;
	}
	public void setAetScore(String aetScore) {
		this.aetScore = aetScore;
	}
	public String getPkScore() {
		return pkScore;
	}
	public void setPkScore(String pkScore) {
		this.pkScore = pkScore;
	}
	public String getFinalType() {
		return finalType;
	}
	public void setFinalType(String finalType) {
		this.finalType = finalType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getFinalPosition() {
		return finalPosition;
	}
	public void setFinalPosition(String finalPosition) {
		this.finalPosition = finalPosition;
	}
	public String getBinaryScore() {
		return binaryScore;
	}
	public void setBinaryScore(String binaryScore) {
		this.binaryScore = binaryScore;
	}
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	
	public Result() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Result [id=" + id + ", homeScore=" + homeScore + ", awayScore=" + awayScore + ", oddType=" + oddType
				+ ", finalValue=" + finalValue + ", tennisFinalScore=" + tennisFinalScore + ", aetScore=" + aetScore
				+ ", pkScore=" + pkScore + ", finalType=" + finalType + ", name=" + name + ", position=" + position
				+ ", finalPosition=" + finalPosition + ", binaryScore=" + binaryScore + ", eventId=" + eventId + "]";
	}
	
	
	
	
	
}
