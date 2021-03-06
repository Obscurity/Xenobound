package omnimudplus.Effects;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import omnimudplus.Utilities;
import omnimudplus.Entities.Mobile;

public class EffectRunnable implements Runnable {

	private int duration;
	private Effect effect;
	private Mobile mobile;
	
	public EffectRunnable(int duration, Effect effect, Mobile mobile) {
		
	}
	
	public void run() {
		
		duration--;
		
		// Run whatever effect, however.
		
		if (duration <= 0) {
			return;
		} else {
			ScheduledFuture<EffectRunnable> newrun = 
					(ScheduledFuture<EffectRunnable>)Utilities.schedule(new EffectRunnable(duration, effect, mobile), 0, TimeUnit.MILLISECONDS);
		}
		
	}
	
}
