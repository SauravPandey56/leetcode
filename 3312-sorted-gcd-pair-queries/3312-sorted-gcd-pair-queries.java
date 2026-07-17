import java.util.*;

class Solution {
    public int[] gcdValues(int[] nums, long[] queries) {
        int max = 0;
        for (int x : nums) max = Math.max(max, x);

        int[] freq = new int[max + 1];
        for (int x : nums) freq[x]++;

        long[] divisible = new long[max + 1];

        // Count numbers divisible by each d
        for (int d = 1; d <= max; d++) {
            for (int m = d; m <= max; m += d) {
                divisible[d] += freq[m];
            }
        }

        long[] exact = new long[max + 1];

        // Inclusion-Exclusion
        for (int d = max; d >= 1; d--) {
            long pairs = divisible[d] * (divisible[d] - 1) / 2;
            for (int m = d + d; m <= max; m += d) {
                pairs -= exact[m];
            }
            exact[d] = pairs;
        }

        long[] prefix = new long[max + 1];
        for (int i = 1; i <= max; i++) {
            prefix[i] = prefix[i - 1] + exact[i];
        }

        int[] ans = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            long target = queries[i] + 1; // 1-based position

            int lo = 1, hi = max;
            while (lo < hi) {
                int mid = (lo + hi) >>> 1;
                if (prefix[mid] >= target) {
                    hi = mid;
                } else {
                    lo = mid + 1;
                }
            }
            ans[i] = lo;
        }

        return ans;
    }
}