class Solution {
    public int uniqueXorTriplets(int[] nums) {
        final int MAX = 2048;

        boolean[][] dp = new boolean[4][MAX];
        dp[0][0] = true;

        for (int v : nums) {
            boolean[][] next = new boolean[4][MAX];

            // Skip current index
            for (int k = 0; k <= 3; k++) {
                System.arraycopy(dp[k], 0, next[k], 0, MAX);
            }

            // Take current index 1, 2, or 3 times
            for (int used = 0; used <= 3; used++) {
                for (int x = 0; x < MAX; x++) {
                    if (!dp[used][x]) continue;

                    // Take once
                    if (used + 1 <= 3)
                        next[used + 1][x ^ v] = true;

                    // Take twice (v ^ v = 0)
                    if (used + 2 <= 3)
                        next[used + 2][x] = true;

                    // Take three times (v ^ v ^ v = v)
                    if (used + 3 <= 3)
                        next[used + 3][x ^ v] = true;
                }
            }

            dp = next;
        }

        int ans = 0;
        for (boolean ok : dp[3]) {
            if (ok) ans++;
        }
        return ans;
    }
}