package Server;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Haichao Song
 * Description:
 */
public class Server {

    private int port;
    private String path;
    private Dict dict;
    private ServerGUI serverGUI;
    private ServerSocket serverSocket;

    public static void main(String[] args) {

        if (Integer.parseInt(args[0]) <= 1024 || Integer.parseInt(args[0]) >= 49151) {
            System.out.println("Invalid Port Number");
            System.exit(-1);
        } else {
            try {
                Server server = new Server(args[0], args[1]);
                server.create();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Server(String port, String path) {
        this.port = Integer.parseInt(port);
        this.path = path;
        this.dict = new Dict(path);
    }

    public void create() {

        try {

            serverSocket = new ServerSocket(this.port);
            this.serverGUI = new ServerGUI(this);
            serverGUI.getFrame().setVisible(true);

            while(true) {

                // socket object to receive incoming client requests
                Socket s = serverSocket.accept();
                System.out.println("A new client is connected : " + s);
                System.out.println("Assigning new thread for this client");

                // create a new thread object
                ClientHandler t = new ClientHandler(s, this, dict);

                // Invoking the start() method
                t.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public JSONArray readDic(String path) {
//
//        JSONParser jsonParser = new JSONParser();
//        JSONArray dictList = new JSONArray();
//
//        try (FileReader reader = new FileReader(path))
//        {
//            //Read JSON file
//            Object obj = jsonParser.parse(reader);
//
//            dictList = (JSONArray) obj;
//            System.out.println(dictList);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        return dictList;
//
//    }


//    public boolean isWordExist(String word) {
//        return dict.toString().contains("\"Word\":\""+word+"\"");
//    }
//
//    public String searchDict(String word) {
//
//        if (isWordExist(word)) {
//            System.out.println("Server check");
//            for (int i = 0 ; i < dict.size(); i ++) {
//                JSONObject res = (JSONObject) dict.get(i);
//                System.out.println("Target Object" + res.toString());
//                if (res.get("Word").toString().equals(word)) {
//                    System.out.println("Get Meaning: " + res.get("Meaning").toString());
//                    return res.get("Meaning").toString();
//                }
//            }
//            return null;
//        } else {
//            return null;
//        }
//    }
//
//    public boolean addDict(String word, String meaning) {
//        if (isWordExist(word)) {
//            System.out.println("Server check");
//            return false;
//        } else {
//            JSONObject wordObject = new JSONObject();
//            wordObject.put("Word", word);
//            wordObject.put("Meaning", meaning);
//
//            dict.add(wordObject);
//
//            try (FileWriter file = new FileWriter(path)) {
//
//                file.write(dict.toJSONString());
//                file.flush();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return true;
//        }
//    }
//
//    public boolean removeDict(String word) {
//        if (isWordExist(word)) {
//            for (int i = 0 ; i < dict.size(); i ++) {
//                JSONObject res = (JSONObject) dict.get(i);
//                if (res.get("Word").toString().equals(word)) {
//                    dict.remove(i);
//                }
//            }
//
//            try (FileWriter file = new FileWriter(path)) {
//
//                file.write(dict.toJSONString());
//                file.flush();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            return true;
//        } else {
//            System.out.println("Server check");
//            return false;
//        }
//    }

    public void printLog(String s) {
        System.out.println(s);
        if (serverGUI != null) serverGUI.getTextArea().append(s + '\n');
    }

    public int getPort() {return this.port;}
    public String getPath() {return this.path;}

}