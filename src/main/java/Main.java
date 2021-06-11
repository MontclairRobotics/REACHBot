public class Main {
    private static boolean escape = false; //If an emergency stop is implemented, make this true when a button is pressed
    public static void main(String[] args){
        Robot.robotInit();
        while(!escape){
            Robot.robotPeriodic();
        }

    }
}