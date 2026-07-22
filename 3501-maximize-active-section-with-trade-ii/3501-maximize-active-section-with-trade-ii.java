class Solution {
    public List<Integer> maxActiveSectionsAfterTrade(String s, int[][] queries) {
        int n = s.length();

        // ---- Run-length encode s ----
        int[] runStart = new int[n];
        int[] runEnd   = new int[n];
        int[] runType  = new int[n];
        int numRuns = 0;
        int i = 0;
        while (i < n) {
            int j = i;
            char c = s.charAt(i);
            while (j < n && s.charAt(j) == c) j++;
            runStart[numRuns] = i;
            runEnd[numRuns]   = j - 1;
            runType[numRuns]  = c - '0';
            numRuns++;
            i = j;
        }

        int[] runIndexOf = new int[n];
        for (int r = 0; r < numRuns; r++) {
            for (int p = runStart[r]; p <= runEnd[r]; p++) runIndexOf[p] = r;
        }

        int totalOnes = 0;
        for (int k = 0; k < n; k++) if (s.charAt(k) == '1') totalOnes++;

        final int NEG = Integer.MIN_VALUE / 2;

        // V[p] = combined length of the two neighboring 0-runs of internal 1-run p
        int[] V = new int[Math.max(numRuns, 1)];
        Arrays.fill(V, NEG);
        for (int p = 1; p <= numRuns - 2; p++) {
            if (runType[p] == 1) {
                int lenLeft  = runEnd[p - 1] - runStart[p - 1] + 1;
                int lenRight = runEnd[p + 1] - runStart[p + 1] + 1;
                V[p] = lenLeft + lenRight;
            }
        }

        // ---- Sparse table for range-max on V ----
        final int LOG = 18; // 2^18 > 1e5
        int[][] sparse = new int[LOG][Math.max(numRuns, 1)];
        sparse[0] = V.clone();
        for (int k = 1; k < LOG; k++) {
            int half = 1 << (k - 1);
            for (int idx = 0; idx + (1 << k) <= numRuns; idx++) {
                sparse[k][idx] = Math.max(sparse[k - 1][idx], sparse[k - 1][idx + half]);
            }
        }
        int[] logTable = new int[numRuns + 2];
        for (int k = 2; k <= numRuns + 1; k++) logTable[k] = logTable[k / 2] + 1;

        int q = queries.length;
        List<Integer> answer = new ArrayList<>(q);

        for (int qi = 0; qi < q; qi++) {
            int l = queries[qi][0], r = queries[qi][1];
            int idxL = runIndexOf[l];
            int idxR = runIndexOf[r];
            long bestGain = 0;

            if (idxL != idxR) {
                // Candidate p = idxL + 1 (left neighbor is the truncated boundary run)
                if (idxL + 1 < idxR && runType[idxL + 1] == 1) {
                    int p = idxL + 1;
                    long Lval = runEnd[idxL] - l + 1;
                    long Rval;
                    if (idxL + 2 == idxR) {
                        Rval = r - runStart[idxR] + 1;
                    } else {
                        Rval = runEnd[p + 1] - runStart[p + 1] + 1;
                    }
                    bestGain = Math.max(bestGain, Lval + Rval);
                }

                // Candidate p = idxR - 1 (right neighbor is the truncated boundary run)
                if (idxR - 1 > idxL && idxR - 1 != idxL + 1 && runType[idxR - 1] == 1) {
                    int p = idxR - 1;
                    long Rval = r - runStart[idxR] + 1;
                    long Lval;
                    if (idxR - 2 == idxL) {
                        Lval = runEnd[idxL] - l + 1;
                    } else {
                        Lval = runEnd[p - 1] - runStart[p - 1] + 1;
                    }
                    bestGain = Math.max(bestGain, Lval + Rval);
                }

                // Fully-internal candidates: range max query on V over [idxL+2, idxR-2]
                int lo = idxL + 2, hi = idxR - 2;
                if (lo <= hi) {
                    int len = hi - lo + 1;
                    int k = logTable[len];
                    int mx = Math.max(sparse[k][lo], sparse[k][hi - (1 << k) + 1]);
                    bestGain = Math.max(bestGain, mx);
                }
            }

            answer.add((int) (totalOnes + Math.max(0, bestGain)));
        }

        return answer;
    }
}