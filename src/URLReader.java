import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLReader {

    public static void main(String[] args) {

        URL webPage = null;
        URLConnection connection = null;
        List<String> csvFileNames = new ArrayList<>();

        try{

            webPage = new URL("https://people.sc.fsu.edu/~jburkardt/data/csv/csv.html");

            connection = webPage.openConnection();

            BufferedReader br = null;
            String line = null;

            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            System.out.println("Número de lineas en los archivos csv de la página web: ");

            while((line = br.readLine()) != null){
                //System.out.println(line);
                Pattern pattern = Pattern.compile("<a\\s+href\\s*=\\s*\"(.*?\\.csv)\"");
                Matcher matcher = pattern.matcher(line);

                while (matcher.find()) {
                    String fileName = matcher.group(1);
                    csvFileNames.add(fileName);
                }
            }

            br.close();

            for (String fileName : csvFileNames) {
                Thread thread = new DownloaderThread("https://people.sc.fsu.edu/~jburkardt/data/csv/" + fileName);
                thread.start();
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}