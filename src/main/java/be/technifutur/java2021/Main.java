package be.technifutur.java2021;

import be.technifutur.java2021.api.ApplicationFactory;

import java.util.Iterator;
import java.util.Optional;
import java.util.ServiceLoader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Main {

    //private static Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private static final Logger LOGGER =  LogManager.getLogger( Main.class );

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
            LOGGER.info("no found ApplicationFactory ");
            return Optional.empty();
        }
     }
}
