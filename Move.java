// Andrew Boles - ckj771
// Move.java
import java.util.*;
public class Move{
	private Board board; // game board
	private boolean moveValid; // true if valid move, false if invalid
	// constructor
	public Move(Board board){
		this.board = board;
	}
	// get board
	public Board getBoard(){
		return board;
	}
	// find piece with given row, col
	public Piece findPiece(int row, int col){
		boolean flag = false; // piece found = true
		ArrayList<Piece> pieces = getBoard().getPieces(); // current pieces
		Piece foundPiece = null; 
		findLoop: for(Piece p: pieces){ // iterate to find piece
			foundPiece = p;
			if(foundPiece.getRow() == row && foundPiece.getCol() == col){
				flag = true; // piece found, break loop
				break findLoop;
			}
		}
		// if the flag is true then return the found piece, otherwise null
		if(flag){
			return foundPiece;
		} else {
			return null;
		}
	}
	// convert String input into row integer
	private int inputToRow(String input){
		char r = input.charAt(1);
		int row;
		switch(r){ // '0' value corresponds to '8', etc
		case '8':
			row = 0;
			break;
		case '7':
			row = 1;
			break;
		case '6':
			row = 2;
			break;
		case '5':
			row = 3;
			break;
		case '4':
			row = 4;
			break;
		case '3':
			row = 5;
			break;
		case '2':
			row = 6;
			break;
		case '1':
			row = 7;
			break;
		default:
			row = -1;
			break;
		}
		return row;
	}
	// convert String input into column integer
	private int inputToCol(String input){
		char c = input.charAt(0);
		int col;
		switch(c){ // '0' value corresponds to 'a', etc
		case 'a':
			col = 0;
			break;
		case 'b':
			col = 1;
			break;
		case 'c':
			col = 2;
			break;
		case 'd':
			col = 3;
			break;
		case 'e':
			col = 4;
			break;
		case 'f':
			col = 5;
			break;
		case 'g':
			col = 6;
			break;
		case 'h':
			col = 7;
			break;
		default:
			col = -1;
			break;
		}
		return col;
	}
	// move piece in selected space to destination
	// returns true if the move was successful
	public boolean move(String curSpace, String destSpace, Player player){
		// convert input Strings into row, col integers
		int curRow, curCol, destRow, destCol;
		curRow = inputToRow(curSpace);
		curCol = inputToCol(curSpace);
		destRow = inputToRow(destSpace);
		destCol = inputToCol(destSpace);
		if((curRow >= 0 && curRow < 8) && (curCol >= 0 && curCol < 8)){ // check source space
			if((destRow >= 0 && destRow < 8) && (destCol >= 0 && destCol < 8)){ // check destination space
				Piece piece = findPiece(curRow, curCol);
				if(piece.getColor() == player.getColor()){ // color match check
					ArrayList<ArrayList<Integer>> allowedMoves = legalPieceMoves(piece, false); // get legal moves
					ArrayList<Integer> r = allowedMoves.get(0); // row
					ArrayList<Integer> c = allowedMoves.get(1); // column
					ListIterator<Integer> rowIter = r.listIterator(); // iterate through row, column
					ListIterator<Integer> colIter = c.listIterator();
					int rNext, cNext;
					while(rowIter.hasNext() && colIter.hasNext()){ // while the iterators still have values
						rNext = rowIter.next();
						cNext = colIter.next();
						if(destRow == rNext && destCol == cNext){ // if it is a valid move...
							getBoard().removePiece(destRow, destCol); // remove target piece (if enemy on space)
							piece.setRow(destRow); // set row, column to new space
							piece.setCol(destCol);
							getBoard().updateGameBoard(); // update game status
							getBoard().currentGameState();
							getBoard().pawnPromotion(player.getColor()); // promote pawns if necessary
							moveValid = true; // successful move!
							return true; 
						}
					}
				}
			} else {
				System.out.println("Not a valid destination space. Try again!");
				moveValid = false; // DO NOT THINK I ACTAULLY NEED THIS ONE!! --> TAKE OUT IF NOT
			}
		} else {
			System.out.println("Not a valid source space. Try again!");
			moveValid = false;
		}
		return false; // move failed
	}
	// check if king piece is on input square
	public boolean kingOnSpace(int r, int c, int color){
		for(Piece p : getBoard().getPieces()){ // iterate through pieces
			if(p.getColor() == color){ // color check
				if(p.getRow() == r && p.getCol() == c){ // space check
					if(p.getType() == 1){ // check that type = king
						return true;
					}
				}
			}
		}
		return false; // no king on space
	}	
	// legal move check for input piece, returns list of allowed moves
	public ArrayList<ArrayList<Integer>> legalPieceMoves(Piece piece, boolean checkMate){
		ArrayList<Integer> row, col; // list of coordinates
		row = new ArrayList<Integer>();
		col = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> king, queen, rook, knight, bishop, pawn; // list for each different type of piece
		ArrayList<ArrayList<Integer>> legalMoves = new ArrayList<ArrayList<Integer>>(); // list to be returned with legal move set
		switch(piece.getType()){ // switch between different types
			// king
			case 1:
				king = possKingMoves(piece, checkMate); // fill king with all possible King moves
				row = king.get(0); // row values are stored in first ArrayList
				col = king.get(1); // column values are stored in second ArrayList
				break;
			// queen
			case 2:
				queen = possQueenMoves(piece, checkMate);
				row = queen.get(0);
				col = queen.get(1);
				break;
			// rook
			case 3:
				rook = possRookMoves(piece, checkMate);
				row = rook.get(0);
				col = rook.get(1);
				break;
			// knight
			case 4:
				knight = possKnightMoves(piece, checkMate);
				row = knight.get(0);
				col = knight.get(1);
				break;
			// bishop
			case 5:
				bishop = possBishopMoves(piece, checkMate);
				row = bishop.get(0);
				col = bishop.get(1);
				break;
			// pawn
			case 6:
				pawn = possPawnMoves(piece, checkMate);
				row = pawn.get(0);
				col = pawn.get(1);
				break;
			default:
				break;
		}
		legalMoves.add(row); // add row, col to legalMoves
		legalMoves.add(col);
		return legalMoves; // return list of legal moves
	}	
	// check if input row, col coordinates are on the board
	private boolean onBoardCheck(int r, int c){
		if((r >= 0 && r <= 7) && (c >= 0 && c <= 7)){
			return true; 
		} else {
			return false; 
		}
	}
	// check for piece on input space, input: row, column
	public boolean pieceOnSpace(int r, int c){
		for(Piece p : getBoard().getPieces()){ // iterate through pieces on board
			if(p.getRow() == r && p.getCol() == c){ // find piece
				return true;
			}
		}
		return false; // no piece found
	}
	// check for piece on input space, input: row, column, color
	public boolean pieceOnSpace(int r, int c, int color){
		for(Piece p : getBoard().getPieces()){ // iterate through pieces
			if(p.getColor () == color){ // color check
				if(p.getRow() == r && p.getCol() == c){ // find piece
					return true;
				}
			}
		}
		return false; // no piece found
	}
	// possible pawn movements: returns ArrayList<ArrayList<Integer>>
	private ArrayList<ArrayList<Integer>> possPawnMoves(Piece piece, boolean checkMate){
		ArrayList<Integer> row, col; // row, column lists
		row = new ArrayList<Integer>();
		col = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> rowAndCol = new ArrayList<ArrayList<Integer>>(); // combined list to return
		int r = piece.getRow(); // row
		int c = piece.getCol(); // column
		int color = piece.getColor(); // color
		int oppColor = piece.getEnemyColor(); // enemy color
		// regular forward movement
		if(piece.getType() == 6 && piece.getColor() == 0){ // check if pawn / right color
			if(r == 6) { // if pawn has not moved, can move one or two spaces
				if(!pieceOnSpace(r-1, c)){ // make sure space is clear
					row.add(r-1); // add row, column to possible moves
					col.add(c);
					if(!pieceOnSpace(r-2, c)){
						row.add(r-2);
						col.add(c);
					}
				}
			} else { // not on home row, can only move one space forward
				if((r-1)>=0 && !pieceOnSpace(r-1, c)){ // make sure space is clear / pawn will not go off board
					row.add(r-1);
					col.add(c);
				}
			}
			// diagonal above: right/left is for capturing opponents
			if(pieceOnSpace(r-1, c+1, oppColor)){ // check for opponent in space (above right)
				if(checkMate){ // if checkMate = true, game is over and pawn can take king
					row.add(r-1);
					col.add(c+1);
				} else {  // game is not over yet
					if(!kingOnSpace(r-1, c+1, oppColor)){ // make sure king is not on space
						row.add(r-1);
						col.add(c+1);
					}
				}
			}
			if(pieceOnSpace(r-1, c-1, oppColor)){ // above left
				if(checkMate){
					row.add(r-1);
					col.add(c-1);
				} else { 
					if(!kingOnSpace(r-1, c-1, oppColor)){
						row.add(r-1);
						col.add(c-1);
					}
				}
			}
			if(getBoard().getChosenGameType() == '1'){ // if INPUT NEW NAME game
				// pawn can also move horizontally one space right/left
				if(!pieceOnSpace(r, c+1)){ // make sure space is clear (right)
					row.add(r); // add row, column to possible moves
					col.add(c+1);
				}
				if(!pieceOnSpace(r, c-1)){ // left
					row.add(r); // add row, column to possible moves
					col.add(c-1);
				}
			}
		} else if(piece.getType() == 6 && piece.getColor() == 1){ // black pawn
			// regular forward movement
			if(r == 1) {
				if(!pieceOnSpace(r+1, c)){
					row.add(r+1);
					col.add(c);
					if(!pieceOnSpace(r+2, c)){
						row.add(r+2);
						col.add(c);
					}
				}
			} else { 
				if((r+1)>=0 && !pieceOnSpace(r+1, c)){
					row.add(r+1);
					col.add(c);
				}
			}
			// diagonal below: right/left is for capturing opponents
			if(pieceOnSpace(r+1, c+1, oppColor)){ // below right
				if(checkMate){
					row.add(r+1);
					col.add(c+1);
				} else { 
					if(!kingOnSpace(r+1, c+1, oppColor)){
						row.add(r+1);
						col.add(c+1);
					}
				}
			}
			if(pieceOnSpace(r+1, c-1, oppColor)){ // below left
				if(checkMate){
					row.add(r+1);
					col.add(c-1);
				} else { 
					if(!kingOnSpace(r+1, c-1, oppColor)){
						row.add(r+1);
						col.add(c-1);
					}
				}
			}
			if(getBoard().getChosenGameType() == '1'){ // if INPUT NEW NAME game
				// pawn can also move horizontally one space right/left
				if(!pieceOnSpace(r, c+1)){ // make sure space is clear (right)
					row.add(r); // add row, column to possible moves
					col.add(c+1);
				}
				if(!pieceOnSpace(r, c-1)){ // left
					row.add(r); // add row, column to possible moves
					col.add(c-1);
				}
			}
		}
		rowAndCol.add(row); // add row, column to combined list
		rowAndCol.add(col);
		return rowAndCol; // return pawn moves
	}
	// possible bishop movements: returns ArrayList<ArrayList<Integer>>
	private ArrayList<ArrayList<Integer>> possBishopMoves(Piece piece, boolean checkMate){
		ArrayList<Integer> row, col; // lists for row, col
		row = new ArrayList<Integer>();
		col = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> rowAndCol = new ArrayList<ArrayList<Integer>>(); // combined list to return
		int r = piece.getRow(); // row
		int c = piece.getCol(); // column
		int color = piece.getColor(); // color
		int oppColor = piece.getEnemyColor(); // enemy color
		// check all diagonal above, right
		int j = c+1; // column count
		int i = r-1; // row count
		while(i >= 0 && j <= 7){ // while on the board
			if(pieceOnSpace(i, j, color)){ // if same color piece is on space, no add, break
				break;
			} else if(pieceOnSpace(i, j, oppColor)){ // check for opponent on space, add then break
				if(checkMate){ // check for checkmate, end game if true
					row.add(i);
					col.add(j);
				} else { // not checkmate
					if(!kingOnSpace(i, j, oppColor)){ // if the piece is not the king, can take the piece
						row.add(i);
						col.add(j);
					}
				}
				break;
			} else { // empty space so add
				row.add(i);
				col.add(j);
			}
			i--; // iterate through counters
			j++;
		}
		// check all diagonal above, left
		j = c-1;
		i = r-1;
		while(i >= 0 && j >= 0){ /// while on board
			if(pieceOnSpace(i, j, color)){ // if same color piece, no add, break
				break;
			} else if(pieceOnSpace(i, j, oppColor)){ // check for opponent on space, add then break
				if(checkMate){ // capture king
					row.add(i);
					col.add(j);
				} else { // not checkmate, make sure piece is not king
					if(!kingOnSpace(i, j, oppColor)){
						row.add(i);
						col.add(j);
					}
				}
				break;
			} else { // empty space so add
				row.add(i);
				col.add(j);
			}
			i--; // iterate counters
			j--;
		}
		// check all diagonal below, right
		j = c+1;
		i = r+1;
		while(i <= 7 && j <= 7){ // while on board
			if(pieceOnSpace(i, j, color)){ // if same color piece, no add, break
				break;
			} else if(pieceOnSpace(i, j, oppColor)){ // check for opponent on space, add then break
				if(checkMate){ // capture king
					row.add(i);
					col.add(j);
				} else { // not checkmate, make sure piece is not king
					if(!kingOnSpace(i, j, oppColor)){
						row.add(i);
						col.add(j);
					}
				}
				break;
			} else { // empty space so add
				row.add(i);
				col.add(j);
			}
			i++; // iterate counters
			j++;
		}
		// check all diagonal, left
		j = c-1;
		i = r+1;
		while(i <= 7 && j >= 0){ // while on board
			if(pieceOnSpace(i, j, color)){ // if same color piece, no add, break
				break;
			} else if(pieceOnSpace(i, j, oppColor)){ // check for opponent on space, add then break
				if(checkMate){ // capture king
					row.add(i);
					col.add(j);
				} else { // not checkmate, make sure piece is not king
					if(!kingOnSpace(i, j, oppColor)){
						row.add(i);
						col.add(j);
					}
				}
				break;
			} else { // empty space so add
				row.add(i);
				col.add(j);
			}
			i++; // iterate counters
			j--;
		}
		rowAndCol.add(row); // add row, column to combination ArrayList
		rowAndCol.add(col);
		return rowAndCol; // return combination
	}	
	// possible knight movements: returns ArrayList<ArrayList<Integer>>
	private ArrayList<ArrayList<Integer>> possKnightMoves(Piece piece, boolean checkMate){
		ArrayList<Integer> row, col; // row, column lists
		row = new ArrayList<Integer>();
		col = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> rowAndCol = new ArrayList<ArrayList<Integer>>(); // combined list to return
		int r = piece.getRow(); // row
		int c = piece.getCol(); // column
		int color = piece.getColor(); // color 
		int oppColor = piece.getEnemyColor(); // enemy color
		// check above, right spaces (2 options)
		// option 1: up two, right one
		if(pieceOnSpace(r-2, c+1, color)){ 
			// if same color piece on space, do not add
		} else if(pieceOnSpace(r-2, c+1, oppColor)){ // check for opponent on space, add then break
			if(checkMate){ // capture king
				row.add(r-2);
				col.add(c+1);
			} else { // not checkmate, make sure piece is not enemy king
				if(!kingOnSpace(r-2, c+1, oppColor)){
					row.add(r-2);
					col.add(c+1);
				}
			}
		} else { // empty space so add
			row.add(r-2);
			col.add(c+1);
		}
		// option 2: up one, right two
		if(pieceOnSpace(r-1, c+2, color)){
			// if same color piece on space, do not add
		} else if(pieceOnSpace(r-1, c+2, oppColor)){ // check for opponent on space, add then break
			if(checkMate){ // capture king
				row.add(r-1);
				col.add(c+2);
			} else { // not checkmate, make sure piece is not enemy king
				if(!kingOnSpace(r-1, c+2, oppColor)){
					row.add(r-1);
					col.add(c+2);
				}
			}
		} else { // empty space so add
			row.add(r-1);
			col.add(c+2);
		}
		// check above, left spaces (2 options)
		//option 1: up two, left one
		if(pieceOnSpace(r-2, c-1, color)){
			// if same color piece on space, do not add
		} else if(pieceOnSpace(r-2, c-1, oppColor)){ // check for opponent on space, add then break
			if(checkMate){ // capture king
				row.add(r-2);
				col.add(c-1);
			} else { // not checkmate, make sure piece is not enemy king
				if(!kingOnSpace(r-2, c-1, oppColor)){
					row.add(r-2);
					col.add(c-1);
				}
			}
		} else { // empty space so add
			row.add(r-2);
			col.add(c-1);
		}
		// option 2: up one, left two
		if(pieceOnSpace(r-1, c-2, color)){
			// if same color piece on space, do not add
		} else if(pieceOnSpace(r-1, c-2, oppColor)){ // check for opponent on space, add then break
			if(checkMate){ // capture king
				row.add(r-1);
				col.add(c-2);
			} else { // not checkmate, make sure piece is not enemy king
				if(!kingOnSpace(r-1, c-2, oppColor)){
					row.add(r-1);
					col.add(c-2);
				}
			}
		} else { // empty space so add
			row.add(r-1);
			col.add(c-2);
		}
		// check below, right spaces (2 options)
		//option 1: down two right one
		if(pieceOnSpace(r+2, c+1, color)){
			// if same color piece on space, do not add
		} else if(pieceOnSpace(r+2, c+1, oppColor)){ // check for opponent on space, add then break
			if(checkMate){ // capture king
				row.add(r+2);
				col.add(c+1);
			} else { // not checkmate, make sure piece is not enemy king
				if(!kingOnSpace(r+2, c+1, oppColor)){
					row.add(r+2);
					col.add(c+1);
				}
			}
		} else { // empty space so add
			row.add(r+2);
			col.add(c+1);
		}
		// option 2: down one, right two
		if(pieceOnSpace(r+1, c+2, color)){
			// if same color piece on space, do not add
		} else if(pieceOnSpace(r+1, c+2, oppColor)){ // check for opponent on space, add then break
			if(checkMate){ // capture king
				row.add(r+1);
				col.add(c+2);
			} else { // not checkmate, make sure piece is not enemy king
				if(!kingOnSpace(r+1, c+2, oppColor)){
					row.add(r+1);
					col.add(c+2);
				}
			}
		} else { // empty space so add
			row.add(r+1);
			col.add(c+2);
		}
		// check below, left spaces (2 options)
		//option 1: down two, left one
		if(pieceOnSpace(r+2, c-1, color)){
			// if same color piece on space, do not add
		} else if(pieceOnSpace(r+2, c-1, oppColor)){ // check for opponent on space, add then break
			if(checkMate){ // capture king
				row.add(r+2);
				col.add(c-1);
			} else { // not checkmate, make sure piece is not enemy king
				if(!kingOnSpace(r+2, c-1, oppColor)){
					row.add(r+2);
					col.add(c-1);
				}
			}
		} else { // empty space so add
			row.add(r+2);
			col.add(c-1);
		}
		// option 2: down one, left two
		if(pieceOnSpace(r+1, c-2, color)){
			// if same color piece on space, do not add
		} else if(pieceOnSpace(r+1, c-2, oppColor)){ // check for opponent on space, add then break
			if(checkMate){ // capture king
				row.add(r+1);
				col.add(c-2);
			} else { // not checkmate, make sure piece is not enemy king
				if(!kingOnSpace(r+1, c-2, oppColor)){
					row.add(r+1);
					col.add(c-2);
				}
			}
		} else { // empty space so add
			row.add(r+1);
			col.add(c-2);
		}
		rowAndCol.add(row); // add row, column to combined list
		rowAndCol.add(col);
		return rowAndCol; // return combined list
	}	
	// possible rook movements: returns ArrayList<ArrayList<Integer>>
	private ArrayList<ArrayList<Integer>> possRookMoves(Piece piece, boolean checkMate){
		ArrayList<Integer> row, col; // list for row, col
		row = new ArrayList<Integer>();
		col = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> rowAndCol = new ArrayList<ArrayList<Integer>>(); // combined list to return
		int r = piece.getRow(); // row
		int c = piece.getCol(); // column
		int color = piece.getColor(); // color
		int oppColor = piece.getEnemyColor(); // enemy color
		// check all spaces above piece
		for(int i = r-1; i >= 0; i--){
			if(pieceOnSpace(i, c, color)){ // if same color piece, no add, break
				break;
			} else if(pieceOnSpace(i, c, oppColor)){ // check for opponent on space, add then break
				if(checkMate){ // capture king
					row.add(i);
					col.add(c);
				} else { // not checkmate so check if piece is enemy king
					if(!kingOnSpace(i, c, oppColor)){
						row.add(i);
						col.add(c);
					}
				}
				break;
			} else { // empty space so add
				row.add(i);
				col.add(c);
			}
		}
		// check all spaces below piece
		for(int i = r+1; i < 8; i++){
			if(pieceOnSpace(i, c, color)){ // if same color piece, no add, break
				break;
			} else if(pieceOnSpace(i, c, oppColor)){ // check for opponent on space, add then break
				if(checkMate){ // capture king
					row.add(i);
					col.add(c);
				} else { // not checkmate so check if piece is enemy king
					if(!kingOnSpace(i, c, oppColor)){
						row.add(i);
						col.add(c);
					}
				}
				break;
			} else { // empty space so add
				row.add(i);
				col.add(c);
			}
		}
		// check all spaces to the right of piece
		for(int i = c+1; i < 8; i++){
			if(pieceOnSpace(i, c, color)){ // if same color piece, no add, break
				break;
			} else if(pieceOnSpace(i, c, oppColor)){ // check for opponent on space, add then break
				if(checkMate){ // capture king
					row.add(i);
					col.add(c);
				} else { // not checkmate so check if piece is enemy king
					if(!kingOnSpace(i, c, oppColor)){
						row.add(i);
						col.add(c);
					}
				}
				break;
			} else { // empty space so add
				row.add(i);
				col.add(c);
			}
		}
		// check all spaces to the left of piece
		for(int i = c-1; i >= 0; i--){
			if(pieceOnSpace(i, c, color)){ // if same color piece, no add, break
				break;
			} else if(pieceOnSpace(i, c, oppColor)){ // check for opponent on space, add then break
				if(checkMate){ // capture king
					row.add(i);
					col.add(c);
				} else { // not checkmate so check if piece is enemy king
					if(!kingOnSpace(i, c, oppColor)){
						row.add(i);
						col.add(c);
					}
				}
				break;
			} else { // empty space so add
				row.add(i);
				col.add(c);
			}
		}
		rowAndCol.add(row); // add row, column to combined list
		rowAndCol.add(col);
		return rowAndCol; // return combined list
	}
	// possible king movements: returns ArrayList<ArrayList<Integer>>
	private ArrayList<ArrayList<Integer>> possKingMoves(Piece piece, boolean checkMate){
		ArrayList<Integer> row, col; // row, column list
		row = new ArrayList<Integer>();
		col = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> rowAndCol = new ArrayList<ArrayList<Integer>>(); // combined list to be returned
		int r = piece.getRow(); // row
		int c = piece.getCol(); // column
		int color = piece.getColor(); // color
		int oppColor = piece.getEnemyColor(); // enemy color
		
		// NOTE: DOES THERE EVEN HAVE TO BE AN ONBOARD CHECK?? --> WOULD NOT BE TOO HARD TO TAKE AWAY, BUT IS NOT TOO CRAZY
		
		// possible horizontal / vertical / diagonal movements
		// space to the right
		if(onBoardCheck(r, c+1) && !pieceOnSpace(r, c+1, color)){ // on board and not a piece that is same color in space, add space
			if(checkMate){ // capture king
				row.add(r);
				col.add(c+1);
			} else { // not checkmate so make sure piece is not enemy king
				if(!kingOnSpace(r, c+1, oppColor)){
					row.add(r);
					col.add(c+1);
				}
			}
		}
		// space to the left
		if(onBoardCheck(r, c-1) && !pieceOnSpace(r, c-1, color)){ // on board and not a piece that is same color in space, add space
			if(checkMate){ // capture king
				row.add(r);
				col.add(c-1);
			} else { // not checkmate so make sure piece is not enemy king
				if(!kingOnSpace(r, c-1, oppColor)){
					row.add(r);
					col.add(c-1);
				}
			}
		}
		// space above
		if(onBoardCheck(r-1, c) && !pieceOnSpace(r-1, c, color)){ // on board and not a piece that is same color in space, add space
			if(checkMate){ // capture king
				row.add(r-1);
				col.add(c);
			} else { // not checkmate so make sure piece is not enemy king
				if(!kingOnSpace(r-1, c, oppColor)){
					row.add(r-1);
					col.add(c);
				}
			}
		}
		// space below
		if(onBoardCheck(r+1, c) && !pieceOnSpace(r+1, c, color)){ // on board and not a piece that is same color in space, add space
			if(checkMate){ // capture king
				row.add(r+1);
				col.add(c);
			} else { // not checkmate so make sure piece is not enemy king
				if(!kingOnSpace(r+1, c, oppColor)){
					row.add(r+1);
					col.add(c);
				}
			}
		}
		// space diagonal up, right
		if(onBoardCheck(r-1, c+1) && !pieceOnSpace(r-1, c+1, color)){ // on board and not a piece that is same color in space, add space
			if(checkMate){ // capture king
				row.add(r-1);
				col.add(c+1);
			} else { // not checkmate so make sure piece is not enemy king
				if(!kingOnSpace(r-1, c+1, oppColor)){
					row.add(r-1);
					col.add(c+1);
				}
			}
		}
		// space diagonal up, left
		if(onBoardCheck(r-1, c-1) && !pieceOnSpace(r-1, c-1, color)){ // on board and not a piece that is same color in space, add space
			if(checkMate){ // capture king
				row.add(r-1);
				col.add(c-1);
			} else { // not checkmate so make sure piece is not enemy king
				if(!kingOnSpace(r-1, c-1, oppColor)){
					row.add(r-1);
					col.add(c-1);
				}
			}
		}
		// space diagonal down, right
		if(onBoardCheck(r+1, c+1) && !pieceOnSpace(r+1, c+1, color)){ // on board and not a piece that is same color in space, add space
			if(checkMate){ // capture king
				row.add(r+1);
				col.add(c+1);
			} else { // not checkmate so make sure piece is not enemy king
				if(!kingOnSpace(r+1, c+1, oppColor)){
					row.add(r+1);
					col.add(c+1);
				}
			}
		}
		// space diagonal down, left
		if(onBoardCheck(r+1, c-1) && !pieceOnSpace(r+1, c-1, color)){ // on board and not a piece that is same color in space, add space
			if(checkMate){ // capture king
				row.add(r+1);
				col.add(c-1);
			} else { // not checkmate so make sure piece is not enemy king
				if(!kingOnSpace(r+1, c-1, oppColor)){
					row.add(r+1);
					col.add(c-1);
				}
			}
		}
		rowAndCol.add(row); // add row, column to combined list
		rowAndCol.add(col);
		return rowAndCol; // return combined list
	}	
	// possible queen movements: returns ArrayList<ArrayList<Integer>>
	private ArrayList<ArrayList<Integer>> possQueenMoves(Piece piece, boolean checkMate){
		ArrayList<Integer> row, col; // row, col list
		row = new ArrayList<Integer>();
		col = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> rowAndCol = new ArrayList<ArrayList<Integer>>(); // combination array to be returned
		// queen is a combination of bishop and rook movements. So: add both to row and col
		ArrayList<ArrayList<Integer>> bishop, rook;
		bishop = possBishopMoves(piece, checkMate);
		rook = possRookMoves(piece, checkMate);
		row.addAll(bishop.get(0));
		col.addAll(bishop.get(1));
		row.addAll(rook.get(0));
		col.addAll(rook.get(1));
		rowAndCol.add(row); // add row, column lists to combined list
		rowAndCol.add(col);
		return rowAndCol; // return combined list
	}
	// possible enemy targets for input piece to capture
	public ArrayList<Piece> possEnemyTargets(Piece piece){
		ArrayList<Piece> enemyTargets = new ArrayList<Piece>(); // list of targets to be returned
		ArrayList<ArrayList<Integer>> allowedMoves = legalPieceMoves(piece, false); // legal moves for piece
		ArrayList<Integer> r = allowedMoves.get(0); // row list
		ArrayList<Integer> c = allowedMoves.get(1); // column list
		ListIterator<Integer> rowIter = r.listIterator(); // iterate through both row, column lists
		ListIterator<Integer> colIter = c.listIterator();
		int rNext, cNext;
		while(rowIter.hasNext() && colIter.hasNext()){ // while the iterators still have values
			rNext = rowIter.next();
			cNext = colIter.next();
			if(pieceOnSpace(rNext, cNext, piece.getEnemyColor())){ // check if enemy is on space, if so add it to list
				enemyTargets.add(board.getPieces(rNext, cNext, piece.getEnemyColor()));
			}
		}
		return enemyTargets; // return list full of targets
	}
	
	// check for check! --> so if the king of the current player (current color) is in the list for any of the opponent's possEnemyTargets then they are in check and the flag should be thrown to show so
	public boolean checkCheck(int color){
		// ArrayList with the current pieces
		ArrayList<Piece> pieces = getBoard().getPieces();
		for(Piece p : pieces){ // iterate over all pieces
			ArrayList<Piece> enemyTargets = possEnemyTargets(p); // fill enemyTargets with Piece p's targets
			ListIterator<Piece> enemyIter = enemyTargets.listIterator();
			Piece nextEnemy = new Piece();
			while(enemyIter.hasNext()){ // while iterator has more values
				nextEnemy = enemyIter.next();
				if(nextEnemy.getType() == 1 && (p.getColor() != color)){ // if the next available target for that piece is a king and the opposite color
					// CHANGE THIS TO BE MORE FLUID!
					//System.out.println("You are in check!");
					return true;
				}
			}
		}
		return false; // no pieces are putting king in check
	}
	
	
}