package be.technifutur.java2021.api;

/**
 * Application travaillant comme écouteur d'événement de son environnement.
 * Les méthodes de l'application sont des réponses de l'application à des événements.
 */
public interface Application {

    /**
     * Actions réalisées au démarrage ou redémarrage de l'application.
     */
    void start();

    /**
     * Retourne l'état finish de l'application.
     * @return true si l'application est finie sinon false.
     */
    boolean isFinsish();

    /**
     * Retourne l'écran à communiquer à l'utilisateur dans l'état actuel de l'application.
     * @return l'écran.
     */
    String getScreen();

    /**
     * Actions réalisées à la réception de la requette {@code request} en fonction de l'état de l'application.
     * @param request la requête de l'utilisateur.
     */
    void request(String request);
}
