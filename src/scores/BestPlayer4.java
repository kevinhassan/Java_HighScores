package scores;

public class BestPlayer4 {

	String player;
	int score;
	
	/**
	 * Constructor of a HighScore class object
	 * 
	 * @param p a string which is the name of the player
	 * @param s a int which the score of the player
	 */
	public BestPlayer4(String p,int s)
	{
		this.player=p;
		this.score=s;
	}

	/**
	 * Getter of the player attribute
	 * 
	 * @return a string which the name of the player
	 */
	public String getPlayer() {
		return player;
	}

	/**
	 * Setter of the player attribute
	 * 
	 * @param player a string which is the new name of the player
	 */
	public void setPlayer(String player) {
		this.player = player;
	}

	/**
	 * Getter of the score attribute
	 * 
	 * @return a string which the score of the player
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Setter of the score attribute
	 * 
	 * @param score a int which is the new score of the player
	 */
	public void setScore(int score) {
		this.score = score;
	}
	
	/**
	 * 
	 * @param p a player to compare with the player this
	 * @return 0 if the both score of the players is equals, -1 if it's the player in param who has the best score and 1 otherwise
	 */
	public int compareTo(BestPlayer4 p)
	{
		int scoreTest=p.getScore();
		int result;
		
		if(this.score==scoreTest)
		{
			result=0;
		}
		else if(this.score<scoreTest)
		{
			result=-1;
		}
		else
		{
			result=1;
		}
		return result;
	}
}
