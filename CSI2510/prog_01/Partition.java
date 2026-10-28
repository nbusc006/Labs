/**
 * Partition ADT Interface
 * A partition of the fixed universe 0, ..., n-1 (n >= 1).
 * Constructors create n singleton clusters.
 * @author Bo Sun
 */
public interface Partition {
    /** Returns the integer identifier of the cluster containing x. */
    int find(int x);

    /**
     * Merges the smaller cluster into the larger; equal sizes keep the lower identifier.
     * Returns true when two distinct clusters merge.
     * Otherwise return false and leaves cluster information unchanged.
     */
    boolean union(int x, int y);

    /** Returns the number of members of x's cluster. */
    int size(int x);

    /** Returns the number of current clusters. */
    int count();

    /** Returns the cumulative union traversal count. */
    long elementVisits();
}
