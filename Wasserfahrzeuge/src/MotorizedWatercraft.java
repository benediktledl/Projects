/**
 * An abstract base class for a motorized watercraft.
 */
public abstract class MotorizedWatercraft extends Watercraft {
    protected int horsepower;

    /**
     * Constructor for a motorized watercraft.
     */
    public MotorizedWatercraft(String name, int length, int horsepower) {
        super(name, length);
        this.horsepower = horsepower;
    }

    /**
     * Getter for the horsepower of the motorized watercraft.
     */
    public int getHorsepower() {
        return horsepower;
    }

    /**
     * Setter for the horsepower of the motorized watercraft.
     */
    public void setHorsepower(int horsepower) {
        this.horsepower = horsepower;
    }
}
