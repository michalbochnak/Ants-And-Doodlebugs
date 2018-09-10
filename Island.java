//-----------------------------------------------------------------------------
// Author: Michal Bochnak, mbochn2
// Project: Project #7, Doodlebugs and Ants
// Class: CS 211
// Professor: Pat Troy
// Date: April 24, 2017
//-----------------------------------------------------------------------------
//
// Island.java
//


import java.awt.*;

// class for Island
public class Island {

    private GridDisplay disp;       // GUI
    private Creature[][] grid;      // grid for Creatures
    private int dayCount;           // actual day on the Island
    private int rows;               // rows
    private int cols;               // columns
    private int numCreatures;       // number of Creatures on the Island

    // ------------------------------------------------------------------------
    // constructors
    // ------------------------------------------------------------------------

    // constructor, initialize the Island values
    public Island() {

        rows = 20;              // rows
        cols = 20;              // columns
        dayCount = 0;           // actual day on the Island
        numCreatures = 0;       // number of Creatures at the Island

        // create an instance of GUI
        disp = new GridDisplay(rows, cols);
        // create 2D array for creatures
        grid = new Creature[rows][cols];

        // initialize the GIU and Creatures
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // set color to background color
                disp.setColor(i, j, Color.LIGHT_GRAY);
                // set all Creatures to null
                grid[i][j] = null;
            }
        }
    }


    // ------------------------------------------------------------------------
    // getters
    // ------------------------------------------------------------------------
    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getNumCreatures() {
        return numCreatures;
    }

    public int getDayCount() {
        return dayCount;
    }


    // ------------------------------------------------------------------------
    // setters
    // ------------------------------------------------------------------------
    public void setSpotToBackgroundColor(int x, int y) {
        disp.setColor(x, y, Color.LIGHT_GRAY);
    }

    public void setSpotToNull(int x, int y) {
        grid[x][y] = null;
    }


    // ------------------------------------------------------------------------
    // methods
    // ------------------------------------------------------------------------

    // perform operation related to nex day on the island
    public void nextDay() {

        dayCount++;         // forward the day and display
        System.out.println("DayCount: " + dayCount);

        // loop through the grid positions and move doodlebugs
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                // make sure grid location contains a doodlebug and doodlebug has not moved this day
                if ((isOccupied(i, j) == true) && (grid[i][j].getDayLastMoved() != dayCount)
                        && (grid[i][j].getClass().getSimpleName().equals("Doodlebug"))) {

                    // Access the doodlebug at this position and move it
                    Creature c = grid[i][j];
                    c.move();
                    c.setDayLastMoved(dayCount);    // update last day moved
                }
            }
        }

        // loop through the grid positions and move ants
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                // make sure grid location contains a doodlebug and doodlebug has not moved this day
                if ((isOccupied(i, j) == true) && (grid[i][j].getDayLastMoved() != dayCount)
                        && (grid[i][j].getClass().getSimpleName().equals("Ant"))) {

                    // Access the doodlebug at this position and try to move it
                    Creature c = grid[i][j];
                    c.move();
                    c.setDayLastMoved(dayCount);    // update last day moved
                }
            }
        }

    }   // end of nextDay() method

    // perform adding creatures on the Island
    public boolean addCreature(Creature c, int x, int y) {

        // make sure x and y are valid
        if (!posValid(x, y))
            return false;

        // put the Creature on the grid
        grid[x][y] = c;

        // if Ant, set color to magenta, else set color to black ( doodlebug )
        String className = c.getClass().getSimpleName();
        if (className.equals("Ant"))            // Creature is Ant
            disp.setColor(x, y, Color.magenta);
        else                                    // Creature is Doodlebug
            disp.setColor(x, y, Color.black);

        numCreatures++;     // update number of Creatures at the Island

        return true;        // Creature added successfully
    }

    // perform moving the Creature on the Island
    public boolean moveCreature(int currX, int currY, int nextX, int nextY) {

        // make sure x and y positions are both valid
        if ( (!posValid(currX, currY)) || (!posValid(nextX, nextY)) )
            return false;

        // make sure a creature exists at the CURR space
        if (isOccupied(currX, currY) == false)
            return false;
        // make sure no creature is already at the NEXT space
        if (isOccupied(nextX, nextY) == true)
            return false;

        // move the creature to the new position,
        // set previous position to null
        grid[nextX][nextY] = grid[currX][currY];
        grid[currX][currY] = null;

        // assign grid Creature to c
        Creature c = grid[nextX][nextY];

        // update the GridDisplay colors to show the creature has moved
        String className = c.getClass().getSimpleName();
        if (className.equals("Ant"))        // Creature was Ant
            disp.setColor(nextX, nextY, Color.magenta);
        else                                // Creature was Doodlebug
            disp.setColor(nextX, nextY, Color.black);

        return true;
    }   // end of moveCreature() method

    // perform removing the Doodlebug from the Island
    public void removeDoodlebug(Doodlebug d) {

        // grab coordinates for doodlebug
        int x = d.getX();
        int y = d.getY();

        // set grid to null
        grid[x][y] = null;

        // update color to background color
        disp.setColor(x, y, Color.LIGHT_GRAY);

        numCreatures--;     // decrement number of Creatures at the Island
    }

    // perform eating Ant by Doodlebug
    public void eatAnt(Coordinates doodlebugCoords, Coordinates antCoords) {

        // remove Ant
        grid[antCoords.getX()][antCoords.getY()] = null;

        // move doodlebug on the ant spot
        grid[antCoords.getX()][antCoords.getY()] =
                grid[doodlebugCoords.getX()][doodlebugCoords.getY()];
        // and change color
        disp.setColor(antCoords.getX(), antCoords.getY(), Color.black);

        // clear old doodlebug spot
        grid[doodlebugCoords.getX()][doodlebugCoords.getY()] = null;
        // change color to background color
        disp.setColor(doodlebugCoords.getX(), doodlebugCoords.getY(), Color.LIGHT_GRAY);

        numCreatures--;     // decrement number of Creatures at the Island
    }

    // find free spot around the Creature
    public Coordinates findFreeSpot(Creature c) {

        // grab coords for creature position
        int x = c.getX();
        int y = c.getY();

        // coords for free spot around
        Coordinates coords = generateCoordsRandomly(c);

        // try to generate random coords couple times
        for (int i = 0; i < 20; i++) {
            // verify is randomly generated coords are valid
            if ((posValid(coords.getX(), coords.getY()))
                    && (!(isOccupied(coords.getX(), coords.getY())))) {

                return coords;      // coords generated successfully, return coords
            }

            // generate new coords randomly
            coords = generateCoordsRandomly(c);
        }

        // if got here, random generation of the coords was not successful,
        // reset coords
        coords.setX(-999);
        coords.setY(-999);

        // check all spots around to see if free spot is available
        if (posValid(x - 1, y - 1) && !(isOccupied(x - 1, y - 1))) {
            coords.setX(x - 1);
            coords.setY(y - 1);
        } else if (posValid(x - 1, y + 1) && !(isOccupied(x - 1, y + 1))) {
            coords.setX(x - 1);
            coords.setY(y + 1);
        } else if (posValid(x + 1, y) && !(isOccupied(x + 1, y))) {
            coords.setX(x + 1);
            coords.setY(y);
        } else if (posValid(x, y - 1) && !(isOccupied(x, y - 1))) {
            coords.setX(x);
            coords.setY(y - 1);
        } else if (posValid(x, y + 1) && !(isOccupied(x, y + 1))) {
            coords.setX(x);
            coords.setY(y + 1);
        } else if (posValid(x + 1, y - 1) && !(isOccupied(x + 1, y - 1))) {
            coords.setX(x + 1);
            coords.setY(y - 1);
        } else if (posValid(x - 1, y) && !(isOccupied(x - 1, y))) {
            coords.setX(x - 1);
            coords.setY(y);
        } else if (posValid(x + 1, y) && !(isOccupied(x + 1, y))) {
            coords.setX(x + 1);
            coords.setY(y);
        } else if (posValid(x + 1, y + 1) && !(isOccupied(x + 1, y + 1))) {
            coords.setX(x + 1);
            coords.setY(y + 1);
        }

        return coords;      // return new coords

    }   // end of findFreeSpot() method

    // search for Ant around given location and return the coordinates
    public Coordinates findAnt(Creature c) {

        // Coordinates to be returned
        Coordinates coords = generateCoordsRandomly(c);

        // grab coords for creature position
        int x = c.getX();
        int y = c.getY();

        // try to generate random coordinates couuple time
        for (int i = 0; i < 20; i++) {

            // check if given coords are corresponding to Ant
            if (isAnt(coords.getX(), coords.getY())) {
                return coords;      // return coords for Ant location
            }

            // try to generate random coords again
            coords = generateCoordsRandomly(c);
        }


        // if got here, random generation of coordinates was not successful,
        // reset coords to default values
        coords.setX(-999);
        coords.setY(-999);

        // check all spots around the creature and update coords if Ant is found
        if (isAnt(x-1, y-1)) {
            coords.setX(x-1);
            coords.setY(y-1);
        }
        else if (isAnt(x-1, y)) {
            coords.setX(x-1);
            coords.setY(y);
        }
        else if (isAnt(x-1, y+1)) {
            coords.setX(x-1);
            coords.setY(y+1);
        }
        else if (isAnt(x, y-1)) {
            coords.setX(x);
            coords.setY(y-1);
        }
        else if (isAnt(x, y+1)) {
            coords.setX(x);
            coords.setY(y+1);
        }
        else if (isAnt(x+1, y-1)) {
            coords.setX(x+1);
            coords.setY(y-1);
        }
        else if (isAnt(x+1, y)) {
            coords.setX(x+1);
            coords.setY(y);
        }
        else if (isAnt(x+1, y+1)) {
            coords.setX(x+1);
            coords.setY(y+1);
        }

        return coords;      // return Ant coords

    }   // end of findAnt() method

    // generates new coords randomly around given creature,
    // one of the 8 available spots is selected at random
    public Coordinates generateCoordsRandomly(Creature c) {

        // coords to be returned
        Coordinates coords = new Coordinates();

        // grab x and y from given creature
        int x = c.getX();
        int y = c.getY();

        // select random direction
        int direction = (int)(Math.random() * 8);


        if (direction == 0) { // attempt to move left - up
            coords.setX(x-1);
            coords.setY(y-1);
        }
        if (direction == 1) {// attempt to move up
            coords.setX(x);
            coords.setY(y-1);
        }
        if (direction == 2) { // attempt to move right - up
            coords.setX(x+1);
            coords.setY(y-1);
        }
        if (direction == 3) { // attempt to move left
            coords.setX(x-1);
            coords.setY(y);
        }
        if (direction == 4) { // attempt to move right
            coords.setX(x+1);
            coords.setY(y);
        }
        if (direction == 5) { // attempt to move left - down
            coords.setX(x-1);
            coords.setY(y+1);
        }
        if (direction == 6) { // attempt to move down
            coords.setX(x);
            coords.setY(y+1);
        }
        if (direction == 7) { // attempt to move right - down
            coords.setX(x);
            coords.setY(y+1);
        }

        return coords;      // return new coords

    }   // end of generateCoordsRandomly() method

    // check if Creature at given location is Ant
    // return true if so, false is returned otherwise
    public boolean isAnt(int x, int y) {

        // make sure x and y positions are both valid
        if (x < 0 || x >= rows || y < 0 || y >= cols)
            return false;
        // return false if spot is empty
        else if (grid[x][y] == null)
            return false;
        // check if this is Ant, return true is so
        else if (grid[x][y].getClass().getSimpleName().equals("Ant"))
            return true;        // Ant
        // not and Ant, return false
        else
            return false;
    }

    // count and return number of Ants on the Island
    public int countNumOfAnts() {

        int count = 0;      // number of Ants

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                // verify if grid location contains Ant and count it if so
                if (isAnt(i, j) == true) {
                    count++;
                }
            }
        }

        return count;   // return number of Ants
    }

    // check if the given spot is occupied, returns true if so,
    // false is returned otherwise
    public boolean isOccupied(int i, int j) {

        if (grid[i][j] == null)
            return false;       // not occupied
        else
            return true;        // occupied
    }

    // check if the given position is in the range of actual grid
    // returns true if so, false is returned otherwise
    public boolean posValid(int x, int y) {

        // make sure x and y positions are both valid
        if (x < 0 || x >= rows || y < 0 || y >= cols)
            return false;           // out of range
        else
            return true;            // in range
    }

}   // end of Island class