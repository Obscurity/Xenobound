package omnimudplus.ChessMinigame.ChessPieces;

import omnimudplus.ChessMinigame.ChessMoveset;

public class ChessKing extends ChessPiece {

	public ChessKing(boolean isWhite) {
		
		this.setName("Boss");
		this.setWhite(isWhite);
		this.setMoveset(ChessMoveset.KING);
		this.setToken('B');
		
	}
	
}
