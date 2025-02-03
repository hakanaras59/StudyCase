package features.Utilities;

import com.intuit.karate.Runner;

import com.intuit.karate.Results;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParalelRunner {

    @Test
    void testParallel() {
        Results results = Runner.path("classpath:features")
                .parallel(5);
        assertEquals(0, results.getFailCount(), results.getErrorMessages());
    }

}
