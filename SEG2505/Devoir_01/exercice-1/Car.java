import java.time.LocalDateTime;
import java.util.Locale;

/**
 * Classe représentant un vélo.
 */
public class Car extends LandTransport {
    /** Poids maximum (en kg). */
    private static final double MAX_WEIGHT = 2500.0;
    /** Vitesse maximale (en km/h). */
    private static final double MAX_SPEED = 200.0;
    /** Date et heure d'enregistrement de la classe. */
    private static final LocalDateTime REGISTRATION_TIME;
    /** Vitesse minimale (en km/h). */
    private static final double MIN_SPEED = initMinSpeed();
    /** Compteur d'instances valides créées pour cette classe. */
    private static int instanceCount = 0;

    static {
        REGISTRATION_TIME = LocalDateTime.now();
    }

    /** @return La vitesse minimale calculée (10% de la vitesse maximale). */
    private static double initMinSpeed() {
        return MAX_SPEED * 0.10;
    }

    /**
     * Constructeur par défaut. Appelle le constructeur paramétré avec 30% des maximums.
     */
    public Car() {
        this(MAX_WEIGHT * 0.3, MAX_SPEED * 0.3);
        System.out.printf("Default constructor completed creation instance %d of Car.\n", getId());
    }

    /**
     * Constructeur avec paramètres.
     * 
     * @param emptyWeight Poids à vide.
     * @param cruisingSpeed Vitesse de croisière.
     * @throws IllegalArgumentException Si le poids ou la vitesse sont invalides.
     */
    public Car(double emptyWeight, double cruisingSpeed) {
        super(emptyWeight, cruisingSpeed);
        if (emptyWeight < 0) {
            throw new IllegalArgumentException("Illegal weight (" + emptyWeight + ").");
        }
        if (emptyWeight > MAX_WEIGHT) {
            throw new IllegalArgumentException("Empty weight exceeds maximum weight.");
        }
        if (cruisingSpeed > MAX_SPEED) {
            throw new IllegalArgumentException("Cruising speed exceeds maximum speed.");
        }
        instanceCount++;
        System.out.printf(Locale.US, "Parametrized constructor of Bike completed creation of instance %d with emptyWeight=%.2f, cruisingSpeed=%.2f\n", getId(), emptyWeight, cruisingSpeed);
    }

    /** @return Le poids maximum. */
    public static double getMaxWeight() { return MAX_WEIGHT; }
    /** @return La vitesse maximale. */
    public static double getMaxSpeed() { return MAX_SPEED; }
    /** @return La date d'enregistrement. */
    public static LocalDateTime getRegistrationTime() { return REGISTRATION_TIME; }
    /** @return Le type de transport. */
    public static String getTransportType() { return "Car"; }
    /** @return Le nombre d'instances valides. */
    public static int count() { return instanceCount; }

    @Override
    public void start() {
        System.out.printf("Car instance %d is starting.\n", getId());
        super.start();
    }

    @Override
    public void move() {
        System.out.printf("Car instance %d is moving.\n", getId());
        super.move();
    }

    @Override
    public void stop() {
        System.out.printf("Car instance %d is stopping.\n", getId());
        super.stop();
    }
}