/**
 * Array Heap implementation of a priority queue
 * @author Lachlan Plant
 */
public class HeapPriorityQueue<K extends Comparable,V> implements PriorityQueue<K ,V> {
    
    private Entry<K, V>[] storage; //The Heap itself in array form
    private int tail;        //Index of last element in the heap
    
    /**
    * Default constructor
    */
    public HeapPriorityQueue(){
    }
    
    /**
    * HeapPriorityQueue constructor with max storage of size elements
    */
    public HeapPriorityQueue(int size){
        tail = 0;
    }
    
    /****************************************************
     * 
     *             Priority Queue Methods
     * 
     ****************************************************/
    
    /**
    * Returns the number of items in the priority queue.
    * O(1)
    * @return number of items
    */
    public int size(){
        return tail;
    }

    /**
    * Tests whether the priority queue is empty.
    * O(1)
    * @return true if the priority queue is empty, false otherwise
    */
    public boolean isEmpty(){
        return tail == 0;
    }
    
    /**
    * Inserts a key-value pair and returns the entry created.
    * O(log(n))
    * @param key     the key of the new entry
    * @param value   the associated value of the new entry
    * @return the entry storing the new key-value pair
    * @throws IllegalArgumentException if the heap is full
    */
 public Entry<K, V> insert(K key, V value) {
    if (tail == storage.length) {
        throw new IllegalArgumentException("Heap is full");
    }

    Entry<K, V> entry = new Entry<>(key, value);
    storage[tail] = entry;
    upHeap(tail);
    tail++;

    return entry;
}
    
    /**
    * Returns (but does not remove) an entry with minimal key.
    * O(1)
    * @return entry having a minimal key (or null if empty)
    */
   public Entry<K, V> min() {
    if (isEmpty()) {
        return null;
    }

    return storage[0];
}
    
    /**
    * Removes and returns an entry with minimal key.
    * O(log(n))
    * @return the removed entry (or null if empty)
    */ 
    public Entry<K, V> removeMin() {
    if (isEmpty()) {
        return null;
    }

    Entry<K, V> minEntry = storage[0];

    swap(0, tail - 1);
    storage[tail - 1] = null;
    tail--;

    if (tail > 0) {
        downHeap(0);
    }

    return minEntry;
}
    
    
    /****************************************************
     * 
     *           Methods for Heap Operations
     * 
     ****************************************************/
    
    /**
    * Algorithm to place element after insertion at the tail.
    * O(log(n))
    */
    private void upHeap(int location) {
    while (location > 0) {
        int p = parent(location);

        if (storage[location].getKey()
                .compareTo(storage[p].getKey()) >= 0) {
            break;
        }

        swap(location, p);
        location = p;
    }
}
    
    /**
    * Algorithm to place element after removal of root and tail element placed at root.
    * O(log(n))
    */
 private void downHeap(int location) {
    while (true) {
        int left = 2 * location + 1;
        int right = 2 * location + 2;
        int smallest = location;

        if (left < tail &&
            storage[left].getKey()
                .compareTo(storage[smallest].getKey()) < 0) {
            smallest = left;
        }

        if (right < tail &&
            storage[right].getKey()
                .compareTo(storage[smallest].getKey()) < 0) {
            smallest = right;
        }

        if (smallest == location) {
            break;
        }

        swap(location, smallest);
        location = smallest;
    }
}
    
    /**
    * Find parent of a given location,
    * Parent of the root is the root
    * O(1)
    */
    private int parent(int location){
        if (location == 0) {return 0;}
        return (location-1)/2;
    }
    
   
    /**
    * Inplace swap of 2 elements, assumes locations are in array
    * O(1)
    */
    private void swap(int location1, int location2){
        Entry<K, V> temp = storage[location1];
        storage[location1] = storage[location2];
        storage[location2] = temp;          
    }
    
}
