import java.util.*;

class Solution {
    public List<String> maxNumOfSubstrings(String s) {
        int n = s.length();

        int[] first = new int[26];
        int[] last = new int[26];

        Arrays.fill(first, -1);

        // Find first and last occurrence
        for (int i = 0; i < n; i++) {
            int idx = s.charAt(i) - 'a';

            if (first[idx] == -1) {
                first[idx] = i;
            }

            last[idx] = i;
        }

        List<int[]> intervals = new ArrayList<>();

        // Try to create a valid interval for each character
        for (int c = 0; c < 26; c++) {

            if (first[c] == -1) {
                continue;
            }

            int l = first[c];
            int r = last[c];

            boolean valid = true;

            for (int i = l; i <= r; i++) {
                int idx = s.charAt(i) - 'a';

                // This character appeared before l
                if (first[idx] < l) {
                    valid = false;
                    break;
                }

                // Need to extend interval
                r = Math.max(r, last[idx]);
            }

            if (valid) {
                intervals.add(new int[]{l, r});
            }
        }

        // Sort by ending position
        intervals.sort((a, b) -> {
            if (a[1] != b[1]) {
                return Integer.compare(a[1], b[1]);
            }
            return Integer.compare(a[0], b[0]);
        });

        List<String> result = new ArrayList<>();

        int prevEnd = -1;

        for (int[] interval : intervals) {
            int l = interval[0];
            int r = interval[1];

            if (l > prevEnd) {
                result.add(s.substring(l, r + 1));
                prevEnd = r;
            }
        }

        return result;
    }
}