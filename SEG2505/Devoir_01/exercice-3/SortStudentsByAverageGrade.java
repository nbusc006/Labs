import java.util.Comparator;

/**
 * Comparateur permettant de trier les étudiants par moyenne générale descendante.
 */
public class SortStudentsByAverageGrade implements Comparator<Student> {
    @Override
    public int compare(Student s1, Student s2) {
        // Renverser l'ordre (s2 par rapport à s1) pour avoir un tri décroissant
        return Double.compare(s2.getAverageGrade(), s1.getAverageGrade());
    }
}