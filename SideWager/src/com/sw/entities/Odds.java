package com.sw.entities;

public class Odds {

	private String id;
	private String eventId; //match id;
	private Source siteId; //Source
	private String moneyLineHome;
	private String moneyLineAway;
	private String drawLine;
	private String pointSpreadHome;
	private String pointSpreadAway;
	private String pointSpreadHomeLine;
	private String pointSpreadAwayLine;
	private String totalNumber;
	private String overLine;
	private String underLine;
	private String lastUpdated;
	private OddType oddType;
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
	public Source getSiteId() {
		return siteId;
	}
	public void setSiteId(Source siteId) {
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
	public OddType getOddType() {
		return oddType;
	}
	public void setOddType(OddType oddType) {
		this.oddType = oddType;
	}
	public String getParticipant() {
		return participant;
	}
	public void setParticipant(String participant) {
		this.participant = participant;
	}
	public Odds(String id, String eventId, Source siteId, String moneyLineHome, String moneyLineAway, String drawLine,
			String pointSpreadHome, String pointSpreadAway, String pointSpreadHomeLine, String pointSpreadAwayLine,
			String totalNumber, String overLine, String underLine, String lastUpdated, OddType oddType,
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
