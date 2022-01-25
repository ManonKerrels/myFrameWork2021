package be.technifutur.java2021.api;

public interface Application {
    void start();

    boolean isFinsish();

    String getScreen();

    void request(String request);
}
