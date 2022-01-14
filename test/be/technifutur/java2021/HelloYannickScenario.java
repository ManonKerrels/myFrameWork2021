package be.technifutur.java2021;

import java.util.Arrays;
import java.util.List;

public class HelloYannickScenario extends MockScenarioBuilder{
    @Override
    protected List<String> getScenario() {
        return Arrays.asList(
                "What's your name? : ",
                "Yannick",
                "Hello Yannick\n",
                "fini"
        );
    }
}
