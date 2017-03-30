package omnimudplus;

public enum ColorSpectrum {

	HEALTHSPECTRUM(new Color[] {Color.DARKRED, Color.BOLDRED, Color.BOLDYELLOW, Color.BOLDGREEN}),
	POWERSPECTRUM(new Color[] {Color.BLUEVIOLET, Color.MAGENTA, Color.MEDIUMPURPLE5, Color.BOLDMAGENTA});
	
	private Color[] colors;
	
	private ColorSpectrum(Color[] colors) {
		
		this.colors = colors;
		
	}
	
	public String getPromptHue(double current, double max) {
		
		double fraction = (double)(100*(current/max));
		double bitpercent = 100/((double)colors.length - 1);
		double test = 0;
		
		if (current == 0) {
			
			return colors[0].getHighCode();
			
		} else {
		
			for (int i = 1; i <= colors.length - 1; i++) {
				
				test = bitpercent*i;
				
				if (test > fraction) {
					break;
				}
				
			}
			
		}
		
		return colors[(int)(test / bitpercent)].getHighCode();
		
	}
	
	public String getMax() {
		
		return colors[colors.length - 1].getHighCode();
		
	}
	
}
