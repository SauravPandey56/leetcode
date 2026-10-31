class Solution {
    public int uniquePaths(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int MOD = 1_000_000_007;

        int[][] ways = new int[n][2];

        ways[0][1] = 1;

        for (int j = 1; j < n && grid[0][j - 1] == 0; j++) {
            ways[j][0] = 1;
        }

        for (int i = 1; i < m; i++) {
            ways[0][1] =
                (grid[i - 1][0] == 0 && ways[0][1] == 1) ? 1 : 0;

            for (int j = 1; j < n; j++) {
                int left = ways[j][0];
                int up = ways[j][1];

                ways[j][0] = (
                    (grid[i][j - 1] == 1 ? 0 : ways[j - 1][0])
                    + ways[j - 1][1]
                ) % MOD;

                ways[j][1] = (
                    (grid[i - 1][j] == 1 ? 0 : up)
                    + left
                ) % MOD;
            }
        }

        return (ways[n - 1][0] + ways[n - 1][1]) % MOD;
    }
}