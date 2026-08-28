class Solution {
    public String lexPalindromicPermutation(String s, String target) {
        int n = s.length();
        int[] freq = new int[26];

        for (char ch : s.toCharArray()) {
            freq[ch - 'a']++;
        }

        // More than one odd frequency => palindrome impossible
        int odd = 0;
        char middle = 0;

        for (int i = 0; i < 26; i++) {
            if (freq[i] % 2 == 1) {
                odd++;
                middle = (char) ('a' + i);
            }
        }

        if (odd > 1) return "";

        // Characters available for the left half
        int[] halfFreq = new int[26];

        for (int i = 0; i < 26; i++) {
            halfFreq[i] = freq[i] / 2;
        }

        int half = n / 2;

        // First try to build a left half equal to target's left half
        int[] temp = halfFreq.clone();
        boolean possible = true;

        for (int i = 0; i < half; i++) {
            int c = target.charAt(i) - 'a';

            if (temp[c] == 0) {
                possible = false;
                break;
            }

            temp[c]--;
        }

        if (possible) {
            char[] left = target.substring(0, half).toCharArray();
            String ans = makePalindrome(left, middle, n);

            if (ans.compareTo(target) > 0) {
                return ans;
            }
        }

        // Find the first position from right where we can increase
        for (int i = half - 1; i >= 0; i--) {

            int[] remaining = halfFreq.clone();

            // Fix the prefix [0 ... i-1]
            boolean valid = true;

            for (int j = 0; j < i; j++) {
                int c = target.charAt(j) - 'a';

                if (remaining[c] == 0) {
                    valid = false;
                    break;
                }

                remaining[c]--;
            }

            if (!valid) continue;

            // Try the smallest character greater than target[i]
            int current = target.charAt(i) - 'a';

            for (int c = current + 1; c < 26; c++) {

                if (remaining[c] == 0) continue;

                remaining[c]--;

                char[] left = new char[half];

                // Copy prefix
                for (int j = 0; j < i; j++) {
                    left[j] = target.charAt(j);
                }

                // Put larger character
                left[i] = (char) ('a' + c);

                // Fill remaining positions
                int pos = i + 1;

                for (int x = 0; x < 26; x++) {
                    while (remaining[x] > 0) {
                        left[pos++] = (char) ('a' + x);
                        remaining[x]--;
                    }
                }

                return makePalindrome(left, middle, n);
            }
        }

        return "";
    }

    private String makePalindrome(char[] left, char middle, int n) {
        StringBuilder sb = new StringBuilder();

        for (char c : left) {
            sb.append(c);
        }

        if (n % 2 == 1) {
            sb.append(middle);
        }

        for (int i = left.length - 1; i >= 0; i--) {
            sb.append(left[i]);
        }

        return sb.toString();
    }
}