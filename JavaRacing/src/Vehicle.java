
public class Vehicle extends Obstacles {
    private String name;
    private int boost;
    private int currentFuel;
    private int maxFuel;
    private int currentDamage;
    private int maxDamage;
    private int currentLaneIndex;
    private int currentIndexInLane;

    public Vehicle(String name, int boost, int currentFuel, int maxFuel, int currentDamage, int maxDamage) {
        this.name = name;
        this.boost = boost;
        this.currentFuel = currentFuel;
        this.maxFuel = maxFuel;
        this.currentDamage = currentDamage;
        this.maxDamage = maxDamage;
    }

    public String getName() {
        return name;
    }

    public int getBoost() {
        return boost;
    }

    public int getMaxFuel() {
        return maxFuel;
    }

    public int getCurrentDamage() {
        return currentDamage;
    }

    public int getMaxDamage() {
        return maxDamage;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBoost(int boost) {
        this.boost = boost;
    }

    public void setMaxFuel(int maxFuel) {
        this.maxFuel = maxFuel;
    }

    public void setCurrentDamage(int currentDamage) {
        this.currentDamage = currentDamage;
    }

    public void setMaxDamage(int maxDamage) {
        this.maxDamage = maxDamage;
    }

    public void setCurrentLaneIndex(int currentLaneIndex) {
        this.currentLaneIndex = currentLaneIndex;
    }

    public int getCurrentLaneIndex() {
        return currentLaneIndex;
    }

    public void setCurrentIndexInLane(int currentIndexInLane) {
        this.currentIndexInLane = currentIndexInLane;
    }

    public int getCurrentIndexInLane() {
        return currentIndexInLane;
    }

    public int getCurrentFuel() {
        return currentFuel;
    }

    public void setCurrentFuel(int currentFuel) {
        this.currentFuel = currentFuel;
    }
}
