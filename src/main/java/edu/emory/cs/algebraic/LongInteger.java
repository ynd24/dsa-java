package edu.emory.cs.algebraic;

import java.util.Arrays;
import java.security.InvalidParameterException;


public class LongInteger extends SignedNumeral<LongInteger> implements Comparable<LongInteger> {

    protected byte[] digits;

    /** Creates a long integer with the default value of "0". */
    public LongInteger() { // default constr that initialize int with 0
        // by calling other constr
        this("0");
    }

    /**
     * Creates a long integer by copying the specific object.
     * @param n the object to be copied.
     */
    public LongInteger(LongInteger n) {
        super(n.sign); // calls constr in SignedNumeral
        digits = Arrays.copyOf(n.digits, n.digits.length); // creates a new array by copying n.digits
    }

    /**
     * Creates a long integer with the specific sign and values.
     * @param n the sign and values to be set.
     * @see #set(String)
     */
    public LongInteger(String n) {
        set(n);
    }
    /**
     * Sets the sign and values of this integer.
     * @param n the sign and values to be set.
     * @throws NullPointerException      when `n` is null.
     * @throws InvalidParameterException when `n` contains non-digit character
     *                                   except for the first character that can be [+-\d].
     */

    public void set(String n) {
        // 'n' must not be null
        if (n == null)
            throw new NullPointerException();

        // set this.sign
        sign = switch (n.charAt(0)) {
            case '-' -> { n = n.substring(1); yield Sign.NEGATIVE; }
            case '+' -> { n = n.substring(1); yield Sign.POSITIVE; }
            default -> Sign.POSITIVE;
        };

        // set this.digits
        digits = new byte[n.length()];

        for (int i = 0, j = n.length() - 1; i < n.length(); i++, j--) {
            byte v = (byte)(n.charAt(i) - 48);
            digits[j] = v;
            if (0 > v || v > 9) {
                String s = String.format("%d is not a valid value", v);
                throw new InvalidParameterException(s);
            }
        }
    }

    @Override
    public void flipSign() { /* to be implemented */ }

    @Override
    public void add(LongInteger n) {
        if (sign == n.sign)
            addSameSign(n);
        else
            addDifferentSign(n);
    }

    /**
     * Adds the specific integer that has the same sign as this integer.
     * @param n the integer to be added with the same sign.
     */
    protected void addSameSign(LongInteger n) {
        // copy this integer to result[]
        int m = Math.max(digits.length, n.digits.length); // whichever one is longer
        byte[] result = new byte[m + 1]; // max no. of digits added from addition is 1
        System.arraycopy(digits, 0, result, 0, digits.length);

        // add n to result
        for (int i = 0; i < n.digits.length; i++) {
            result[i] += n.digits[i];
            if (result[i] >= 10) {
                result[i] -= 10;
                result[i + 1] += 1; // carry over
            }
        }

        // set this.digits
        digits = result[m] == 0 ? Arrays.copyOf(result, m) : result; // trims most sig dig if it is 0
    }

    /**
     * Adds the specific integer that has a different sign as this integer.
     * @param n the integer to be added with a different sign
     */
    protected void addDifferentSign(LongInteger n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void multiply(LongInteger n) {
        // set this.sign
        sign = (sign == n.sign) ? Sign.POSITIVE : Sign.NEGATIVE;

        // multiply this and n and save it to result
        byte[] result = new byte[digits.length + n.digits.length];
        for (int i = 0; i < digits.length; i++) {
            for (int j = 0; j < n.digits.length; j++) {
                int k = i + j, prod = digits[i] * n.digits[j];
                result[k] += prod;
                result[k + 1] += result[k] / 10;
                result[k] %= 10;
            }
        }

        // set this.digits
        int m; for (m = result.length - 1; m > 0; m--)
            if (result[m] != 0) break;
        digits = ++m < result.length ? Arrays.copyOf(result, m) : result;
    }

    @Override
    public String toString() {
        StringBuilder build = new StringBuilder();
        if (sign == Sign.NEGATIVE) build.append("-");
        for (int i = digits.length - 1; i >= 0; i--)
            build.append(digits[i]);
        return build.toString();
    }

    @Override
    public int compareTo(LongInteger n) {
        if (isPositive())
            return n.isNegative() ? 1 : compareAbs(n);
        else
            return n.isPositive() ? -1 : -compareAbs(n);
    }

    /**
     * @param n the object to be compared.
     * @return a negative integer, zero, or a positive integer as the absolute value of this object is
     * less than, equal to, or greater than the absolute value of the specified object.
     */
    public int compareAbs(LongInteger n) {
        int diff = digits.length - n.digits.length;

        if (diff == 0) {
            for (int i = digits.length - 1; i >= 0; i--) {
                diff = digits[i] - n.digits[i];
                if (diff != 0) break;
            }
        }

        return diff;
    }
}
