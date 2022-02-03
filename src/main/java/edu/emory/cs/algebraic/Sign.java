package edu.emory.cs.algebraic;

public enum Sign {
    POSITIVE('+'),
    NEGATIVE('-');

    private final char value; // final makes it a constant value

    Sign(char value) {
        this.value = value; // this points to the object  created by this constructor.
    }

    /** @return the value of the corresponding item. */
    public char value() {
        return value;
    }
}
