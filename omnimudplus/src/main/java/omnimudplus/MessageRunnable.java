package omnimudplus;

import java.nio.channels.NonWritableChannelException;

public class MessageRunnable implements Runnable {
	
	private ConnectNode cn = null;
	private String message = "";
	
	public MessageRunnable(ConnectNode cn, String message) {
		
		this.cn = cn;
		this.message = message;
		
	}
	
	public void run() {
		
		try {
			
			if (cn != null) {
		
				cn.println(message);
				cn.setActionMessage(null);
				cn.putPrompt();
				
			}
			
		} catch (NonWritableChannelException e) {
			
			e.printStackTrace();
			cn.cleanup();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			cn.println("You have caused a general exception (" + e.getMessage() + "). Please don't do that again.");
			cn.putPrompt();
			
		}
		
	}

}
