package omnimudplus;

import java.util.Random;

public enum Attack {
	
	PUNCH("punch",
			// 1p
			"You punch <target> very hard, sending <targetobj> staggering.",
			// 2p
			"<caster> punches you very hard, sending you staggering.",
			// 3p singular
			"<caster> punches <target> very hard, sending <targetobj> staggering.",
			// 3p plural
			"<caster> punches <target> very hard, sending <targetobj> staggering.",
			// 1p reflexive
			"You punch yourself very hard.",
			// 3p reflexive
			"<caster> punches <casterreflexive> very hard.",
			// Delay, Range, EffectType, Cost, Damage, Variance
			2000, 1, EffectType.BLUNT, 0, 2, 2),
	
	FIREBALL("fireball",
			// 1p
			"You hurl a fireball at <target>, burning <targetobj>.",
			// 2p
			"<caster> hurls a fireball at you, burning you.",
			// 3p singular
			"<caster> hurls a fireball at <target>, burning <targetobj>.",
			// 3p plural
			"<caster> hurls a fireball at <target>, burning <targetobj>.",
			// 1p reflexive
			"You hurl a fireball at yourself.",
			// 3p reflexive
			"<caster> hurls a fireball at <casterreflexive>.",
			// Delay, Range, EffectType, Cost, Damage, Variance
			4000, 10, EffectType.HEAT, 1, 1, 2);
	
	private final LockObject messageLock = new LockObject();
	
	private String name;
	
	private String firstPersonMessage;
	
	private String secondPersonMessage;
	
	private String thirdPersonSingularMessage;
	
	private String thirdPersonPluralMessage;
	
	private String firstPersonReflexive;
	
	private String thirdPersonReflexive;
	
	private final LockObject actionTimeLock = new LockObject();
	
	private int actionTime;
	
	private final LockObject rangeLock = new LockObject();
	
	private int range;
	
	private final LockObject damageTypeLock = new LockObject();
	
	private EffectType damageType;
	
	private final LockObject powerCostLock = new LockObject();
	
	private int powerCost;
	
	private final LockObject damageRollLock = new LockObject();
	
	private int damage;
	
	private int variance;
	
	private Attack(String name, String firstPersonMessage, String secondPersonMessage,
			String thirdPersonSingularMessage, String thirdPersonPluralMessage,
			String firstPersonReflexive, String thirdPersonReflexive,
			int actionTime, int range, EffectType damageType, int powerCost,
			int damage, int variance) {
		
		this.name = name;
		this.firstPersonMessage = firstPersonMessage;
		this.secondPersonMessage = secondPersonMessage;
		this.thirdPersonSingularMessage = thirdPersonSingularMessage;
		this.thirdPersonPluralMessage = thirdPersonPluralMessage;
		this.firstPersonReflexive = firstPersonReflexive;
		this.thirdPersonReflexive = thirdPersonReflexive;
		this.actionTime = actionTime;
		this.range = range;
		this.damageType = damageType;
		this.powerCost = powerCost;
		this.damage = damage;
		this.variance = variance;
		
	}
	
	public String getName() {
		
		synchronized (messageLock) {
			
			return name;
			
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
	
	public EffectType getEffectType() {
		
		synchronized (damageTypeLock) {
		
			return damageType;
		
		}
		
	}
	
	public int getDamage() {
		
		synchronized(damageRollLock) {
			
			return damage;
			
		}
		
	}
	
	public int getVariance() {
		
		synchronized(damageRollLock) {
			
			return variance;
			
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
		
		ConnectNode cn = mobile.getConnectNode();
		
		if (!mobile.hasActionBalance()) {
			
			if (cn != null) {
			
				cn.println("You need to wait before performing another action.");
			
			}
			
			return;
			
		}
		
		Random rand = new Random();
		
		Zone zone = mobile.getZone();
		
		int tryrange = GameFunction.getApproximateDistance(mobile, target.getLocation());
		
		if (tryrange > range) {
			
			if (cn != null) {
			
				cn.println("Your target is not within range. (must be within <boldred>" + range + "<white> meters)");
			
			}
			
			return;
			
		}
		
		cn.getShell().takeActionBalance(cn, getActionTime(), true);
		
		cn.getShell().spendPower(getPowerCost());
		
		target.takeDamage(damage + rand.nextInt(getVariance()));
		
		for (Mobile other : mobile.getLocation().getMobiles()) {
			
			ConnectNode witness = other.getConnectNode();
			
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
