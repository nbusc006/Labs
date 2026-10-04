/**
 * Représente un événement avec un nom et une priorité.
 */
public class Event {
    private String name;
    private int priority;

    /**
     * Constructeur de l'événement.
     * @param name Nom de l'événement.
     * @param priority Priorité (1 à 5).
     */
    public Event(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }

    /** @return Le nom de l'événement. */
    public String getName() { return name; }
    
    /** @return La priorité de l'événement. */
    public int getPriority() { return priority; }

    @Override
    public String toString() {
        return "Event{name='" + name + "', priority=" + priority + "}";
    }
}