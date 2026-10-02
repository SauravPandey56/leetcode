class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();

        backtrack(result, new StringBuilder(), 0, 0, n);

        return result;
    }

    private void backtrack(List<String> result, StringBuilder sb,
                            int open, int close, int n) {

        // Valid combination completed
        if (sb.length() == 2 * n) {
            result.add(sb.toString());
            return;
        }

        // Add opening bracket
        if (open < n) {
            sb.append('(');
            backtrack(result, sb, open + 1, close, n);
            sb.deleteCharAt(sb.length() - 1);
        }

        // Add closing bracket only when valid
        if (close < open) {
            sb.append(')');
            backtrack(result, sb, open, close + 1, n);
            sb.deleteCharAt(sb.length() - 1);
        }
    }
}