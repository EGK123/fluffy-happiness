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
 * Interface for classes that might want to score a game. Includes a method to
 * get the winner of a game and a method to get the loser of a game.
 * 
 * @author A-Team 40
 *
 */
public interface Scoreable {
	/**
	 * Returns the winner of a match. The class that implements this method will
	 * decide how the "winner" is determined.
	 * 
	 * @return the winning team
	 */
	public Team getWinner();

	/**
	 * Returns the loser of a match. The class that implements this method will
	 * decide how the "loser" is determined.
	 * 
	 * @return the losing team
	 */
	public Team getLoser();
}
