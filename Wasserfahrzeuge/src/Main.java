import java.util.Scanner;

/**
 * A testing class for the Watercraft hierarchy.
 */
public class Main {
    public static void main(String[] args) {
        Watercraft[] watercrafts = new Watercraft[4];
        Scanner scanner = new Scanner(System.in);

        // Create some watercraft
        Motorboat mb1 = new Motorboat("MB-1", 20, 50);
        Motorboat mb2 = new Motorboat("MB-2", 25, 75);
        PedalBoat pb1 = new PedalBoat("PB-1", 10, true, 2);
        PedalBoat pb2 = new PedalBoat("PB-2", 8, false, 1);
        Liner l1 = new Liner("L-1", 100, 5000, 10);
        MiniSubmarine ms1 = new MiniSubmarine("MS-1", 30, 200, 200);

        // Add the watercraft to the array
        watercrafts[0] = mb1;
        watercrafts[1] = pb1;
        watercrafts[2] = l1;
        watercrafts[3] = ms1;

        boolean quit = false;
        while (!quit) {
            // Print menu
            System.out.println("Watercraft Management Menu:");
            System.out.println("1. List all watercraft");
            System.out.println("2. Create new watercraft");
            System.out.println("3. Delete watercraft");
            System.out.println("4. Quit");

            // Get user input
            int choice = scanner.nextInt();

            int horsepower;
            int i;

            switch (choice) {
                case 1:
                    // List all watercraft
                    for (Watercraft w : watercrafts) {
                        if(w == null){
                            continue;
                        }
                        System.out.println("Name: " + w.getName());
                        System.out.println("Length: " + w.getLength());
                    }
                    break;
                case 2:
                    // Create new watercraft
                    System.out.println("Enter the type of watercraft (1. Motorboat, 2. PedalBoat, 3. Liner, 4. MiniSubmarine):");
                    int type = scanner.nextInt();
                    System.out.println("Enter the name of the watercraft:");
                    String name = scanner.next();
                    System.out.println("Enter the length of the watercraft:");
                    int length = scanner.nextInt();
                    Watercraft newWatercraft;
                    switch (type) {
                        case 1:
                            System.out.println("Enter the horsepower of the motorboat:");
                            horsepower = scanner.nextInt();
                            newWatercraft = new Motorboat(name, length, horsepower);
                            break;
                        case 2:
                            System.out.println("Enter whether the pedal boat has a canopy (true or false):");
                            boolean hasCanopy = scanner.nextBoolean();
                            System.out.println("Enter the number of seats on the pedal boat:");
                            int numSeats = scanner.nextInt();
                            newWatercraft = new PedalBoat(name, length, hasCanopy, numSeats);
                            break;
                        case 3:
                            System.out.println("Enter the number of passengers on the liner:");
                            int numPassengers = scanner.nextInt();
                            System.out.println("Enter the maximum speed of the liner:");
                            int maxSpeed = scanner.nextInt();
                            newWatercraft = new Liner(name, length, numPassengers, maxSpeed);
                            break;
                        case 4:
                            System.out.println("Enter the horsepower of the mini-submarine:");
                            horsepower = scanner.nextInt();
                            System.out.println("Enter the max-depth of the mini-submarine:");
                            int maxDepth = scanner.nextInt();
                            newWatercraft = new Liner(name, length, horsepower, maxDepth);
                            break;
                        default:
                            newWatercraft = null;
                            System.out.println("Continuing...");
                            break;
                    }
                    // ADD TO watercrafts ARRAY
                    i = 0;
                    boolean didAction = false;
                    for (Watercraft w : watercrafts){
                        if(w == null){
                            watercrafts[i] = newWatercraft;
                            didAction = true;
                            break;
                        }
                        i++;
                    }
                    if(!didAction){
                        // No space in watercrafts array
                        System.out.println("No more slots left, couldn't add element");
                        newWatercraft = null;
                    }
                    break;
                case 3:
                    // Delete watercraft
                    System.out.println("Which watercraft do you want to destroy?");
                    i = 0;
                    for (Watercraft w : watercrafts) {
                        if(w == null){
                            continue;
                        }
                        System.out.println("Name: " + w.getName());
                        System.out.println("Id: " + i);
                        i++;
                    }
                    int id = scanner.nextInt();
                    watercrafts[id] = null;

                    break;
                case 4:
                    // Quit
                    quit = true;
                    break;
                default:
                    // Invalid
                    System.out.println("Invalid action");
                    break;
            }
        }
    }
}