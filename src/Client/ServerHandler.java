package Client;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

/**
 * Haichao Song
 * Description:
 */
public class ServerHandler extends Thread  {

    private String address, command, word, meaning, synonym;
    private int port, state;
    private String[] request = {"","",""};
    private Socket socket;

    public ServerHandler(String address, int port, String command, String word, String meaning, String synonym) {

        this.address = address;
        this.port = port;
        this.command = command;
        this.word = word;
        this.meaning = meaning;
        this.synonym = synonym;
        this.state = 0;
        this.socket = null;
    }

    @Override
    public void run()  {

        try {

            socket = new Socket(address, port);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            dos.writeUTF(writeJSON().toJSONString());
            dos.flush();
            String result = dis.readUTF();
            JSONObject jsonObject = parse(result);
            state = Integer.parseInt(jsonObject.get("state").toString());
            if (state == 1) {
                meaning = (String) jsonObject.get("meaning");
                synonym = (String) jsonObject.get("synonym");
            }
            dis.close();
            dos.close();

        } catch (UnknownHostException e) {
            state = 2;
            e.printStackTrace();
        } catch (IOException e) {
            state = 2;
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        request[0] = String.valueOf(state);
        request[1] = meaning;
        request[2] = synonym;
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] getRequest() {
        return request;
    }

    private JSONObject writeJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("command", command);
        jsonObject.put("word", word);
        jsonObject.put("meaning", meaning);
        jsonObject.put("synonym", synonym);
        return jsonObject;
    }

    private JSONObject parse(String s) {
        JSONObject jsonObject = null;
        try {
            JSONParser parser = new JSONParser();
            jsonObject = (JSONObject) parser.parse(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

}
