import org.testng.Assert;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;

public class InvokeDockerFilesTest {

    static boolean hubStatus = false;
    static long stopAtMilliseconds;
    static File dockerFileStartsLogsFile = new File("dockerFileStarts-logs.txt");
    static File dockerFileStopsLogsFile = new File("dockerFileStops-logs.txt");

    public static void startDockerFile() throws IOException, InterruptedException {
        Runtime.getRuntime().exec("cmd Pushd \"D:\" /c start dockerFileStarts.bat");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 45);
        stopAtMilliseconds = calendar.getTimeInMillis();
        while (System.currentTimeMillis() < stopAtMilliseconds) {
            if (hubStatus) {
                break;
            }
            if(dockerFileStartsLogsFile.exists()) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader("dockerFileStarts-logs.txt"));
                String line = bufferedReader.readLine();
                while (line != null && !hubStatus) {
                    if (line.contains("Node has been added")) {
                        hubStatus = true;
                        break;
                    }
                    line = bufferedReader.readLine();
                }
                bufferedReader.close();
            }
        }
        Assert.assertTrue(hubStatus);
        Thread.sleep(3000);
    }

    public static void stopDockerFile() throws IOException, InterruptedException {
        Runtime.getRuntime().exec("cmd Pushd \"D:\" /c start dockerFileStops.bat");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 45);
        stopAtMilliseconds = calendar.getTimeInMillis();
        while (System.currentTimeMillis() < stopAtMilliseconds) {
            if(dockerFileStopsLogsFile.exists()) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader("dockerFileStops-logs.txt"));
                String line = bufferedReader.readLine();
                while (line != null && !hubStatus) {
                    if (line.contains("stopped: selenium-grid-hub")) {
                        System.out.println("Hub has been stopped");
                        hubStatus = false;
                        break;
                    }
                    line = bufferedReader.readLine();
                }
                bufferedReader.close();
            }
        }
        Assert.assertTrue(hubStatus);
        Thread.sleep(3000);
    }
}
