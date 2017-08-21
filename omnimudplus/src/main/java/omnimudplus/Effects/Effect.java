package omnimudplus.Effects;

import omnimudplus.LockObject;

// This is the standard effect class. It deals with simple, self-targeted effects.

public class Effect {
	
	private final LockObject intLock = new LockObject();
	
	// What sort of effect is this? This can refer to damage type, magical source, etc.
	
	private EffectType effectType;
	
	// Okay, but what does this thing actually do?
	
	private Mechanic[] mechanics;
	
	// What is the magnitude of its duration?
	
	private Magnitude duration;
	
	// What is the magnitude of its value?
	
	private Magnitude magnitude;
	
	public Effect() {
		
		this.effectType = EffectType.NONE;
		
		this.mechanics = new Mechanic[] {Mechanic.DAMAGE};
		
	}
	
	public Effect(EffectType effectType, Mechanic[] mechanics,
			Magnitude duration, Magnitude magnitude) {
		
		this.effectType = effectType;
		
		this.mechanics = mechanics;
		
		this.duration = duration;
		
		this.magnitude = magnitude;
		
	}
	
	public Mechanic[] getMechanics() {
		
		return mechanics;
		
	}
	
	public EffectType getEffectType() {
		
		synchronized (intLock) {
		
			return effectType;
		
		}
		
	}

	public Magnitude getDuration() {
		return duration;
	}

	public Magnitude getMagnitude() {
		return magnitude;
	}

}
