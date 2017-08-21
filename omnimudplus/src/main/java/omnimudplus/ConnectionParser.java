package omnimudplus;

import java.util.Arrays;

import com.orientechnologies.orient.core.record.impl.ODocument;

import omnimudplus.Account.Account;
import omnimudplus.Account.TempAccount;
import omnimudplus.Database.Database;

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
				
				if (cn.getAccount() == null) {
					
					ODocument accountDoc = Database.retrieveAccount(command[0]);
					
					if (accountDoc != null) {
						
						TempAccount account = new TempAccount(command[0]);
						
						account.setPotential(accountDoc.field("potential"));
						
						account.setPassword(accountDoc.field("password"));
						
						account.setSalt(accountDoc.field("salt"));
						
						cn.setAccount(account);
						
						
					} else {
					
						cn.println("No existing account by that name.\n");
						return;
						
					}
					
				} else if (cn.getAccount() instanceof TempAccount) {
					
					TempAccount account = (TempAccount)cn.getAccount();
					
					byte[] salt = account.getSalt();
					
					byte[] password = Database.getEncryptedPassword(command[0], salt);
					
					byte[] storedpass = account.getPassword();
					
					if (Arrays.equals(password, storedpass)) {
						
						cn.println("Password accepted.\n");
						
						cn.setConnectState(ConnectionState.ACCOUNT);
						
						Account acc =
								Account.reconstructAccount(
										Database.retrieveAccount(account.getName())
										);
						
						cn.setAccount(acc);
						
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
			
			/* switch (command[0].toLowerCase()) {
				default: cn.println("Invalid selection.\n"); break;
			} */
			
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
		
		Omnimud.cleanup(cn);
		
	}

}
