
class Solution {

    static class State {
        long sum;
        List<Integer> indices;

        State(long sum, List<Integer> indices) {
            this.sum = sum;
            this.indices = indices;
        }
    }

    public int[] maximumWeight(List<List<Integer>> intervals) {

        int n = intervals.size();

        int[][] arr = new int[n][4];

        for (int i = 0; i < n; i++) {
            arr[i][0] = intervals.get(i).get(0); // start
            arr[i][1] = intervals.get(i).get(1); // end
            arr[i][2] = intervals.get(i).get(2); // weight
            arr[i][3] = i;                       // original index
        }

        // Sort by start time
        Arrays.sort(arr, (a, b) -> {
            if (a[0] != b[0])
                return Integer.compare(a[0], b[0]);

            if (a[1] != b[1])
                return Integer.compare(a[1], b[1]);

            return Integer.compare(a[3], b[3]);
        });

        // Find next non-overlapping interval
        int[] next = new int[n];

        for (int i = 0; i < n; i++) {
            next[i] = binarySearch(arr, i);
        }

        /*
         * dp[i][k] =
         * best answer starting from i
         * when we can still choose k intervals.
         */
        State[][] dp = new State[n + 1][5];

        for (int k = 0; k <= 4; k++) {
            dp[n][k] = new State(0, new ArrayList<>());
        }

        for (int i = n - 1; i >= 0; i--) {

            dp[i][0] = new State(0, new ArrayList<>());

            for (int k = 1; k <= 4; k++) {

                // Option 1: skip current interval
                State skip = dp[i + 1][k];

                // Option 2: take current interval
                State nextState = dp[next[i]][k - 1];

                List<Integer> takeIndices =
                        new ArrayList<>(nextState.indices);

                takeIndices.add(arr[i][3]);

                Collections.sort(takeIndices);

                State take = new State(
                        (long) arr[i][2] + nextState.sum,
                        takeIndices
                );

                dp[i][k] = better(take, skip);
            }
        }

        List<Integer> answer = dp[0][4].indices;

        int[] result = new int[answer.size()];

        for (int i = 0; i < answer.size(); i++) {
            result[i] = answer.get(i);
        }

        return result;
    }

    // Returns the better state
    private State better(State a, State b) {

        // Higher weight is better
        if (a.sum != b.sum) {
            return a.sum > b.sum ? a : b;
        }

        // Same weight -> lexicographically smaller indices
        return compareLexicographically(a.indices, b.indices) <= 0
                ? a
                : b;
    }

    private int compareLexicographically(
            List<Integer> a,
            List<Integer> b) {

        int len = Math.min(a.size(), b.size());

        for (int i = 0; i < len; i++) {

            if (!a.get(i).equals(b.get(i))) {
                return Integer.compare(a.get(i), b.get(i));
            }
        }

        return Integer.compare(a.size(), b.size());
    }

    // First interval whose start > current end
    private int binarySearch(int[][] arr, int index) {

        int target = arr[index][1];

        int low = index + 1;
        int high = arr.length - 1;

        int ans = arr.length;

        while (low <= high) {

            int mid = low + (high - low) / 2;

            if (arr[mid][0] > target) {
                ans = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return ans;
    }
}