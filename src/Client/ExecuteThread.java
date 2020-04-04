package Client;

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
public class ExecuteThread extends Thread  {

    private String address, command, word, meaning;
    private int port, state;

    public ExecuteThread(String address, int port, String command, String word, String meaning) {

        this.address = address;
        this.port = port;
        this.command = command;
        this.word = word;
        this.meaning = meaning;

    }

    @Override
    public void run()  {

        try {

            Socket socket = new Socket(address, port);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            JSONObject jsonWrite = new JSONObject();
            jsonWrite.put("command", command);
            jsonWrite.put("word", word);
            jsonWrite.put("meaning", meaning);

            dos.writeUTF(jsonWrite.toJSONString());
            dos.flush();

            JSONObject jsonRead;
            JSONParser parser = new JSONParser();
            jsonRead = (JSONObject) parser.parse(dis.readUTF());
            meaning = (String) jsonRead.get("meaning");

            dis.close();
            dos.close();
            socket.close();

        } catch (IOException e) {
            System.out.println("Error: I/O ERROR!");
        } catch (ParseException e) {
            System.out.println("Error: Parse ERROR!");
        }

    }

}
