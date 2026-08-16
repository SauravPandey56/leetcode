class Solution {
    public boolean stoneGameIX(int[] stones) {
        int[] count = new int[3];

        for (int stone : stones) {
            count[stone % 3]++;
        }

        // If there are no stones with remainder 1 or 2,
        // Alice must eventually take a 0-mod-3 stone and lose.
        if (count[1] == 0 && count[2] == 0) {
            return false;
        }

        // If the number of 0-mod-3 stones is even,
        // the game depends on whether both types (1 and 2)
        // are available.
        if (count[0] % 2 == 0) {
            return count[1] > 0 && count[2] > 0;
        }

        // If count[0] is odd:
        // Alice wins if the imbalance between count[1] and count[2]
        // is large enough.
        return Math.abs(count[1] - count[2]) > 2;
    }
}