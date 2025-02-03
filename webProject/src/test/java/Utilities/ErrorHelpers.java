package Utilities;

import java.io.IOException;
import static BaseTest.BaseTest.LogCountManager;

public class ErrorHelpers {
    public static void sendErrorMessageAndTakeScreenShot(String errorMessage) throws IOException {
        ScreenshotHelper.takeScreenShot();
        Log.error(errorMessage);
        LogCountManager();
    }
}