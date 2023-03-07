/**
 * A class for a mini-submarine, which is a motorized watercraft.
 */
public class MiniSubmarine extends MotorizedWatercraft {
    protected int maxDepth;

    /**
     * Constructor for a mini-submarine.
     */
    public MiniSubmarine(String name, int length, int horsepower, int maxDepth) {
        super(name, length, horsepower);
        this.maxDepth = maxDepth;
    }

    /**
     * Getter for the maximum depth the mini-submarine can go.
     */
    public int getMaxDepth() {
        return maxDepth;
    }

    /**
     * Setter for the maximum depth the mini-submarine can go.
     */
    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    /**
     * Overrides the move() method of the Watercraft class.
     * This method moves the mini-submarine forward and downward using the motor and propeller.
     */
    @Override
    public void move() {
        System.out.println(name + " is moving forward and downward with " + horsepower + " horsepower and propeller.");
    }
}
