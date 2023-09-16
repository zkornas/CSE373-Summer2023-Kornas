# The ExtrinsicMinPQ Interface

The main task for this assignment will be to develop an array-based implementation of the ExtrinsicMinPQ interface.

The priority for each item will be extrinsic to the object—that is, rather than relying on some sort of comparison function to decide which item has less priority than another, we manually assign priorities by passing in numbers along with the items.

| Signature	| Description |
|-----------|-------------|
| void add(T item, double priority) |	Adds an item with the given priority value. |
| boolean contains(T item)	| Returns true if the PQ contains the given item; false otherwise. |
| T peekMin()	| Returns the item with least-valued priority. |
| T removeMin()	| Removes and returns the item with least-valued priority. |
| void changePriority(T item, double priority)	| Changes the priority of the given item. |
| int size()	| Returns the number of items in the PQ. |
| boolean isEmpty()	| Returns true if the PQ is empty, false otherwise. |

One last note: this priority queue can only contain unique items. There cannot be two copies of the same item, though it’s possible for two different items to be assigned the same priority value. In this case, you may break ties arbitrarily.

Implementations
Naive Implementation
For reference, we have provided NaiveMinPQ, a simple and slow implementation of the ExtrinsicMinPQ interface. You can use the naive implementation when writing your own tests, as a reference to compare results against when testing your own implementation.

peekMin, removeMin, contains, and changePriority use Java’s Stream API to do brute force searches over the entire list of items. This takes time proportional to the length of the list, or Θ(n).

Heap Implementation

Complete ArrayHeapMinPQ, implementing ExtrinsicMinPQ subject to the following constraints:

Required fields:
- You must store your min-heap in the field named items. It should be an ArrayList of PriorityNodes, the class used to store items along with their priorities.
- Your heap ArrayList must start at the index indicated by START_INDEX. (You may change the value of this constant to match whatever you implement.)

Required runtimes (where n is the heap size):
- peekMin, contains, size and changePriority must run in O(logn) time.
- add and removeMin must run in O(logn) time except for the rare resize operation.
- You may not import other priority queue implementations. However, you are allowed to import other built-in Java structures (ex. java.util.HashMap, TreeMap, ArrayList, etc.) when developing your code.

Remember that the the O(⋅) notation only denotes upper bounds. It is possible to implement some methods with better runtimes.
