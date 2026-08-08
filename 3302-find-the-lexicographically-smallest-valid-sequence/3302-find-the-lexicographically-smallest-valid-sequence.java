class Solution {

    public int[] validSequence(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();

        
        int[] suf = new int[m + 1];

        // n means: impossible
        java.util.Arrays.fill(suf, n);

        suf[m] = n;

        int p = n - 1;

        for (int j = m - 1; j >= 0; j--) {

            while (p >= 0 && word1.charAt(p) != word2.charAt(j)) {
                p--;
            }

            if (p < 0) {
                break;
            }

            suf[j] = p;
            p--;
        }

        int[] ans = new int[m];

        int prev = -1;
        boolean usedChange = false;

        for (int j = 0; j < m; j++) {

            int start = prev + 1;

            int exact = -1;

            for (int i = start; i < n; i++) {
                if (word1.charAt(i) == word2.charAt(j)) {
                    exact = i;
                    break;
                }
            }

            int change = -1;

            if (!usedChange && start < n
                    && word1.charAt(start) != word2.charAt(j)) {

               
                if (j == m - 1 ||
                    (suf[j + 1] < n && start < suf[j + 1])) {

                    change = start;
                }
            }

            // No possible choice
            if (exact == -1 && change == -1) {
                return new int[0];
            }

            if (change != -1 &&
                (exact == -1 || change < exact)) {

                ans[j] = change;
                prev = change;
                usedChange = true;

            } else {

                ans[j] = exact;
                prev = exact;
            }
        }

        return ans;
    }
}