package com.sw.entities;

public class Game {
	
	private String id;
	private String homeTeam;
	private String awayTeam;
	private String homeTeamShortName;
	private String awayTeamShortName;
	private int sport;
	private String sportName;
	private String wholeMatchTime;
	private String matchTime;
	private String matchDate;
	private String pointSpread;
	private String underLine;
	private String type;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHomeTeam() {
		return homeTeam;
	}
	public void setHomeTeam(String homeTeam) {
		this.homeTeam = homeTeam;
	}
	public String getAwayTeam() {
		return awayTeam;
	}
	public void setAwayTeam(String awayTeam) {
		this.awayTeam = awayTeam;
	}
	public String getHomeTeamShortName() {
		return homeTeamShortName;
	}
	public void setHomeTeamShortName(String homeTeamShortName) {
		this.homeTeamShortName = homeTeamShortName;
	}
	public String getAwayTeamShortName() {
		return awayTeamShortName;
	}
	public void setAwayTeamShortName(String awayTeamShortName) {
		this.awayTeamShortName = awayTeamShortName;
	}
	public int getSport() {
		return sport;
	}
	public void setSport(int sport) {
		this.sport = sport;
	}
	public String getSportName() {
		return sportName;
	}
	public void setSportName(String sportName) {
		this.sportName = sportName;
	}
	public String getWholeMatchTime() {
		return wholeMatchTime;
	}
	public void setWholeMatchTime(String wholeMatchTime) {
		this.wholeMatchTime = wholeMatchTime;
	}
	public String getMatchTime() {
		return matchTime;
	}
	public void setMatchTime(String matchTime) {
		this.matchTime = matchTime;
	}
	public String getMatchDate() {
		return matchDate;
	}
	public void setMatchDate(String matchDate) {
		this.matchDate = matchDate;
	}
	public String getPointSpread() {
		return pointSpread;
	}
	public void setPointSpread(String pointSpread) {
		this.pointSpread = pointSpread;
	}
	public String getUnderLine() {
		return underLine;
	}
	public void setUnderLine(String underLine) {
		this.underLine = underLine;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "Game [id=" + id + ", homeTeam=" + homeTeam + ", awayTeam=" + awayTeam + ", homeTeamShortName="
				+ homeTeamShortName + ", awayTeamShortName=" + awayTeamShortName + ", sport=" + sport + ", sportName="
				+ sportName + ", wholeMatchTime=" + wholeMatchTime + ", matchTime=" + matchTime + ", matchDate="
				+ matchDate + ", pointSpread=" + pointSpread + ", underLine=" + underLine + ", type=" + type + "]";
	}
	
	
	
	

}
