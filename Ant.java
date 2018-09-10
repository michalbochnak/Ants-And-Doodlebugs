//-----------------------------------------------------------------------------
// Author: Michal Bochnak, mbochn2
// Project: Project #7, Doodlebugs and Ants
// Class: CS 211
// Professor: Pat Troy
// Date: April 24, 2017
//-----------------------------------------------------------------------------
//
// Ant.java
//


// class for Ant, subclass of Creature
public class Ant extends Creature {

    // ------------------------------------------------------------------------
    // constructors
    // ------------------------------------------------------------------------

    // constructor, creates Ant at random free spot
    public Ant(Island island) {
        // call constructor from Creature class
        super(island);
    }

    // constructor, creates Doodlebug at specified spot
    public Ant (Island island, Coordinates coords) {
        // call constructor from Creature class
        super(island, coords);
    }


    // overriden method from Creature class
    // perform daily Ant tasks
    public void move() {

        wander();

        // spawn if at least 3 days elapsed since last spawn
        if (daysSinceLastSpawn() > 2)
            spawn();

    }

}   // end of Ant class