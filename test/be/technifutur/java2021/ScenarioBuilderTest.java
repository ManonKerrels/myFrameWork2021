package be.technifutur.java2021;


import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.easymock.EasyMock.verify;

public class ScenarioBuilderTest {

    @Test
    void testApplication(){
        ApplicationFactory sb = new MockScenarioBuilder(){
            @Override
            protected List<String> getScenario() {
                return getScenarioList();
            }
        };
        testScenario(sb);

    }

    @Test
    void testMain(){
       Main.main("be.technifutur.java2021.HelloYannickScenario");
    }

    private void testScenario(ApplicationFactory sb) {
        Application application = sb.getApplication();
        testScenarioWithApplication(sb, application);
        verify(application);
    }

    private void testScenarioWithApplication(ApplicationFactory sb, Application application) {
        Supplier<String> supplier = sb.getInput();
        TestScenarioWithApplicationAndInput(sb, application, supplier);
        verify(supplier);
    }

    private void TestScenarioWithApplicationAndInput(ApplicationFactory sb, Application application, Supplier<String> supplier) {
        Consumer<String> consumer = sb.getOutput();
        new MyFramework().start(application, supplier, consumer);
        verify(consumer);
    }

    private static List<String> getScenarioList() {
        return Arrays.asList(
                "What's your name? : ",
                "Yannick",
                "Hello Yannick\n",
                "fini"
        );
    }
}
