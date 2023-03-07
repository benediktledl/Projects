/**
 * A class for a motorized rowboat, which is a motorized watercraft.
 */
public class MotorizedRowboat extends MotorizedWatercraft {
    protected int numSeats;

    /**
     * Constructor for a motorized rowboat.
     */
    public MotorizedRowboat(String name, int length, int horsepower, int numSeats) {
        super(name, length, horsepower);
        this.numSeats = numSeats;
    }

    /**
     * Getter for the number of seats in the motorized rowboat.
     */
    public int getNumSeats() {
        return numSeats;
    }

    /**
     * Setter for the number of seats in the motorized rowboat.
     */
    public void setNumSeats(int numSeats) {
        this.numSeats = numSeats;
    }

    /**
     * Overrides the move() method of the Watercraft class.
     * This method moves the motorized rowboat forward using the motor.
     */
    @Override
    public void move() {
        System.out.println(name + " is moving forward with " + horsepower + " horsepower.");
    }
}
