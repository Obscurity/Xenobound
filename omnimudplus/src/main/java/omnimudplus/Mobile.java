package omnimudplus;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Mobile extends Entity implements Serializable {
	
	private transient ConnectNode cn;

	private final LockObject healthLock = new LockObject();

	private int health; // Hit points, calculated from constitution.

	private int maxHealth; // Maximum hit points, calculated from constitution.
	
	private final LockObject powerLock = new LockObject();

	private int power; // Casting resource, calculated from willpower.

	private int maxPower; // Maximum casting resource, calculated from willpower.
	
	private final LockObject statLock = new LockObject();

	private int strength; // Duh.

	private int dexterity; // Duh.

	private int constitution; // Duh.

	private int willpower; // Duh.
	
	private int perception;

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
	
	private final LockObject powerRegenBalanceLock = new LockObject();
	
	private long powerRegenBalance = 0;
	
	private int powerRegenPeriod = 15000;
	
	private transient ScheduledFuture<MobileRunnable> powerRegenTask;
	
	private final LockObject travelLock = new LockObject();
	
	private transient ScheduledFuture<TravelRunnable> travelTask;
	
	static final private long serialVersionUID = 31L;

	public Mobile() {

		super();

		strength = 10;

		dexterity = 10;

		constitution = 10;

		willpower = 10;

		initHealth();

		initPower();

		initMaxWeight();

		calcWeightCarried();

	}

	public Mobile(ConnectNode cn, String name, String[] aliases, String shortDesc, String roomDesc, String longDesc,
			char token, int weight, Material material, int strength, int dexterity, int constitution,
			int willpower, int perception) {

		super(name, aliases, shortDesc, roomDesc, longDesc, token, weight, material);
		
		this.cn = cn;

		this.strength = strength;

		this.dexterity = dexterity;

		this.constitution = constitution;

		this.willpower = willpower;
		
		this.perception = perception;

		this.health = 0;

		this.maxHealth = 0;

		initHealth();

		initPower();

		initMaxWeight();

		calcWeightCarried();

	}

	public void setStrength(int strength) {

		synchronized (statLock) {

			this.strength = strength;

		}

	}

	public int getStrength() {

		synchronized (statLock) {

			return strength;

		}

	}

	public void setDexterity(int dexterity) {

		synchronized (statLock) {

			this.dexterity = dexterity;

		}

	}

	public int getDexterity() {

		synchronized (statLock) {

			return dexterity;

		}

	}

	public void setConstitution(int constitution) {

		synchronized (statLock) {

			this.constitution = constitution;

		}

	}

	public int getConstitution() {

		synchronized (statLock) {

			return constitution;

		}

	}

	public void setWillpower(int willpower) {

		synchronized (statLock) {

			this.willpower = willpower;

		}

	}

	public int getWillpower() {

		synchronized (statLock) {

			return willpower;

		}

	}
	
	public void setPerception(int perception) {

		try {
		
			synchronized (statLock) {
	
				this.perception = perception;
	
			}
			
		} catch (NumberFormatException e) {
		
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}

	}

	public int getPerception() {

		synchronized (statLock) {

			return perception;

		}

	}


	public void initHealth() {

		synchronized (healthLock) {

			maxHealth = constitution * 10;

			health = maxHealth;

		}

	}

	public void initPower() {

		synchronized (powerLock) {

			maxPower = willpower * 10;

			power = maxPower;

		}

	}

	public void initMaxWeight() {

		synchronized (weightLock) {

			maxWeight = strength * 160;

		}

	}

	public int getMaxWeight() {

		synchronized (weightLock) {

			return maxWeight;

		}

	}

	public void calcWeightCarried() {

		synchronized (weightLock) {

			weightCarried = 0;

			for (Entity object : inventory) {

				weightCarried += object.getWeight();

			}

		}

	}
	
	public int getWeightCarried() {
		
		synchronized (weightLock) {
			
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

	public int getPower() {

		synchronized (powerLock) {

			return power;

		}

	}

	public void setPower(int power) {

		synchronized (powerLock) {

			this.power = power;

		}

	}

	public void spendPower(int drain) {

		synchronized (powerLock) {

			power -= drain;
			
			resetPowerRegenBalance(powerRegenPeriod);

		}

	}

	public void gainPower(int gain) {

		synchronized (powerLock) {

			power += gain;
			
			resetPowerRegenBalance(powerRegenPeriod);

		}

	}

	public int getMaxPower() {

		synchronized (powerLock) {

			return maxPower;

		}

	}

	public void setMaxPower(int maxPower) {

		synchronized (powerLock) {

			this.maxPower = maxPower;

		}

	}
	
	public int getRegenPeriod() {
		
		synchronized (regenBalanceLock) {
		
			return regenPeriod;
		
		}
		
	}
	
	public int getPowerRegenPeriod() {
		
		synchronized (powerRegenBalanceLock) {
			
			return powerRegenPeriod;
			
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
	
	public boolean hasPowerRegenBalance() {
		
		synchronized (powerRegenBalanceLock) {
			
			if (powerRegenBalance < System.currentTimeMillis()) {
				
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
	public void resetPowerRegenBalance(long value) {
		
		if (powerRegenTask != null) {
			
			powerRegenTask.cancel(false);
		
			powerRegenTask = null;
			
		}
			
		if (power < maxPower) {
		
			powerRegenTask = (ScheduledFuture<MobileRunnable>)Utilities.schedule(new MobileRunnable(this, "powerRegen"), value, TimeUnit.MILLISECONDS);
					
		}
		
	}
	
	public void stopRegen() {
		
		if (regenTask != null) {
			
			regenTask.cancel(false);
			
			regenTask = null;
			
		}
		
		if (powerRegenTask != null) {
			
			powerRegenTask.cancel(false);
			
			powerRegenTask = null;
			
		}
		
	}
	
	public void startRegen() {
		
		resetRegenBalance(regenPeriod);
		
		resetPowerRegenBalance(powerRegenPeriod);
		
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
			
			Location destination = null;
			
			if (zone instanceof Area) {
				
				destination = ((Room)location).getExit(dir).getDestination();
				
			}
			
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
			
			Location destination = null;
			
			if (zone instanceof Area) {
				
				destination = ((Room)location).getExit(dir).getDestination();
				
			}
			
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
		
		Location destination = null;
		
		if (location instanceof Room) {
			
			destination = ((Room)location).getExit(dir).getDestination();
			
		}
		
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
	
	public void setZone(Zone zone) {
		
		if (this.zone != null) {
			
			this.zone.removeMobile(this);
			
		}
		
		this.zone = zone;
		
		if (this.zone != null) {
			
			this.zone.addMobile(this);
			
		}
		
	}
	
	public void setLocation(Location location) {
		
		if (this.location != null) {
			
			if (this.location instanceof Room) {
				
				Room reassign = (Room)this.location;
				
				reassign.removeMobile(this);
				
				reassign.getZone().removeMobile(this);
				
			}
			
		}
		
		this.location = location;
		
		this.coors = null;
		
		if (location != null) {
			
			this.coors = location.getCoordinates();
				
			if (location instanceof Room) {
			
				((Room)location).addMobile(this);
				
			}
				
			location.getZone().addMobile(this);
			
			setZone(location.getZone());
			
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

		str = str + "\nhealth: " + health + "\nmaxhealth: " + maxHealth + "\npower: " + power + "\nmaxpower: " + maxPower + "\nstrength: " + strength + "\ndexterity: " + dexterity + "\nconstitution: " + constitution + "\nwillpower: " + willpower + "\nweightcarried: " + weightCarried + "\nmaxweight: " + maxWeight + "\ninventory: " + inventory;

		return str;

	}

}