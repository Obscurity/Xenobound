package omnimudplus;

public enum Pronouns {

	HE("he", "him", "his", "his", "himself", false),
	SHE("she", "her", "her", "hers", "herself", false),
	THEY("they", "them", "their", "theirs", "themselves", true),
	EY("ey", "em", "eir", "eirs", "emself", false),
	IT("it", "it", "its", "its", "itself", false);
	
	private String subject;
	private String object;
	private String possAdj;
	private String possPro;
	private String reflexive;
	private boolean plural;
	
	private Pronouns(String subject, String object, String possAdj, String possPro, String reflexive, boolean plural) {
		
		this.subject   = subject;
		this.object    = object;
		this.possAdj   = possAdj;
		this.possPro   = possPro;
		this.reflexive = reflexive;
		this.plural    = plural;
		
	}
	
	public String getSubject() {
		
		return subject;
		
	}
	
	public String getObject() {
		
		return object;
		
	}
	
	public String getPossAdj() {
		
		return possAdj;
		
	}
	
	public String getPossPro() {
		
		return possPro;
		
	}
	
	public String getReflexive() {
		
		return reflexive;
		
	}
	
	public boolean getPlural() {
		
		return plural;
		
	}
	
}
