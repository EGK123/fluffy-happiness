package application;

/**
 * Class that stores relevant information for teams in the tournament, such as
 * the team name and seed.
 * 
 * @author A-Team 42
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

	@Override
	public String toString() {
		return "(" + seed + ") " + teamName;
	}

}
