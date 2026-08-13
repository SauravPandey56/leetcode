class Solution {

    static class Node {
        int len;
        int prefix;
        int suffix;
        int best;
        char leftChar;
        char rightChar;

        Node() {}

        Node(char c) {
            len = 1;
            prefix = 1;
            suffix = 1;
            best = 1;
            leftChar = c;
            rightChar = c;
        }
    }

    Node[] tree;

    private Node merge(Node a, Node b) {
        if (a == null) return b;
        if (b == null) return a;

        Node res = new Node();

        res.len = a.len + b.len;
        res.leftChar = a.leftChar;
        res.rightChar = b.rightChar;

        res.prefix = a.prefix;
        res.suffix = b.suffix;

        res.best = Math.max(a.best, b.best);

        // The two parts can form one continuous
        // repeating-character substring.
        if (a.rightChar == b.leftChar) {

            res.best = Math.max(
                res.best,
                a.suffix + b.prefix
            );

            // Entire prefix belongs to the same character
            if (a.prefix == a.len) {
                res.prefix = a.len + b.prefix;
            }

            // Entire suffix belongs to the same character
            if (b.suffix == b.len) {
                res.suffix = b.len + a.suffix;
            }
        }

        return res;
    }

    private void build(char[] s, int node, int l, int r) {
        if (l == r) {
            tree[node] = new Node(s[l]);
            return;
        }

        int mid = l + (r - l) / 2;

        build(s, node * 2, l, mid);
        build(s, node * 2 + 1, mid + 1, r);

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    private void update(
        char[] s,
        int node,
        int l,
        int r,
        int index
    ) {
        if (l == r) {
            tree[node] = new Node(s[l]);
            return;
        }

        int mid = l + (r - l) / 2;

        if (index <= mid) {
            update(s, node * 2, l, mid, index);
        } else {
            update(s, node * 2 + 1, mid + 1, r, index);
        }

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    public int[] longestRepeating(
        String s,
        String queryCharacters,
        int[] queryIndices
    ) {
        int n = s.length();
        int k = queryIndices.length;

        char[] chars = s.toCharArray();

        tree = new Node[4 * n];

        // Build segment tree
        build(chars, 1, 0, n - 1);

        int[] answer = new int[k];

        for (int i = 0; i < k; i++) {

            int index = queryIndices[i];
            char newChar = queryCharacters.charAt(i);

            // Update string
            chars[index] = newChar;

            // Update segment tree
            update(chars, 1, 0, n - 1, index);

            // Root contains answer for entire string
            answer[i] = tree[1].best;
        }

        return answer;
    }
}