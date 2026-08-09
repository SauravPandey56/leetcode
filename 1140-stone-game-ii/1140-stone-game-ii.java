public class Solution {
    public int stoneGameII(int[] piles) {
        int n = piles.length;
        
        // Calculate suffix sums
        int[] suffixSum = new int[n];
        suffixSum[n - 1] = piles[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            suffixSum[i] = suffixSum[i + 1] + piles[i];
        }
        
        // dp[i][M] stores the max stones a player can get starting at index i with M
        // M can range up to n
        int[][] dp = new int[n][n + 1];
        
        return getRemaining(0, 1, piles, suffixSum, dp);
    }
    
    private int getRemaining(int i, int M, int[] piles, int[] suffixSum, int[][] dp) {
        int n = piles.length;
        
        // Base case: If the player can take all remaining piles in one turn
        if (i + 2 * M >= n) {
            return suffixSum[i];
        }
        
        // Return cached result if already computed
        if (dp[i][M] != 0) {
            return dp[i][M];
        }
        
        int maxStones = 0;
        
        // Try taking X piles (1 <= X <= 2M)
        for (int X = 1; X <= 2 * M; X++) {
            int nextM = Math.max(M, X);
            
            // Current player gets total remaining minus what the opponent optimally gets next
            int currentOption = suffixSum[i] - getRemaining(i + X, nextM, piles, suffixSum, dp);
            maxStones = Math.max(maxStones, currentOption);
        }
        
        dp[i][M] = maxStones;
        return maxStones;
    }
}