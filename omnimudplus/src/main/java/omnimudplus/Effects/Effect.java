package omnimudplus.Effects;

import omnimudplus.LockObject;

public class Effect {
	
	private final LockObject intLock = new LockObject();
	
	// What sort of effect is this? This can refer to damage type, magical source, etc.
	
	private EffectType effectType;
	
	// Magnitude - how much damage something deals, how much health it recovers, etc etc.
	
	private int magnitude;
	
	// How many ticks does the effect last? 0 means it's one-off.
	
	private int duration;
	
	// How often it ticks, in milliseconds. 0 means no tick.
	
	private int tick;
	
	// Variance - how much the magnitude can and will vary.
	
	private int variance;
	
	// Okay, but what does it DO? Defense? Attack? Passive ability?
	
	private Mechanic[] mechanics;
	
	public Effect(EffectType effectType, int magnitude, int duration, int tick, int variance, Mechanic[] mechanics) {
		
		this.effectType = effectType;
		
		this.magnitude = magnitude;
		
		this.duration = duration;
		
		this.tick = tick;
		
		this.variance = variance;
		
		this.mechanics = mechanics;
		
	}
	
	public Mechanic[] getMechanics() {
		
		return mechanics;
		
	}
	
	public EffectType getEffectType() {
		
		synchronized (intLock) {
		
			return effectType;
		
		}
		
	}
	
	public int getDuration() {
		
		synchronized(intLock) {
			
			return duration;
			
		}
		
	}
	
	public int getTick() {
		
		synchronized(intLock) {
			
			return tick;
			
		}
		
	}
	
	public int getMagnitude() {
		
		synchronized(intLock) {
			
			return magnitude;
			
		}
		
	}
	
	public int getVariance() {
		
		synchronized(intLock) {
			
			return variance;
			
		}
		
	}

}
