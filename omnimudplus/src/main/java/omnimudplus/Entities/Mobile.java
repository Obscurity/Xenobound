package omnimudplus.Entities;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import Runnables.MessageRunnable;
import Runnables.MobileRunnable;
import Runnables.MobileRunnableType;
import Runnables.TravelRunnable;
import omnimudplus.ConnectNode;
import omnimudplus.LockObject;
import omnimudplus.Utilities;
import omnimudplus.Effects.Effect;
import omnimudplus.Geography.Area;
import omnimudplus.Geography.Direction;
import omnimudplus.Geography.Room;
import omnimudplus.Geography.Vector;

public abstract class Mobile extends Entity implements Serializable {

	// TODO make everything property lock-synced
	
	private int nrs; // Casting resource.

	private int maxNrs; // Maximum casting resource.

	private int weightCarried; // Amount of weight carried, calculated from inventory. Measured in ounces.

	private int maxWeight; // Maximum weight carried, calculated from strength. Measured in ounces.

	private Body body;
	
	private LinkedHashSet<TakableEntity> inventory = new LinkedHashSet<TakableEntity>();
	
	private long moveBalance = 0;
	
	// In milliseconds. Subject to alteration.
	
	private static final int moveSpeed = 500;
	
	private long actionBalance = 0;
	
	private long regenBalance = 0;
	
	private static final int regenPeriod = 15000;
	
	private transient ScheduledFuture<MobileRunnable> regenTask;
	
	private long nrsRegenBalance = 0;
	
	private static final int nrsRegenPeriod = 15000;
	
	private transient ScheduledFuture<MobileRunnable> nrsRegenTask;
	
	private transient ScheduledFuture<TravelRunnable> travelTask;
	
	private LinkedHashSet<Organization> organizations;
	
	static final private long serialVersionUID = 31L;

	public Mobile() {

		super();

		initDurability();

		initNrs();

		initMaxWeight();

		calcWeightCarried();

	}

	public Mobile(String name, String[] aliases, String shortDesc,
			String roomDesc, String longDesc, Material material,
			Size size, Pronouns pronouns, Species species) {

		super(name, aliases, shortDesc, roomDesc, longDesc, 0, material, pronouns, size);

		body = new Body(species);
		
		initDurability();

		initNrs();

		initMaxWeight();

		calcWeightCarried();

		setWeight(body.getWeight());
		
	}
	
	public Mobile(String name, String[] aliases, String shortDesc,
			String roomDesc, String longDesc, Material material,
			Size size, Pronouns pronouns, Species species, Body body) {

		super(name, aliases, shortDesc, roomDesc, longDesc, 0, material, pronouns, size);

		this.body = body;
		
		initDurability();

		initNrs();

		initMaxWeight();

		calcWeightCarried();

		setWeight(body.getWeight());
		
	}

	public void initNrs() {

		synchronized (getPropertyLock()) {

			maxNrs = 10;

			nrs = maxNrs;

		}

	}

	public void initMaxWeight() {

		synchronized (getPropertyLock()) {

			maxWeight = 160;

		}

	}

	public int getMaxWeight() {

		synchronized (getPropertyLock()) {

			return maxWeight;

		}

	}

	public void calcWeightCarried() {

		synchronized (getPropertyLock()) {

			weightCarried = 0;

			for (Entity object : inventory) {

				weightCarried += object.getWeight();

			}

		}

	}
	
	public int getWeightCarried() {
		
		synchronized (getPropertyLock()) {
			
			calcWeightCarried();
			
			return weightCarried;
			
		}
		
	}

	public void applyEffect(Effect e) {
		
		body.applyEffect(e);
		
	}

	public int getNrs() {

		synchronized (getPropertyLock()) {

			return nrs;

		}

	}

	public void setNrs(int nrs) {

		synchronized (getPropertyLock()) {

			this.nrs = nrs;

		}

	}

	public void spendNrs(int drain) {

		synchronized (getPropertyLock()) {

			nrs -= drain;
			
			resetNrsRegenBalance(nrsRegenPeriod);

		}

	}

	public void gainNrs(int gain) {

		synchronized (getPropertyLock()) {

			nrs += gain;
			
			resetNrsRegenBalance(nrsRegenPeriod);

		}

	}

	public int getMaxNrs() {

		synchronized (getPropertyLock()) {

			return maxNrs;

		}

	}

	public void setMaxNrs(int maxNrs) {

		synchronized (getPropertyLock()) {

			this.maxNrs = maxNrs;

		}

	}
	
	public int getRegenPeriod() {
		
		synchronized (getPropertyLock()) {
		
			return regenPeriod;
		
		}
		
	}
	
	public int getNrsRegenPeriod() {
		
		synchronized (getPropertyLock()) {
			
			return nrsRegenPeriod;
			
		}
		
	}
	
	public void setInventory(LinkedHashSet<TakableEntity> inventory) {

		synchronized (getPropertyLock()) {

			this.inventory = inventory;

		}

	}

	public Iterator<TakableEntity> getInventory() {

		synchronized (getPropertyLock()) {

			return inventory.iterator();

		}

	}
	
	public int getInventoryCount() {
		
		synchronized (getPropertyLock()) {
			
			return inventory.size();
			
		}
		
	}

	public void addEntity(TakableEntity entity) {

		synchronized (getPropertyLock()) {

			inventory.add(entity);

		}

	}

	public void removeEntity(Entity entity) {

		synchronized (getPropertyLock()) {

			inventory.remove(entity);

		}

	}
	
	public boolean hasEntity(Entity entity) {
		
		synchronized (getPropertyLock()) {
			
			return inventory.contains(entity);
			
		}
		
	}
	
	public boolean hasActionBalance() {
		
		if (actionBalance < System.currentTimeMillis()) {
			
			return true;
			
		}
		
		return false;
		
	}
	
	public boolean hasMoveBalance() {
		
		if (moveBalance < System.currentTimeMillis() && !isTraveling()) {
			
			return true;
			
		}
		
		return false;
		
	}
	
	public boolean hasRegenBalance() {
		
		synchronized (getPropertyLock()) {
			
			if (regenBalance < System.currentTimeMillis()) {
				
				return true;
				
			}
			
			return false;
			
		}
		
	}
	
	public boolean hasNrsRegenBalance() {
		
		synchronized (getPropertyLock()) {
			
			if (nrsRegenBalance < System.currentTimeMillis()) {
				
				return true;
				
			}
			
			return false;
			
		}
		
	}
	
	public long getActionBalance() {
		
		synchronized (getPropertyLock()) {
			
			return actionBalance;
			
		}
		
	}
	
	public long getMoveBalance() {
		
		synchronized (getPropertyLock()) {
			
			return moveBalance;
			
		}
		
	}
	
	public long getRegenBalance() {
		
		synchronized (getPropertyLock()) {
			
			return regenBalance;
			
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public void takeActionBalance(ConnectNode cn, long value, boolean message) {
		
		synchronized (getPropertyLock()) {
		
			if (actionBalance < System.currentTimeMillis()) {
				
				actionBalance = System.currentTimeMillis() + value;
				
				if (message) {
				
					cn.setActionMessage((ScheduledFuture<MessageRunnable>)Utilities.schedule(new MessageRunnable(cn, "You may perform another action."), value, TimeUnit.MILLISECONDS));
				
				}
				
			} else {
				
				actionBalance += value;
				
				if (!message) { return; }
				
				long offset = actionBalance - System.currentTimeMillis();
				
				cn.setActionMessage((ScheduledFuture<MessageRunnable>)Utilities.schedule(new MessageRunnable(cn, "You may perform another action."), offset, TimeUnit.MILLISECONDS));
				
			}
		
		}
		
	}
	
	public void takeMoveBalance(long value) {
		
		synchronized (getPropertyLock()) {
			
			if (moveBalance < System.currentTimeMillis()) {
				
				moveBalance = System.currentTimeMillis() + value;
				
			} else {
				
				moveBalance += value;
				
			}
			
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public void resetRegenBalance(long value) {
		
		if (regenTask != null) {
			
			regenTask.cancel(false);
		
			regenTask = null;
			
		}
			
		if (isDamaged()) {
		
			regenTask = (ScheduledFuture<MobileRunnable>)Utilities.schedule(new MobileRunnable(this, MobileRunnableType.REGEN), value, TimeUnit.MILLISECONDS);
					
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public void resetNrsRegenBalance(long value) {
		
		if (nrsRegenTask != null) {
			
			nrsRegenTask.cancel(false);
		
			nrsRegenTask = null;
			
		}
			
		if (nrs < maxNrs) {
		
			nrsRegenTask = (ScheduledFuture<MobileRunnable>)Utilities.schedule(new MobileRunnable(this, MobileRunnableType.NRS_REGEN), value, TimeUnit.MILLISECONDS);
					
		}
		
	}
	
	public void stopRegen() {
		
		if (regenTask != null) {
			
			regenTask.cancel(false);
			
			regenTask = null;
			
		}
		
		if (nrsRegenTask != null) {
			
			nrsRegenTask.cancel(false);
			
			nrsRegenTask = null;
			
		}
		
	}
	
	public void startRegen() {
		
		resetRegenBalance(regenPeriod);
		
		resetNrsRegenBalance(nrsRegenPeriod);
		
	}
	
	public void takeRegenBalance(long value) {
		
		synchronized (getPropertyLock()) {
			
			long predictiveTime = System.currentTimeMillis() + value;
			
			if (predictiveTime > regenBalance) {
				
				regenBalance = predictiveTime;
				
			}
			
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public void setTravelCommand(Direction dir) {
		
		synchronized (getPropertyLock()) {
		
			if (getTravelTask() != null) {
			
				getTravelTask().cancel(false);
				setTravelTask(null);
			
			}
			
			Room<?> origin = this.getRoom();
			
			Room<?> destination = origin.getExit(dir).getDestination();
			
			int milliCost = 500;
			
			if (destination != null) {
				
				Vector coors = new Vector(origin.getCoordinates(), destination.getCoordinates());
			
				milliCost = (int)(moveSpeed*coors.getAbsoluteOffset());
			
			}
			
			setTravelTask((ScheduledFuture<TravelRunnable>)Utilities.scheduleAtFixedRate(new TravelRunnable(this, dir), milliCost, milliCost, TimeUnit.MILLISECONDS));
		
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public void setTravelCommand(Direction dir, int count) {
		
		synchronized (getPropertyLock()) {
			
			if (getTravelTask() != null) {
			
				getTravelTask().cancel(false);
				setTravelTask(null);
			
			}
			
			Room<?> origin = this.getRoom();
			
			Room<?> destination = origin.getExit(dir).getDestination();
			
			int milliCost = 500;
			
			if (destination != null) {
				
				Vector coors = new Vector(origin.getCoordinates(), destination.getCoordinates());
				
				milliCost = (int)(moveSpeed*coors.getAbsoluteOffset());
			
			}
			
			setTravelTask((ScheduledFuture<TravelRunnable>)Utilities.scheduleAtFixedRate(new TravelRunnable(this, dir, count), milliCost, milliCost, TimeUnit.MILLISECONDS));
			
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public void setTravelCommand(ArrayList<Direction> path, int count) {
		
		Direction dir = path.get(count);
		
		if (getTravelTask() != null) {
			
			getTravelTask().cancel(false);
			setTravelTask(null);
			
		}
		
		Room<?> origin = getRoom();
		
		Room<?> destination = getRoom().getExit(dir).getDestination();
		
		Vector coors = new Vector(origin.getCoordinates(), destination.getCoordinates());
		
		int milliCost = (int)(moveSpeed*coors.getAbsoluteOffset());
		
		setTravelTask((ScheduledFuture<TravelRunnable>)Utilities.scheduleAtFixedRate(new TravelRunnable(this, path, count), milliCost, milliCost, TimeUnit.MILLISECONDS));
		
	}
	
	public void travelStop() {
		
		if (getTravelTask() != null) {
			
			getTravelTask().cancel(false);
			setTravelTask(null);
			
		}
		
	}
	
	public boolean isTraveling() {
		
		if (getTravelTask() != null) {
		
			return true;
		
		}
		
		return false;
		
	}
	
	public int getMoveSpeed() {
		
		synchronized (getPropertyLock()) {
			
			return moveSpeed;
			
		}
		
	}

	public LinkedHashSet<Organization> getOrganizations() {
		
		synchronized (getPropertyLock()) {
		
			return organizations;
		
		}
		
	}

	public void setOrganizations(LinkedHashSet<Organization> organizations) {
		
		synchronized (getPropertyLock()) {
			this.organizations = organizations;
		}
		
	}
	
	public void addOrganization(Organization organization) {
		
		synchronized (getPropertyLock()) {
			organizations.add(organization);
		}
		
	}
	
	public void removeOrganization(Organization organization) {
		
		synchronized (getPropertyLock()) {
			organizations.remove(organization);
		}
		
	}
	
	public boolean belongsToOrg(Organization organization) {
		
		synchronized (getPropertyLock()) {
			return organizations.contains(organization);
		}
		
	}
	
	public String toString() {

		synchronized (getPropertyLock()) {
		
			String str = super.toString();
	
			str = str + "\nhealth: " + getDurability() + "\nmaxhealth: " + getMaxDurability() +
					"\nnrs: " + nrs + "\nmaxnrs: " + maxNrs + "\nweightcarried: "
					+ weightCarried + "\nmaxweight: " + maxWeight + "\ninventory: "
					+ inventory;
	
			return str;
		
		}

	}

	public ScheduledFuture<TravelRunnable> getTravelTask() {
		
		synchronized (getPropertyLock()) {
		
			return travelTask;
		
		}
		
	}

	public void setTravelTask(ScheduledFuture<TravelRunnable> travelTask) {
		
		synchronized (getPropertyLock()) {
		
			this.travelTask = travelTask;
		
		}
		
	}

	public Body getBody() {
		
		synchronized (getPropertyLock()) {
		
			return body;
		
		}
		
	}
	
	public void initDurability() {
		
		if (body != null) {
			
			setMaxDurability(body.getDurability());
			
			setDurability(body.getDurability());
			
			return;
			
		}
		
		// Failsafe.
		super.initDurability();
		
	}
	
	public boolean isDamaged() {
		
		synchronized (getPropertyLock()) {
		
			if (body.isDamaged()) {
				return true;
			}
			return false;
		
		}
		
	}
	
	public int getHealth() {
		
		return (int)(((double)body.getDurability()/body.getMaxDurability())*100);
		
	}
	
	public int getDurability() {
		return body.getDurability();
	}
	
	public int getMaxDurability() {
		return body.getMaxDurability();
	}
	
	protected void setMaxDurability(int maxDurability) {
		body.setMaxDurability(maxDurability);
	}
	
	// These methods simply do not apply to mobiles, so we overwrite them.
	
	public void addDurability(int offset) {

	}
	
	public void removeDurability(int offset) {
		
	}

}