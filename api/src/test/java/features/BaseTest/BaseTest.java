package features.BaseTest;

import com.intuit.karate.junit5.Karate;

public class BaseTest {

    @Karate.Test
    Karate ApiTest() {
        return Karate.run("../ApiTest").relativeTo(getClass());
    }
}