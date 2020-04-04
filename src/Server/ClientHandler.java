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
    private String path;
    private DataInputStream dis;
    private DataOutputStream dos;


    // Constructor
    public ClientHandler(Socket s, Server server, String path)
    {
        this.s = s;
        this.server = server;
        this.path = path;
    }

    @Override
    public void run()
    {
        String received;
        String toreturn;
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

                    } else if  (command == "Add")  {

                    } else {

                    }

                    s.close();


            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }

}
