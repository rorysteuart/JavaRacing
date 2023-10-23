
public class Obstacles {
    protected int fuel;
    protected int damage;
    protected String display;

    @Override
    public String toString() {
        return "Obstacles{" +
                "fuel= " + fuel +
                ", damage= " + damage +
                ", display='" + display + '\'' +
                '}';
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getDisplay() {
        return display;
    }

    public int getFuel() {
        return fuel;
    }

    public int getCurrentDamage() {
        return damage;
    }
}
