package omnimudplus;

import java.io.Serializable;

public class ColorScheme implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Color background;
	private Color foreground;
	private Color mobiles;
	private Color players;
	private Color exits;
	private Color speech;
	private Color highlight;
	
	private boolean monochrome = false;
	
	public ColorScheme() {
		
		background = Color.BLACK;
		foreground = Color.WHITE;
		mobiles = Color.YELLOW;
		players = Color.CYAN;
		exits = Color.MAGENTA;
		speech = Color.BOLDCYAN;
		highlight = Color.BOLDRED;
		monochrome = false;
		
	}
	
	public String getBackground() {
		
		if (monochrome)
			return "";
		
		return background.getBackgroundCode();
		
	}
	
	public String getForeground() {
		
		if (monochrome)
			return "";
		
		return foreground.getHighCode();
		
	}
	
	public String getExits() {
		
		if (monochrome)
			return "";
		
		return exits.getHighCode();
		
	}
	
	public String getSpeech() {
		
		if (monochrome)
			return "";
		
		return speech.getHighCode();
		
	}
	
	public String getHighlight() {
		
		if (monochrome)
			return "";
		
		return highlight.getHighCode();
		
	}
	
	public String getMobiles() {
		
		if (monochrome)
			return "";
		
		return mobiles.getHighCode();
		
	}
	
	public String getPlayers() {
		
		if (monochrome)
			return "";
		
		return players.getHighCode();
		
	}

}
