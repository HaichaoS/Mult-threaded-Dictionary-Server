package Server;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Haichao Song
 * Description: the class that is responsible for:
 * 1) read the dictionary at the start of the server and store it into JSON array form.
 * 2) Do operation according to the command on JSON array and JSON dictionary.
 */
public class Dict {

    private String path;
    private JSONArray dict;

    public Dict(String path) {

        JSONParser jsonParser = new JSONParser();
        this.dict = new JSONArray();
        this.path = path;

        try (FileReader reader = new FileReader(path))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            dict = (JSONArray) obj;
            System.out.println(dict);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /* Check if the word is already exist in the dictionary */
    public boolean isWordExist(String word) {
        return dict.toString().contains("\"Word\":\""+word+"\"");
    }

    /* Get the word information of a word */
    public ArrayList<String> searchDict(String word) {

        ArrayList<String> result = new ArrayList<>();

        if (isWordExist(word)) {
            System.out.println("Server check");
            for (int i = 0 ; i < dict.size(); i ++) {
                JSONObject res = (JSONObject) dict.get(i);
                System.out.println("Target Object" + res.toString());
                if (res.get("Word").toString().equals(word)) {
                    System.out.println("Get Meaning: " + res.get("Meaning").toString());
                    result.add(res.get("Meaning").toString());
                    result.add(res.get("Synonym").toString());
                    return result;
                }
            }
            return null;
        } else {
            return null;
        }
    }

    /* Add the word and its information into dictionary */
    public boolean addDict(String word, String meaning, String synonym) {
        if (isWordExist(word)) {
            System.out.println("Server check");
            return false;
        } else {
            JSONObject wordObject = new JSONObject();
            wordObject.put("Word", word);
            wordObject.put("Meaning", meaning);
            wordObject.put("Synonym", synonym);

            dict.add(wordObject);

            try (FileWriter file = new FileWriter(path)) {

                file.write(dict.toJSONString());
                file.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    /* Remove the word and its information in the dictionary */
    public boolean removeDict(String word) {
        if (isWordExist(word)) {
            for (int i = 0 ; i < dict.size(); i ++) {
                JSONObject res = (JSONObject) dict.get(i);
                if (res.get("Word").toString().equals(word)) {
                    dict.remove(i);
                }
            }

            try (FileWriter file = new FileWriter(path)) {

                file.write(dict.toJSONString());
                file.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return true;
        } else {
            System.out.println("Server check");
            return false;
        }
    }

}
