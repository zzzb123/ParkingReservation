package parkingserver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;

public class FileInteract {
    public static LinkedList<String> getFileContents(String filename) throws Exception {
        LinkedList<String> ret = new LinkedList<>();
        BufferedReader file = new BufferedReader(new FileReader(filename));
        while(file.ready()){
            ret.add(file.readLine());
        }
        file.close();
        return ret;
    }
}