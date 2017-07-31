package com.sw.entities;

import com.google.gson.annotations.SerializedName;

public class Odds {

	@SerializedName("ID")
	private String id;
	@SerializedName("EventID")
	private String eventId; //match id;
	@SerializedName("SiteID")
	private int siteId; //Source
	@SerializedName("MoneyLineHome")
	private String moneyLineHome;
	@SerializedName("MoneyLineAway")
	private String moneyLineAway;
	@SerializedName("DrawLine")
	private String drawLine;
	@SerializedName("PointSpreadHome")
	private String pointSpreadHome;
	@SerializedName("PointSpreadAway")
	private String pointSpreadAway;
	@SerializedName("PointSpreadHomeLine")
	private String pointSpreadHomeLine;
	@SerializedName("PointSpreadAwayLine")
	private String pointSpreadAwayLine;
	@SerializedName("TotalNumber")
	private String totalNumber;
	@SerializedName("OverLine")
	private String overLine;
	@SerializedName("UnderLine")
	private String underLine;
	@SerializedName("LastUpdated")
	private String lastUpdated;
	@SerializedName("OddType")
	private String oddType;
	@SerializedName("Participant")
	private String participant;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public int getSiteId() {
		return siteId;
	}
	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}
	public String getMoneyLineHome() {
		return moneyLineHome;
	}
	public void setMoneyLineHome(String moneyLineHome) {
		this.moneyLineHome = moneyLineHome;
	}
	public String getMoneyLineAway() {
		return moneyLineAway;
	}
	public void setMoneyLineAway(String moneyLineAway) {
		this.moneyLineAway = moneyLineAway;
	}
	public String getDrawLine() {
		return drawLine;
	}
	public void setDrawLine(String drawLine) {
		this.drawLine = drawLine;
	}
	public String getPointSpreadHome() {
		return pointSpreadHome;
	}
	public void setPointSpreadHome(String pointSpreadHome) {
		this.pointSpreadHome = pointSpreadHome;
	}
	public String getPointSpreadAway() {
		return pointSpreadAway;
	}
	public void setPointSpreadAway(String pointSpreadAway) {
		this.pointSpreadAway = pointSpreadAway;
	}
	public String getPointSpreadHomeLine() {
		return pointSpreadHomeLine;
	}
	public void setPointSpreadHomeLine(String pointSpreadHomeLine) {
		this.pointSpreadHomeLine = pointSpreadHomeLine;
	}
	public String getPointSpreadAwayLine() {
		return pointSpreadAwayLine;
	}
	public void setPointSpreadAwayLine(String pointSpreadAwayLine) {
		this.pointSpreadAwayLine = pointSpreadAwayLine;
	}
	public String getTotalNumber() {
		return totalNumber;
	}
	public void setTotalNumber(String totalNumber) {
		this.totalNumber = totalNumber;
	}
	public String getOverLine() {
		return overLine;
	}
	public void setOverLine(String overLine) {
		this.overLine = overLine;
	}
	public String getUnderLine() {
		return underLine;
	}
	public void setUnderLine(String underLine) {
		this.underLine = underLine;
	}
	public String getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	public String getOddType() {
		return oddType;
	}
	public void setOddType(String oddType) {
		this.oddType = oddType;
	}
	public String getParticipant() {
		return participant;
	}
	public void setParticipant(String participant) {
		this.participant = participant;
	}
	public Odds(String id, String eventId, int siteId, String moneyLineHome, String moneyLineAway, String drawLine,
			String pointSpreadHome, String pointSpreadAway, String pointSpreadHomeLine, String pointSpreadAwayLine,
			String totalNumber, String overLine, String underLine, String lastUpdated, String oddType,
			String participant) {
		super();
		this.id = id;
		this.eventId = eventId;
		this.siteId = siteId;
		this.moneyLineHome = moneyLineHome;
		this.moneyLineAway = moneyLineAway;
		this.drawLine = drawLine;
		this.pointSpreadHome = pointSpreadHome;
		this.pointSpreadAway = pointSpreadAway;
		this.pointSpreadHomeLine = pointSpreadHomeLine;
		this.pointSpreadAwayLine = pointSpreadAwayLine;
		this.totalNumber = totalNumber;
		this.overLine = overLine;
		this.underLine = underLine;
		this.lastUpdated = lastUpdated;
		this.oddType = oddType;
		this.participant = participant;
	}
	public Odds() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
