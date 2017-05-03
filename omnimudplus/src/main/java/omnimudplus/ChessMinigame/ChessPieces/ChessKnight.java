package omnimudplus.ChessMinigame.ChessPieces;

import omnimudplus.ChessMinigame.ChessMoveset;

public class ChessKnight extends ChessPiece {
	
	public ChessKnight(boolean isWhite) {
		
		this.setName("Guard");
		this.setWhite(isWhite);
		this.setMoveset(ChessMoveset.KNIGHT);
		this.setToken('G');
		
	}

}
