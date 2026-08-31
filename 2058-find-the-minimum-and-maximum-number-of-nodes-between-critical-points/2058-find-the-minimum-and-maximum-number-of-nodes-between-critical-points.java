/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public int[] nodesBetweenCriticalPoints(ListNode head) {

        int index = 1;
        int first = -1;
        int last = -1;
        int minDistance = Integer.MAX_VALUE;

        ListNode prev = head;
        ListNode curr = head.next;

        while (curr.next != null) {

            ListNode next = curr.next;

            // Check for local maximum or local minimum
            if ((curr.val > prev.val && curr.val > next.val) ||
                (curr.val < prev.val && curr.val < next.val)) {

                // First critical point
                if (first == -1) {
                    first = index;
                } else {
                    // Distance between consecutive critical points
                    minDistance = Math.min(minDistance, index - last);
                }

                last = index;
            }

            prev = curr;
            curr = next;
            index++;
        }

        // Fewer than two critical points
        if (first == last) {
            return new int[]{-1, -1};
        }

        // Maximum distance = last critical point - first critical point
        int maxDistance = last - first;

        return new int[]{minDistance, maxDistance};
    }
}