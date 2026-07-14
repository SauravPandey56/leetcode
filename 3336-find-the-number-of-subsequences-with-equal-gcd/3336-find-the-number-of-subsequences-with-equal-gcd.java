class Solution {
    private static final int MOD = 1_000_000_007;
    private static final int MAX = 200;

    public int subsequencePairCount(int[] nums) {
        int[][] gcd = new int[MAX + 1][MAX + 1];

        // Precompute all GCD values
        for (int i = 0; i <= MAX; i++) {
            for (int j = 0; j <= MAX; j++) {
                gcd[i][j] = findGcd(i, j);
            }
        }

        long[][] dp = new long[MAX + 1][MAX + 1];
        dp[0][0] = 1;

        for (int num : nums) {
            long[][] next = new long[MAX + 1][MAX + 1];

            for (int g1 = 0; g1 <= MAX; g1++) {
                for (int g2 = 0; g2 <= MAX; g2++) {
                    long count = dp[g1][g2];

                    if (count == 0) {
                        continue;
                    }

                    // Skip num
                    next[g1][g2] += count;
                    if (next[g1][g2] >= MOD) {
                        next[g1][g2] -= MOD;
                    }

                    // Add num to seq1
                    int newG1 = gcd[g1][num];

                    next[newG1][g2] += count;
                    if (next[newG1][g2] >= MOD) {
                        next[newG1][g2] -= MOD;
                    }

                    // Add num to seq2
                    int newG2 = gcd[g2][num];

                    next[g1][newG2] += count;
                    if (next[g1][newG2] >= MOD) {
                        next[g1][newG2] -= MOD;
                    }
                }
            }

            dp = next;
        }

        long answer = 0;

        for (int g = 1; g <= MAX; g++) {
            answer += dp[g][g];

            if (answer >= MOD) {
                answer -= MOD;
            }
        }

        return (int) answer;
    }

    private int findGcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }

        return a;
    }
}