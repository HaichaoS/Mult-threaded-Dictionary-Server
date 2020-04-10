package Server;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Haichao Song
 * Description:
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

    public boolean isWordExist(String word) {
        return dict.toString().contains("\"Word\":\""+word+"\"");
    }

    public String searchDict(String word) {

        if (isWordExist(word)) {
            System.out.println("Server check");
            for (int i = 0 ; i < dict.size(); i ++) {
                JSONObject res = (JSONObject) dict.get(i);
                System.out.println("Target Object" + res.toString());
                if (res.get("Word").toString().equals(word)) {
                    System.out.println("Get Meaning: " + res.get("Meaning").toString());
                    return res.get("Meaning").toString();
                }
            }
            return null;
        } else {
            return null;
        }
    }

    public boolean addDict(String word, String meaning) {
        if (isWordExist(word)) {
            System.out.println("Server check");
            return false;
        } else {
            JSONObject wordObject = new JSONObject();
            wordObject.put("Word", word);
            wordObject.put("Meaning", meaning);

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
