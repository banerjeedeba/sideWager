package com.sw.entities;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class Match {
	@SerializedName("ID")
	private String id;
	@SerializedName("HomeTeam")
	private String homeTeam;
	@SerializedName("AwayTeam")
	private String awayTeam;
	@SerializedName("Sport")
	private int sport;
	@SerializedName("MatchTime")
	private String matchTime;
	@SerializedName("Odds")
	private ArrayList<Odds> odds;
	@SerializedName("League")
	private String league;
	@SerializedName("DisplayRegion")
	private String displayRegion;
	@SerializedName("HomeROT")
	private String homeRot;
	@SerializedName("AwayROT")
	private String awayRot;
	@SerializedName("HomePitcher")
	private String homePitcher;
	@SerializedName("AwayPitcher")
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

	public ArrayList<Odds> getOdds() {
		return odds;
	}

	public void setOddslist(ArrayList<Odds> odds) {
		this.odds = odds;
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
			String league, String displayRegion, String homeRot, String awayRot, String homePitcher,
			String awayPitcher) {
		super();
		this.id = id;
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.sport = sport;
		this.matchTime = matchTime;
		this.odds = odds;
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
	
	@Override
	public String toString() {
		StringBuffer output = new StringBuffer();
		output.append("id: "+(id!=null?id:"empty"));
		output.append(" homeTeam: "+(homeTeam!=null?homeTeam:"empty"));
		output.append(" awayTeam: "+(awayTeam!=null?awayTeam:"empty"));
		output.append(" sport: "+sport);
		output.append(" matchTime: "+(matchTime!=null?matchTime:"empty"));
		output.append(" league: "+(league!=null?league:"empty"));
		output.append(" displayRegion: "+(displayRegion!=null?displayRegion:"empty"));
		output.append(" homeRot: "+(homeRot!=null?homeRot:"empty"));
		output.append(" awayRot: "+(awayRot!=null?awayRot:"empty"));
		output.append(" homePitcher: "+(homePitcher!=null?homePitcher:"empty"));
		output.append(" awayPitcher: "+(awayPitcher!=null?awayPitcher:"empty"));
		if(odds!=null){
			for(int i=0;i<odds.size();i++){
				output.append("odd "+i);
				output.append("--------------");
				Odds odd = odds.get(i);
				output.append(" odds.id: "+(odd.getId()!=null?odd.getId():"empty"));
				output.append(" odds.eventId: "+(odd.getEventId()!=null?odd.getEventId():"empty"));
				output.append(" odds.siteId: "+(odd.getSiteId()));
				output.append(" odds.moneyLineHome: "+(odd.getMoneyLineHome()!=null?odd.getMoneyLineHome():"empty"));
				output.append(" odds.moneyLineAway: "+(odd.getMoneyLineAway()!=null?odd.getMoneyLineAway():"empty"));
				output.append(" odds.drawLine: "+(odd.getDrawLine()!=null?odd.getDrawLine():"empty"));
				output.append(" odds.pointSpreadHome: "+(odd.getPointSpreadHome()!=null?odd.getPointSpreadHome():"empty"));
				output.append(" odds.pointSpreadAway: "+(odd.getPointSpreadAway()!=null?odd.getPointSpreadAway():"empty"));
				output.append(" odds.pointSpreadHomeLine: "+(odd.getPointSpreadHomeLine()!=null?odd.getPointSpreadHomeLine():"empty"));
				output.append(" odds.pointSpreadAwayLine: "+(odd.getPointSpreadAwayLine()!=null?odd.getPointSpreadAwayLine():"empty"));
				output.append(" odds.totalNumber: "+(odd.getTotalNumber()!=null?odd.getTotalNumber():"empty"));
				output.append(" odds.overLine: "+(odd.getOverLine()!=null?odd.getOverLine():"empty"));
				output.append(" odds.underLine: "+(odd.getUnderLine()!=null?odd.getUnderLine():"empty"));
				output.append(" odds.lastUpdated: "+(odd.getLastUpdated()!=null?odd.getLastUpdated():"empty"));
				output.append(" odds.oddType: "+(odd.getOddType()!=null?odd.getOddType():"empty"));
				output.append(" odds.participant: "+(odd.getParticipant()!=null?odd.getParticipant():"empty"));
				
			}
		}
		
		return output.toString();
	}

}
