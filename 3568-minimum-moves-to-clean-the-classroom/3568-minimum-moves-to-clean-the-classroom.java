import java.util.*;

class Solution {

    static class State {
        int r, c, energy, mask;

        State(int r, int c, int energy, int mask) {
            this.r = r;
            this.c = c;
            this.energy = energy;
            this.mask = mask;
        }
    }

    public int minMoves(String[] classroom, int energy) {

        int m = classroom.length;
        int n = classroom[0].length();

        int sr = -1, sc = -1;

        int[][] trashId = new int[m][n];

        for (int[] row : trashId) {
            Arrays.fill(row, -1);
        }

        int trashCount = 0;

        // Find starting point and trash cells
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                char ch = classroom[i].charAt(j);

                if (ch == 'S') {
                    sr = i;
                    sc = j;
                }

                if (ch == 'L') {
                    trashId[i][j] = trashCount++;
                }
            }
        }

        int targetMask = (1 << trashCount) - 1;

        boolean[][][][] visited =
                new boolean[m][n][energy + 1][1 << trashCount];

        Queue<State> queue = new LinkedList<>();

        queue.offer(new State(sr, sc, energy, 0));
        visited[sr][sc][energy][0] = true;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        int moves = 0;

        while (!queue.isEmpty()) {

            int size = queue.size();

            while (size-- > 0) {

                State cur = queue.poll();

                int r = cur.r;
                int c = cur.c;
                int e = cur.energy;
                int mask = cur.mask;

                // All trash collected
                if (mask == targetMask) {
                    return moves;
                }

                for (int d = 0; d < 4; d++) {

                    int nr = r + dr[d];
                    int nc = c + dc[d];

                    // Out of bounds
                    if (nr < 0 || nr >= m || nc < 0 || nc >= n) {
                        continue;
                    }

                    // Wall
                    if (classroom[nr].charAt(nc) == 'X') {
                        continue;
                    }

                    // No energy to move
                    if (e == 0) {
                        continue;
                    }

                    int newEnergy = e - 1;
                    int newMask = mask;

                    // Collect trash
                    if (trashId[nr][nc] != -1) {
                        newMask |= (1 << trashId[nr][nc]);
                    }

                    // Recharge
                    if (classroom[nr].charAt(nc) == 'R') {
                        newEnergy = energy;
                    }

                    if (!visited[nr][nc][newEnergy][newMask]) {

                        visited[nr][nc][newEnergy][newMask] = true;

                        queue.offer(
                            new State(
                                nr,
                                nc,
                                newEnergy,
                                newMask
                            )
                        );
                    }
                }
            }

            moves++;
        }

        return -1;
    }
}