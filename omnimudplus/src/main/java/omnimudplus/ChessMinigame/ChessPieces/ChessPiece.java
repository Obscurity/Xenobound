package omnimudplus.ChessMinigame.ChessPieces;

import omnimudplus.ChessMinigame.ChessMoveset;

public abstract class ChessPiece {
	
	private String name;
	private boolean isWhite;
	private char token;
	private ChessMoveset moveset;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isWhite() {
		return isWhite;
	}
	public void setWhite(boolean isWhite) {
		this.isWhite = isWhite;
	}
	public char getToken() {
		return token;
	}
	public void setToken(char token) {
		this.token = token;
	}
	public ChessMoveset getMoveset() {
		return moveset;
	}
	public void setMoveset(ChessMoveset moveset) {
		this.moveset = moveset;
	}
	
}
