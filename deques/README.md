# Deque ADT
Deques can do everything that both stacks and queues can do. Specifically, any deque implementation must have the following operations:

| Singature | Description |
|-----------|-------------|
| void addFirst(T item) | Adds an item of type T to the front of the deque. |
| void addLast(T item)	| Adds an item of type T to the back of the deque. |
| T removeFirst()	| Removes and returns the item at the front of the deque. |
| T removeLast()	| Removes and returns the item at the back of the deque. |
| int size()	| Returns the number of items in the deque. |
| boolean isEmpty()	| Returns true if deque is empty, false otherwise. |
| T get(int index)	| Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth. |
