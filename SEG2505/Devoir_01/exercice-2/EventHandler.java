/**
 * Interface fonctionnelle pour la gestion d'événements.
 */
@FunctionalInterface
public interface EventHandler {
    /**
     * Traite un événement spécifique.
     * @param event L'événement à traiter.
     */
    void handle(Event event);
}