import java.util.Locale;

/**
 * Classe abstraite représentant un transport terrestre.
 */
public abstract class LandTransport extends Transport {

    /** Constructeur par défaut. */
    public LandTransport() {}

    /**
     * Constructeur avec paramètres.
     * 
     * @param emptyWeight Poids à vide.
     * @param cruisingSpeed Vitesse de croisière.
     */
    public LandTransport(double emptyWeight, double cruisingSpeed) {
        super(emptyWeight, cruisingSpeed);
        System.out.printf(Locale.US, "Parametrized constructor of LandTransport completed creation of instance %d with emptyWeight=%.2f, cruisingSpeed=%.2f\n", getId(), emptyWeight, cruisingSpeed);
    }

    @Override
    public void start() {
        System.out.printf("LandTransport instance %d is starting.\n", getId());
        System.out.printf("Transport instance %d is starting.\n", getId());
    }

    @Override
    public void move() {
        System.out.printf("LandTransport instance %d is moving.\n", getId());
        System.out.printf("Transport instance %d is moving.\n", getId());
    }

    @Override
    public void stop() {
        System.out.printf("LandTransport instance %d is stopping.\n", getId());
        System.out.printf("Transport instance %d is stopping.\n", getId());
    }
}