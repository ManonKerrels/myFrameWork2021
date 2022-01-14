package be.technifutur.java2021;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MyFramework {

    private static Logger LOGGER = LoggerFactory.getLogger(MyFramework.class);

    private InputStream systemIn;
    private PrintStream systemOut;
    private Supplier<String> supplier;
    private Consumer<String> consumer;

    public void start(ApplicationFactory factory) {
        setSupplier(factory.getInput());
        setConsumer(factory.getOutput());
        start(factory.getApplication());
    }

    public void start(Application app, Supplier<String> supplier, Consumer<String> consumer) {
        setSupplier(supplier);
        setConsumer(consumer);
        start(app);
    }

    public void setSupplier(Supplier<String> supplier) {
        this.supplier = supplier;
    }

    public void setConsumer(Consumer<String> consumer) {
        this.consumer = consumer;
    }

    public void start(Application app) {
        LOGGER.info("Démarrage du framework");
        configureSystem();
        Supplier<String> input = getInput();
        Consumer<String> output = getOutput();
        try {
            LOGGER.info(String.format("Démarrage de l'application %s",app.getClass()));
            app.start();
            while (!app.isFinsish()) {
                output.accept(app.getScreen());
                app.request(input.get());
            }
            output.accept(app.getScreen());
            LOGGER.info(String.format("arrêt de l'application"));
        } catch (Exception e) {
            LOGGER.error(String.format("L’application c’est arrêtée sur l’erreur : %s - %s", e.getClass(), e.getMessage()));
        } finally {
            restoreSystem();
        }
        LOGGER.info(String.format("arrêt du framework"));
    }

    private Consumer<String> getOutput() {
        if (this.consumer == null) {
            this.consumer = (s)->{};
        }
        return this.consumer;
    }

    private void restoreSystem() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

    private void configureSystem() {
        systemIn = System.in;
        systemOut = System.out;
        System.setIn(null);
        System.setOut(null);
    }

    private Supplier<String> getInput() {
        if (this.supplier == null) {
            this.supplier = () -> "no input";
        }
        return this.supplier;
    }
}
