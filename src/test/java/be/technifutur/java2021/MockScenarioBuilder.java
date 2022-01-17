package be.technifutur.java2021;

import org.easymock.EasyMock;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class MockScenarioBuilder implements ApplicationFactory {

    private List<String> scenario;

    public MockScenarioBuilder() {
        List<String> scenario = getScenario();
        this.scenario = scenario == null ? new ArrayList<>() :new ArrayList<>(scenario);
        if (this.scenario.size() % 2 == 0) {
            this.scenario.add("THIS END");
        }
    }

    protected abstract List<String> getScenario();

    public Supplier<String> createSupplier() {
        Supplier<String> supplier = EasyMock.createMock(Supplier.class);
        boolean input = false;
        for (String s : scenario) {
            if (input) {
                EasyMock.expect(supplier.get()).andReturn(s);
            }
            input = !input;
        }
        EasyMock.replay(supplier);
        return supplier;
    }

    public Consumer<String> createConsumer() {
        Consumer<String> consumer = EasyMock.createMock(Consumer.class);
        boolean output = true;
        for (String s : scenario) {
            if (output) {
                consumer.accept(s);
            }
            output = !output;
        }
        EasyMock.replay(consumer);
        return consumer;
    }

    public Application createApplication() {
        Application application = EasyMock.createMock(Application.class);
        application.start();
        int i = 0;
        for (; i < scenario.size() - 1; i++) {
            EasyMock.expect(application.isFinsish()).andReturn(false);
            EasyMock.expect(application.getScreen()).andReturn(scenario.get(i++));
            application.request(scenario.get(i));

        }
        EasyMock.expect(application.isFinsish()).andReturn(true);
        EasyMock.expect(application.getScreen()).andReturn(scenario.get(i));

        EasyMock.replay(application);
        return application;
    }

    @Override
    public Application getApplication() {
        return createApplication();
    }

    @Override
    public Consumer<String> getOutput() {
        return createConsumer();
    }

    @Override
    public Supplier<String> getInput() {
        return createSupplier();
    }
}
