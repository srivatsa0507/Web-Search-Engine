
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

public class WebCrawler {
    // Mapping the URL to the path
    private static Set<String> urls = new HashSet<>();   // Using the hashset to dedup values

    public static Set<String> getUrls() {
        return urls;
    }

    public static void clear() {
        urls.clear();
    }

    // Checking for Valid/in Valid URL
    public static boolean isValidURL(String url) {
        if (!url.contains("http"))
            url = "https://" + url;
        try {
            URL object = new URL(url);
            object.toURI();
            return true;
        } catch (Exception e) {
        }
        return false;
    }
    
    // Crawling the URL till depth
    public static boolean startCrawler(String url, int depth) {
        if (!urls.contains(url) && depth < Settings.MAX_DEPTH) {
            System.out.printf("[%d]: %s\n", urls.size(), url);
            try {
                Document document = Jsoup.connect(url).get();
                String fileName = document.title().toString().replace(" ", "_").replace("|", "_"); 
                saveAsHtml(document.text(), fileName);
                saveAsText(url + Settings.DELIMITER + document.text(), fileName);
                urls.add(url);
                for (Element page: document.select("a[href]")) {
                    String link = page.attr("abs:href");
                    startCrawler(link, depth+1);
                }
            } catch(java.net.UnknownHostException| org.jsoup.HttpStatusException e) {
            }catch (Exception e) {
                System.err.printf("[%s] %s\n",url, e);
                e.printStackTrace();
            }
        }
        return urls.size() > 0;
    }

    // Saving document in the HTML file
    public static void saveAsHtml(String document, String fileName) {
        try(
            FileWriter fw = new FileWriter(Settings.HTML_PATH + File.separator+ fileName + ".html");
            PrintWriter pw = new PrintWriter(fw);
        ) {
            pw.write(document);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Saving document in the TXT file
    public static void saveAsText(String document, String fileName) {
        try(
            FileWriter fw = new FileWriter(Settings.TEXT_PATH + File.separator+ fileName +  ".txt");
            PrintWriter pw = new PrintWriter(fw);
        ) {
        	
            String data = document.toLowerCase();
            pw.write(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
