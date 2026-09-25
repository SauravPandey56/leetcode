class Solution {

    public List<String> braceExpansionII(String expression) {
        Set<String> set = dfs(expression, 0, expression.length());

        List<String> ans = new ArrayList<>(set);

        // Important: LeetCode requires lexicographical order
        Collections.sort(ans);

        return ans;
    }

    private Set<String> dfs(String s, int l, int r) {

        Set<String> result = new HashSet<>();
        result.add("");

        int i = l;

        while (i < r) {

            Set<String> curr = new HashSet<>();

            // Expression inside braces
            if (s.charAt(i) == '{') {

                int balance = 0;
                int j = i;

                while (j < r) {

                    if (s.charAt(j) == '{') {
                        balance++;
                    } else if (s.charAt(j) == '}') {
                        balance--;
                    }

                    if (balance == 0) {
                        break;
                    }

                    j++;
                }

                curr = parseBraces(s, i + 1, j);

                i = j + 1;

            } else {

                // Normal character
                curr.add(String.valueOf(s.charAt(i)));
                i++;
            }

            // Concatenation
            Set<String> temp = new HashSet<>();

            for (String a : result) {
                for (String b : curr) {
                    temp.add(a + b);
                }
            }

            result = temp;
        }

        return result;
    }

    private Set<String> parseBraces(String s, int l, int r) {

        Set<String> result = new HashSet<>();

        int balance = 0;
        int start = l;

        for (int i = l; i < r; i++) {

            char ch = s.charAt(i);

            if (ch == '{') {
                balance++;
            } 
            else if (ch == '}') {
                balance--;
            } 
            else if (ch == ',' && balance == 0) {

                // One union part
                result.addAll(dfs(s, start, i));

                start = i + 1;
            }
        }

        // Last part
        result.addAll(dfs(s, start, r));

        return result;
    }
}