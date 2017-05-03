package omnimudplus.ChessMinigame.ChessPieces;

import omnimudplus.ChessMinigame.ChessMoveset;

public class ChessBishop extends ChessPiece {
	
	public ChessBishop(boolean isWhite) {
		
		this.setName("Advisor");
		this.setWhite(isWhite);
		this.setMoveset(ChessMoveset.BISHOP);
		this.setToken('A');
		
	}

}
