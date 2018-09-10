//-----------------------------------------------------------------------------
// Author: Michal Bochnak, mbochn2
// Project: Project #7, Doodlebugs and Ants
// Class: CS 211
// Professor: Pat Troy
// Date: April 24, 2017
//-----------------------------------------------------------------------------
//
// Doodlebug.java
//


// class for Doodlebug, subclass of Creature
public class Doodlebug extends Creature {

    // day last eaten is additional property for Doodlebug
    private int dayLastEat;


    // ------------------------------------------------------------------------
    // constructors
    // ------------------------------------------------------------------------

    // constructor, creates Doodlebug at random free spot
    public Doodlebug (Island island) {

        // call constructor from Creature class
        super(island);
        // set dayLastEat as current day
        dayLastEat = island.getDayCount();
    }

    // constructor, creates Doodlebug at specified spot
    public Doodlebug (Island island, Coordinates coords) {
        // call constructor from Creature class
        super(island, coords);
        // set dayLastEat as current day
        dayLastEat = island.getDayCount();
    }


    // ------------------------------------------------------------------------
    // methods
    // ------------------------------------------------------------------------

    // overriden method from Creature class
    // perform daily Doodlebug tasks
    public void move() {

        // if hunt is not succesful, wander
        if (hunt() == false)
            wander();

        // spawn if at least 8 days elapsed since last spawn
        if (daysSinceLastSpawn() > 7)
            spawn();

        // starve if not eaten for 3 or more days
        if ((daysSinceLastEat(this.getIsland())) > 2)
            this.starve(this.getIsland());

    }

    // perform hunt
    public boolean hunt () {

        // store Doodlebug Coordinates as set of Coordinates
        Coordinates doodlebugCoords = new Coordinates();
        doodlebugCoords.setX(this.getX());
        doodlebugCoords.setY(this.getY());

        // get the Island
        Island island = this.getIsland();

        // check if Ant is on the adjacent spot, get the coords if so
        Coordinates antCoords = island.findAnt(this);

        // if coords have default values ( -999 ), Ant was not found
        if (antCoords.getX() == -999) {
            return false;       // hunt unsuccessful
        }
        else {                  // eat the Ant
            island.eatAnt(doodlebugCoords, antCoords);
            // update doodlebug coords
            this.setX(antCoords.getX());
            this.setY(antCoords.getY());

            // set dayLastEat as current day
            dayLastEat = this.getIsland().getDayCount();

            return true;        // hunt successful
        }
    }

    // perform starve
    public void starve (Island island) {

        // remove doodlebug from the Island
        island.removeDoodlebug(this);
    }

    // returns days elapsed since Doodlebug last ate
    public int daysSinceLastEat(Island island) {

        return island.getDayCount() - this.dayLastEat;
   }

}   // end of Doodlebug class
