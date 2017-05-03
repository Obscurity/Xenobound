package omnimudplus;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.concurrent.ScheduledFuture;

import omnimudplus.Entities.Mobile;

public class ConnectNode {
	
	private ConnectionState connState           = ConnectionState.CONNECTED;
	private Account account                     = null;
	private Mobile shell                        = null;
	private SocketChannel client                = null;
	private ColorScheme colorscheme             = new ColorScheme();
	private ScheduledFuture<MessageRunnable> moveMessage;
	private ScheduledFuture<MessageRunnable> actionMessage;
	
	public ConnectNode(Mobile shell, SocketChannel client) {
		
		this.shell = shell;
		this.client = client;
		
		shell.setRoom(null);
		
	}
	
	public ConnectNode(SocketChannel client) {
		
		this.client = client;
		
	}
	
	public ConnectNode(Mobile shell, SocketChannel client, Room room) {
		
		this.shell = shell;
		this.client = client;
		
		shell.setRoom(room);
		shell.setArea(room.getArea());
		
	}
	
	public ColorScheme getColorScheme() {
		
		return colorscheme;
		
	}
	
	public void setShell(Mobile shell) {
		
		this.shell = shell;
		shell.setConnectNode(this);
		
	}
	
	public Mobile getShell() {
		
		return shell;
		
	}
	
	public void setClient(SocketChannel client) {
		
		this.client = client;
		
	}
	
	public SocketChannel getClient() {
		
		return client;
		
	}
	
	public void color(Color color) {
		
		ByteBuffer buffer = ByteBuffer.wrap(color.getHighCode().getBytes());
		
		try {
		
			client.write(buffer);
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	public void putPrompt() {
		
		StringBuilder commands = new StringBuilder();
		
		commands.append(colorscheme.getForeground());
		
		if (connState == ConnectionState.CONNECTED) {
			
			if (account == null) {
			
				commands.append("Please enter your option: ");
			
			} else {
				
				commands.append("Enter your password: ");
				
			}
			
		} else if (connState == ConnectionState.ACCOUNTGEN) {
			
			if (account == null) {
				
				commands.append("Please enter your desired account name: ");
				
			} else if (!AccountService.hasPassword(account)) {
				
				commands.append("Enter a password for your account: ");
				
			} else {
				
				commands.append("Repeat the password to confirm: ");
				
			}
			
		} else if (connState == ConnectionState.ACCOUNT) {
			
			commands.append(GameFunction.displayAccount(account));
			
			commands.append("Please enter your option: ");
			
		} else if (connState == ConnectionState.IN_GAME) {
		
			commands.append(ColorSpectrum.HEALTHSPECTRUM.getPromptHue(shell.getHealth(), shell.getMaxHealth()));
			
			commands.append(shell.getHealth());
			
			commands.append(colorscheme.getForeground());
			
			commands.append("/");
			
			commands.append(ColorSpectrum.HEALTHSPECTRUM.getMax());
			
			commands.append(shell.getMaxHealth());
			
			commands.append("h ");
			
			commands.append(ColorSpectrum.POWERSPECTRUM.getPromptHue(shell.getNrs(), shell.getMaxNrs()));
			
			commands.append(shell.getNrs());
			
			commands.append(colorscheme.getForeground());
			
			commands.append("/");
			
			commands.append(ColorSpectrum.POWERSPECTRUM.getMax());
			
			commands.append(shell.getMaxNrs());
			
			commands.append("n");
			
			commands.append(colorscheme.getForeground());
			
			commands.append(" [");
			
			if (shell.hasActionBalance()) {
				
				commands.append('a');
				
			} else {
				
				commands.append('-');
				
			}
			
			if (shell.hasMoveBalance()) {
				
				commands.append('m');
				
			} else {
				
				commands.append('-');
			}
			
			commands.append(colorscheme.getForeground());
			
			commands.append("]");
		
		}
		
		commands.append("\u00FF\u00F9");
		
		ByteBuffer buffer = ByteBuffer.wrap(commands.toString().getBytes());
		
		try {
			
			client.write(buffer);
		
		} catch (IOException e) {
			
			this.cleanup();
			
		}
		
	}
	
	public void print(ByteBuffer buffer) {
		
		try {
			
			client.write(buffer);
		
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	public void print(String input) {
		
		String newinput = input;
		
		for (Color c : Color.values()) {
			
			if (newinput.contains("<") && newinput.contains(">")) {
			
				newinput = newinput.replace("<" + c.getName() + ">", c.getHighCode());
				
			} else {
				
				break;
				
			}
			
		}
		
		newinput += Color.WHITE.getHighCode();
		
		ByteBuffer buffer = ByteBuffer.wrap(newinput.getBytes());
		
		/* for (byte b: input.getBytes()) {
		
			buffer.put(b);
		
		} */
		
		try {
		
			client.write(buffer);
		
		} catch (ClosedByInterruptException e) {
			
			System.out.println(shell.getName() + " > Interrupt caught.");
			e.printStackTrace();
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	public void println() {
		
		String input = "\n";
		
		print(input);
		
	}
	
	public void println(String input) {
		
		input = input + "\n";
		
		print(input);
		
	}
	
	public void setActionMessage(ScheduledFuture<MessageRunnable> mr) {
		
		if (actionMessage != null) {
			
			actionMessage.cancel(false);
			actionMessage = null;
			
		}
		
		actionMessage = mr;
		
	}
	
	public void setMoveMessage(ScheduledFuture<MessageRunnable> mr) {
		
		if (moveMessage != null) {
			
			moveMessage.cancel(false);
			moveMessage = null;
			
		}
		
		moveMessage = mr;
		
	}
	
	public void cleanup() {
		
		System.out.println("Cleaning up " + account.getName() + " : " + shell.getName());
		
		try {
		
			AccountService.storeAccount(account);
			
		} catch (Exception e) {
			
		}
		
		if (connState == ConnectionState.IN_GAME) {
		
			if (shell.getRoom() != null) {
				
				shell.setRoom(null);
				
			}
			
			if (shell.isTraveling()) {
				
				shell.travelStop();
				
			}
			
			shell.stopRegen();
			
			try {
			
				this.client.close();
				
			} catch (IOException e) {
				
				e.printStackTrace();
				
			}
			
			shell.setArea(null);
			
			synchronized (Omnimud.playerlock) {
				
				Omnimud.players.remove(this);
				Omnimud.playershells.remove(shell);
			
			}
		
		} else {
			
			try {
				
				this.client.close();
				
			} catch (IOException e) {
				
				e.printStackTrace();
				
			}
			
		}
		
	}
	
	public void disconnect() {
		
	}

	public ConnectionState getConnectionState() {
		return connState;
	}

	public void setConnectState(ConnectionState connState) {
		this.connState = connState;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

}
