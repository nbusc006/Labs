import java.util.Locale;

/**
 * Représente un étudiant et définit son ordre naturel par identifiant.
 */
public class Student implements Comparable<Student> {
    private String studentId;
    private String firstName;
    private String lastName;
    private double averageGrade;

    /**
     * Constructeur de Student.
     * @param studentId Identifiant unique.
     * @param firstName Prénom.
     * @param lastName Nom de famille.
     * @param averageGrade Moyenne.
     */
    public Student(String studentId, String firstName, String lastName, double averageGrade) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.averageGrade = averageGrade;
    }

    /** @return L'identifiant étudiant. */
    public String getStudentId() { return studentId; }
    
    /** @return Le nom de famille. */
    public String getLastName() { return lastName; }
    
    /** @return La moyenne générale. */
    public double getAverageGrade() { return averageGrade; }

    @Override
    public int compareTo(Student other) {
        return this.studentId.compareTo(other.studentId);
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "Student{studentId='%s', firstName='%s', lastName='%s', averageGrade=%.2f}",
                studentId, firstName, lastName, averageGrade);
    }
}