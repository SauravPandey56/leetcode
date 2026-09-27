class Solution {
    public String reverseParentheses(String s) {
        Stack<String> stack = new Stack<>();
        StringBuilder curr = new StringBuilder();

        for (char ch : s.toCharArray()) {

            if (ch == '(') {
                stack.push(curr.toString());
                curr.setLength(0);

            } else if (ch == ')') {
                curr.reverse();

                String prev = stack.pop();
                curr.insert(0, prev);

            } else {
                curr.append(ch);
            }
        }

        return curr.toString();
    }
}