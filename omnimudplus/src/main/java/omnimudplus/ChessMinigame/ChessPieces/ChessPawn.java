package omnimudplus.ChessMinigame.ChessPieces;

import omnimudplus.ChessMinigame.ChessMoveset;

public class ChessPawn extends ChessPiece {
	
	public ChessPawn(boolean isWhite) {
		
		this.setName("Laborer");
		this.setWhite(isWhite);
		this.setMoveset(ChessMoveset.PAWN);
		this.setToken('L');
		
	}

}
