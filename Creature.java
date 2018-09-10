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


// class for Creature, superclass for Doodlebug and Ant
public class Creature {

        private Island isl;         // island
        private int x;              // x position value
        private int y;              // y position value
        private int dayLastMoved;   // day when last moved
        private int dayLastSpawn;   // day when last spawned


    // ------------------------------------------------------------------------
    // constructors
    // ------------------------------------------------------------------------

    // constructor, place Creature at random free spot on the Island
    public Creature (Island island) {

            isl = island;
            x = (int)(Math.random() * island.getRows() );
            y = (int)(Math.random() * island.getCols() );

            // find free spot on the island
            while (island.isOccupied(x, y) == true) {
                x = (int) (Math.random() * island.getRows());
                y = (int) (Math.random() * island.getCols());
            }

            // initialize to 0's
            dayLastMoved = 0;
            dayLastSpawn = 0;

            // put Creature on the Island
            island.addCreature (this, x, y);
    }

    // constructor, place Creature at specified spot on the Island
    public Creature(Island island, Coordinates coords) {

        isl = island;
        // get coordinates and store into x, y
        x = coords.getX();
        y = coords.getY();

        // set to current day
        dayLastMoved = isl.getDayCount();
        dayLastSpawn = isl.getDayCount();

        // put Creature at the Island
        island.addCreature (this, x, y);
    }


    // ------------------------------------------------------------------------
    // getters
    // ------------------------------------------------------------------------
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDayLastMoved () {
        return dayLastMoved;
    }

    public Island getIsland() {
        return isl;
    }


    // ------------------------------------------------------------------------
    // setters
    // ------------------------------------------------------------------------
    public void setDayLastMoved (int dlm) {
        dayLastMoved = dlm;
    }

    public void setDayLastSpawn(int dls) {
        dayLastSpawn = dls;
    }

    public void setX(int newX) {
        x = newX;
    }

    public void setY(int newY) {
        y = newY;
    }


    // ------------------------------------------------------------------------
    // methods
    // ------------------------------------------------------------------------

    // perform move, overriden in Ant and Creature class
    public void move() {

        // overriden in Ant / Doodlebug classes
    }

    // perform wander
    public void wander () {

        // initialize nextX and nextY to -999
        int nextX = -999;
        int nextY = -999;

        // generate random direction
        int direction = (int)(Math.random() * 8);


        if (direction == 0) { // attempt to move left - up
            nextX = x-1; nextY = y-1;
        }
        if (direction == 1) { // attempt to move up
            nextX = x; nextY = y-1;
        }
        if (direction == 2) { // attempt to move right - up
            nextX = x+1; nextY = y-1;
        }
        if (direction == 3) { // attempt to move left
            nextX = x-1; nextY = y;
        }
        if (direction == 4) { // attempt to move right
            nextX = x+1; nextY = y;
        }
        if (direction == 5) { // attempt to move left - down
            nextX = x-1; nextY = y+1;
        }
        if (direction == 6) { // attempt to move down
            nextX = x; nextY = y+1;
        }
        if (direction == 7) { // attempt to move right - down
            nextX = x; nextY = y+1;
        }


        // try to move the Creature to the NEXT position,
        // and update display and coordinates
        if ( isl.moveCreature ( x, y, nextX, nextY ) == true) {

            // update display grid
            isl.setSpotToBackgroundColor(x, y);
            isl.setSpotToNull(x,y);

            // update coordinates
            x = nextX;
            y = nextY;
        }
    }   // end of wander() method

    // perform spawn
    public void spawn() {

        // try to find free spot around, and write it into coords
        Coordinates coords = isl.findFreeSpot(this);

        // if x have default value of -999 free spot was not found, return
        if (coords.getX() == -999) {
            return;
        }
        // free spot available, so spawn
        else {
            // verify what kind of Creature should be added
            String className = this.getClass().getSimpleName();
            if (className.equals("Ant")) {      // add Ant
                new Ant(isl, coords);
            }
            else {                              // add Doodlebug
                new Doodlebug(isl, coords);
            }

            // update last day spawn
            this.setDayLastSpawn(isl.getDayCount());
        }
    }

    // returns the number of days elapsed since last spawn
    public int daysSinceLastSpawn() {
        return isl.getDayCount() - dayLastSpawn;
    }

}   // end of Creature class