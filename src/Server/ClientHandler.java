package Server;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 * Haichao Song
 * Description:
 */
public class ClientHandler extends Thread {

    private Socket s;
    private Server server;
    private Dict dict;

    // Constructor
    public ClientHandler(Socket s, Server server, Dict dict)
    {
        this.s = s;
        this.server =server;
        this.dict = dict;
    }

    @Override
    public void run()
    {

        try {

        	DataInputStream dis = new DataInputStream(s.getInputStream());;
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            JSONObject jsonObject = parse(dis.readUTF());
            String command = jsonObject.get("command").toString();
            String word = jsonObject.get("word").toString();
            String meaning = jsonObject.get("meaning").toString();
            String synonym = jsonObject.get("synonym").toString();
            int state = 0;
            server.printLog("Request\n  Command: " + command + "\n  Word: " + word);

            if (command.equals("Search")) {

            	if (dict.isWordExist(word)) {
            		meaning = dict.searchDict(word).get(0);
                    synonym = dict.searchDict(word).get(1);
            		state = 1;
            		server.printLog("Search Success");
            	} else {
            		state = 0;
            		server.printLog("Search Fail");
            	}
            	dos.writeUTF(writeJSON(state, meaning, synonym).toJSONString());
            	dos.flush();

            } else if  (command.equals("Add")) {

            	if (!dict.isWordExist(word)) {
                    dict.addDict(word, meaning, synonym);
            		state = 1;
            		server.printLog("Add Success");
            	} else {
            		state = 0;
            		server.printLog("Add Fail");
            	}
            	dos.writeUTF(writeJSON(state, "", "").toJSONString());
            	dos.flush();

            } else if (command.equals("Remove")) {

                if (dict.isWordExist(word)) {
                    dict.removeDict(word);
                    state = 1;
                    server.printLog("Remove Success");
                } else {
                    state = 0;
                    server.printLog("Remove Fail");
                }
                dos.writeUTF(writeJSON(state, "", "").toJSONString());
                dos.flush();
            }

            dis.close();
            dos.close();
            s.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JSONObject writeJSON(int state, String meaning, String synonym) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("state", String.valueOf(state));
        jsonObject.put("meaning", meaning);
        jsonObject.put("synonym", synonym);
        return jsonObject;
    }

    private JSONObject parse(String s) {
        JSONObject jsonObject = null;
        try {
            JSONParser jsonParser = new JSONParser();
            jsonObject = (JSONObject) jsonParser.parse(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


}
