// Andrew Boles - ckj771
// Piece.java
import java.util.*;
public class Piece{
	//constant integer piece type
	public static final int KING = 1;
	public static final int QUEEN = 2;
	public static final int ROOK = 3;
	public static final int KNIGHT = 4;
	public static final int BISHOP = 5;
	public static final int PAWN = 6;
	private int color; // white = 0, black = 1
	private int type; // type of piece
	private int row, col; // position on board
	// default constructor, used for blank space
	public Piece(){} 
	// constructor to create pieces
	public Piece(int color, int type, int row, int col){ 
		this.color = color;
		this.type = type;
		this.row = row;
		this.col = col;
	}
	// get piece name for board
	public String getPieceName(){
		String name = "";
		if(getColor() == 0 && getType() != 0){
			name = "W";
		} else {
			name = "B";
		}
		switch (getType()){
		case 1:
			name += "Ki";
			break;
		case 2:
			name += "Qu";
			break;
		case 3:
			name += "Rk";
			break;
		case 4:
			name += "Kn";
			break;
		case 5:
			name += "Bi";
			break;
		case 6:
			name += "Pn";
			break;
		default: // show error to terminal, break
			System.out.println("The given piece type is not in the valid range.");
			break;
		}
		return name;
	}
	// get type
	public int getType(){
		return type;
	}
	// set type
	public void setType(int type){
		this.type = type;
	}
	// get color
	public int getColor(){
		return color;
	}
	// get enemy color
	public int getEnemyColor(){
		if(color == 0){
			return 1;
		} else {
			return 0;
		}
	}
	// set color
	public void setColor(int color){
		this.color = color;
	}	
	// get row
	public int getRow(){
		return row;
	}	
	// set row
	public void setRow(int row){
		this.row = row;
	}	
	// get column
	public int getCol(){
		return col;
	}	
	// set column
	public void setCol(int col){
		this.col = col;
	}
}