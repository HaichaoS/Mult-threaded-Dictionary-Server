package Server;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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

            JSONObject jsonObject = parse(dis.readUTF());
            String command = jsonObject.get("command").toString();
            String word = jsonObject.get("word").toString();
            String meaning = jsonObject.get("meaning").toString();
            int state = 0;

            if (command == "Search") {

                if (server.isWordExist(word)) {
                    meaning = server.searchDict(word);
                    state = 1;
                } else {
                    state = 0;
                }
                dos.writeUTF(writeJSON(state, meaning).toJSONString());
               dos.flush();

            } else if  (command == "Add") {

                if (!server.isWordExist(word)) {
                    server.addDict(word, meaning);
                    state = 1;
                } else {
                    state = 0;
                }
                dos.writeUTF(writeJSON(state, "").toJSONString());
                dos.flush();

            } else if (command == "Remove") {

                if (server.isWordExist(word)) {
                    server.removeDict(word);
                    state = 1;
                } else {
                    state = 0;
                }
                dos.writeUTF(writeJSON(state, "").toJSONString());
                dos.flush();
            }

            dis.close();
            dos.close();
            s.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JSONObject writeJSON(int state, String meaning) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("state", String.valueOf(state));
        jsonObject.put("meaning", meaning);
        return jsonObject;
    }

    private JSONObject parse(String res) {
        JSONObject jsonObject = null;
        try {
            JSONParser jsonParser = new JSONParser();
            jsonObject = (JSONObject) jsonParser.parse(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


}
