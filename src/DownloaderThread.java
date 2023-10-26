import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

public class DownloaderThread extends Thread {
    private URL url;

    public DownloaderThread(String urlString) {
        try {
            url = new URL(urlString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        InputStream input = null;
        try {
            input = url.openStream();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String file = url.getFile();
        int pos = file.lastIndexOf("/");
        String fileName = file.substring(pos + 1);

        try {
            FileOutputStream outputStream = new FileOutputStream(fileName);
            int data;
            int lineCount = 0;
            while ((data = input.read()) != -1) {
                outputStream.write(data);
                if (data == '\n') {
                    lineCount++;
                }
            }
            outputStream.close();

            System.out.println(Thread.currentThread().getName() + ": " + fileName + ", líneas: " + lineCount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
