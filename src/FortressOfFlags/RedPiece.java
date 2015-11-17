package FortressOfFlags;

public class RedPiece {
	
	public static final String RED="Red";
	public static final String BLUE="Blue";
	
	private int x;
	private int y;
	private String terrain;
	private int flagID;
	private String player;
	private String rank;
	private boolean revealed;
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public String getTerrain() {
		return terrain;
	}
	public void setTerrain(String terrain) {
		this.terrain = terrain;
	}
	public int getFlagID() {
		return flagID;
	}
	public void setFlagID(int flagID) {
		this.flagID = flagID;
	}
	public String getPlayer() {
		return player;
	}
	public void setPlayer(String player) {
		this.player = player;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public boolean isRevealed() {
		return revealed;
	}
	public void setRevealed(boolean revealed) {
		this.revealed = revealed;
	}

}
