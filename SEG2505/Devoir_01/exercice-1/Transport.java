import java.util.Locale;

/**
 * Classe abstraite de base représentant un moyen de transport générique.
 */
public abstract class Transport implements Printable {
    /** Compteur global pour attribuer un identifiant unique à chaque tentative de création. */
    private static int globalCount = 0;
    
    /** Identifiant unique de l'instance. */
    private int id;
    
    /** Poids à vide du transport. */
    private double emptyWeight;
    
    /** Vitesse de croisière du transport. */
    private double cruisingSpeed;

    /**
     * Constructeur par défaut.
     */
    public Transport() {
    }

    /**
     * Constructeur avec paramètres.
     * 
     * @param emptyWeight Poids à vide.
     * @param cruisingSpeed Vitesse de croisière.
     */
    public Transport(double emptyWeight, double cruisingSpeed) {
        this.id = ++globalCount;
        this.emptyWeight = emptyWeight;
        this.cruisingSpeed = cruisingSpeed;
        System.out.printf(Locale.US, "Parametrized constructor of Transport completed creation of instance %d with emptyWeight=%.2f, cruisingSpeed=%.2f\n", id, emptyWeight, cruisingSpeed);
    }

    /** @return L'identifiant du transport. */
    public int getId() { return id; }
    /** @param id Le nouvel identifiant. */
    public void setId(int id) { this.id = id; }

    /** @return Le poids à vide. */
    public double getEmptyWeight() { return emptyWeight; }
    /** @param emptyWeight Le nouveau poids à vide. */
    public void setEmptyWeight(double emptyWeight) { this.emptyWeight = emptyWeight; }

    /** @return La vitesse de croisière. */
    public double getCruisingSpeed() { return cruisingSpeed; }
    /** @param cruisingSpeed La nouvelle vitesse de croisière. */
    public void setCruisingSpeed(double cruisingSpeed) { this.cruisingSpeed = cruisingSpeed; }

    /** Démarre le transport. */
    public abstract void start();
    
    /** Fait avancer le transport. */
    public abstract void move();
    
    /** Arrête le transport. */
    public abstract void stop();

    /**
     * Exécute la séquence complète : démarrage, mouvement, arrêt.
     */
    public void run() {
        start();
        System.out.println();
        move();
        System.out.println();
        stop();
    }

    /**
     * Affiche les informations relatives à l'objet.
     */
    @Override
    public void dump() {
        System.out.printf(Locale.US, "Instance %d of Transport: emptyWeight=%.2f, cruisingSpeed=%.2f\n", id, emptyWeight, cruisingSpeed);
    }
}