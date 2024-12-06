package pages;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

public class AmazonPageWebService {

    public static class PageResponse {

        private String status;
        private String pageTitle;

        public PageResponse(String status, String pageTitle) {
            this.status = status;
            this.pageTitle = pageTitle;
        }

        public String getStatus() {
            return status;
        }

        public String getPageTitle() {
            return pageTitle;
        }
    }



    public PageResponse getPageResponse(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36");
        connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        connection.setRequestProperty("Accept-Language", "en-US,en;q=0.9");
        connection.setRequestProperty("Connection", "keep-alive");
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        String status = responseCode == 200 ? "OK" : "Dead link (" + responseCode + ")";

        String searchLabelText = "";
        if (responseCode == 200) {
            InputStream inputStream = connection.getInputStream();
            String encoding = connection.getContentEncoding();

            if ("gzip".equalsIgnoreCase(encoding)) {
                inputStream = new GZIPInputStream(inputStream);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder responseBody = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                responseBody.append(line);
            }
            reader.close();

            Document doc = Jsoup.parse(responseBody.toString());
            Element searchLabel = doc.getElementById("nav-search-label-id");

            if (searchLabel != null) {
                searchLabelText = searchLabel.text();
            }
        }

        return new PageResponse(status, searchLabelText);
    }


}
