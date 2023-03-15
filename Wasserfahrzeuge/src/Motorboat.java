/**
 * A class representing a motorboat, a type of watercraft.
 */
public class Motorboat extends MotorizedWatercraft implements Buyable {
    private boolean hasPropeller;

    /**
     * Constructs a motorboat with the given name, length, and horsepower.
     *
     * @param name the name of the motorboat
     * @param length the length of the motorboat in feet
     * @param horsepower the horsepower of the motorboat
     */


    public Motorboat(String name, int length, int horsepower) {
        super(name, length, horsepower);
        this.hasPropeller = true;
    }

    public String getName() {
        return name + this.advertise();
    }


    /**
     * Returns whether the motorboat has a propeller or not.
     *
     * @return true if the motorboat has a propeller, false otherwise
     */
    public boolean hasPropeller() {
        return hasPropeller;
    }

    /**
     * Override of Buyable interface
     */
    @Override
    public String advertise() {
        return " This motorboat can be bought!";
    }

    /**
     * Sets whether the motorboat has a propeller or not.
     *
     * @param hasPropeller true if the motorboat has a propeller, false otherwise
     */
    public void setHasPropeller(boolean hasPropeller) {
        this.hasPropeller = hasPropeller;
    }

    /**
     * Increases the horsepower of the motorboat by the given amount.
     *
     * @param amount the amount to increase the horsepower by
     */
    public void increaseHorsepower(int amount) {
        this.setHorsepower(this.getHorsepower() + amount);
    }

    /**
     * Moves the motorboat by turning on the motor.
     */
    @Override
    public void move() {
        System.out.println("The motorboat is moving by turning on the motor.");
    }
}
