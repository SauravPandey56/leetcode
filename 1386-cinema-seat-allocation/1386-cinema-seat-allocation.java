import java.util.*;

class Solution {
    public int maxNumberOfFamilies(int n, int[][] reservedSeats) {

        Map<Integer, Integer> map = new HashMap<>();

        // Store reserved seats as a bitmask for each row
        for (int[] seat : reservedSeats) {
            int row = seat[0];
            int col = seat[1];

            // Use bit (col - 1) for seat number col
            map.put(row, map.getOrDefault(row, 0) | (1 << (col - 1)));
        }

        int answer = (n - map.size()) * 2;

        // Masks for:
        // 2,3,4,5
        int left = (1 << 1) | (1 << 2) | (1 << 3) | (1 << 4);

        // 4,5,6,7
        int middle = (1 << 3) | (1 << 4) | (1 << 5) | (1 << 6);

        // 6,7,8,9
        int right = (1 << 5) | (1 << 6) | (1 << 7) | (1 << 8);

        for (int mask : map.values()) {

            boolean canLeft = (mask & left) == 0;
            boolean canMiddle = (mask & middle) == 0;
            boolean canRight = (mask & right) == 0;

            if (canLeft && canRight) {
                answer += 2;
            } else if (canLeft || canMiddle || canRight) {
                answer += 1;
            }
        }

        return answer;
    }
}