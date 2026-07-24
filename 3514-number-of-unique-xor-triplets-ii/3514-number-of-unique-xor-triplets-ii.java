import java.util.*;

class Solution {
    public int uniqueXorTriplets(int[] nums) {
        int MAXV = 2048; // since nums[i] <= 1500, XORs stay < 2048
        
        boolean[] present1 = new boolean[MAXV];
        for (int x : nums) present1[x] = true;
        
        List<Integer> s1 = new ArrayList<>();
        for (int v = 0; v < MAXV; v++) {
            if (present1[v]) s1.add(v);
        }
        
        boolean[] present2 = new boolean[MAXV];
        for (int x : s1) {
            for (int y : s1) {
                present2[x ^ y] = true;
            }
        }
        
        boolean[] present3 = new boolean[MAXV];
        for (int x = 0; x < MAXV; x++) {
            if (!present2[x]) continue;
            for (int y : s1) {
                present3[x ^ y] = true;
            }
        }
        
        int count = 0;
        for (int v = 0; v < MAXV; v++) {
            if (present3[v]) count++;
        }
        return count;
    }
}