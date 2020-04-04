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
//        String received;
//        String toreturn;
        while (true)
        {
            try {
                    dis = new DataInputStream(s.getInputStream());
                    dos = new DataOutputStream(s.getOutputStream());
                    JSONParser jsonParser = new JSONParser();
                    JSONObject jsonObject = (JSONObject) jsonParser.parse(dis.readUTF());

                    String command =  (String) jsonObject.get("command");
                    String word = (String) jsonObject.get("word");
                    String meaning =  (String) jsonObject.get("meaning");

                    if (command == "Search") {

                        if (server.searchDict(word) == null) {
                            server.printLog("Fail, the word does not in the dictionary");
                        } else {
                            server.printLog( word + ": " + server.searchDict(word));
                        }

                        jsonObject.put("meaning", meaning);
                        dos.writeUTF(jsonObject.toJSONString());
                        dos.flush();

                    } else if  (command == "Add") {

                        if (server.addDict(word, meaning) == true) {
                            server.printLog("Word add success");
                        } else {
                            server.printLog("Fail, the word already in the dictionary");
                        }

                        jsonObject.put("meaning", "");
                        dos.writeUTF(jsonObject.toJSONString());
                        dos.flush();

                    } else if (command == "Remove") {

                        if (server.removeDict(word) == true) {
                            server.printLog("Word remove success");
                        } else {
                            server.printLog("Fail, the word does not in the dictionary");
                        }

                        jsonObject.put("meaning", "");
                        dos.writeUTF(jsonObject.toJSONString());
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

}
