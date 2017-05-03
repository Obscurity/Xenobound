package omnimudplus;

import java.io.Serializable;
import java.util.LinkedHashSet;

import omnimudplus.Entities.Mobile;

public class Account implements Serializable {
	
	private String name;
	
	private static final long serialVersionUID = 1L;
	
	private LinkedHashSet<Mobile> characters = new LinkedHashSet<Mobile>();
	
	private int potential = 0;
	
	public Account(String name) {
		
		this.name = name;
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LinkedHashSet<Mobile> getCharacters() {
		return characters;
	}

	public void setCharacters(LinkedHashSet<Mobile> characters) {
		this.characters = characters;
	}

	public int getPotential() {
		return potential;
	}

	public void setPotential(int potential) {
		this.potential = potential;
	}

}
