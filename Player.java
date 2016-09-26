// Andrew Boles - ckj771
// Player.java
import java.util.*;
public class Player{
	private Board board; // current board
	private int color; // white = 0, black = 1
	private String name; // player name
	private int numPiecesLeft; // total number of a player's pieces remaining; max = 16
	// default constructor
	public Player(){
	}
	// constructor to choose color
	public Player(int color){
		this.color = color;
	}
	// constructor to input name as well as color
	public Player(int color, String name){
		this.color = color;
		this.name = name;
	}	
	// get board
	private Board getBoard(){
		return board;
	}
	// get color
	public int getColor(){
		return color;
	}	
	// set color
	public void setColor(int color){
		this.color = color;
	}	
	// return enemy color
	public int getEnemyColor(){
		if(getColor() == 0){
			return 1;
		} else {
			return 0;
		}
	}	
	// get name
	public String getName(){
		return name;
	}	
	// set name
	public void setName(String name){
		this.name = name;
	}
	// get number of pieces remaining for player
	public int getNumPiecesLeft(){
		ArrayList<Piece> pieces = getBoard().getPieces(); // get current pieces
		int numLeft = 0; // assume no pieces
		for(Piece p : pieces){
			if(p.getColor() == color){ // if same color, increment numLeft
				numLeft++;
			}
		}
		return numLeft;
	}
	
}