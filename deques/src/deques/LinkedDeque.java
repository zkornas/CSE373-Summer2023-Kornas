package deques;

/**
 * @see Deque
 */
public class LinkedDeque<T> extends AbstractDeque<T> {
    private int size;
    // IMPORTANT: Do not rename these fields or change their visibility.
    // We access these during grading to test your code.
    Node<T> front;
    Node<T> back;

    // Feel free to add any additional fields you may need, though.

    public LinkedDeque() {
        size = 0;
        front = new Node<>(null);
        back = new Node<>(null);
        front.next = back;
        back.prev = front;
    }

    //If size is at least 1, front.next and back.prev reference the first and last regular nodes, respectively.
    public void addFirst(T item) {
        size += 1;

        Node<T> newNode = new Node<>(item, front, front.next);
        front.next.prev = newNode;
        front.next = newNode;
        //newNode.prev = front;
        //newNode.next = front.next;
        //back.prev = newNode;
    }

    public void addLast(T item) {
        size += 1;
        Node<T> newNode = new Node<>(item, back.prev, back);
        back.prev.next = newNode;
        back.prev = newNode;
        //newNode.prev = front.next;
        //newNode.next = back;
        //newNode.next.prev = newNode;
        //newNode.prev.next = newNode;
        //back.prev = newNode;
    }
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        if (size == 1) {
            T val = front.next.value;
            front.next = back;
            back.prev = front;
            size -= 1;
            return val;
        }
        T val = front.next.value;
        front.next = front.next.next;
        front.next.prev = front;
        size-= 1;
        return val;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        if (size == 1) {
            size--;
            T val = back.prev.value;
            back.prev = front;
            front.next = back;
            return val;
        }
        size--;
        T val = back.prev.value;
        back.prev = back.prev.prev;
        back.prev.next = back;
        return val;
    }

    public T get(int index) {
        if ((index >= size) || (index < 0)) {
            return null;
        }
        index++;
        if (index > (size / 2)) {
            Node<T> curr = back.prev;
            int count = size;
            while (curr.value != null) {
                if (index == count) {
                    return curr.value;
                }
                curr = curr.prev;
                count--;
            }
        } else {
            Node<T> curr = front.next;
            int count = 1;
            while (curr.value != null) {
                if (index == count) {
                    return curr.value;
                }
                curr = curr.next;
                count++;
            }
        }
        return null;
    }

    public int size() {
        return size;
    }
}
