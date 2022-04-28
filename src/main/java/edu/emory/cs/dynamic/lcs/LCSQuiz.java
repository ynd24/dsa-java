package edu.emory.cs.dynamic.lcs;

import java.util.Set;
import java.util.*;

public class LCSQuiz extends LCSDynamic {

    /**
     * @param a the first string.
     * @param b the second string.
     * @return a set of all longest common sequences between the two strings.
     */

    public Set<String> solveAll(String a, String b) {

        int[][] lookup = new int[a.length() + 1][b.length() + 1];
        Set<String> lcs = LCS(a, b, lookup);

        return lcs;

    }

    public static Set<String> LCS(String X, String Y, int[][] lookup)
    {

        LCSLength(X, Y, lookup);
        List<String> lcs = LCS(X, Y, X.length(), Y.length(), lookup);

        return new HashSet<>(lcs);
    }

    public static List<String> LCS(String X, String Y, int m, int n, int[][] lookup)
    {

        if (m == 0 || n == 0)
        {

            return new ArrayList<>(Collections.nCopies(1, ""));
        }

        if (X.charAt(m - 1) == Y.charAt(n - 1))
        {

            List<String> lcs = LCS(X, Y, m - 1, n - 1, lookup);

            for (int i = 0; i < lcs.size(); i++) {
                lcs.set(i, lcs.get(i) + (X.charAt(m - 1)));
            }

            return lcs;
        }

        if (lookup[m - 1][n] > lookup[m][n - 1]) {
            return LCS(X, Y, m - 1, n, lookup);
        }

        if (lookup[m][n - 1] > lookup[m - 1][n]) {
            return LCS(X, Y, m, n - 1, lookup);
        }

        List<String> top = LCS(X, Y, m - 1, n, lookup);
        List<String> left = LCS(X, Y, m, n - 1, lookup);

        top.addAll(left);

        return top;
    }

    public static void LCSLength(String X, String Y, int[][] lookup)
    {

        for (int i = 1; i <= X.length(); i++)
        {
            for (int j = 1; j <= Y.length(); j++)
            {

                if (X.charAt(i - 1) == Y.charAt(j - 1)) {
                    lookup[i][j] = lookup[i - 1][j - 1] + 1;
                }

                else {
                    lookup[i][j] = Integer.max(lookup[i - 1][j], lookup[i][j - 1]);
                }
            }
        }
    }

}