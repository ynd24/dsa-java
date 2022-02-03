package edu.emory.cs.algebraic;

public abstract class SignedNumeral<T extends SignedNumeral<T>> implements Numeral<T> { // T can be safely passed onto Numeral
    // because if it is a subclass of SignedNumeral,
    // it must be a subclass of Numeral,
    // which is how T is defined in the Numeral class.

    /** The sign of this numeral. */
    protected Sign sign;

    /**
     * Create a signed numeral.
     * the default sign is {@link Sign#POSITIVE}.
     */
    public SignedNumeral() { // default constructor no param
        this(Sign.POSITIVE); // calls constructor in L23
    }

    /**
     * Create a signed numeral.
     * @param sign the sign of this numeral.
     */
    public SignedNumeral(Sign sign) { // another constructor with sign param
        this.sign = sign;
    }

    /** @return true if this numeral is positive; otherwise, false. */
    public boolean isPositive() {
        return sign == Sign.POSITIVE;
    }

    /** @return true if this numeral is negative; otherwise, false. */
    public boolean isNegative() {
        return sign == Sign.NEGATIVE;
    }

    /** Flips the sign of this numeral. */
    public void flipSign() {
        sign = isPositive() ? Sign.NEGATIVE : Sign.POSITIVE;
        // if sign is +ve then change it to negative else positive
    }

    /**
     * Subtracts `n` from this numeral.
     * @param n the numeral to be subtracted.
     */
    public void subtract(T n) {
        n.flipSign(); add(n); n.flipSign();
    }

    /**
     * Multiplies `n` to this numeral.
     * @param n the numeral to be multiplied.
     */
    public abstract void multiply(T n);
}
