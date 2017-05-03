package omnimudplus.Entities;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import omnimudplus.Area;
import omnimudplus.ConnectNode;
import omnimudplus.Direction;
import omnimudplus.LockObject;
import omnimudplus.Material;
import omnimudplus.MessageRunnable;
import omnimudplus.MobileRunnable;
import omnimudplus.Room;
import omnimudplus.TravelRunnable;
import omnimudplus.Utilities;
import omnimudplus.Vector;

public class Mobile extends Entity implements Serializable {
	
	private transient ConnectNode cn;

	private final LockObject healthLock = new LockObject();

	private int health; // Hit points, calculated from constitution.

	private int maxHealth; // Maximum hit points, calculated from constitution.
	
	private final LockObject nrsLock = new LockObject();

	private int nrs; // Casting resource, calculated from willpower.

	private int maxNrs; // Maximum casting resource, calculated from willpower.
	
	private final LockObject statLock = new LockObject();

	private int weightCarried; // Amount of weight carried, calculated from inventory. Measured in ounces.

	private int maxWeight; // Maximum weight carried, calculated from strength. Measured in ounces.
	
	private final LockObject inventoryLock = new LockObject();

	private LinkedHashSet<Entity> inventory = new LinkedHashSet<Entity>();
	
	private final LockObject moveBalanceLock = new LockObject();
	
	private long moveBalance = 0;
	
	// In milliseconds. Subject to alteration.
	
	private int moveSpeed = 500;
	
	private final LockObject actionBalanceLock = new LockObject();
	
	private long actionBalance = 0;
	
	private final LockObject regenBalanceLock = new LockObject();
	
	private long regenBalance = 0;
	
	private int regenPeriod = 15000;
	
	private transient ScheduledFuture<MobileRunnable> regenTask;
	
	private final LockObject nrsRegenBalanceLock = new LockObject();
	
	private long nrsRegenBalance = 0;
	
	private int nrsRegenPeriod = 15000;
	
	private transient ScheduledFuture<MobileRunnable> nrsRegenTask;
	
	private final LockObject travelLock = new LockObject();
	
	private transient ScheduledFuture<TravelRunnable> travelTask;
	
	static final private long serialVersionUID = 31L;

	public Mobile() {

		super();

		initHealth();

		initNrs();

		initMaxWeight();

		calcWeightCarried();

	}

	public Mobile(ConnectNode cn, String name, String[] aliases, String shortDesc,
			String roomDesc, String longDesc, int weight, Material material) {

		super(name, aliases, shortDesc, roomDesc, longDesc, weight, material);
		
		this.cn = cn;

		this.health = 0;

		this.maxHealth = 0;

		initHealth();

		initNrs();

		initMaxWeight();

		calcWeightCarried();

	}

	public void initHealth() {

		synchronized (healthLock) {

			maxHealth = 10;

			health = maxHealth;

		}

	}

	public void initNrs() {

		synchronized (nrsLock) {

			maxNrs = 10;

			nrs = maxNrs;

		}

	}

	public void initMaxWeight() {

		synchronized (propertyLock) {

			maxWeight = 160;

		}

	}

	public int getMaxWeight() {

		synchronized (propertyLock) {

			return maxWeight;

		}

	}

	public void calcWeightCarried() {

		synchronized (propertyLock) {

			weightCarried = 0;

			for (Entity object : inventory) {

				weightCarried += object.getWeight();

			}

		}

	}
	
	public int getWeightCarried() {
		
		synchronized (propertyLock) {
			
			calcWeightCarried();
			
			return weightCarried;
			
		}
		
	}

	public int getHealth() {

		synchronized (healthLock) {

			return health;

		}

	}

	public void setHealth(int health) {

		synchronized (healthLock) {

			this.health = health;

		}

	}

	public void takeDamage(int damage) {

		synchronized (healthLock) {

			health -= damage;
			resetRegenBalance(regenPeriod);

		}

	}

	public void healDamage(int heal) {

		synchronized (healthLock) {

			health += heal;
			
			resetRegenBalance(regenPeriod);

		}

	}

	public int getMaxHealth() {

		synchronized (healthLock) {

			return maxHealth;

		}

	}

	public void setMaxHealth(int maxHealth) {

		synchronized (healthLock) {

			this.maxHealth = maxHealth;

		}

	}

	public int getNrs() {

		synchronized (nrsLock) {

			return nrs;

		}

	}

	public void setNrs(int nrs) {

		synchronized (nrsLock) {

			this.nrs = nrs;

		}

	}

	public void spendNrs(int drain) {

		synchronized (nrsLock) {

			nrs -= drain;
			
			resetNrsRegenBalance(nrsRegenPeriod);

		}

	}

	public void gainNrs(int gain) {

		synchronized (nrsLock) {

			nrs += gain;
			
			resetNrsRegenBalance(nrsRegenPeriod);

		}

	}

	public int getMaxNrs() {

		synchronized (nrsLock) {

			return maxNrs;

		}

	}

	public void setMaxNrs(int maxNrs) {

		synchronized (nrsLock) {

			this.maxNrs = maxNrs;

		}

	}
	
	public int getRegenPeriod() {
		
		synchronized (regenBalanceLock) {
		
			return regenPeriod;
		
		}
		
	}
	
	public int getNrsRegenPeriod() {
		
		synchronized (nrsRegenBalanceLock) {
			
			return nrsRegenPeriod;
			
		}
		
	}
	
	public void setInventory(LinkedHashSet<Entity> inventory) {

		synchronized (inventoryLock) {

			this.inventory = inventory;

		}

	}

	public LinkedHashSet<Entity> getInventory() {

		synchronized (inventoryLock) {

			return inventory;

		}

	}

	public void addEntity(Entity entity) {

		synchronized (inventoryLock) {

			inventory.add(entity);

		}

	}

	public void removeEntity(Entity entity) {

		synchronized (inventoryLock) {

			inventory.remove(entity);

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
		
		synchronized (regenBalanceLock) {
			
			if (regenBalance < System.currentTimeMillis()) {
				
				return true;
				
			}
			
			return false;
			
		}
		
	}
	
	public boolean hasNrsRegenBalance() {
		
		synchronized (nrsRegenBalanceLock) {
			
			if (nrsRegenBalance < System.currentTimeMillis()) {
				
				return true;
				
			}
			
			return false;
			
		}
		
	}
	
	public long getActionBalance() {
		
		synchronized (actionBalanceLock) {
			
			return actionBalance;
			
		}
		
	}
	
	public long getMoveBalance() {
		
		synchronized (moveBalanceLock) {
			
			return moveBalance;
			
		}
		
	}
	
	public long getRegenBalance() {
		
		synchronized (regenBalanceLock) {
			
			return regenBalance;
			
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public void takeActionBalance(ConnectNode cn, long value, boolean message) {
		
		synchronized (actionBalanceLock) {
		
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
	
	@SuppressWarnings("unchecked")
	public void takeMoveBalance(long value, boolean message) {
		
		synchronized (moveBalanceLock) {
			
			if (moveBalance < System.currentTimeMillis()) {
				
				moveBalance = System.currentTimeMillis() + value;
				
				if (!message) { return; }
				
				if (cn != null) {
				
					cn.setMoveMessage((ScheduledFuture<MessageRunnable>)Utilities.schedule(new MessageRunnable(cn, "You may move once again."), value, TimeUnit.MILLISECONDS));
				
				}
				
			} else {
				
				moveBalance += value;
				
				if (!message) { return; }
				
				long offset = moveBalance - System.currentTimeMillis();
				
				if (cn != null) {
				
					cn.setMoveMessage((ScheduledFuture<MessageRunnable>)Utilities.schedule(new MessageRunnable(cn, "You may move once again."), offset, TimeUnit.MILLISECONDS));
					
				}
				
			}
			
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public void resetRegenBalance(long value) {
		
		if (regenTask != null) {
			
			regenTask.cancel(false);
		
			regenTask = null;
			
		}
			
		if (health < maxHealth) {
		
			regenTask = (ScheduledFuture<MobileRunnable>)Utilities.schedule(new MobileRunnable(this, "regen"), value, TimeUnit.MILLISECONDS);
					
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public void resetNrsRegenBalance(long value) {
		
		if (nrsRegenTask != null) {
			
			nrsRegenTask.cancel(false);
		
			nrsRegenTask = null;
			
		}
			
		if (nrs < maxNrs) {
		
			nrsRegenTask = (ScheduledFuture<MobileRunnable>)Utilities.schedule(new MobileRunnable(this, "nrsRegen"), value, TimeUnit.MILLISECONDS);
					
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
		
		synchronized (regenBalanceLock) {
			
			long predictiveTime = System.currentTimeMillis() + value;
			
			if (predictiveTime > regenBalance) {
				
				regenBalance = predictiveTime;
				
			}
			
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public void setTravelCommand(Direction dir) {
		
		synchronized (travelLock) {
		
			if (travelTask != null) {
			
				travelTask.cancel(false);
				travelTask = null;
			
			} else {
				
				if (cn != null) {
					
					cn.println("You start traveling to the " + dir.getName() + ".");
				
				}
				
			}
			
			Room destination = this.getRoom().getExit(dir).getDestination();
			
			int milliCost = 500;
			
			if (destination != null) {
				
				Vector coors = new Vector(getCoordinates(), destination.getCoordinates());
			
				milliCost = (int)(moveSpeed*coors.getAbsoluteOffset());
			
			}
			
			travelTask = (ScheduledFuture<TravelRunnable>)Utilities.scheduleAtFixedRate(new TravelRunnable(this, dir), milliCost, milliCost, TimeUnit.MILLISECONDS);
		
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public void setTravelCommand(Direction dir, int count) {
		
		synchronized (travelLock) {
			
			if (travelTask != null) {
			
				travelTask.cancel(false);
				travelTask = null;
			
			} else {
				
				if (cn != null) {
					
					cn.println("You start traveling to the " + dir.getName() + ".");
				
				}
				
			}
			
			Room destination = room.getExit(dir).getDestination();
			
			int milliCost = 500;
			
			if (destination != null) {
				
				Vector coors = new Vector(getCoordinates(), destination.getCoordinates());
				
				milliCost = (int)(moveSpeed*coors.getAbsoluteOffset());
			
			}
			
			travelTask = (ScheduledFuture<TravelRunnable>)Utilities.scheduleAtFixedRate(new TravelRunnable(this, dir, count), milliCost, milliCost, TimeUnit.MILLISECONDS);
			
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public void setTravelCommand(ArrayList<Direction> path, int count) {
		
		Direction dir = path.get(count);
		
		if (travelTask != null) {
			
			travelTask.cancel(false);
			travelTask = null;
			
		} else {
			
			if (cn != null) {
				
				cn.println("You start traveling.");
				
			}
			
		}
		
		Room destination = room.getExit(dir).getDestination();
		
		Vector coors = new Vector(getCoordinates(), destination.getCoordinates());
		
		int milliCost = (int)(moveSpeed*coors.getAbsoluteOffset());
		
		travelTask = (ScheduledFuture<TravelRunnable>)Utilities.scheduleAtFixedRate(new TravelRunnable(this, path, count), milliCost, milliCost, TimeUnit.MILLISECONDS);
		
	}
	
	public void travelStop() {
		
		if (travelTask != null) {
			
			travelTask.cancel(false);
			travelTask = null;
			
		}
		
	}
	
	public boolean isTraveling() {
		
		if (travelTask != null) {
		
			return true;
		
		}
		
		return false;
		
	}
	
	public LockObject getTravelLock() {
		
		return travelLock;
		
	}
	
	public LockObject getActionBalanceLock() {
		
		synchronized (actionBalanceLock) {
		
			return actionBalanceLock;
		
		}
		
	}
	
	public LockObject getMoveBalanceLock() {
		
		synchronized (moveBalanceLock) {
		
			return moveBalanceLock;
		
		}
		
	}
	
	public LockObject getRegenBalanceLock() {
		
		synchronized (regenBalanceLock) {
			
			return regenBalanceLock;
			
		}
		
	}
	
	public int getMoveSpeed() {
		
		synchronized (moveBalanceLock) {
			
			return moveSpeed;
			
		}
		
	}
	
	public boolean isPlayer() {
		
		if (cn != null) {
			return true;
		}
		
		return false;
		
	}
	
	public void setArea(Area area) {
		
		if (this.area != null) {
			
			this.area.removeMobile(this);
			
		}
		
		this.area = area;
		
		if (this.area != null) {
			
			this.area.addMobile(this);
			
		}
		
	}
	
	public void setRoom(Room room) {
		
		if (this.room != null) {
				
			Room reassign = this.room;
				
			reassign.removeMobile(this);
				
			if (reassign.getArea() != null) {
				
				reassign.getArea().removeMobile(this);
				
			}
			
		}
		
		this.room = room;
		
		this.coors = null;
		
		if (room != null) {
			
			this.coors = room.getCoordinates();
			
			room.addMobile(this);
			
			Area area = room.getArea();
			
			if (area != null) {
				
				room.getArea().addMobile(this);
				
				setArea(room.getArea());
				
			}
			
		}
		
	}
	
	public void setConnectNode(ConnectNode cn) {
		
		this.cn = cn;
		
	}
	
	public ConnectNode getConnectNode() {
		
		return cn;
		
	}
	
	public String toString() {

		String str = super.toString();

		str = str + "\nhealth: " + health + "\nmaxhealth: " + maxHealth + "\nnrs: " + nrs + "\nmaxnrs: " + maxNrs + "\nweightcarried: " + weightCarried + "\nmaxweight: " + maxWeight + "\ninventory: " + inventory;

		return str;

	}

}