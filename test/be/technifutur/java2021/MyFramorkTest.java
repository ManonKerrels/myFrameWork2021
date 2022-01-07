package be.technifutur.java2021;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

public class MyFramorkTest {
    @Test
    void testStartHelloWorld(){
        Application app = EasyMock.createMock(Application.class);
        app.start();
        EasyMock.expect(app.isFinsish()).andReturn(true);
        EasyMock.expect(app.getScreen()).andReturn("Hello world");
        EasyMock.replay(app);
        new MyFramework().start(app);
        EasyMock.verify(app);
    }

    @Test
    void testStartHelloYannick(){
        Application app = EasyMock.createMock(Application.class);
        app.start();
        EasyMock.expect(app.isFinsish()).andReturn(false);
        EasyMock.expect(app.getScreen()).andReturn("What's your name? : ");
        app.request("Yannick");
        EasyMock.expect(app.isFinsish()).andReturn(true);
        EasyMock.expect(app.getScreen()).andReturn("Hello Yannick");
        EasyMock.replay(app);
        MyFramework myFramework = new MyFramework();
        myFramework.setSupplier(()->"Yannick");
        myFramework.start(app);
        EasyMock.verify(app);
    }

}
