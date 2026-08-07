import java.util.Arrays;

class Solution {

    // Helper to get prime factor breakdown of a single digit d (1-9)
    private void getDigitFactors(int d, int[] factors) {
        Arrays.fill(factors, 0);
        if (d == 2) factors[0] = 1;
        else if (d == 3) factors[1] = 1;
        else if (d == 4) factors[0] = 2;
        else if (d == 5) factors[2] = 1;
        else if (d == 6) { factors[0] = 1; factors[1] = 1; }
        else if (d == 7) factors[3] = 1;
        else if (d == 8) factors[0] = 3;
        else if (d == 9) factors[1] = 2;
    }

    // Returns the lexicographically smallest digit string satisfying remaining factors
    private String getMinDigits(int c2, int c3, int c5, int c7) {
        c2 = Math.max(0, c2);
        c3 = Math.max(0, c3);
        c5 = Math.max(0, c5);
        c7 = Math.max(0, c7);

        String best = "";
        int minLen = Integer.MAX_VALUE;

        // Search over bounded counts for 6s, 4s, 3s, 2s to minimize total digit count
        for (int n6 = 0; n6 <= 2; ++n6) {
            for (int n4 = 0; n4 <= 1; ++n4) {
                for (int n3 = 0; n3 <= 1; ++n3) {
                    for (int n2 = 0; n2 <= 2; ++n2) {
                        int rem3 = c3 - n6 - n3;
                        int rem2 = c2 - n6 - 2 * n4 - n2;

                        int n9 = (rem3 > 0) ? (rem3 + 1) / 2 : 0;
                        int n8 = (rem2 > 0) ? (rem2 + 2) / 3 : 0;

                        int cov2 = n8 * 3 + n6 * 1 + n4 * 2 + n2 * 1;
                        int cov3 = n9 * 2 + n6 * 1 + n3 * 1;

                        if (cov2 >= c2 && cov3 >= c3) {
                            int len = n9 + n8 + c7 + n6 + c5 + n4 + n3 + n2;
                            StringBuilder sb = new StringBuilder();
                            sb.append("2".repeat(n2))
                              .append("3".repeat(n3))
                              .append("4".repeat(n4))
                              .append("5".repeat(c5))
                              .append("6".repeat(n6))
                              .append("7".repeat(c7))
                              .append("8".repeat(n8))
                              .append("9".repeat(n9));

                            String s = sb.toString();
                            if (len < minLen || (len == minLen && s.compareTo(best) < 0)) {
                                minLen = len;
                                best = s;
                            }
                        }
                    }
                }
            }
        }
        return best;
    }

    public String smallestNumber(String num, long t) {
        // Step 1: Prime factorize t
        int a = 0, b = 0, c = 0, d = 0;
        long tempT = t;
        while (tempT % 2 == 0) { a++; tempT /= 2; }
        while (tempT % 3 == 0) { b++; tempT /= 3; }
        while (tempT % 5 == 0) { c++; tempT /= 5; }
        while (tempT % 7 == 0) { d++; tempT /= 7; }

        if (tempT > 1) return "-1"; // Prime factor > 7 exists

        int n = num.length();

        // Step 2: Locate first '0' digit if present
        int firstZero = num.indexOf('0');
        int maxP = (firstZero == -1) ? n : firstZero;

        // Step 3: Compute prefix sum array of prime factors
        int[] p2 = new int[n + 1];
        int[] p3 = new int[n + 1];
        int[] p5 = new int[n + 1];
        int[] p7 = new int[n + 1];

        int[] curFactors = new int[4];
        for (int i = 0; i < maxP; ++i) {
            getDigitFactors(num.charAt(i) - '0', curFactors);
            p2[i + 1] = p2[i] + curFactors[0];
            p3[i + 1] = p3[i] + curFactors[1];
            p5[i + 1] = p5[i] + curFactors[2];
            p7[i + 1] = p7[i] + curFactors[3];
        }

        // Step 4: Try prefixes from longest to shortest
        for (int p = maxP; p >= 0; --p) {
            int rem2 = a - p2[p];
            int rem3 = b - p3[p];
            int rem5 = c - p5[p];
            int rem7 = d - p7[p];

            if (p == n) {
                if (getMinDigits(rem2, rem3, rem5, rem7).isEmpty()) {
                    return num; // num itself is already divisible by t
                }
                continue;
            }

            int startDigit = (p < maxP) ? (num.charAt(p) - '0' + 1) : 1;

            for (int dVal = startDigit; dVal <= 9; ++dVal) {
                getDigitFactors(dVal, curFactors);

                String minS = getMinDigits(
                    rem2 - curFactors[0],
                    rem3 - curFactors[1],
                    rem5 - curFactors[2],
                    rem7 - curFactors[3]
                );

                int remLen = n - 1 - p;

                if (minS.length() <= remLen) {
                    StringBuilder res = new StringBuilder();
                    res.append(num, 0, p);
                    res.append(dVal);
                    res.append("1".repeat(remLen - minS.length()));
                    res.append(minS);
                    return res.toString();
                }
            }
        }

        // Step 5: If no n-digit solution exists, increase length
        String minS = getMinDigits(a, b, c, d);
        int targetLen = Math.max(n + 1, minS.length());
        int onesNeeded = targetLen - minS.length();

        return "1".repeat(onesNeeded) + minS;
    }
}