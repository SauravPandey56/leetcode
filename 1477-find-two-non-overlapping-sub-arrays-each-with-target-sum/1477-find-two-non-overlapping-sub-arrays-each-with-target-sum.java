class Solution {
    public int minSumOfLengths(int[] arr, int target) {
        int n = arr.length;
        
        // best[i] = minimum length of a valid subarray
        // completely inside indices [0 ... i]
        int[] best = new int[n];
        
        int INF = n + 1;
        java.util.Arrays.fill(best, INF);

        // prefixSum -> index
        java.util.HashMap<Integer, Integer> map = new java.util.HashMap<>();
        map.put(0, -1);

        int prefixSum = 0;
        int answer = INF;
        int minLength = INF;

        for (int i = 0; i < n; i++) {
            prefixSum += arr[i];

            // Check if a subarray ending at i has sum = target
            if (map.containsKey(prefixSum - target)) {
                int start = map.get(prefixSum - target);
                int length = i - start;

                // Need a previous non-overlapping subarray
                if (start >= 0 && best[start] != INF) {
                    answer = Math.min(answer, length + best[start]);
                }

                // If start == 0, best[-1] conceptually doesn't exist
                if (start == -1) {
                    minLength = Math.min(minLength, length);
                } else if (best[start] != INF) {
                    minLength = Math.min(minLength, length);
                }
            }

            // Store the best valid subarray ending at or before i
            if (i > 0) {
                best[i] = best[i - 1];
            }

            if (map.containsKey(prefixSum - target)) {
                int start = map.get(prefixSum - target);
                int length = i - start;
                best[i] = Math.min(best[i], length);
            }

            map.put(prefixSum, i);
        }

        return answer == INF ? -1 : answer;
    }
}