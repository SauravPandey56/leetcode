import java.util.*;

class Solution {

    public int findMaxPathScore(int[][] edges, boolean[] online, long k) {
        int n = online.length;

        List<int[]>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        int[] indegree = new int[n];
        TreeSet<Integer> costs = new TreeSet<>();

        for (int[] e : edges) {
            graph[e[0]].add(new int[]{e[1], e[2]});
            indegree[e[1]]++;
            costs.add(e[2]);
        }

        // Topological Sort
        int[] topo = new int[n];
        Queue<Integer> q = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) q.offer(i);
        }

        int idx = 0;
        while (!q.isEmpty()) {
            int u = q.poll();
            topo[idx++] = u;

            for (int[] edge : graph[u]) {
                int v = edge[0];
                if (--indegree[v] == 0) {
                    q.offer(v);
                }
            }
        }

        if (costs.isEmpty()) return -1;

        ArrayList<Integer> vals = new ArrayList<>(costs);

        int lo = 0, hi = vals.size() - 1;
        int ans = -1;

        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            int threshold = vals.get(mid);

            if (canReach(threshold, graph, topo, online, k, n)) {
                ans = threshold;
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }

        return ans;
    }

    private boolean canReach(int threshold,
                             List<int[]>[] graph,
                             int[] topo,
                             boolean[] online,
                             long k,
                             int n) {

        long INF = Long.MAX_VALUE / 4;
        long[] dist = new long[n];
        Arrays.fill(dist, INF);
        dist[0] = 0;

        for (int u : topo) {
            if (dist[u] == INF) continue;

            for (int[] edge : graph[u]) {
                int v = edge[0];
                int w = edge[1];

                if (w < threshold) continue;

                if (v != n - 1 && !online[v]) continue;

                long nd = dist[u] + w;
                if (nd < dist[v]) {
                    dist[v] = nd;
                }
            }
        }

        return dist[n - 1] <= k;
    }
}