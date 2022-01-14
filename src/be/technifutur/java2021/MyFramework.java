package be.technifutur.java2021;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MyFramework {


    private InputStream systemIn;
    private PrintStream systemOut;
    private Supplier<String> supplier;
    private Consumer<String> consumer;

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
        configureSystem();
        Supplier<String> input = getInput();
        Consumer<String> output = getOutput();
        try {
            app.start();
            while (!app.isFinsish()) {
                output.accept(app.getScreen());
                app.request(input.get());
            }
            output.accept(app.getScreen());

        } catch (Exception e) {
            systemOut.printf("L’application c’est arrêtée sur l’erreur : %s - %s", e.getClass(), e.getMessage());
        } finally {
            restoreSystem();
        }

    }

    private Consumer<String> getOutput() {
        if (this.consumer == null) {
            this.consumer = systemOut::print;
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
            Scanner scanner = new Scanner(systemIn);
            this.supplier = () -> scanner.nextLine();
        }
        return this.supplier;
    }
}
