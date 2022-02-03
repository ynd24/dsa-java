package edu.emory.cs.algebraic;

import java.util.Arrays;

/** @author Jinho D. Choi */
public class LongIntegerQuiz extends LongInteger {
    public LongIntegerQuiz(LongInteger n) { super(n); }

    public LongIntegerQuiz(String n) { super(n); }

    @Override
    protected void addDifferentSign(LongInteger n) {
        // TODO: to be filled
        int m = Math.max(digits.length, n.digits.length); // whichever one is longer
        byte[] result = new byte[m];
        System.arraycopy(digits, 0, result, 0, digits.length);

        for (int i = 0; i < n.digits.length; i++) {
            if (compareAbs(n) >= 0) { // means this abs > n abs
                if (result[i] < n.digits[i]) {
                    result[i + 1] -= 1;
                    result[i] += 10;
                    result[i] += n.digits[i];
                } else {
                    result[i] += n.digits[i];
                }
            } else {
                n.flipSign();
                if (n.digits[i] < result[i]) {
                    n.digits[i+1] -= 1;
                    n.digits[i] += 10;
                    result[i] = (byte) (n.digits[i] - result[i]);

                } else {
                    result[i] = (byte) (n.digits[i] - result[i]);
                }

            }

            }
        digits = result[m] == 0 ? Arrays.copyOf(result, m) : result;
        }

    }
