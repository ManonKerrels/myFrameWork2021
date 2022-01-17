package be.technifutur.java2021;

import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Supplier;

public interface ApplicationFactory {

    Application getApplication();

    default Consumer<String> getOutput(){
        return System.out::print;
    }

    default Supplier<String> getInput(){
        Scanner scanner = new Scanner(System.in);
        return () -> scanner.nextLine();
    }
}
