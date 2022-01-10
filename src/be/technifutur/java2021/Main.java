package be.technifutur.java2021;

import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) {
        if(args.length == 1){
            try {
                Class<Application> app = (Class<Application>) Class.forName(args[0]);
                Constructor<Application> constructor = app.getDeclaredConstructor();
                Application application = constructor.newInstance();
            } catch (ClassNotFoundException e) {
                System.out.printf("la classe : %s n'a pas été trouvée !%n",args[0]);
            } catch (ClassCastException e){
                System.out.printf("la classe : %s n'implémente pas %s !%n",args[0], Application.class);
            } catch (NoSuchMethodException e) {
                System.out.printf("la classe : %s n'a pas de constructeur sans paramètres !%n",args[0]);
            } catch (IllegalAccessException e) {
                System.out.printf("le constructeur de la classe : %s n'a pas accessible !%n",args[0]);
            } catch (InstantiationException e) {
                System.out.printf("la classe : %s ne peux pas être instanciée !%n",args[0]);
            } catch (InvocationTargetException e) {
                System.out.printf("le constructeur de la classe : %s a soulevé l'exception %s !%n",args[0], e);
            } catch (Exception e) {

                e.printStackTrace();
        }

        } else{
            System.out.println(" Usage : java [option] be.technifutur.java2021.Main <Nom_de_la_classe_Application>");
        }
    }
}
