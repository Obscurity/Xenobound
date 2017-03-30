package omnimudplus;

import java.util.Arrays;

public class AccountGenParser {
	
	public static void parse(String[] command, ConnectNode cn)
		throws Exception {
		
		// TODO
		
		if (cn.getAccount() == null) {
			
			String name = command[0].toLowerCase();
			
			name = name.substring(0, 1).toUpperCase() + name.substring(1);
			
			if (AccountService.getAccount(name) != null) {
				
				cn.println("Account by that name already in existence.\n");
				return;
				
			}
			
			Account newaccount = new Account(name);
			
			cn.setAccount(newaccount);
			
			AccountService.storeAccount(newaccount);
			
			byte[] salt = AccountService.generateSalt();
			
			AccountService.storeSalt(newaccount, salt);
			
		} else if (!AccountService.hasPassword(cn.getAccount())) {
			
			if (command[0].length() < 4 || command[0].length() > 32) {
				
				cn.println("Provide a password between 4 and 32 characters.\n");
				return;
				
			}
			
			Account account = cn.getAccount();
			
			byte[] salt = AccountService.recoverSalt(account);
			
			byte[] password = AccountService.getEncryptedPassword(command[0], salt);
			
			System.out.println(password);
			
			AccountService.storeEncryptedPassword(account, password);
			
		} else if (AccountService.hasPassword(cn.getAccount())) {
			
			String testpass = command[0];
			
			Account account = cn.getAccount();
			
			byte[] salt = AccountService.recoverSalt(account);
			
			byte[] password = AccountService.getEncryptedPassword(command[0], salt);
			
			byte[] storedpass = AccountService.recoverEncryptedPassword(account);
			
			if (Arrays.equals(password, storedpass)) {
				
				cn.println("Account created.\n");
				
				AccountService.storeAccount(account);
				
				cn.setAccount(null);
				
				cn.println(GameFunction.connectOptions());
				
				cn.setConnectState(ConnectionState.CONNECTED);
				
			} else {
				
				cn.println("Password mismatch.\n");
				
				AccountService.removeEncryptedPassword(account);
				
			}
			
		}
		
	}

}
