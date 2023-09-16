package maps;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * @see AbstractIterableMap
 * @see Map
 */
public class ChainedHashMap<K, V> extends AbstractIterableMap<K, V> {
    private static final double DEFAULT_RESIZING_LOAD_FACTOR_THRESHOLD = .9; //< 1
    private static final int DEFAULT_INITIAL_CHAIN_COUNT = 4; //size of array, 16, 32
    private static final int DEFAULT_INITIAL_CHAIN_CAPACITY = 2; //anything, 10, normally less than chain count

    /*
    Warning:
    You may not rename this field or change its type.
    We will be inspecting it in our secret tests.
     */
    AbstractIterableMap<K, V>[] chains;
    private int size;
    private double resizingLoadFactorThreshold;
    private int chainCount;
    private int chainCapacity;

    private int count;
    // You're encouraged to add extra fields (and helper methods) though!

    /**
     * Constructs a new ChainedHashMap with default resizing load factor threshold,
     * default initial chain count, and default initial chain capacity.
     */
    public ChainedHashMap() {
        this(DEFAULT_RESIZING_LOAD_FACTOR_THRESHOLD, DEFAULT_INITIAL_CHAIN_COUNT, DEFAULT_INITIAL_CHAIN_CAPACITY);
    }

    /**
     * Constructs a new ChainedHashMap with the given parameters.
     *
     * @param resizingLoadFactorThreshold the load factor threshold for resizing. When the load factor
     *                                    exceeds this value, the hash table resizes. Must be > 0.
     * @param initialChainCount the initial number of chains for your hash table. Must be > 0.
     * @param chainInitialCapacity the initial capacity of each ArrayMap chain created by the map.
     *                             Must be > 0.
     */
    public ChainedHashMap(double resizingLoadFactorThreshold, int initialChainCount, int chainInitialCapacity) {
        //similar to first constructor, instead make 3 variable that are private and = parameters

        this.chains = createArrayOfChains(initialChainCount);
        this.resizingLoadFactorThreshold = resizingLoadFactorThreshold;
        this.chainCount = initialChainCount;
        this.chainCapacity = chainInitialCapacity;
        this.size = 0;
        this.count = 0;
        this.chains[0] = createChain(chainInitialCapacity);
    }

    /**
     * This method will return a new, empty array of the given size that can contain
     * {@code AbstractIterableMap<K, V>} objects.
     *
     * Note that each element in the array will initially be null.
     *
     * Note: You do not need to modify this method.
     * @see ArrayMap createArrayOfEntries method for more background on why we need this method
     */
    @SuppressWarnings("unchecked")
    private AbstractIterableMap<K, V>[] createArrayOfChains(int arraySize) {
        return (AbstractIterableMap<K, V>[]) new AbstractIterableMap[arraySize];
    }

    /**
     * Returns a new chain.
     *
     * This method will be overridden by the grader so that your ChainedHashMap implementation
     * is graded using our solution ArrayMaps.
     *
     * Note: You do not need to modify this method.
     */
    protected AbstractIterableMap<K, V> createChain(int initialSize) {
        return new ArrayMap<>(initialSize);
    }

    private int getIndex(Object key) {
        int hashCode = key.hashCode();
        int index = (hashCode % chainCount);
        if (index < 0) {
            index = index * -1;
        }
        return index;
    }

    //don't have to resize
    //given a key, do int hashcode = key.hashcode;
    //2 edgecases:
    //1 - if the hashmap at that index does not have a key then return null
    //2 - if the hashmap at that index has a key and the array map has key values, but none matching to the given
    // key, then it will return null
    @Override
    //original
    public V get(Object key) {
        int index = getIndex(key);
        if (Objects.equals(this.chains[index], null)) {
            return null;
        }
        return this.chains[index].get(key);
    }

    //Associates the specified value with the specified key in this map. Returns the previous value associated with key,
    //or null if there was no mapping for key.

    //before we go in, calculate double load factor = # current elements / arrayszie
    //if it > loadfactor, resize
    //then get index
    //we call put method we did in arraymap
    @Override
    /*Associates the specified value with the specified key in this map. Returns the previous value associated with key,
     or null if there was no mapping for key.*/
    public V put(K key, V value) {
        if ((double) (size + 1) / chainCount > resizingLoadFactorThreshold) {
            System.out.println(size + 1 + "/" + chainCount);
            count++;
            System.out.println("Count: " + this.count);
            resize(chainCount * 2);
        }
        int index = getIndex(key);
        if (Objects.equals(this.chains[index], null)) {
            this.chains[index] = createChain(this.chainCapacity);
        }
        if (!this.chains[index].containsKey(key)) {
            size++;
        }
        return this.chains[index].put(key, value);
    }

    private void resize(int updatedCapacity) {
        AbstractIterableMap<K, V>[] resizedChains = createArrayOfChains(updatedCapacity);
        chainCount = updatedCapacity;
        //size = 0;
        for (Map.Entry<K, V> entry : this) {
            K currKey = entry.getKey();
            V currValue = entry.getValue();
            int index = getIndex(currKey);
            if (Objects.equals(resizedChains[index], null)) {
                resizedChains[index] = createChain(this.chainCapacity);
            }
            //size++;
            resizedChains[index].put(currKey, currValue);
        }
        this.chains = resizedChains;
    }

    @Override
    public V remove(Object key) {
        int index = getIndex(key);
        if (Objects.equals(this.chains[index], null)) {
            return null;
        }
        V val = this.chains[index].remove(key);
        size--;
        return val;
    }

    @Override
    public void clear() {
        AbstractIterableMap<K, V>[] newChains = createArrayOfChains(chainCount);
        this.chains = newChains;
        this.size = 0;
    }

    @Override
    public boolean containsKey(Object key) {
        int index = getIndex(key);
        if (Objects.equals(chains[index], null)) {
            return false;
        }
        return this.chains[index].containsKey(key);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        // Note: you won't need to change this method (unless you add more constructor parameters)
        return new ChainedHashMapIterator<>(this.chains, this.size);
    }
    // Doing so will give you a better string representation for assertion errors the debugger.

    @Override
    public String toString() {
        return super.toString();
    }

    /*
    See the assignment webpage for tips and restrictions on implementing this iterator.
     */
    private static class ChainedHashMapIterator<K, V> implements Iterator<Map.Entry<K, V>> {
        private AbstractIterableMap<K, V>[] chains;
        // You may add more fields and constructor parameters

        private int index;
        private Iterator<Map.Entry<K, V>> iter;

        public ChainedHashMapIterator(AbstractIterableMap<K, V>[] chains, int  size) {
            this.chains = chains;
            index = -1;

            setIndex();
        }

        @Override
        public boolean hasNext() {
            return (index < chains.length);
        }

        @Override
        public Map.Entry<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Map.Entry<K, V> curr = iter.next();
            if (!iter.hasNext()) {
                setIndex();
            }
            return curr;
        }

        private void setIndex() {
            index++;
            while (hasNext() && (Objects.equals(this.chains[index], null) || this.chains[index].isEmpty())) {
                index++;
            }
            if (hasNext()) {
                iter = this.chains[index].iterator();
            } else {
                iter = null;
            }
        }
    }
}
