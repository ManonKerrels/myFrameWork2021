package be.technifutur.java2021;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

public class MyFramorkTest {
    @Test
    void testStartHelloWorld(){
        Application app = getHelloWorld();
        MyFramework myFramework = new MyFramework();
        myFramework.setConsumer((s)->{});
        myFramework.start(app);
        EasyMock.verify(app);
    }

    private Application getHelloWorld() {
        Application app = EasyMock.createMock(Application.class);
        app.start();
        EasyMock.expect(app.isFinsish()).andReturn(true);
        EasyMock.expect(app.getScreen()).andReturn("Hello world\n");
        EasyMock.replay(app);
        return app;
    }

    @Test
    void testStartHelloYannick(){
        Application app = getHelloYannick();
        new MyFramework().start(app,()->"Yannick",(s)->{});
        EasyMock.verify(app);
    }

    private Application getHelloYannick() {
        Application app = EasyMock.createMock(Application.class);
        app.start();
        EasyMock.expect(app.isFinsish()).andReturn(false);
        EasyMock.expect(app.getScreen()).andReturn("What's your name? : ");
        app.request("Yannick");
        EasyMock.expect(app.isFinsish()).andReturn(true);
        EasyMock.expect(app.getScreen()).andReturn("Hello Yannick\n");
        EasyMock.replay(app);
        return app;
    }

}
