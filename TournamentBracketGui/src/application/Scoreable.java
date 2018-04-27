package application;

/**
 * Interface for classes that might want to score a game. Includes a method to
 * get a winner of a game and a method to get a loser of a game.
 * 
 * @author A-Team 42
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
