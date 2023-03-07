/**
 * A class for a liner, which is a motorized watercraft.
 */
public class Liner extends MotorizedWatercraft {
    protected int numDecks;

    /**
     * Constructor for a liner.
     */
    public Liner(String name, int length, int horsepower, int numDecks) {
        super(name, length, horsepower);
        this.numDecks = numDecks;
    }

    /**
     * Getter for the number of decks on the liner.
     */
    public int getNumDecks() {
        return numDecks;
    }

    /**
     * Setter for the number of decks on the liner.
     */
    public void setNumDecks(int numDecks) {
        this.numDecks = numDecks;
    }

    /**
     * Overrides the move() method of the Watercraft class.
     * This method moves the liner forward using the motor.
     */
    @Override
    public void move() {
        System.out.println(name + " is moving forward with " + horsepower + " horsepower.");
    }
}
