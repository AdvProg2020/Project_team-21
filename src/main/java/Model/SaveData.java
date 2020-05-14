package Model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SaveData {

    private static ArrayList<String> allFilesAdd = new ArrayList<>();

    {

    }

    public void readData(Object object){
        try {
            FileReader reader = new FileReader("MyFile.txt");
            int character;

            while ((character = reader.read()) != -1) {
                System.out.print((char) character);
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeData(){
        try {
            FileWriter writer = new FileWriter("MyFile.txt", true);
            writer.write("Hello World");
            writer.write("\r\n");   // write new line
            writer.write("Good Bye!");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
