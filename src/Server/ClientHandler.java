package Server;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Haichao Song
 * Description:
 */
public class ClientHandler extends Thread {

    private Socket s;
    private Server server;
    private DataInputStream dis;
    private DataOutputStream dos;


    // Constructor
    public ClientHandler(Socket s, Server server)
    {
        this.s = s;
        this.server = server;
    }

    @Override
    public void run()
    {

        try {
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(dis.readUTF());

            String command =  (String) jsonObject.get("command");
            String word = (String) jsonObject.get("word");
            String meaning =  (String) jsonObject.get("meaning");
            int state = 0;

            JSONObject jsonServer = new JSONObject();

            if (command == "Search") {

                if (server.searchDict(word) == null) {
                    server.printLog("Fail, the word does not in the dictionary");
                } else {
                    state = 1;
                    server.printLog( word + ": " + server.searchDict(word));
                }

                jsonServer.put("state", String.valueOf(state));
                jsonServer.put("meaning", meaning);
                dos.writeUTF(jsonServer.toJSONString());
                dos.flush();

            } else if  (command == "Add") {

                if (server.addDict(word, meaning) == true) {
                    state = 1;
                    server.printLog("Word add success");
                } else {
                    server.printLog("Fail, the word already in the dictionary");
                }

                jsonServer.put("state", String.valueOf(state));
                jsonServer.put("meaning", "");
                dos.writeUTF(jsonServer.toJSONString());
                dos.flush();

            } else if (command == "Remove") {

                if (server.removeDict(word) == true) {
                    state = 1;
                    server.printLog("Word remove success");
                } else {
                    server.printLog("Fail, the word does not in the dictionary");
                }

                jsonServer.put("state", String.valueOf(state));
                jsonServer.put("meaning", "");
                dos.writeUTF(jsonServer.toJSONString());
                dos.flush();
            }

            dis.close();
            dos.close();
            s.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


}
