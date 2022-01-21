package be.technifutur.java2021.api;

import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Service donnant accès à une {@link Application} et à des canaux d'entrée/sortie.
 * <ul>
 *     <li>application : {@link Application} l'application.</li>
 *     <li>input : {@link Supplier}&lt;{@link String}&gt; le canal d'entré.</li>
 *     <li>output : {@link Consumer}&lt;{@link String}&gt; le canal de sortie.</li>
 * </ul>
 */
public interface ApplicationFactory {

    /**
     * Retourne une application prète à être utilisée.
     * @return l'application.
     */
    Application getApplication();

    /**
     * Retourne un canal de sortie.
     * Par défaut la méthode retourne( System.out.print() )
     * @return le canal de sortie
     */
    default Consumer<String> getOutput(){
        return System.out::print;
    }

    /**
     * Retourne un canal d'entrée.
     * Par défaut la méthode retourne( new Scanner(System.in).nextline() )
     * @return le canal d'entrée
     */
    default Supplier<String> getInput(){
        Scanner scanner = new Scanner(System.in);
        return () -> scanner.nextLine();
    }
}
