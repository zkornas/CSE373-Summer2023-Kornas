package problems;

import datastructures.LinkedIntList;
// Checkstyle will complain that this is an unused import until you use it in your code.
import datastructures.LinkedIntList.ListNode;

/**
 * See the spec on the website for example behavior.
 *
 * REMEMBER THE FOLLOWING RESTRICTIONS:
 * - do not call any methods on the `LinkedIntList` objects.
 * - do not construct new `ListNode` objects for `reverse3` or `firstToLast`
 *      (though you may have as many `ListNode` variables as you like).
 * - do not construct any external data structures such as arrays, queues, lists, etc.
 * - do not mutate the `data` field of any node; instead, change the list only by modifying
 *      links between nodes.
 */

public class LinkedIntListProblems {

    /**
     * Reverses the 3 elements in the `LinkedIntList` (assume there are exactly 3 elements).
     */
    public static void reverse3(LinkedIntList list) {
        if (list.front != null && (list.front).next != null) {
            ListNode prev = null;
            ListNode curr = list.front;
            ListNode next = null;
            while (curr != null) {
                next = curr.next;
                curr.next = prev;
                prev = curr;
                curr = next;
            }
            list.front = prev;
        }
    }

    /**
     * Moves the first element of the input list to the back of the list.
     */
    public static void firstToLast(LinkedIntList list) {
        if (list.front != null && (list.front).next != null) {
            ListNode front = list.front;
            ListNode newFront = (list.front).next;
            ListNode end = list.front;
            while (end.next != null) {
                end = end.next;
            }
            end.next = front;
            front.next = null;
            list.front = newFront;
        }
    }

    /**
     * Returns a list consisting of the integers of a followed by the integers
     * of n. Does not modify items of A or B.
     */
    public static LinkedIntList concatenate(LinkedIntList a, LinkedIntList b) {
        if (a.front == null && b.front != null) {
            return b;
        } else if (b.front == null && a.front != null) {
            return a;
        } else if (a.front == null && b.front == null) {
            return new LinkedIntList();
        } else {
            LinkedIntList concatList = new LinkedIntList();
            ListNode concatCurr = new ListNode((a.front).data);
            ListNode aCurr = a.front;
            concatList.front = concatCurr;
            while (aCurr.next != null) {
                concatCurr.next = new ListNode(aCurr.next.data);
                concatCurr = concatCurr.next;
                aCurr = aCurr.next;
            }
            concatCurr.next = new ListNode((b.front).data);
            ListNode bCurr = b.front;
            concatCurr = concatCurr.next;
            while (bCurr.next != null) {
                concatCurr.next = new ListNode(bCurr.next.data);
                concatCurr = concatCurr.next;
                bCurr = bCurr.next;
            }
            return concatList;
        }
    }
}
