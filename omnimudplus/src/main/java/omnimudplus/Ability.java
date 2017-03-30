package omnimudplus;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class Ability {
	
	private final LockObject messageLock = new LockObject();
	
	private String name = "New Ability";
	
	/* In order:
	 * First person,
	 * second person,
	 * third person singular,
	 * third person plural,
	 * first person reflexive,
	 * third person reflexive
	*/
	
	private String m1p = "<message needed>";
	
	private String m2p = "<message needed>";
	
	private String m3ps = "<message needed>";
	
	private String m3pp = "<message needed>";
	
	private String m1pr = "<message needed>";
	
	private String m3pr = "<message needed>";
	
	private final LockObject actionTimeLock = new LockObject();
	
	// How long it takes to apply the effects of the ability.
	
	private int actionTime;
	
	private final LockObject rangeLock = new LockObject();
	
	// How far away from a target can it be used? 1 is adjacent - a range of 0 is self-only.
	
	private int range;
	
	private final LockObject powerCostLock = new LockObject();
	
	// How much does it cost to use?
	
	private int powerCost;
	
	private ArrayList<Effect> effects = new ArrayList<Effect>();
	
	/* What the ability needs to function. This can be left null for some things
	 *  - others need weapons, wands, etc etc. and can't just be done on the fly.
	 */
	
	private LinkedHashSet<Entity> dependencies = null;
	
	/* This ability only functions in certain terrain. This can be left null to
	 * specify any, or a list can be provided.
	 */

	private LinkedHashSet<Terrain> terrains = null;
	
	/* Can the ability only be performed outdoors? */
	
	private boolean outdoors = false;
	
	/* On the overworld? */
	
	private boolean overworld = false;
	
	public Ability() {
		
	}
	
	public Ability(String name, String m1p, String m2p, String m3ps, String m3pp, String m1pr, String m3pr,
			int actionTime, int range, int powerCost, ArrayList<Effect> effects) {
		
		this.name = name;
		this.m1p = m1p;
		this.m2p = m2p;
		this.m3ps = m3ps;
		this.m3pp = m3pp;
		this.m1pr = m1pr;
		this.m3pr = m3pr;
		this.actionTime = actionTime;
		this.range = range;
		this.powerCost = powerCost;
		this.effects = effects;
		
	}
	
	public String getName() {
		
		synchronized (messageLock) {
			
			return name;
			
		}
		
	}
	
	public String get1P() {
		
		synchronized (messageLock) {
			
			return m1p;
			
		}
		
	}
	
	public String get2P() {
		
		synchronized (messageLock) {
			
			return m2p;
			
		}
		
	}
	
	public String get3PS() {
		
		synchronized (messageLock) {
			
			return m3ps;
			
		}
		
	}
	
	public String get3PP() {
		
		synchronized (messageLock) {
			
			return m3pp;
			
		}
		
	}
	
	public String get1PR() {
		
		synchronized (messageLock) {
			
			return m1pr;
			
		}
		
	}
	
	public String get3PR() {
		
		synchronized (messageLock) {
			
			return m3pr;
			
		}
		
	}
	
	public int getActionTime() {
		
		synchronized (actionTimeLock) {
			
			return actionTime;
			
		}
		
	}
	
	public int getRange() {
		
		synchronized (rangeLock) {
			
			return range;
			
		}
		
	}
	
	public int getPowerCost() {
		
		synchronized (powerCostLock) {
			
			return powerCost;
			
		}
		
	}
	
	public void addEffect(Effect effect) {
		
		synchronized (effects) {
			
			effects.add(effect);
			
		}
		
	}
	
	public void removeEffect(Effect effect) {
		
		synchronized (effects) {
			
			effects.remove(effect);
			
		}
		
	}
	
	public void removeEffect(int index) {
		
		synchronized (effects) {
			
			effects.remove(index);
			
		}
		
	}
	
	public LinkedHashSet<Entity> getDependencies() {
		
		synchronized (dependencies) {
			
			return dependencies;
			
		}
		
	}
	
	public LinkedHashSet<Terrain> getTerrains() {
		
		synchronized (terrains) {
			
			return terrains;
			
		}
		
	}
	
	public boolean isOutdoors() {
		
		return outdoors;
		
	}
	
	public boolean isOverworld() {
		
		return overworld;
		
	}
	
	public String formatAbilityMessage(Mobile mobile, Mobile tar, String message) {
		
		String tempstring = message;
		
		Pronouns caster = mobile.getPronouns();
		Pronouns target = tar.getPronouns();
		
		// Proper names.
		
		tempstring = tempstring.replaceAll("<caster>", mobile.getShortDesc());
		tempstring = tempstring.replaceAll("<target>", tar.getShortDesc());
		
		// Subject pronouns.
		
		tempstring = tempstring.replaceAll("<castersubj>", caster.getSubject());
		tempstring = tempstring.replaceAll("<targetsubj>", target.getSubject());
		
		// Object pronouns.
		
		tempstring = tempstring.replaceAll("<casterobj>", caster.getObject());
		tempstring = tempstring.replaceAll("<targetobj>", target.getObject());
		
		// Possessive adjectives.
		
		tempstring = tempstring.replaceAll("<casterpossadj>", caster.getPossAdj());
		tempstring = tempstring.replaceAll("<targetpossadj>", target.getPossAdj());
		
		// Possessive pronouns.
		
		tempstring = tempstring.replaceAll("<casterposspro>", caster.getPossPro());
		tempstring = tempstring.replaceAll("<targetposspro>", target.getPossPro());
		
		// Reflexive pronouns.
		
		tempstring = tempstring.replaceAll("<casterreflexive>", caster.getReflexive());
		tempstring = tempstring.replaceAll("<targetreflexive>", target.getReflexive());
		
		return tempstring;
		
	}

}
