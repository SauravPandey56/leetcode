class Solution {
    public int longestSubsequence(int[] nums) {
        int xor = 0;
        boolean hasNonZero = false;

        for (int num : nums) {
            xor ^= num;

            if (num != 0) {
                hasNonZero = true;
            }
        }

        // XOR of all elements is already non-zero
        if (xor != 0) {
            return nums.length;
        }

        // XOR is zero, but we can remove one non-zero element
        if (hasNonZero) {
            return nums.length - 1;
        }

        // All elements are zero
        return 0;
    }
}