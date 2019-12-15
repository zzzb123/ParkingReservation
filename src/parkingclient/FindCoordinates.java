package parkingclient;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FindCoordinates {

    public static String getCoordinates(String inputStr) throws IOException {
        String readyString = inputStr.replace(" ", "+");
        URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address=" + readyString + ",+CA&key=AIzaSyAez0a7tgsZdb2Vuywbq_pWZ7UQNVveX1A");
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        InputStreamReader in = new InputStreamReader(conn.getInputStream());
        int read;
        char[] buff = new char[1024];
        StringBuilder jsonStr = new StringBuilder();
        while((read = in.read(buff)) != -1) {
            jsonStr.append(buff, 0, read);
        }
        String latAndLng = "";
        int i = jsonStr.indexOf("\"lat\" : ") + 8;
        while(jsonStr.charAt(i) != ',') {
            latAndLng += jsonStr.charAt(i);
            i++;
        }
        latAndLng += ",";
        int n = jsonStr.indexOf("\"lng\" : ") + 8;
        while(Character.isDigit(jsonStr.charAt(n)) || jsonStr.charAt(n) == '-' || jsonStr.charAt(n) == '.') {
            latAndLng += jsonStr.charAt(n);
            n++;
        }
        return latAndLng;
    }

}
