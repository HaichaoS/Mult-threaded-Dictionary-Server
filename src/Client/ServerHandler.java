package Client;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Haichao Song
 * Description: the class that is responsible for
 * 1) send the request, side which contains information about the command and the word in JSON format, to server side.
 * 2) Receive the responses which contains the operation state and word information in JSON form,
 * from the server side and read them
 * 3) handle all kinds of failures.
 */
public class ServerHandler extends Thread  {

    private String address, command, word, meaning, synonym;
    private int port, state;
    private ArrayList<String> response;
    private Socket socket;
    private final int SUCCESS = 1;
    private final int CONNECTION_FAIL = 2;

    public ServerHandler(String address, int port, String command, String word, String meaning, String synonym) {

        this.address = address;
        this.port = port;
        this.command = command;
        this.word = word;
        this.meaning = meaning;
        this.synonym = synonym;
        this.state = 0;
        this.socket = null;
        this.response = new ArrayList<>();
    }

    @Override
    public void run()  {

        try {

            // send the request and get the response from server
            socket = new Socket(address, port);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(writeJSON().toJSONString());
            dos.flush();
            String result = dis.readUTF();
            JSONObject jsonObject = parse(result);
            state = Integer.parseInt(jsonObject.get("state").toString());
            if (state == SUCCESS) {
                meaning = (String) jsonObject.get("meaning");
                synonym = (String) jsonObject.get("synonym");
            }
            dis.close();
            dos.close();

        } catch (UnknownHostException e) {
            state = CONNECTION_FAIL;
            e.printStackTrace();
        } catch (IOException e) {
            state = CONNECTION_FAIL;
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.add(String.valueOf(state));
        response.add(meaning);
        response.add(synonym);
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getResponse() {
        return response;
    }

    /* write JSON object for request */
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
