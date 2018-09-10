//-----------------------------------------------------------------------------
// Author: Michal Bochnak, mbochn2
// Project: Project #7, Doodlebugs and Ants
// Class: CS 211
// Professor: Pat Troy
// Date: April 24, 2017
//-----------------------------------------------------------------------------
//
// Coordinates.java
//


// coordinates class, holds set of position values: x and y
public class Coordinates {

    private int x;
    private int y;


    // ------------------------------------------------------------------------
    // constructors
    // ------------------------------------------------------------------------

    // constructor, initializes x and y to -999
    public Coordinates() {

        x = -999;
        y = -999;
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


    // ------------------------------------------------------------------------
    // setters
    // ------------------------------------------------------------------------
    public void setX(int newX) {
        x = newX;
    }

    public void setY(int newY) {
        y = newY;
    }

}   // end of Coordinates method
