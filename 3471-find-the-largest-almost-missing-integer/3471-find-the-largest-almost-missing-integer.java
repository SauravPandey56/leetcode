class Solution {
    public int largestInteger(int[] nums, int k) {
        int n = nums.length;

        // count[x] = number of size-k subarrays containing x
        int[] count = new int[51];

        // Check every subarray of size k
        for (int i = 0; i <= n - k; i++) {

            // Avoid counting the same number twice
            // if it occurs multiple times in this window
            boolean[] present = new boolean[51];

            for (int j = i; j < i + k; j++) {
                present[nums[j]] = true;
            }

            // Count this window for each distinct number
            for (int x = 0; x <= 50; x++) {
                if (present[x]) {
                    count[x]++;
                }
            }
        }

        // Find largest number appearing in exactly one subarray
        for (int x = 50; x >= 0; x--) {
            if (count[x] == 1) {
                return x;
            }
        }

        return -1;
    }
}