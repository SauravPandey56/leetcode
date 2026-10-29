class Solution {
    private List<Integer>[] adj;
    private int[][] up;
    private int[] depth, tin, tout, parent;
    private int[] fenwick;
    private int timer, log, n;

    public List<Boolean> palindromePath(int n, int[][] edges, String s, String[] queries) {
        this.n = n;
        this.log = 1;

        while ((1 << log) <= n) {
            log++;
        }

        adj = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int[] e : edges) {
            adj[e[0]].add(e[1]);
            adj[e[1]].add(e[0]);
        }

        depth = new int[n];
        tin = new int[n];
        tout = new int[n];
        parent = new int[n];
        up = new int[log][n];
        fenwick = new int[n + 2];

        int[] size = new int[n];
        int[] order = new int[n];
        Arrays.fill(parent, -2);

        // Iterative DFS
        int[] stack = new int[n];
        int top = 0, count = 0;

        stack[top++] = 0;
        parent[0] = -1;

        while (top > 0) {
            int u = stack[--top];

            tin[u] = count;
            order[count++] = u;

            for (int v : adj[u]) {
                if (v == parent[u]) {
                    continue;
                }

                parent[v] = u;
                depth[v] = depth[u] + 1;
                stack[top++] = v;
            }
        }

        Arrays.fill(size, 1);

        for (int i = n - 1; i >= 0; i--) {
            int u = order[i];

            if (parent[u] != -1) {
                size[parent[u]] += size[u];
            }
        }

        for (int i = 0; i < n; i++) {
            tout[i] = tin[i] + size[i] - 1;
        }

        // Binary lifting
        for (int i = 0; i < n; i++) {
            up[0][i] = parent[i] == -1 ? i : parent[i];
        }

        for (int j = 1; j < log; j++) {
            for (int i = 0; i < n; i++) {
                up[j][i] = up[j - 1][up[j - 1][i]];
            }
        }

        char[] chars = s.toCharArray();
        int[] prefix = new int[n];

        for (int u : order) {
            int p = parent[u];
            prefix[u] = (p == -1 ? 0 : prefix[p])
                    ^ (1 << (chars[u] - 'a'));
        }

        List<Boolean> answer = new ArrayList<>();

        for (String query : queries) {
            String[] parts = query.split(" ");

            if (parts[0].equals("update")) {
                int u = Integer.parseInt(parts[1]);
                char c = parts[2].charAt(0);

                int oldMask = 1 << (chars[u] - 'a');
                int newMask = 1 << (c - 'a');
                int delta = oldMask ^ newMask;

                if (delta != 0) {
                    rangeXor(tin[u], tout[u], delta);
                    chars[u] = c;
                }

            } else {
                int u = Integer.parseInt(parts[1]);
                int v = Integer.parseInt(parts[2]);

                int lca = getLCA(u, v);

                int mask = getPrefix(u, prefix)
                        ^ getPrefix(v, prefix)
                        ^ (1 << (chars[lca] - 'a'));

                answer.add((mask & (mask - 1)) == 0);
            }
        }

        return answer;
    }

    private void rangeXor(int l, int r, int value) {
        updateBIT(l + 1, value);
        updateBIT(r + 2, value);
    }

    private void updateBIT(int i, int value) {
        while (i < fenwick.length) {
            fenwick[i] ^= value;
            i += i & -i;
        }
    }

    private int queryBIT(int i) {
        int result = 0;

        while (i > 0) {
            result ^= fenwick[i];
            i -= i & -i;
        }

        return result;
    }

    private int getPrefix(int u, int[] prefix) {
        return prefix[u] ^ queryBIT(tin[u] + 1);
    }

    private int getLCA(int u, int v) {
        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        int diff = depth[u] - depth[v];

        for (int j = 0; j < log; j++) {
            if ((diff & (1 << j)) != 0) {
                u = up[j][u];
            }
        }

        if (u == v) {
            return u;
        }

        for (int j = log - 1; j >= 0; j--) {
            if (up[j][u] != up[j][v]) {
                u = up[j][u];
                v = up[j][v];
            }
        }

        return parent[u];
    }
}