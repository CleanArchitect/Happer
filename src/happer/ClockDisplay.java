/*
 * ClockDisplay.java
 *
 * Created on 10 juni 2007, 21:43
 */

package happer;

/**
 * This class is used to make a 2 digit ClockDisplay for the Game stopwatch.
 * It has an rollover value, and a increment. The rollover value prefends
 * the displayvalue of the Clockdisplay to go further to the given max value.
 * @author Nemesis
 */
public class ClockDisplay{
    private int limit;
    private int value;

    /**
     * Makes a ClockDisplay object with a given rollovervalue.
     * @param rollOverLimit Maximum displayvalue
     */
    public ClockDisplay(int rollOverLimit) {
        limit = rollOverLimit;
        value = 0;
    }    

    /**
     * Returns the current value of the ClockDisplay object.
     * @return The current value
     */
    public int getValue() {
        return value;
    }

    /**
     * Returns a String with the current value in 2 digits.
     * @return The 2 digit value
     */
    public String getDisplayValue() {
        if(value < 10){
            return "0" + value;
        } else {
            return "" + value;
        }
    }

    /**
     * Sets the current value to the new value.
     * @param replacementValue The new Value for the ClockDisplay object.
     */
    public void setValue(int replacementValue) {
        if((replacementValue >= 0) && (replacementValue < limit)){
            value = replacementValue;
        }
    }

    /**
     * Increments the current Value with 1 but not further than the rolloverlimit.
     */
    public void increment() {
        value = (value + 1) % limit;
    }
}
