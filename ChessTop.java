// Andrew Boles - ckj771
// ChessTop.java
import java.util.*;
import java.io.*;
public class ChessTop{
	
	private Board board; // current board
	private ArrayList<Player> players = new ArrayList<Player>(); // list of players
	private ArrayList<String> playerNames = new ArrayList<String>(); // 
	//private boolean turn; // current turn
	// constructor
	public ChessTop(){
		// initialize anything needed here
	}
	
	// get board
	public Board getBoard(){
		return board;
	}
	//get list of players
	public ArrayList<Player> getPlayers(){
		return players;
	}
	// main function for testing
	public static void main(String[] args){
		// Main Menu
		for(int i = 0; i < 5; i++){
			System.out.println();
		}
		System.out.println(" Hello! And welcome to Chess Scramble!"); // these two lines are always shown when game starts
		System.out.println(" Please observe our Main Menu's list of options/commands:");
		boolean mainMenu = true; // true --> game is still playing
		mainMenuLoop:
			while(mainMenu){
				for(int i = 0; i < 5; i++){
					System.out.println();
				}
				System.out.println(" ------------------------------------");
				System.out.println("             MAIN MENU               ");
				System.out.println(" ------------------------------------");
				System.out.println("   1. Play a new game.");
				System.out.println(" ------------------------------------");
				System.out.println("   2. Display the help menu.");
				System.out.println(" ------------------------------------");
				System.out.println(" Please type in option number: ");
				Scanner userInput = new Scanner(System.in);
				String inputString;
				inputString = userInput.nextLine();
				if(inputString.charAt(0) == '1'){ // start a new game!
					System.out.println();
					Board chessBoard = new Board();
					Move mover = new Move(chessBoard);
					Help help = new Help();
					Player white = new Player(0);
					System.out.println("Hello Player 1. Please input your desired user name: ");
					white.setName(userInput.nextLine());
					System.out.println();
					Player black = new Player(1);
					System.out.println("And hello Player 2. Please input your desired user name: ");
					black.setName(userInput.nextLine());
					System.out.println();
					System.out.println("Thank you both very much. Now, discuss with one another and decide upon");
					System.out.println("which game type you would like to play:");
					chessBoard.initBoard(); // initiate the board, start game
					boolean checkMate = false; // no checkmate to start game
					boolean turn = false; // white starts the game
					String source, destin;
					play:
						while(true){ 
							if(!turn){
								for(int i = 0; i < 5; i++){
									System.out.println();
								}
								chessBoard.setTurn(0);
								if(mover.checkCheck(white.getColor())){
									System.out.println(white.getName() + ", you are in check. Proceed with caution.");
								} else{
									System.out.println(white.getName() + " it is your turn. Choose wisely.");
								}
								System.out.println("Input current coordinates of the piece that you want to move.");
								System.out.println("Type 'H' for help and to access specific commands.");
								source = userInput.nextLine();
								if(source.charAt(0) == 'H'){ // help menu!
									help.display();
									continue play; // if help just exited normally, restart player's turn
								}
								System.out.println("Input coordinates of the destination space.");
								System.out.println("Type 'H' for help and to access specific commands.");
								destin = userInput.nextLine();
								if(destin.charAt(0) == 'H'){ // help menu!
									help.display();
									continue play; // if help just exited normally, restart player's turn
								}
								mover.move(source, destin, white);
								turn = true;
							} else { // black player's turn!
								for(int i = 0; i < 5; i++){
									System.out.println();
								}
								chessBoard.setTurn(1);
								if(mover.checkCheck(black.getColor())){
									System.out.println(black.getName() + ", you are in check. Proceed with caution.");
								} else{
									System.out.println(black.getName() + " it is your turn. Choose wisely.");
								}
								System.out.println("Input current coordinates of the piece that you want to move.");
								System.out.println("Type 'H' for help and to access specific commands.");
								source = userInput.nextLine();
								if(source.charAt(0) == 'H'){ // help menu!
									help.display();
									continue play; // if help just exited normally, restart player's turn
								}
								System.out.println("Input coordinates of the destination space.");
								System.out.println("Type 'H' for help and to access specific commands.");
								destin = userInput.nextLine();
								if(destin.charAt(0) == 'H'){ // help menu!
									help.display();
									continue play; // if help just exited normally, restart player's turn
								}
								mover.move(source, destin, black);
								turn = false;
							}
						}
				} else if(inputString.charAt(0) == '2'){ // display help!
					Help help = new Help();
					help.display();
				} else {
					System.out.println("I did not recognize that command. Please try again.");
				}
			}
	}
}