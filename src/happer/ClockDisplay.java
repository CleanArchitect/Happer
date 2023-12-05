/* Created on 10 juni 2007 */

package happer;

/**
 * This class is used to make a 2 digit ClockDisplay for the Game stopwatch.
 * It has an rollover value, and a increment. The rollover value prefends
 * the displayvalue of the Clockdisplay to go further to the given max value.
 * 
 * @author Kay
 */
public class ClockDisplay {
    private int limit;
    private int value;

    /**
     * Makes a ClockDisplay object with a given rollovervalue.
     * 
     * @param rollOverLimit Maximum displayvalue
     */
    public ClockDisplay(int rollOverLimit) {
        limit = rollOverLimit;
        value = 0;
    }

    public int getValue() {
        return value;
    }

    public String getDisplayValue() {
        if (value < 10) {
            return "0" + value;
        } else {
            return "" + value;
        }
    }

    public void setValue(int replacementValue) {
        if ((replacementValue >= 0) && (replacementValue < limit)) {
            value = replacementValue;
        }
    }

    public void increment() {
        value = (value + 1) % limit;
    }
}