package ua.javacoders.utils;

/**
 * The Emoji enum represents a collection of commonly used emoji characters.
 * Each emoji is defined by a pair of Unicode characters.
 * The Emoji enum provides a toString method to convert the enum value to a String representation of the emoji.
 */
public enum Emoji {


    HOUSE_BUILDING('\uD83C', '\uDFE0'),
    BAR_CHART('\uD83D', '\uDCCA'),
    WARNING_SIGN(null, '\u26A0'),
    GEAR(null, '\u2699'),
    WHITE_HEAVY_CHECK_MARK(null, '\u2705'),
    LEFTWARDS_ARROW_WITH_HOOK(null, '\u21A9'),
    DOOR('\uD83D', '\uDEAA'),
    MONEY_BAG('\uD83D', '\uDCB0'),
    BANK('\uD83C', '\uDFE6'),
    HEAVY_DOLLAR_SIGN('\uD83D', '\uDCB2'),
    DOLLAR('\uD83D', '\uDCB5'),
    EURO('\uD83D', '\uDCB6'),
    LEFT_RIGHT_ARROW(null, '\u2194');

    final Character firstChar;
    final Character secondChar;

    /**
     * Constructs an Emoji with the specified Unicode characters.
     *
     * @param firstChar  the first Unicode character of the emoji
     * @param secondChar the second Unicode character of the emoji
     */
    Emoji(Character firstChar, Character secondChar) {
        this.firstChar = firstChar;
        this.secondChar = secondChar;
    }

    /**
     * Returns a String representation of the emoji.
     * This method combines the Unicode characters of the emoji into a single String.
     *
     * @return a String representation of the emoji
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (this.firstChar != null) {
            sb.append(this.firstChar);
        }
        if (this.secondChar != null) {
            sb.append(this.secondChar);
        }

        return sb.toString();
    }
}