package omnimudplus;

import java.util.LinkedHashSet;

import omnimudplus.Entities.Mobile;

public class AccountParser {
	
	public static void parse(String[] command, ConnectNode cn)
		throws Exception {
		
		// TODO
		
		try {
		
			int option = Integer.parseInt(command[0]);
		
			switch (option) {
			case 1: goToChargen(cn); break;
			case 2: cn.println("Goodbye!"); cn.cleanup(); break;
			}
			
		} catch (NumberFormatException e) {
			
			Account account = cn.getAccount();
			
			LinkedHashSet<Mobile> characters = account.getCharacters();
			
			Mobile shell = null;
			
			for (Mobile character : characters) {
				
				if (command[0].toLowerCase().equals(character.getName().toLowerCase())) {
					
					shell = character;
					break;
					
				}
				
			}
			
			if (shell == null) {
				
				cn.println("Invalid character.\n");
				
			} else {
				
				shell.setRoom(Omnimud.areatest.getOrigin());
				
				cn.setShell(shell);
				
				cn.setConnectState(ConnectionState.IN_GAME);
				
				cn.println("Now entering the game. Welcome!\n");
				
				IngameParser.look(shell);
				
			}
			
		}
		
	}
	
	public static void goToChargen(ConnectNode cn)
			throws Exception {
		
		// cn.setConnectState(ConnectionState.CHARGEN);
		
		Account account = cn.getAccount();
		
		String[] aliases = {"test", "person", account.getName().toLowerCase()};
		
		LinkedHashSet<Mobile> characters = account.getCharacters();
		
		if (characters.size() == 0) {
		
			characters.add(new Mobile(null, account.getName(), aliases, 
				account.getName(), account.getName() + " stands here.",
				"Not much to say.", 1, Material.UNDEFINED));
		
			AccountService.storeAccount(account);
			
			cn.println("Test shell created.");
		
		} else {
			
			cn.println("Not implemented yet!\n");
			
		}
		
	}

}
