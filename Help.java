// Andrew Boles - ckj771
// Help.java
import java.util.*;
import java.io.*;
public class Help{
	private Board board;
	// default constructor
	public Help(){
	}
	// get board method
	public Board getBoard(){
		return board;
	}
	/*
	// castle --> does not work
	public void castle(Player player){
		ArrayList<Piece> pieces = getBoard().getPieces();
		ArrayList<Piece> castle = new ArrayList<Piece>();
		int color = player.getColor();
		int castleType;
		for(Piece p : pieces){
			if(p.getColor() == color && (p.getType() == (1 || 3))){
				castle.add(p);
			}
		}
		for(Piece p : castle){
			if(p.getType() == 3 && p.getRow() == (0 || 7)){
				if(p.getCol() == 7){
					castleType = 0;
				} else if(p.getCol() == 0) {
					castleType = 1;
				}
			} else {
				System.out.println("Player cannot castle.");
			}
		}
		if(castleType == 0){
			if(color == 0){
				for(Piece p : castle){
					if(p.getType() == 1){
						getBoard().swapPieces(p, getBoard().getPieces(0,6,color));
					} else{
						getBoard().swapPieces(p, getBoard().getPieces(0,5,color));
					}
				}
			} else{
				for(Piece p : castle){
					if(p.getType() == 1){
						getBoard().swapPieces(p, getBoard().getPieces(0,6,color));
					} else{
						getBoard().swapPieces(p, getBoard().getPieces(0,5,color));
					}
				}
			}
		} else{
			if(color == 0){
				for(Piece p : castle){
					if(p.getType() == 1){
						getBoard().swapPieces(p, getBoard().getPieces(0,2,color));
					} else{
						getBoard().swapPieces(p, getBoard().getPieces(0,3,color));
					}
				}
			} else{
				for(Piece p : castle){
					if(p.getType() == 1){
						getBoard().swapPieces(p, getBoard().getPieces(7,2,color));
					} else{
						getBoard().swapPieces(p, getBoard().getPieces(7,3,color));
					}
				}
			}
		}
	}
	*/
	// app info
	public void appInfo(){
		for(int i = 0; i < 5; i++){
			System.out.println();
		}
		System.out.println("Creator: Andrew Boles");
		System.out.println("Engineering Programming II Final Project");
		System.out.println("University of Texas San Antonio");
	}
	// Basic Chess Rules
	public void basicChessInfo(){
		for(int i = 0; i < 5; i++){
			System.out.println();
		}
		System.out.println("To learn about the fantastic game of chess..");
		System.out.println("http://www.intuitor.com/chess/");
		System.out.println("^^ This website has great information for beginning players and experienced ones alike!");
	}
	// scramble chess rules
	public void scrambleChessRules(){
		for(int i = 0; i < 5; i++){
			System.out.println();
		}
		System.out.println("Scramble Chess Rules!");
		System.out.println("Different from regular chess in two ways:");
		System.out.println("	1. Before the game, each player can rearrange their back lines.");
		System.out.println("		Every piece gets to be changed except for the pawns and king.");
		System.out.println("	2. During gameplay, pawns can move one space horizontally");
		System.out.println("		as well as vertically.");
		System.out.println("		Pawns still capture pieces diagonally however.");
	}
	// quit game
	public void quitGame(){
		for(int i = 0; i < 5; i++){
			System.out.println();
		}
		System.out.println("GAME OVER. (Press Control+c to exit game)");
		// quit game 
	}
	
	// display help menu, should allow user to navigate through help
	public void display(){
		for(int i = 0; i < 5; i++){
			System.out.println();
		}
		Scanner scan = new Scanner(System.in);
		System.out.println("This is the help class!");
		while(true){
			System.out.println("The following are your options:");
			System.out.println("1. App Info");
			System.out.println("2. Basic Chess Rules");
			System.out.println("3. Chess Scramble Rules");
			System.out.println("4. Castle");
			System.out.println("5. Quit Game");
			System.out.println();
			System.out.println("Please enter the number of your choice.");
			String choice = scan.nextLine();
			if(choice.charAt(0) == '1'){
				appInfo();
				break;
			} else if(choice.charAt(0) == '2'){
				basicChessInfo();
				break;
			} else if(choice.charAt(0) == '3'){
				scrambleChessRules();
				break; 
			} else if(choice.charAt(0) == '4'){
				System.out.println("This is where the player would castle.");
				//castle();
				break;
			} else if(choice.charAt(0) == '5'){
				quitGame();
				break;
			} else {
				System.out.println("That is not an option, try again.");
			}
		}
	}
}
	