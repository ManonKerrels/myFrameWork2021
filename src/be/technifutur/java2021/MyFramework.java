package be.technifutur.java2021;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.function.Supplier;

public class MyFramework {


    private InputStream systemIn;
    private PrintStream systemOut;
    private Supplier<String> supplier;

    public void setSupplier(Supplier<String> supplier) {
        this.supplier = supplier;
    }

    public void start(Application app) {
        configureSystem();
        Supplier<String> input = getInput();
        try {
            app.start();
            while (!app.isFinsish()) {
                systemOut.print(app.getScreen());
                app.request(input.get());
            }
            systemOut.print(app.getScreen());

        } catch (Exception e) {
            systemOut.printf("L’application c’est arrêtée sur l’erreur : %s - %s", e.getClass(), e.getMessage());
        } finally {
            restoreSystem();
        }

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
