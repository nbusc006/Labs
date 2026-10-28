/** Array-based implementation of Partition ADT. 
* Complete every TODO
* Replace each throw new UnsupportedOperationException("TODO: ...") with your implementation.
* @author Bo Sun
*/

public class ArrayPartition implements Partition {
    private int[] setId;
    private int[] setSize;
    private int numberOfSets;
    private long visits;

    public ArrayPartition(int n) {     
        setId = new int[n];
        setSize = new int[n];
        numberOfSets = 0;
        visits = 0;
        for (int i = 0; i<n; i++){
            setId[i] = i;
            setSize[i] = 1;
            numberOfSets++;
        }
    }

    @Override
    public int find(int x) {
        return setId[x];
    }

    @Override
public boolean union(int x, int y) {
    int rootX = find(x);
    int rootY = find(y);
    if (rootX == rootY) {
        return false;
    }
    int survivor, absorbed;
    if (setSize[rootX] > setSize[rootY]) {
        survivor = rootX;
        absorbed = rootY;
    } else if (setSize[rootY] > setSize[rootX]) {
        survivor = rootY;
        absorbed = rootX;
    } else {
        if (rootX < rootY) {
            survivor = rootX;
            absorbed = rootY;
        } else {
            survivor = rootY;
            absorbed = rootX;
        }
    }

    // Mise à jour de tous les éléments du groupe absorbé
    for (int i = 0; i < setId.length; i++) {
        if (setId[i] == absorbed) {
            setId[i] = survivor;
            visits++; // On compte chaque visite/modification selon la logique du devoir
        }
    }

    // Mise à jour des tailles et du nombre de groupes
    setSize[survivor] += setSize[absorbed];
    setSize[absorbed] = 0;
    numberOfSets--;

    return true;
}

    @Override
    public int size(int x) {
        return setSize[x];
    }

    @Override
    public int count() {
        return numberOfSets;
    }

    @Override
    public long elementVisits() {
        return visits;
    }
}
