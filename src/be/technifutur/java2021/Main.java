package be.technifutur.java2021;

import be.technifutur.java2021.api.ApplicationFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;
import java.util.Optional;
import java.util.ServiceLoader;

/**
 * Classe de démarrage du framework. L'application à exécutée est récupéré sous la forme d'un service fournit dans le modulePath
 */
public class Main {

    private static final Logger LOGGER =  LogManager.getLogger( Main.class );

    /**
     * Exécute le framework {@link MyFramework}. Avec le service {@link ApplicationFactory} fournit
     * @param args pas utilisé.
     */
    public static void main(String... args) {
            Optional<ApplicationFactory> factory = null;
                factory = newInstance();
                if(factory.isPresent()){
                    new MyFramework().start(factory.get());

            }
        }

    private static Optional<ApplicationFactory> newInstance(){
        ServiceLoader<ApplicationFactory> services = ServiceLoader.load(ApplicationFactory.class);

        Iterator<ApplicationFactory> iterator = services.iterator();

        if (iterator.hasNext()){
            ApplicationFactory factory = iterator.next();
            LOGGER.info(String.format("ApplicationFactory : %s",factory.getClass()));
            return Optional.of(factory);
        }else {
            LOGGER.error("no found ApplicationFactory ");
            return Optional.empty();
        }
     }
}
