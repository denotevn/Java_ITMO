package utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import data.SpaceMarine;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Stack;

public class FileManager {
    /**
     * Reads collection from a file.
     * @return collection .
     */
    public static Stack<SpaceMarine> readCollection(String fileName){
        Stack<SpaceMarine> collection = null;
        Outputer outputer = new Outputer();
        try (FileReader reader = new FileReader(fileName)) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setDateFormat("M/d/yy");
            Gson gson = gsonBuilder.create();
            collection = gson.fromJson(reader, new TypeToken<Stack<SpaceMarine>>() {}.getType());
           Outputer.println("Collection loaded successfully!");
            return collection;
        } catch (FileNotFoundException e) {
            Outputer.printerror("load file not found!");
            e.printStackTrace();
        }catch (NoSuchElementException e){
            Outputer.printerror("the file is empty");
        }catch (IOException e) {
            e.printStackTrace();
        }

        return collection;
    }

    /**
     * Writes collection to a file.
     * @param  marineCollection<SpaceMarine> </SpaceMarine> Collection to write.
     */
    public static  void writeCollection(Stack<SpaceMarine> marineCollection){
        Gson gson = new Gson();

        try(FileOutputStream file = new FileOutputStream("listMarine.json")){
            file.write(gson.toJson(marineCollection).getBytes());
            ResponseOutputer.appendln("Successfully saved the file.");
        } catch (IOException e) {
            ResponseOutputer.appenderror("An error has occurred.");
            e.printStackTrace();
        }

    }
}
