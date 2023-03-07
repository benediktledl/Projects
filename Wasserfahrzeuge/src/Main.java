/**
 * A testing class for the Watercraft hierarchy.
 */
public class Main {
    public static void main(String[] args) {
        Watercraft[] watercrafts = new Watercraft[4];

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

        // Test the watercraft by calling their methods
        for (Watercraft w : watercrafts) {
            System.out.println("Name: " + w.getName());
            System.out.println("Length: " + w.getLength());
            w.move();
            System.out.println();
        }

        // Test specific methods of the motorboats
        System.out.println("MB-1 horsepower: " + mb1.getHorsepower());
        mb1.increaseHorsepower(10);
        System.out.println("MB-1 Upgrading Motorboat 1, new Horsepower: " + mb1.getHorsepower());
        System.out.println("MB-2 property hasPropeller: " + mb2.hasPropeller());
        mb2.setHasPropeller(true);
        System.out.println("MB-2 removing Propeller, hasPropeller: " + mb2.hasPropeller());

        // Test specific methods of the pedal boats
        System.out.println("PB-1 property hasCanopy: " + pb1.getHasCanopy());
        pb1.setHasCanopy(false);
        System.out.println("PB-1 removing Canopy, hasCanopy: " + pb1.getHasCanopy());
        System.out.println("PB-2 number of seats:" + pb2.getNumSeats());
        pb2.setNumSeats(2);
        System.out.println("PB-2 modifying PedalBoat, seats: " + pb2.getNumSeats());
    }
}
