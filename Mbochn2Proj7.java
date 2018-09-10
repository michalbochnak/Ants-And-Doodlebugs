//-----------------------------------------------------------------------------
// Author: Michal Bochnak, mbochn2
// Project: Project #7, Doodlebugs and Ants
// Class: CS 211
// Professor: Pat Troy
// Date: April 24, 2017
//-----------------------------------------------------------------------------
//
// Mbochn2Proj7.java
//

// main class MBochn2Proj7
public class Mbochn2Proj7 {

    public static void main(String[] args) {

        System.out.println(args.length);

        // display Author info
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("Author: Michal Bochnak, mbochn2");
        System.out.println("Project: Project #7, Doodlebugs and Ants");
        System.out.println("Class: CS 211");
        System.out.println("Professor: Pat Troy");
        System.out.println("Date: April 24, 2017");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("");

        // display app info
        System.out.println("Welcome to the Doodlebugs and Ants simulation.");
        System.out.println("Island consists of 5 Doodlebugs and 100 Ants at the beginning.");
        System.out.println("Doodlebugs color is black, Ants color is magenta, Island color is light gray.");
        System.out.println("Simulation will last until all Creatures die, " +
                "Island will be filled up with Ants, or forever if none of the previous will occur.");
        System.out.println("Enjoy.");
        System.out.println("");


        int sleep = 500;    // default delay
        boolean userSleepSet = false;


        // process command line arguments
        if (args.length == 2) {

            if (args[0].equals("-d")) {
                // use delay specified by user
                try {
                    userSleepSet = true;
                    sleep = Integer.parseInt(args[1]);
                }
                // if not a number reset sleep to default
                catch (NumberFormatException nfe) {
                    sleep = 500;
                    userSleepSet = false;   // user sleep not set
                    System.out.println("Usage: java <program_name> -d <your_delay_in_milliseconds>");
                    System.exit(-1);
                }
            }
            else {
                System.out.println("Usage: java <program_name> -d <your_delay_in_milliseconds>");
                System.exit(-1);
            }
        }
        else if (args.length == 0) {
            sleep = 500;
        }
        else {
                System.out.println("Usage: java <program_name> -d <your_delay_in_milliseconds>");
                System.exit(-1);
        }


        // display information about used delay for the day
        if (!userSleepSet) {
            System. out.println("Running simulation with default day length of " + sleep + " milliseconds.");
        } else {
            System.out.println("Running simulation with day length of " + sleep + " milliseconds.");
        }


        // create my island
        Island island = new Island();

        // create 5 doodlebugs
        for (int i = 0 ; i < 5 ; i++)
        {
            // check if island is not full, to avoid infinite loop,
            // if number of doodlebugs is greater than 400
            if (island.getNumCreatures() == 400 )
                break;

            new Doodlebug(island);      // create new doodlebug
        }


        // create 100 ants
        for (int i = 0 ; i < 100; i++)
        {
            // check if island is not full, to avoid infinite loop,
            // if number of Creatures on the Island is greater than 400
            if (island.getNumCreatures() == 400 )
                break;

            new Ant(island);            // create new ant
        }


        // run simulation until all Creatures will die, or until Ants will fill out the Island,
        // or forever if none of previous will occur
        while (true) {

            GridDisplay.mySleep ( sleep );      // display Island using specified delay
            island.nextDay();                   // simulate next day

            // if 400 Ants is on the Island, simulation ends, Island is full,
            // nothing can change, display message and break out of the loop
            if (island.countNumOfAnts() == 400) {
                System.out.println("Ants survived. Simulation lasted "
                        + island.getDayCount() + " days.");
                System.out.println("Num of Ants on the Island: "
                        + island.countNumOfAnts());
                break;
            }
            // if 0 Creatures is on the Island, simulation ends, Island is empty,
            // nothing can change, display message and break out of the loop
            if (island.getNumCreatures() == 0) {
                System.out.println("Both species extinct. Simulation lasted "
                        + island.getDayCount() + " days.");
                System.out.println("Num of Creatures on the Island: "
                        + island.getNumCreatures());
                break;
            }
        }

        // leave final Island set up for couple seconds
        GridDisplay.mySleep ( 5000 );

        System.exit(0);

    }   // end of main method

}   // end of Mbochn2Proj7 class

