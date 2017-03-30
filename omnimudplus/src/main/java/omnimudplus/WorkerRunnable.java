package omnimudplus;

import java.nio.channels.NonWritableChannelException;

public class WorkerRunnable implements Runnable {

	private ConnectNode cn               = null;
	private String command               = "";

	public WorkerRunnable(ConnectNode cn, String command) {

        this.cn = cn;
        this.command = command;

	}
	
	public void run() {
		
		try {
			
			if (cn != null) {
		
				Parser.parse(command, cn);
				
				if (cn.getClient().isOpen()) {
				
					cn.putPrompt();
				
				} else {
					
					cn.cleanup();
					
				}
			
			} else {
				
				Parser.parse(command, null);
				
			}
			
		} catch (NumberFormatException e) {
			
			cn.println("You need to provide a valid number.");
			cn.putPrompt();
			
		} catch (NonWritableChannelException e) {
			
			e.printStackTrace();
			cn.cleanup();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			cn.println("You have caused a general exception. Please don't do that again.");
			cn.putPrompt();
			
		}
		
	}

}