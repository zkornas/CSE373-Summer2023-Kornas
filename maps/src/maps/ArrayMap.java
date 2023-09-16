package maps;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * @see AbstractIterableMap
 * @see Map
 */
public class ArrayMap<K, V> extends AbstractIterableMap<K, V> {
    private static final int DEFAULT_INITIAL_CAPACITY = 8;

    //resizing
    //resize factor, how much do we wanna resize, double size?
    //entries is overall array
    //key - integer
    //everything through simple entry which takes care of hashing
    
    /*
    Warning:
    You may not rename this field or change its type.
    We will be inspecting it in our secret tests.
     */
    SimpleEntry<K, V>[] entries;
    private int size;

    // You may add extra fields or helper methods though!

    /**
     * Constructs a new ArrayMap with default initial capacity.
     */
    public ArrayMap() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    /**
     * Constructs a new ArrayMap with the given initial capacity (i.e., the initial
     * size of the internal array).
     *
     * @param initialCapacity the initial capacity of the ArrayMap. Must be > 0.
     */
    public ArrayMap(int initialCapacity) {
        this.entries = this.createArrayOfEntries(initialCapacity);
        size = 0;
    }

    /**
     * This method will return a new, empty array of the given size that can contain
     * {@code Entry<K, V>} objects.
     *
     * Note that each element in the array will initially be null.
     *
     * Note: You do not need to modify this method.
     */
    @SuppressWarnings("unchecked")
    private SimpleEntry<K, V>[] createArrayOfEntries(int arraySize) {
        /*
        It turns out that creating arrays of generic objects in Java is complicated due to something
        known as "type erasure."

        We've given you this helper method to help simplify this part of your assignment. Use this
        helper method as appropriate when implementing the rest of this class.

        You are not required to understand how this method works, what type erasure is, or how
        arrays and generics interact.
        */
        return (SimpleEntry<K, V>[]) (new SimpleEntry[arraySize]);
    }

    @Override
    public V get(Object key) {

        for (int i = 0; i < this.size; i++) {
            K currKey = this.entries[i].getKey();
            if (Objects.equals(key, currKey)) {
                return (this.entries[i].getValue());
            }
        }
        return null;
    }


    //at each index there is a bucket

    @Override
    public V put(K key, V value) {
        if (this.size == this.entries.length) {
            // resize!
            resize(this.entries.length * 2);
        }
        if (containsKey(key)) {
            for (int i = 0; i < this.size; i++) {
                if (Objects.equals(this.entries[i].getKey(), key)) {
                    V oldValue = this.entries[i].getValue();
                    this.entries[i].setValue(value);
                    return oldValue;
                }
            }
        } else {
            SimpleEntry<K, V> newEntry = new SimpleEntry<>(key, value);
            this.entries[this.size] = newEntry;
            size++;
        }
        return null;
    }

    private void resize(int updatedCapacity) {
        SimpleEntry<K, V>[] resizeSimpleEntry = createArrayOfEntries(updatedCapacity);

        //int newSize = 0;

        //add old entries into new arraymap
        for (int i = 0; i < this.size; i++) {
            //SimpleEntry<K, V> entry = entries[i];
            resizeSimpleEntry[i] = entries[i];

        }
        this.entries = resizeSimpleEntry;
    }

    @Override
    public V remove(Object key) {
        //iterate through each entry in SimpleEntry
        for (int i = 0; i < this.size; i++) {
            //if the object equals the given key
            if (Objects.equals(this.entries[i].getKey(), key)) {
                //store the object so that way we can return it
                V previousValue = this.entries[i].getValue();
                //replace it with last entry
                this.entries[i] = this.entries[this.size - 1];
                //set last entry to null bc it replaced the removed key at that index
                this.entries[this.size - 1] = null;
                this.size--;
                return previousValue;
            }
        }
        return null;
    }

    @Override
    public void clear() {
        for (SimpleEntry<K, V> entry : this.entries) {
            entry = null;
        }
        this.size = 0;
    }

    @Override
    public boolean containsKey(Object key) {
        for (int i = 0; i < this.size; i++) {
            if (Objects.equals(this.entries[i].getKey(), key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        // Note: You may or may not need to change this method, depending on whether you
        // add any parameters to the ArrayMapIterator constructor.
        return new ArrayMapIterator<>(this.entries, this.size);
    }
    // Doing so will give you a better string representation for assertion errors the debugger.
    @Override
    public String toString() {
        return super.toString();
    }

    private static class ArrayMapIterator<K, V> implements Iterator<Map.Entry<K, V>> {
        private final SimpleEntry<K, V>[] entries;
        // You may add more fields and constructor parameters
        private int index;

        private int iteratorSize;

        public ArrayMapIterator(SimpleEntry<K, V>[] entries, int size) {
            this.entries = entries;
            index = 0;
            this.iteratorSize = size;
        }

        @Override
        public boolean hasNext() {
            return (index < iteratorSize);
        }

        @Override
        public Map.Entry<K, V> next() {
            if (hasNext()) {
                SimpleEntry<K, V> nextEntry = this.entries[index];
                this.index = this.index + 1;
                return nextEntry;
            }
            throw new NoSuchElementException("No next element");
        }
    }
}
