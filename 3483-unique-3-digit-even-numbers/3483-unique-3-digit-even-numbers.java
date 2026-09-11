import java.util.*;

class Solution {
    public int totalNumbers(int[] digits) {
        
        Set<Integer> set = new HashSet<>();
        int n = digits.length;

        // Units digit
        for (int i = 0; i < n; i++) {

            // Last digit must be even
            if (digits[i] % 2 != 0) {
                continue;
            }

            // Tens digit
            for (int j = 0; j < n; j++) {

                if (i == j) {
                    continue;
                }

                // Hundreds digit
                for (int k = 0; k < n; k++) {

                    if (k == i || k == j) {
                        continue;
                    }

                    // Cannot have leading zero
                    if (digits[k] == 0) {
                        continue;
                    }

                    int num = digits[k] * 100
                            + digits[j] * 10
                            + digits[i];

                    set.add(num);
                }
            }
        }

        return set.size();
    }
}