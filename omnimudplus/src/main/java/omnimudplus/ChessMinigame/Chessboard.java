package omnimudplus.ChessMinigame;

import omnimudplus.ChessMinigame.ChessPieces.*;
import omnimudplus.Entities.Mobile;

public class Chessboard {

	private Mobile whitePlayer;
	
	private Mobile blackPlayer;
	
	private boolean whiteTurn;
	
	private boolean isPromotable;
	
	private ChessboardSquare[][] chessboard;
	
	public Chessboard() {
		
		whitePlayer = null;
		blackPlayer = null;
		whiteTurn = true;
		chessboard = new ChessboardSquare[8][8];
		
		// 'i' represents the rows, corresponding to i + 1.
		
		// 'j' represents the columns, corresponding to j + 1. 0 = a, 1 = b...
		
		// Thus, when i is even, all even j squares are black.
		
		// And when i is odd, all odd j squares are black.
		
		for (int i = 0; i < chessboard.length; i++) {
			
			for (int j = 0; j < chessboard[i].length; j++) {
				
				boolean odd = i % 2 == 1;
				
				boolean secondOdd = j % 2 == 1;
				
				if (odd && secondOdd) {
					
					chessboard[i][j] = new ChessboardSquare(false);
					continue;
					
				}
				
				if (!odd && !secondOdd) {
					
					chessboard[i][j] = new ChessboardSquare(false);
					continue;
					
				}
				
				chessboard[i][j] = new ChessboardSquare(true);
				
			}
			
		}
		
		for (int i = 0; i < 2; i++) {
			
			for (int j = 0; j < chessboard.length/2; j++) {
				
				if (i == 1) {
					
					chessboard[i][j].setOccupant(new ChessPawn(true));
					chessboard[i][7 - j].setOccupant(new ChessPawn(true));
					chessboard[7 - i][j].setOccupant(new ChessPawn(false));
					chessboard[7 - i][7 - j].setOccupant(new ChessPawn(false));
					continue;
					
				}
				
				if (j == 0) {
					
					chessboard[i][j].setOccupant(new ChessRook(true));
					chessboard[i][7 - j].setOccupant(new ChessRook(true));
					chessboard[7 - i][j].setOccupant(new ChessRook(false));
					chessboard[7 - i][7 - j].setOccupant(new ChessRook(false));
					
				} else if (j == 1) {
					
					chessboard[i][j].setOccupant(new ChessKnight(true));
					chessboard[i][7 - j].setOccupant(new ChessKnight(true));
					chessboard[7 - i][j].setOccupant(new ChessKnight(false));
					chessboard[7 - i][7 - j].setOccupant(new ChessKnight(false));
					
				} else if (j == 2) {
					
					chessboard[i][j].setOccupant(new ChessBishop(true));
					chessboard[i][7 - j].setOccupant(new ChessBishop(true));
					chessboard[7 - i][j].setOccupant(new ChessBishop(false));
					chessboard[7 - 1][7 - j].setOccupant(new ChessBishop(false));
					
				} else if (j == 3) {
					
					// Fuck it, too high to think about where to put kings/queens.
					
					// TODO
					
				}
				
			}
			
		}
		
	}

	public Mobile getWhitePlayer() {
		return whitePlayer;
	}

	public void setWhitePlayer(Mobile whitePlayer) {
		this.whitePlayer = whitePlayer;
	}

	public Mobile getBlackPlayer() {
		return blackPlayer;
	}

	public void setBlackPlayer(Mobile blackPlayer) {
		this.blackPlayer = blackPlayer;
	}

	public ChessboardSquare[][] getChessboard() {
		return chessboard;
	}

	public void setChessboard(ChessboardSquare[][] chessboard) {
		this.chessboard = chessboard;
	}

	public boolean isWhiteTurn() {
		return whiteTurn;
	}

	public void setWhiteTurn(boolean whiteTurn) {
		this.whiteTurn = whiteTurn;
	}

	public boolean isPromotable() {
		return isPromotable;
	}

	public void setPromotable(boolean isPromotable) {
		this.isPromotable = isPromotable;
	}
	
}
