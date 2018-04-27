package application;

/**
 * 
 * @author Zach Kremer, _______
 *
 */
public class Team {
	String teamName;
	Integer seed;

	/**
	 * 
	 * @param team
	 * @param seed
	 */
	public Team(String team, Integer seed) {
		this.teamName = team;
		this.seed = seed;
	}

	/**
	 * 
	 * @return
	 */
	public String getTeamName() {
		return teamName;
	}

	/**
	 * 
	 * @param teamName
	 */
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	/**
	 * 
	 * @return
	 */
	public Integer getSeed() {
		return seed;
	}

	/**
	 * 
	 * @param seed
	 */
	public void setSeed(Integer seed) {
		this.seed = seed;
	}

	/**
	 * 
	 */
	@Override
	public String toString() {
		return "(" + seed + ") " + teamName;
	}

}
