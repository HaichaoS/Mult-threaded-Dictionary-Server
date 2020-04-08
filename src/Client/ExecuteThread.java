package Client;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * Haichao Song
 * Description:
 */
public class ExecuteThread extends Thread  {

    private String address, command, word, meaning;
    private int port, state;
    private String[] request = {"",""};
    private Socket socket;

    public ExecuteThread(String address, int port, String command, String word, String meaning) {

        this.address = address;
        this.port = port;
        this.command = command;
        this.word = word;
        this.meaning = meaning;
        this.state = 0;
        this.socket = null;
    }

    @Override
    public void run()  {

        try {

            System.out.println("Check I/O Error:");
            System.out.println(command);
            System.out.println(word);
            System.out.println(meaning);

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
            }
            dis.close();
            dos.close();

        }  catch (SocketTimeoutException e) {
            state = 2;
            System.out.println("Timeout!");
        } catch (UnknownHostException e) {
            state = 2;
            System.out.println("Unknown Host!");
        } catch (IOException e) {
            state = 2;
            System.out.println("I/O Error!");
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        request[0] = String.valueOf(state);
        request[1] = meaning;
    }

    public String[] getRequest() {
        return request;
    }

    private JSONObject writeJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("command", command);
        jsonObject.put("word", word);
        jsonObject.put("meaning", meaning);
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
