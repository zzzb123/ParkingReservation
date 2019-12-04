import java.net.*;
import org.json.simple.*;
import java.io.*;
import java.net.http.HttpRequest;
import java.util.*;

public class GoogleAPI {

    //API Key: AIzaSyAez0a7tgsZdb2Vuywbq_pWZ7UQNVveX1A

    private static final String[] lots = {"Lot%20A", "Lot%20C", "Lot%20L", "Lot%20K", "Lot%20M", "Lot%20O", "Lot%20P", "Lot%20PV", "Lot%20I", "Lot%20J", "Shenandoah%20Parking%20Deck", "Mason%20Pond%20Parking%20Deck", "Rappahannock%20River%20Parking%20Deck", "West%20Campus%20Parking"};
    private static String originalLocation;
    private static int currentLotIndex = 0;
    private static int totalRequests = 0;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print("Current Location: ");
        originalLocation = sc.nextLine();
        System.out.println(originalLocation);
        originalLocation = originalLocation.replace(" ", "+");
        System.out.println(originalLocation);
        while(currentLotIndex != lots.length && totalRequests < 100) {
            try {
                getJSON();
            } catch(MalformedURLException e) {
                System.out.println("MalformedURLException");
            } catch(IOException e) {
                System.out.println("IOException");
            }
        }


    }

    private static void getJSON() throws IOException {
        //URL url = new URL("https://maps.googleapis.com/maps/api/place/findplacefromtext/json?key=AIzaSyAez0a7tgsZdb2Vuywbq_pWZ7UQNVveX1A&input=GMU%20" + lots[currentLotIndex++] + "&inputtype=textquery");
        URL url = new URL("https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=" + originalLocation + "&destinations=GMU%20" + lots[currentLotIndex++] + ",NY&key=AIzaSyAez0a7tgsZdb2Vuywbq_pWZ7UQNVveX1A");
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        InputStreamReader in = new InputStreamReader(conn.getInputStream());
        int read;
        char[] buff = new char[1024];
        StringBuilder str = new StringBuilder();
        while((read = in.read(buff)) != -1) {
            str.append(buff, 0, read);
            totalRequests++;
        }
        System.out.println(str);
        /*HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://maps.googleapis.com/maps/api/place/findplacefromtext/json?key=AIzaSyAez0a7tgsZdb2Vuywbq_pWZ7UQNVveX1A&input=GMU%20" + lots[currentLotIndex++] + "&inputtype=textquery"))
                .build();
        */
    }

}