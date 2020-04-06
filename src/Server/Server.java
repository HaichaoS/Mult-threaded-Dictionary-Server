package Server;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Haichao Song
 * Description:
 */
public class Server {

    private int port;
    private String path;
    private JSONArray dict;
    private ServerGUI serverGUI;

    public static void main(String[] args) {
        try {
            Server server = new Server(args[0], args[1]);
            server.create();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Server(String port, String path) {
        this.port = Integer.parseInt(port);
        this.path = path;
        this.dict = readDic(path);
    }

    public void create() {

        try {

            ServerSocket ss = new ServerSocket(this.port);
            this.serverGUI = new ServerGUI(this);
            serverGUI.getFrame().setVisible(true);

            while(true) {

                // socket object to receive incoming client requests
                Socket s = ss.accept();
                System.out.println("A new client is connected : " + s);

                // obtaining input and out streams
//                DataInputStream dis = new DataInputStream(s.getInputStream());
//                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                System.out.println("Assigning new thread for this client");

                // create a new thread object
                Thread t = new ClientHandler(s,this);

                // Invoking the start() method
                t.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JSONArray readDic(String path) {
        JSONParser jsonParser = new JSONParser();
        JSONArray dictList = new JSONArray();

        try (FileReader reader = new FileReader(path))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            dictList = (JSONArray) obj;
            System.out.println(dictList);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dictList;

    }

    public String searchDict(String word) {
        if (dict.contains(word)) {
            return null;
        } else {
            for (int i = 0 ; i < dict.size(); i ++) {
                JSONObject res = (JSONObject) dict.get(i);
                if (res.get("Word").toString().equals(word)) {
                    return res.get("Meaning").toString();
                }
            }
            return null;
        }
    }

    public boolean addDict(String word, String meaning) {
        if (dict.contains(word)) {
            return false;
        } else {
            JSONObject wordObject = new JSONObject();
            wordObject.put("Word", word);
            wordObject.put("Meaning", meaning);

            dict.add(wordObject.toJSONString());

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
        if (dict.contains(word)) {
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
            return false;
        }
    }

    public void printLog(String s) {
        System.out.println(s);
        if (serverGUI != null) serverGUI.getTextArea().append(s + '\n');
    }

    public int getPort() {return this.port;}
    public String getPath() {return this.path;}

}