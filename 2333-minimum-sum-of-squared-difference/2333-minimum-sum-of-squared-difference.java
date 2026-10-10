class Solution {
    public long minSumSquareDiff(int[] nums1, int[] nums2, int k1, int k2) {
        long k = (long) k1 + k2;
        int n = nums1.length;
        int[] freq = new int[100001];

        long sum = 0;
        int max = 0;

        for (int i = 0; i < n; i++) {
            int d = Math.abs(nums1[i] - nums2[i]);
            freq[d]++;
            sum += d;
            max = Math.max(max, d);
        }

        if (sum <= k) {
            return 0;
        }

        for (int d = max; d > 0 && k > 0; d--) {
            if (freq[d] == 0) {
                continue;
            }

            int count = (int) Math.min(k, freq[d]);
            freq[d] -= count;
            freq[d - 1] += count;
            k -= count;
        }

        long ans = 0;

        for (int d = 1; d <= max; d++) {
            ans += (long) d * d * freq[d];
        }

        return ans;
    }
}