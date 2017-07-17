package com.sw.entities;

import java.util.List;

public class Tournament {

	private List<Match> matches;

	public List<Match> getMatches() {
		return matches;
	}

	public void setMatches(List<Match> matches) {
		this.matches = matches;
	}

	public Tournament(List<Match> matches) {
		super();
		this.matches = matches;
	}

	public Tournament() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
