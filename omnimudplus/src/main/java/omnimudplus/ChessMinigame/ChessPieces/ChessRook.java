package omnimudplus.ChessMinigame.ChessPieces;

import omnimudplus.ChessMinigame.ChessMoveset;

public class ChessRook extends ChessPiece {
	
	public ChessRook(boolean isWhite) {
		
		this.setName("Tower");
		this.setWhite(isWhite);
		this.setMoveset(ChessMoveset.ROOK);
		this.setToken('T');
		
	}

}
