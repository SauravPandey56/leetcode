import java.math.BigInteger;

class Solution {
    public String smallestPalindrome(String s, int k) {
        int[] freq = new int[26];
        for (char c : s.toCharArray()) freq[c - 'a']++;

        int[] half = new int[26];
        char mid = 0;
        int halfLen = 0;

        for (int i = 0; i < 26; i++) {
            half[i] = freq[i] / 2;
            halfLen += half[i];
            if ((freq[i] & 1) == 1) mid = (char) ('a' + i);
        }

        if (countPerm(half, k).compareTo(BigInteger.valueOf(k)) < 0) {
            return "";
        }

        StringBuilder left = new StringBuilder();

        for (int pos = 0; pos < halfLen; pos++) {
            for (int c = 0; c < 26; c++) {
                if (half[c] == 0) continue;

                half[c]--;
                BigInteger ways = countPerm(half, k);

                if (ways.compareTo(BigInteger.valueOf(k)) >= 0) {
                    left.append((char) ('a' + c));
                    break;
                } else {
                    k -= ways.intValue();
                    half[c]++;
                }
            }
        }

        StringBuilder ans = new StringBuilder(left);
        if (mid != 0) ans.append(mid);
        ans.append(new StringBuilder(left).reverse());

        return ans.toString();
    }

    private BigInteger countPerm(int[] half, int cap) {
        int rem = 0;
        for (int x : half) rem += x;

        BigInteger res = BigInteger.ONE;
        BigInteger limit = BigInteger.valueOf((long) cap + 1);

        for (int x : half) {
            if (x == 0) continue;
            res = res.multiply(combCap(rem, x, limit));
            if (res.compareTo(limit) > 0) return limit;
            rem -= x;
        }
        return res;
    }

    private BigInteger combCap(int n, int r, BigInteger limit) {
        r = Math.min(r, n - r);
        BigInteger res = BigInteger.ONE;

        for (int i = 1; i <= r; i++) {
            res = res.multiply(BigInteger.valueOf(n - r + i))
                     .divide(BigInteger.valueOf(i));
            if (res.compareTo(limit) > 0) return limit;
        }
        return res;
    }
}