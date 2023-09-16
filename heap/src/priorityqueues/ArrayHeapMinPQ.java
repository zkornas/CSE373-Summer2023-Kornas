package priorityqueues;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.HashMap;

/**
 * @see ExtrinsicMinPQ
 */
public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    // IMPORTANT: Do not rename these fields or change their visibility.
    // We access these during grading to test your code.
    static final int START_INDEX = 0;
    List<PriorityNode<T>> items;
    //create a hashmap where key = item, value = index of the item
    HashMap<T, Integer> itemIndexMap;

    public ArrayHeapMinPQ() {
        items = new ArrayList<>();
        itemIndexMap = new HashMap<>();
    }

    // Here's a method stub that may be useful. Feel free to change or remove it, if you wish.
    // You'll probably want to add more helper methods like this one to make your code easier to read.
    /**
     * A helper method for swapping the items at two indices of the array heap.
     */
    private void swap(int a, int b) {
        PriorityNode<T> saveItem = items.get(a);
        items.set(a, items.get(b));
        items.set(b, saveItem);

        //make the updates in the hashmap
        itemIndexMap.put(items.get(a).getItem(), a);
        itemIndexMap.put(items.get(b).getItem(), b);
    }

    @Override
    public void add(T item, double priority) {
        //Edge Case: item already exists and we don't want duplicates so we throw exception
        if (contains(item)) {
            throw new IllegalArgumentException();
        }
        //create a new PriorityNode with the given item and priority
        //add the item to the end of the items list (the bottom most right)
        items.add(new PriorityNode<>(item, priority));
        int itemSize = items.size() - 1;
        itemIndexMap.put(item, itemSize);
        percolateUp(itemSize); //pass in the newly added item which is the last index
    }

    private void percolateUp(int currentElementIndex) {
        //compare the given node to their parent node to see which one is smaller. Then swap them and keep looping
        //while there is another node to compare to (and the priority is higher than the parent) and will stop once it
        //reaches the root node
        while (currentElementIndex > START_INDEX &&
            items.get(currentElementIndex).getPriority() < items.get((currentElementIndex - 1) / 2).getPriority()) {
            //move the higher priority node up
            swap(currentElementIndex, (currentElementIndex - 1) / 2);
            //make sure that the currentElementIndex is now the parent index bc we swapped them
            currentElementIndex = (currentElementIndex - 1) / 2;
        }
    }

        @Override
    public boolean contains(T item) {
        //bc we know that all of the nodes are in the map, to see if the item exists, we can just check to see if key
        //exists in the map.
        return itemIndexMap.containsKey(item);
    }

    @Override
    //Returns the item with least-valued priority.
    public T peekMin() {
        //if there is nothing in items, throw no such element
        if (items.isEmpty()) {
            throw new NoSuchElementException();
        }
        //bc the min is always root node which is the starting index, we return it
        return items.get(START_INDEX).getItem();
    }

    private void percolateDown(int currentElementIndex) {
        int leftChildIndex = 2 * currentElementIndex + 1;
        int rightChildIndex = 2 * currentElementIndex + 2;
        int smallestChildIndex = leftChildIndex;

        //bc heap min moves left to right, we know that while there is a left child node parent node first if they
        //have a child.If it doesn't have a left node, then it is a leaf node then we don't want to percolate and just
        //exit the method.
        if (leftChildIndex >= items.size()) {
            return;
        }

        //if there is a right node and the priority is higher than left node, then asign the variable to the right child
        if (rightChildIndex < items.size() &&
            items.get(rightChildIndex).getPriority() < items.get(smallestChildIndex).getPriority()) {
            smallestChildIndex = rightChildIndex;
        }

        //after we get the smallest child, we compare it to the parent node. If child node is higher priority, we
        //swap it
        if (items.get(smallestChildIndex).getPriority() < items.get(currentElementIndex).getPriority()) {
            swap(currentElementIndex, smallestChildIndex);
            percolateDown(smallestChildIndex);
        }
    }

    @Override
    //Removes and returns the item with least-valued priority.
    public T removeMin() {
        if (items.isEmpty()) {
            throw new NoSuchElementException();
        }
        T minItem = peekMin();
        swap(START_INDEX, items.size() - 1);
        itemIndexMap.remove(minItem);
        //min at the bottom most right of tree and last index so we can remove
        items.remove(items.size() - 1);
        //move the old last index and percolate down
        percolateDown(START_INDEX);
        return minItem;
    }

    //Changes the priority of the given item.
    @Override
    public void changePriority(T item, double priority) {
        //Edge Case: bc we need to change priority of an existing item, if it doesn't exist, throw exception
        if (!contains(item)) {
            throw new NoSuchElementException();
        }
        //find index of the item from hashmap
        int itemIndex = itemIndexMap.get(item);
        //go to the index in the array and get the old priority
        double oldPriority = items.get(itemIndex).getPriority();
        //change that priority to the new one that is given
        items.get(itemIndex).setPriority(priority);

        //after that prioirity, it will determine whether we have to move the node
        //if the new < old, then priority level is higher and we have to percolate up
        if (priority < oldPriority) {
            percolateUp(itemIndex);
            //} else if (priority > oldPriority) {
        } else {
            percolateDown(itemIndex);
        }
        //if new > old, then we have to percolate down
    }

    @Override
    public int size() {
        return items.size();
    }
}
