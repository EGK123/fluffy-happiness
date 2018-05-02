/////////////////////////////////////////////////////////////////////////////
// Semester: CS400 Spring 2018
// PROJECT: Team Project, Milestone 3
// FILES: Main.java
// Scoreable.java
// Team.java
// TypeOfMatch.java
// VersusBox.java
// application.css
// teams.txt
//
// Authors: Zach Kremer, Ege Kula, Patrick Lacina, Nathan Kolbow, Jong Kim
// Due date: 10:00 PM on Thursday, May 3rd
// Outside sources: None
//
// Instructor: Deb Deppeler (deppeler@cs.wisc.edu)
// Bugs: No known bugs
//
//////////////////////////// 80 columns wide //////////////////////////////////

package application;

/**
 * Class that stores relevant information for teams in the tournament, such as
 * the team name and seed.
 * 
 * @author Zach Kremer
 *
 */
public class Team {
	String teamName; // the name of the team to be competing
	Integer seed; // the seed of the team in the tournament

	/**
	 * Constructor for the Team class. Initializes teamName and seed.
	 * 
	 * @param team
	 *            the name of the team to be competing in the tournament
	 * @param seed
	 *            the seed of the team in the tournament
	 */
	public Team(String team, Integer seed) {
		this.teamName = team;
		this.seed = seed;
	}

	/**
	 * The getter for the team name
	 * 
	 * @return the team name
	 */
	public String getTeamName() {
		return teamName;
	}

	/**
	 * The setter for the team name
	 * 
	 * @param teamName
	 *            the name of the team
	 */
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	/**
	 * Getter for seed.
	 * 
	 * @return the seed of the team
	 */
	public Integer getSeed() {
		return seed;
	}

	/**
	 * Setter for the seed.
	 * 
	 * @param seed
	 *            the team seed
	 */
	public void setSeed(Integer seed) {
		this.seed = seed;
	}

	/**
	 * Overrides toString() to print the name and seed of a Team.
	 * 
	 * @return the seed and name of a Team
	 */
	@Override
	public String toString() {
		return "(" + seed + ") " + teamName;
	}

}
