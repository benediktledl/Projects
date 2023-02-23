import java.util.Scanner;
import java.text.DecimalFormat;
public class BMI {
    public static void calculateBMI(){
        Scanner scanner = new Scanner(System.in);

        double height;
        double mass;

        System.out.println("Enter height in metres:");

        try {
            height = scanner.nextDouble();
            if( height > 3 ){
                throw new Exception("You can't be that tall, can you?");
            }
        }
        catch ( Exception e ){
            System.out.println("Invalid Input! " + e);
            return;
        }
        System.out.println("Enter Weight:");

        try {
            mass = scanner.nextDouble();
            if( mass > 200 ){
                throw new Exception("You can't be that heavy, can you?");
            }
        }
        catch ( Exception e ){
            System.out.println("Invalid Character! " + e);
            return;
        }

        double bmi = mass / ( height * height );

        DecimalFormat df = new DecimalFormat("#.##");

        System.out.println("Your BMI is: " + df.format(bmi));
    }
}
