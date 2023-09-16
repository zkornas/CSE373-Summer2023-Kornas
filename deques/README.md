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

Unlike lists, we do not allow clients to add items to the middle of a deque. We do, however, allow clients to access elements in the deque. This method primarily exists to make it easier for us to test the implementations.

To get started, open deques/src/deques in IntelliJ. Take a look at the Deque.java file to explore the interface in more detail. There are a few important things to note here:
- The interface methods defined there include comments with extra notes on edge cases and preconditions.
- The Deque interface extends Java’s Queue interface—the Deque interface inherits all the methods defined by Java’s Queue interface, and any implementations of Deque must define those methods.
- In AbstractDeque.java, we define the relationships between our Deque interface and Java’s Queue; this means that subclasses of AbstractDeque can implement the functionality of a deque using our Deque interface, but also provide the functionality of a queue through Java’s Queue interface for free.

## You can read the full spec [here](https://courses.cs.washington.edu/courses/cse373/23su/projects/deques/programming/)!
