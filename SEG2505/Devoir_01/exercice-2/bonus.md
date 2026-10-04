# Bonus 1 : Implémentation avec les Lambdas

Voici à quoi ressembleraient les gestionnaires avec des fonctions lambdas :

```java
public static final EventHandler LOGGER = event -> 
    System.out.println("[LOG] " + event.getName() + " (Priority: " + event.getPriority() + ")");

public static final EventHandler ALERTER = event -> {
    if (event.getPriority() == 5) {
        System.out.println("[ALERT] CRITICAL: " + event.getName() + "!");
    }
};

public static final EventHandler NOTIFIER = event -> 
    System.out.println("[NOTIFICATION] User alerted about: " + event.getName());
```

## Pour 
Le code est extrêmement concis, lisible et allège visuellement la classe. L'inférence de type accélère le développement.

## Contre
Il n'est pas possible d'ajouter des variables d'état (attributs) internes spécifiques au gestionnaire, contrairement à une classe anonyme où l'on pourrait conserver un état local (ex: un compteur de passages).