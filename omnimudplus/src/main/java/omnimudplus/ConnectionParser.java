package omnimudplus;

import java.util.Arrays;

public class ConnectionParser {
	
	public static void parse(String[] command, ConnectNode cn) {
		
		try {
			int option = Integer.parseInt(command[0]);
			
			switch (option) {
				// Create account
				case 1: createAccount(cn); break;
				case 3: disconnect(cn); break;
				default: cn.println("Invalid option.\n"); break;
			}
			
		} catch (NumberFormatException e) {
			
			try {
				
				Account account = AccountService.getAccount(command[0]);
				
				if (account == null && cn.getAccount() == null) {
					
					cn.println("No existing account by that name.\n");
					return;
					
				} else if (cn.getAccount() == null) {
				
					cn.setAccount(account);
					return;
					
				} else if (cn.getAccount() != null) {
					
					account = cn.getAccount();
					
					byte[] salt = AccountService.recoverSalt(account);
					
					byte[] password = AccountService.getEncryptedPassword(command[0], salt);
					
					byte[] storedpass = AccountService.recoverEncryptedPassword(account);
					
					if (Arrays.equals(password, storedpass)) {
						
						cn.println("Password accepted.\n");
						
						cn.setConnectState(ConnectionState.ACCOUNT);
						
						cn.setAccount(account);
						
						return;
						
					} else {
						
						cn.println("Password incorrect.\n");
						
						cn.setAccount(null);
						
						return;
						
					}
					
				}
				
			} catch (Exception ex) {
				
				ex.printStackTrace();
				
			}
			
			switch (command[0].toLowerCase()) {
				default: cn.println("Invalid selection.\n"); break;
			}
			
		}
		
	}
	
	public static void createAccount(ConnectNode cn) {
		cn.println("\n");
		cn.setConnectState(ConnectionState.ACCOUNTGEN);
		
	}
	
	public static void connectToAccount(String[] input, ConnectNode cn) {
		
	}
	
	public static void disconnect(ConnectNode cn) {
		
		cn.println("Goodbye!");
		
		cn.cleanup();
		
	}

}
