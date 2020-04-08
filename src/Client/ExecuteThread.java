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

            socket = new Socket(address, port);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            JSONObject jsonWrite = new JSONObject();
            jsonWrite.put("command", command);
            jsonWrite.put("word", word);
            jsonWrite.put("meaning", meaning);

            dos.writeUTF(jsonWrite.toJSONString());
            dos.flush();

            String result = dis.readUTF();
            JSONObject jsonRead = null;

            try {
                JSONParser parser = new JSONParser();
                jsonRead = (JSONObject) parser.parse(result);
            } catch (Exception e) {
                e.printStackTrace();
            }

            state = Integer.parseInt(jsonRead.get("state").toString());

            if (state == 1) {
                meaning = (String) jsonRead.get("meaning");
            }

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

}
