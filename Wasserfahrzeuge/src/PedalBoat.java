/**
 * A class for a pedal boat, which is a non-motorized watercraft.
 */
public class PedalBoat extends Watercraft {
    protected boolean hasCanopy;
    protected int numSeats;

    /**
     * Constructor for a pedal boat.
     */
    public PedalBoat(String name, int length, boolean hasCanopy, int numSeats) {
        super(name, length);
        this.hasCanopy = hasCanopy;
        this.numSeats = numSeats;
    }

    /**
     * Getter for whether the pedal boat has a canopy.
     */
    public boolean getHasCanopy() {
        return hasCanopy;
    }

    /**
     * Setter for whether the pedal boat has a canopy.
     */
    public void setHasCanopy(boolean hasCanopy) {
        this.hasCanopy = hasCanopy;
    }

    /**
     * Getter for the number of seats in the pedal boat.
     */
    public int getNumSeats() {
        return numSeats;
    }

    /**
     * Setter for the number of seats in the pedal boat.
     */
    public void setNumSeats(int numSeats) {
        this.numSeats = numSeats;
    }

    /**
     * Overrides the move() method of the Watercraft class.
     * This method moves the pedal boat forward by pedaling.
     */
    @Override
    public void move() {
        System.out.println(name + " is moving forward by pedaling.");
    }
}
