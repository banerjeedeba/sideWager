package com.sw.entities;

public class Result {

	private String id;
	private String homeScore;
	private String awayScore;
	private OddType oddType;
	private boolean finalValue;
	private String tennisFinalScore;
	private String aetScore;
	private String pkScore;
	private FinalType finalType;
	private String name;
	private String position;
	private String finalPosition;
	private String binaryScore;
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
	public OddType getOddType() {
		return oddType;
	}
	public void setOddType(OddType oddType) {
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
	public FinalType getFinalType() {
		return finalType;
	}
	public void setFinalType(FinalType finalType) {
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
	public Result(String id, String homeScore, String awayScore, OddType oddType, boolean finalValue,
			String tennisFinalScore, String aetScore, String pkScore, FinalType finalType, String name, String position,
			String finalPosition, String binaryScore) {
		super();
		this.id = id;
		this.homeScore = homeScore;
		this.awayScore = awayScore;
		this.oddType = oddType;
		this.finalValue = finalValue;
		this.tennisFinalScore = tennisFinalScore;
		this.aetScore = aetScore;
		this.pkScore = pkScore;
		this.finalType = finalType;
		this.name = name;
		this.position = position;
		this.finalPosition = finalPosition;
		this.binaryScore = binaryScore;
	}
	public Result() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
