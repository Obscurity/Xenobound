package omnimudplus.ChessMinigame;

import omnimudplus.ChessMinigame.ChessPieces.ChessPiece;

public class ChessboardSquare {

	boolean isWhite;
	
	ChessPiece occupant;
	
	public ChessboardSquare() {
		
		isWhite = true;
		occupant = null;
		
	}
	
	public ChessboardSquare(boolean isWhite) {
		
		this.isWhite = isWhite;
		
	}
	
	public boolean isWhite() {
		
		return isWhite;
		
	}
	
	public void setOccupant(ChessPiece occupant) {
		
		this.occupant = occupant;
		
	}
	
	public ChessPiece getOccupant() {
		
		return occupant;
		
	}
	
}
