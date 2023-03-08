/**
 * An abstract base class for a watercraft.
 */
public abstract class Watercraft implements Drivable {
    protected String name;
    protected int length;

    /**
     * Constructor for a watercraft.
     */
    public Watercraft(String name, int length) {
        this.name = name;
        this.length = length;
    }

    /**
     * Getter for the name of the watercraft.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name of the watercraft.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the length of the watercraft.
     */
    public int getLength() {
        return length;
    }

    /**
     * Setter for the length of the watercraft.
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * Drives the watercraft.
     */
    @Override
    public void drive() {
        System.out.println("The watercraft is being driven.");
    }

    /**
     * Abstract method for moving the watercraft.
     */
    public abstract void move();
}
