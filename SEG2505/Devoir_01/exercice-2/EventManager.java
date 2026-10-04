import java.util.Arrays;
import java.util.List;

/**
 * Gestionnaire permettant de définir et traiter des événements via des classes anonymes.
 */
public class EventManager {
    
    /** Liste des événements par défaut. */
    public static final List<Event> DEFAULT_EVENTS = Arrays.asList(
        new Event("Database Backup", 1),
        new Event("Server Down", 5),
        new Event("User Login", 2)
    );

    /** Handler qui écrit dans les logs. */
    public static final EventHandler LOGGER = new EventHandler() {
        @Override
        public void handle(Event event) {
            System.out.println("[LOG] " + event.getName() + " (Priority: " + event.getPriority() + ")");
        }
    };

    /** Handler qui déclenche une alerte critique si la priorité est élevée. */
    public static final EventHandler ALERTER = new EventHandler() {
        @Override
        public void handle(Event event) {
            if (event.getPriority() == 5) {
                System.out.println("[ALERT] CRITICAL: " + event.getName() + "!");
            }
        }
    };

    /** Handler qui envoie une notification à l'utilisateur. */
    public static final EventHandler NOTIFIER = new EventHandler() {
        @Override
        public void handle(Event event) {
            System.out.println("[NOTIFICATION] User alerted about: " + event.getName());
        }
    };

    /**
     * Traite une liste d'événements avec une série de gestionnaires (handlers).
     * @param events Liste d'événements.
     * @param handlers Liste de gestionnaires.
     */
    public static void processEvents(List<Event> events, List<EventHandler> handlers) {
        for (Event event : events) {
            System.out.println("\n--- Processing Event: " + event + " ---");
            for (EventHandler handler : handlers) {
                handler.handle(event);
            }
        }
    }
}