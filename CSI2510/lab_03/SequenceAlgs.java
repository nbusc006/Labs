public class SequenceAlgs<E> {

    // Vérifie si la séquence est palindromique en O(n)
    public boolean isPalindrome(Sequence<E> S) {
        if (S.isEmpty()) return true;
        
        Position<E> left = S.first();
        Position<E> right = S.last();
        
        int half = S.size() / 2;
        for (int i = 0; i < half; i++) {
            if (!left.getElement().equals(right.getElement())) {
                return false;
            }
            left = S.after(left);
            right = S.before(right);
        }
        return true;
    }

    // Inverse la séquence sur place (sans nouvelle liste) en O(n)
    public void inplaceReverse(Sequence<E> S) {
        if (S.isEmpty()) return;
        
        Position<E> left = S.first();
        Position<E> right = S.last();
        
        int half = S.size() / 2;
        for (int i = 0; i < half; i++) {
            // Échange des valeurs
            E temp = left.getElement();
            S.set(left, right.getElement());
            S.set(right, temp);
            
            // Rapprochement des pointeurs
            left = S.after(left);
            right = S.before(right);
        }
    }

    // DÉFI OPTIONNEL : Inverse par blocs de taille k
    public void inplaceKReverse(Sequence<E> S, int k) {
        if (S.isEmpty() || k <= 1) return;
        
        Position<E> groupStart = S.first();
        
        while (groupStart != null) {
            // Identifier le début et la fin du sous-groupe de taille k
            Position<E> groupEnd = groupStart;
            int count = 1;
            while (count < k && S.after(groupEnd) != null) {
                groupEnd = S.after(groupEnd);
                count++;
            }
            
            // Sauvegarder le début du prochain groupe pour l'itération suivante de la boucle while
            Position<E> nextGroupStart = S.after(groupEnd);
            
            // Inverser le groupe courant
            Position<E> left = groupStart;
            Position<E> right = groupEnd;
            int swaps = count / 2;
            
            for (int i = 0; i < swaps; i++) {
                E temp = left.getElement();
                S.set(left, right.getElement());
                S.set(right, temp);
                
                left = S.after(left);
                right = S.before(right);
            }
            
            // Avancer au prochain groupe
            groupStart = nextGroupStart;
        }
    }
}