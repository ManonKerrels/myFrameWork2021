package be.technifutur.java2021;

import be.technifutur.java2021.api.Application;
import be.technifutur.java2021.api.ApplicationFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * MyFramework est un environnement excécution basique d'une {@link Application}.
 * MyFramework est défini par :
 * <ul>
 *     <li>input : {@link Supplier}&lt;{@link String}&gt; le canal d'entré du framework.</li>
 *     <li>output : {@link Consumer}&lt;{@link String}&gt; le canal de sortie du framework.</li>
 * </ul>
 * Le protocole d'appel d'une application app est
 * <ol>
 *     <li>app.start();</li>
 *     <li>tantque not app.isFinsish()
 *     <ol>
 *         <li>output.accept(app.getScreen());</li>
 *         <li>app.request(input.get()));</li>
 *     </ol>
 *     fintantque
 *     </li>
 *     <li>output.accept(app.getScreen());</li>
 * </ol>
 */
public class MyFramework {

    private static final Logger LOGGER = LogManager.getLogger(MyFramework.class);

    private InputStream systemIn;
    private PrintStream systemOut;
    private Supplier<String> input;
    private Consumer<String> output;

    /**
     * Exécute l'application fournie par la factory et initialise les canaux d'entré/sortie du Framework.
     * <ul>
     *     <li>Le canal d'entée est récupéré par l'appel de {@link ApplicationFactory#getInput()}</li>
     *     <li>Le canal de sortie est récupéré par l'appel de {@link ApplicationFactory#getOutput()}</li>
     *     <li>l'application est récupérée par l'appel de {@link ApplicationFactory#getApplication()}</li>
     * </ul>
     *
     * @param factory La factory de l'application démarrée.
     * @throws NullPointerException si la factory est null ou si {@link ApplicationFactory#getApplication()} retourne null.
     */
    public void start(ApplicationFactory factory) {
        setInput(factory.getInput());
        setOutput(factory.getOutput());
        start(factory.getApplication());
    }

    /**
     * Exécute l'application {@code app}
     *
     * @param app l'application.
     */
    public void start(Application app) {
        LOGGER.info("Démarrage du framework");
        configureSystem();
        Supplier<String> input = getInput();
        Consumer<String> output = getOutput();
        try {
            LOGGER.info(String.format("Démarrage de l'application %s", app.getClass()));
            app.start();
            while (!app.isFinsish()) {
                output.accept(app.getScreen());
                app.request(input.get().toString());
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
        if (this.output == null) {
            this.output = (s) -> {
            };
        }
        return this.output;
    }

    /**
     * Modifie le canal de sortie du framework.
     *
     * @param output le canal de sortie.
     */
    public void setOutput(Consumer<String> output) {
        this.output = output;
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
        if (this.input == null) {
            this.input = () -> "no input";
        }
        return this.input;
    }

    /**
     * Modifie le canal d'entré du framework.
     *
     * @param input le canal d'entré.
     */
    public void setInput(Supplier<String> input) {
        this.input = input;
    }

    /**
     * Exécute l'application {@code app} et initialise les canaux d'entré/sortie avec {@code input} et{@code output}.
     *
     * @param app    l'application exécutée.
     * @param input  le canal d'entrée du framework.
     * @param output le canal de sortie du framework.
     */
    public void start(Application app, Supplier<String> input, Consumer<String> output) {
        setInput(input);
        setOutput(output);
        start(app);
    }
}
