package model;

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
    

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }


    public int getFuel() {
        return fuel;
    }

    public int getDamage() {
        return damage;
    }
    
    /**
    public class OpenManhole extends Obstacles {
        public OpenManhole() {
            super.damage = 60;
            super.display = "O";
        }
    }
    */
}
