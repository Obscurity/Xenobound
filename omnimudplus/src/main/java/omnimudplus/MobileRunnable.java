package omnimudplus;

public class MobileRunnable implements Runnable {
	
	private Mobile mobile                = null;
	private String command               = "";

	public MobileRunnable(Mobile mobile, String command) {

        this.mobile = mobile;
        this.command = command;

	}
	
	public void run() {
		
		try {
			
			if (command.equals("regen")) {
				
				if (mobile.hasRegenBalance()) {
					
					mobile.healDamage(1);
					
				}
				
			} else if (command.equals("powerRegen")) {
				
				if (mobile.hasPowerRegenBalance()) {
					
					mobile.gainPower(1);
					
				}
				
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
	}

}
