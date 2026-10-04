import java.util.Comparator;

/**
 * Comparateur permettant de trier les étudiants par ordre alphabétique du nom de famille.
 */
public class SortStudentsByLastName implements Comparator<Student> {
    @Override
    public int compare(Student s1, Student s2) {
        return s1.getLastName().compareTo(s2.getLastName());
    }
}