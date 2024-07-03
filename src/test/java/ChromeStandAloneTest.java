import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ChromeStandAloneTest{

    @BeforeTest
    public void setUp() throws IOException, InterruptedException {
        if(InvokeDockerFilesTest.dockerFileStartsLogsFile.exists()) InvokeDockerFilesTest.dockerFileStartsLogsFile.delete();
        if(InvokeDockerFilesTest.dockerFileStopsLogsFile.exists()) InvokeDockerFilesTest.dockerFileStopsLogsFile.delete();
        InvokeDockerFilesTest.startDockerFile();
    }

    @Test
    public static void testChrome() throws MalformedURLException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setBrowserName("chrome");
        URL url = new URL("http://localhost:4444/wd/hub");
        RemoteWebDriver driver = new RemoteWebDriver(url, desiredCapabilities);
        driver.get("https://www.google.com");
        System.out.println(driver.getTitle());
        driver.quit();
    }

    @AfterTest
    public void tearDown() throws IOException, InterruptedException {
        InvokeDockerFilesTest.stopDockerFile();
    }
}
