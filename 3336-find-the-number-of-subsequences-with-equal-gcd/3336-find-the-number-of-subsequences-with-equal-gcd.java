class Solution {
    private static final int MOD = 1_000_000_007;

    public int subsequencePairCount(int[] nums) {
        int max = 200;

        long[][] dp = new long[max + 1][max + 1];
        dp[0][0] = 1;

        for (int num : nums) {
            long[][] next = new long[max + 1][max + 1];

            for (int gcd1 = 0; gcd1 <= max; gcd1++) {
                for (int gcd2 = 0; gcd2 <= max; gcd2++) {

                    if (dp[gcd1][gcd2] == 0) {
                        continue;
                    }

                    long count = dp[gcd1][gcd2];

                    // 1. Do not take num
                    next[gcd1][gcd2] =
                        (next[gcd1][gcd2] + count) % MOD;

                    // 2. Add num to seq1
                    int newGcd1 = gcd(gcd1, num);

                    next[newGcd1][gcd2] =
                        (next[newGcd1][gcd2] + count) % MOD;

                    // 3. Add num to seq2
                    int newGcd2 = gcd(gcd2, num);

                    next[gcd1][newGcd2] =
                        (next[gcd1][newGcd2] + count) % MOD;
                }
            }

            dp = next;
        }

        long answer = 0;

        for (int gcd = 1; gcd <= max; gcd++) {
            answer = (answer + dp[gcd][gcd]) % MOD;
        }

        return (int) answer;
    }

    private int gcd(int a, int b) {
        if (a == 0) {
            return b;
        }

        while (b != 0) {
            int remainder = a % b;
            a = b;
            b = remainder;
        }

        return a;
    }
}