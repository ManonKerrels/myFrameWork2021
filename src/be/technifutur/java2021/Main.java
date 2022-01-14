package be.technifutur.java2021;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String... args) {
        try {
            ApplicationFactory factory = null;
            if (args.length == 1) {
                factory = newInstance(args[0], ApplicationFactory.class);
                new MyFramework().start(factory);
            } else {
                System.out.println(" Usage : java [option] be.technifutur.java2021.Main <Nom_de_la_classe_Application>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private static <T> T newInstance(String ApplicationClassName, Class<T> type) throws Exception {
        try {
            type = (Class<T>) Class.forName(ApplicationClassName);
            Constructor<T> constructor = type.getDeclaredConstructor();
            return constructor.newInstance();
        } catch (ClassNotFoundException e) {
            throw new Exception(String.format("la classe : %s n'a pas été trouvée !%n", ApplicationClassName), e);
        } catch (ClassCastException e) {
            throw new Exception(String.format("la classe : %s n'implémente pas %s !%n", ApplicationClassName, Application.class), e);
        } catch (NoSuchMethodException e) {
            throw new Exception(String.format("la classe : %s n'a pas de constructeur sans paramètres !%n", ApplicationClassName), e);
        } catch (IllegalAccessException e) {
            throw new Exception(String.format("le constructeur de la classe : %s n'a pas accessible !%n", ApplicationClassName), e);
        } catch (InstantiationException e) {
            throw new Exception(String.format("la classe : %s ne peux pas être instanciée !%n", ApplicationClassName), e);
        } catch (InvocationTargetException e) {
            throw new Exception(String.format("le constructeur de la classe : %s a soulevé l'exception %s !%n", ApplicationClassName, e), e);
        }
    }
}
