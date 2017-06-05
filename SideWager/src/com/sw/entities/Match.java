package com.sw.entities;

import java.util.ArrayList;

public class Match {
 private String id;
 private String homeTeam;
 private String awayTeam;
 private int sport;
 private String matchTime;
 private ArrayList<Odds> oddslist;
 private String league;
 private String displayRegion;
 private String homeRot;
 private String awayRot;
 private String homePitcher;
 private String awayPitcher;
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
public int getSport() {
	return sport;
}
public void setSport(int sport) {
	this.sport = sport;
}
public String getMatchTime() {
	return matchTime;
}
public void setMatchTime(String matchTime) {
	this.matchTime = matchTime;
}
public ArrayList<Odds> getOddslist() {
	return oddslist;
}
public void setOddslist(ArrayList<Odds> oddslist) {
	this.oddslist = oddslist;
}
public String getLeague() {
	return league;
}
public void setLeague(String league) {
	this.league = league;
}
public String getDisplayRegion() {
	return displayRegion;
}
public void setDisplayRegion(String displayRegion) {
	this.displayRegion = displayRegion;
}
public String getHomeRot() {
	return homeRot;
}
public void setHomeRot(String homeRot) {
	this.homeRot = homeRot;
}
public String getAwayRot() {
	return awayRot;
}
public void setAwayRot(String awayRot) {
	this.awayRot = awayRot;
}
public String getHomePitcher() {
	return homePitcher;
}
public void setHomePitcher(String homePitcher) {
	this.homePitcher = homePitcher;
}
public String getAwayPitcher() {
	return awayPitcher;
}
public void setAwayPitcher(String awayPitcher) {
	this.awayPitcher = awayPitcher;
}
public Match(String id, String homeTeam, String awayTeam, int sport, String matchTime, ArrayList<Odds> oddslist,
		String league, String displayRegion, String homeRot, String awayRot, String homePitcher, String awayPitcher) {
	super();
	this.id = id;
	this.homeTeam = homeTeam;
	this.awayTeam = awayTeam;
	this.sport = sport;
	this.matchTime = matchTime;
	this.oddslist = oddslist;
	this.league = league;
	this.displayRegion = displayRegion;
	this.homeRot = homeRot;
	this.awayRot = awayRot;
	this.homePitcher = homePitcher;
	this.awayPitcher = awayPitcher;
}
public Match() {
	super();
	// TODO Auto-generated constructor stub
}
 
 
 
}
