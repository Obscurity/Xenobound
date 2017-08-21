package omnimudplus;

import omnimudplus.Account.AccountGenParser;
import omnimudplus.Account.AccountParser;

public class Parser {
	
	public static void parse(String input, ConnectNode cn) {
		
		ConnectionState connState = cn.getConnectionState();
		
		String command[] = input.split("\\s+");
		
		if (connState == ConnectionState.CONNECTED) {
			
			ConnectionParser.parse(command, cn);
			
		} else if (connState == ConnectionState.ACCOUNTGEN) {
			
			try {
			
				AccountGenParser.parse(command, cn);
			
			} catch (Exception e) {
				
				e.printStackTrace();
				
			}
			
		} else if (connState == ConnectionState.CHARGEN) {
			
			ChargenParser.parse(command, cn);
			
		} else if (connState == ConnectionState.ACCOUNT) {
			
			try {
			
				AccountParser.parse(command, cn);
			
			} catch (Exception e) {
				
				e.printStackTrace();
				
			}
			
		} else if (connState == ConnectionState.IN_GAME) {
			
			IngameParser.parse(command, cn);
			
		} else {
			
			//TODO
			
		}
		
	}

}