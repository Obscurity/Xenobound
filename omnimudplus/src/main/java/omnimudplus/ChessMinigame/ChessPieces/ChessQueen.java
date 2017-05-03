package omnimudplus.ChessMinigame.ChessPieces;

import omnimudplus.ChessMinigame.ChessMoveset;

public class ChessQueen extends ChessPiece {

	public ChessQueen(boolean isWhite) {
		
		this.setName("Mother");
		this.setWhite(isWhite);
		this.setMoveset(ChessMoveset.QUEEN);
		this.setToken('S');
		
	}
	
}
