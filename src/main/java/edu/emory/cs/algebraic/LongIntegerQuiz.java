package edu.emory.cs.algebraic;

import java.util.Arrays;

/** @author Jinho D. Choi */
public class LongIntegerQuiz extends LongInteger {
    public LongIntegerQuiz(LongInteger n) { super(n); }

    public LongIntegerQuiz(String n) { super(n); }

    public void Helper(LongInteger n) {
        int m = Math.max(digits.length, n.digits.length); // whichever one is longer
        byte[] result = new byte[m];
        System.arraycopy(digits, 0, result, 0, digits.length);

        for (int i = 0; i < n.digits.length; i++) {
            if (result[i] < n.digits[i]) {
                result[i + 1] -= 1;
                result[i] += 10;
                result[i] -= n.digits[i];
            } else {
                result[i] -= n.digits[i];
            }
        }
        for (int i = 1; i < m; i++) {
            digits = result[m-i] == 0 ? Arrays.copyOf(result, m-i) : result;
        }

    }

    public void Helper2(LongInteger n) {
        int m = Math.max(digits.length, n.digits.length); // whichever one is longer
        byte[] result = new byte[m];
        System.arraycopy(digits, 0, result, 0, digits.length);

        for (int i = 0; i < n.digits.length; i++) {
            if (result[i] < n.digits[i]) {
                n.digits[i] -= result[i];
                result[i] = n.digits[i];

            } else {
                result[i] -= n.digits[i];

            }
        }
        for (int i = 1; i < m; i++) {
            digits = result[m-i] == 0 ? Arrays.copyOf(result, m-i) : result;
        }

    }

     @Override
    protected void addDifferentSign(LongInteger n) {
        // TODO: to be filled
            if (compareAbs(n) >= 0) { // means this abs > n abs
                Helper(n);
            } else {
                n.flipSign();
                flipSign();
                Helper2(n);
                sign = isPositive() ? Sign.NEGATIVE : Sign.POSITIVE;

                }

     }

}




