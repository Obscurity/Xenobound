package omnimudplus;

import java.util.Iterator;
import java.util.Random;

import omnimudplus.Effects.Effect;
import omnimudplus.Effects.EffectType;
import omnimudplus.Effects.Magnitude;
import omnimudplus.Effects.Mechanic;
import omnimudplus.Entities.Mobile;
import omnimudplus.Entities.Pronouns;
import omnimudplus.Entities.Shell;

public enum Attack {
	
	// Reference for the strings
	// Actual Names
	// <caster>, <target>
	// Subject pronouns (He/She/They etc)
	// <csubj>, <tsubj>
	// Object pronouns (Him/Her/Them etc)
	// <cobj>, <tobj>
	// Possessive Adjectives (His/Her/Their etc)
	// <cpadj>, <tpadj>
	// Possessive Pronouns (His/Hers/Theirs etc)
	// <cppro>, <tppro>
	// Reflexive Pronouns (Himself/Herself/Themself etc)
	// <crefl>, <trefl>
	
	PUNCH("punch",
			// 1p
			"You punch <target> very hard, sending <tobj> staggering.",
			// 2p
			"<caster> punches you very hard, sending you staggering.",
			// 3p singular
			"<caster> punches <target> very hard, sending <tobj> staggering.",
			// 3p plural
			"<caster> punches <target> very hard, sending <tobj> staggering.",
			// 1p reflexive
			"You punch yourself very hard.",
			// 3p reflexive
			"<caster> punches <crefl> very hard.",
			// Delay, Ranged, Cost, Effect
			2000, false, 0,
					new Effect(EffectType.PHYSICAL,
					new Mechanic[] {},
					new Magnitude(),
					new Magnitude())
			),
	
	FIREBALL("fireball",
			// 1p
			"You hurl a fireball at <target>, burning <tobj>.",
			// 2p
			"<caster> hurls a fireball at you, burning you.",
			// 3p singular
			"<caster> hurls a fireball at <target>, burning <tobj>.",
			// 3p plural
			"<caster> hurls a fireball at <target>, burning <tobj>.",
			// 1p reflexive
			"You hurl a fireball at yourself.",
			// 3p reflexive
			"<caster> hurls a fireball at <crefl>.",
			// Delay, Ranged, Cost, Effect
			4000, true, 1, new Effect());
	
	private final LockObject messageLock = new LockObject();
	
	private String name;
	
	private String firstPersonMessage;
	
	private String secondPersonMessage;
	
	private String thirdPersonSingularMessage;
	
	private String thirdPersonPluralMessage;
	
	private String firstPersonReflexive;
	
	private String thirdPersonReflexive;
	
	private final LockObject attackLock = new LockObject();
	
	private int actionTime;
	
	private boolean ranged;
	
	private int nrsCost;
	
	private Effect effect;
	
	private Attack(String name, String firstPersonMessage, String secondPersonMessage,
			String thirdPersonSingularMessage, String thirdPersonPluralMessage,
			String firstPersonReflexive, String thirdPersonReflexive,
			int actionTime, boolean ranged, int nrsCost, Effect effect) {
		
		this.name = name;
		this.firstPersonMessage = firstPersonMessage;
		this.secondPersonMessage = secondPersonMessage;
		this.thirdPersonSingularMessage = thirdPersonSingularMessage;
		this.thirdPersonPluralMessage = thirdPersonPluralMessage;
		this.firstPersonReflexive = firstPersonReflexive;
		this.thirdPersonReflexive = thirdPersonReflexive;
		this.actionTime = actionTime;
		this.ranged = ranged;
		this.nrsCost = nrsCost;
		this.effect = effect;
		
	}
	
	public String getName() {
		
		synchronized (messageLock) {
			
			return name;
			
		}
		
	}
	
	public int getActionTime() {
		
		synchronized (attackLock) {
			
			return actionTime;
			
		}
		
	}
	
	public boolean getRanged() {
		
		synchronized (attackLock) {
			
			return ranged;
			
		}
		
	}
	
	public int getNrsCost() {
		
		synchronized (attackLock) {
			
			return nrsCost;
			
		}
		
	}
	
	public String formatAttackMessage(Mobile mobile, Mobile tar, String message) {
		
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
	
	public void attackUse(Mobile mobile, Mobile target) {
		
		ConnectNode cn = null;
		
		if (mobile instanceof Shell) {
			
			cn = ((Shell)mobile).getConnectNode();
			
		}
		
		if (!mobile.hasActionBalance()) {
			
			if (cn != null) {
			
				cn.println("You need to wait before performing another action.");
			
			}
			
			return;
			
		}
		
		Random rand = new Random();
		
		if (ranged) {
			
		} else {
			
			if (mobile.getRoom() != target.getRoom()) {
				
				cn.println("Your target is not within range.");
				return;
				
			}
			
		}
		
		cn.getShell().takeActionBalance(cn, getActionTime(), true);
		
		cn.getShell().spendNrs(getNrsCost());
		
		target.applyEffect(effect);
		
		Iterator<Shell> shells = mobile.getRoom().getShells();
		
		while (shells.hasNext()) {
			
			ConnectNode witness = shells.next().getConnectNode();
			
			if (witness == cn) {
				
				if (witness.getShell() == target) {
					
					witness.println(formatAttackMessage(mobile, target, firstPersonReflexive));
					
				} else {
				
					witness.println(formatAttackMessage(mobile, target, firstPersonMessage));
				
				}
				
			} else if (witness.getShell() == target) {
				
				witness.println(formatAttackMessage(mobile, target, secondPersonMessage));
				witness.putPrompt();
				
			} else {
				
				Pronouns targetpronouns = target.getPronouns();
				
				if (mobile == target) {
				
					witness.println(formatAttackMessage(mobile, target, thirdPersonReflexive));
					witness.putPrompt();
					
				} else if (!targetpronouns.getPlural()) {
					
					witness.println(formatAttackMessage(mobile, target, thirdPersonSingularMessage));
					witness.putPrompt();
					
				} else {
					
					witness.println(formatAttackMessage(mobile, target, thirdPersonPluralMessage));
					witness.putPrompt();
					
				}
				
			}
			
		}
		
	}

}
