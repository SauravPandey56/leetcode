class Solution {
    public String lexGreaterPermutation(String s, String target) {

        int n = s.length();

        int[] count = new int[26];

        for (char ch : s.toCharArray()) {
            count[ch - 'a']++;
        }

        // Match target as much as possible
        for (int i = 0; i < n; i++) {

            int cur = target.charAt(i) - 'a';

            // If target[i] is available, keep the prefix equal
            if (count[cur] > 0) {
                count[cur]--;
                continue;
            }

            // target[i] is unavailable.
            // Find the smallest character greater than target[i].
            for (int j = cur + 1; j < 26; j++) {

                if (count[j] > 0) {

                    StringBuilder ans = new StringBuilder();

                    // Keep prefix equal to target
                    ans.append(target.substring(0, i));

                    // Make the string greater
                    ans.append((char) ('a' + j));

                    count[j]--;

                    // Add remaining characters in sorted order
                    for (int k = 0; k < 26; k++) {
                        while (count[k] > 0) {
                            ans.append((char) ('a' + k));
                            count[k]--;
                        }
                    }

                    return ans.toString();
                }
            }

            // No greater character at this position.
            // Need to backtrack.
            break;
        }

        
        count = new int[26];

        for (char ch : s.toCharArray()) {
            count[ch - 'a']++;
        }

        int matched = 0;

        while (matched < n) {

            int cur = target.charAt(matched) - 'a';

            if (count[cur] == 0) {
                break;
            }

            count[cur]--;
            matched++;
        }

        // Change the rightmost possible position
        for (int i = matched - 1; i >= 0; i--) {

            int cur = target.charAt(i) - 'a';

            // Put target[i] back
            count[cur]++;

            // Find smallest character greater than target[i]
            for (int j = cur + 1; j < 26; j++) {

                if (count[j] > 0) {

                    StringBuilder ans = new StringBuilder();

                    // Prefix equal to target
                    ans.append(target.substring(0, i));

                    // Make it greater
                    ans.append((char) ('a' + j));

                    count[j]--;

                    // Remaining characters in sorted order
                    for (int k = 0; k < 26; k++) {
                        while (count[k] > 0) {
                            ans.append((char) ('a' + k));
                            count[k]--;
                        }
                    }

                    return ans.toString();
                }
            }
        }

        return "";
    }
}