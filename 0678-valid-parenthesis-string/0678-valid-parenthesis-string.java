class Solution {
    public boolean checkValidString(String s) {
        int low = 0;
        int high = 0;

        for (char ch : s.toCharArray()) {

            if (ch == '(') {
                low++;
                high++;
            } 
            else if (ch == ')') {
                low--;
                high--;
            } 
            else { // '*'
                low--;   // '*' acts as ')'
                high++;  // '*' acts as '('
            }

            // Even the maximum possibility has too many ')'
            if (high < 0) {
                return false;
            }

            // We can treat negative low as 0
            low = Math.max(low, 0);
        }

        // Valid if zero unmatched '(' is possible
        return low == 0;
    }
}