package Server;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 * Haichao Song
 * Description:
 */
public class Server {

    private int port;
    private String path;
    private HashMap<String, String> dict;
    private ServerGUI serverGUI;

    public static void main(String[] args) {
        try {
            Server server = new Server(args[0], args[1]);
            server.create();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Server(String port, String path) {
        this.port = Integer.parseInt(port);
        this.path = path;
        this.dict = readDic(path);
    }

    public void create() {

        try {

            ServerSocket ss = new ServerSocket(this.port);
            this.serverGUI = new ServerGUI(this);
            serverGUI.getFrame().setVisible(true);

            while(true) {

                // socket object to receive incoming client requests
                Socket s = ss.accept();
                System.out.println("A new client is connected : " + s);

                // obtaining input and out streams
//                DataInputStream dis = new DataInputStream(s.getInputStream());
//                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                System.out.println("Assigning new thread for this client");

                // create a new thread object
                Thread t = new ClientHandler(s,this, dict);

                // Invoking the start() method
                t.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, String> readDic(String path) {
        HashMap<String, String> dict = new HashMap<>();
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
            dict = (HashMap<String, String>) ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  dict;
    }

    public String searchDict(String word) {
        return dict.get(word);
    }

    public boolean addDict(String word, String meaning) {
        if (dict.containsKey(word)) {
            return false;
        } else {
            dict.put(word, meaning);
            try {
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
                oos.writeObject(dict);
                oos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    public boolean removeDict(String word) {
        if (dict.containsKey(word)) {
            dict.remove(word);
            try {
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
                oos.writeObject(dict);
                oos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        } else {
            return false;
        }
    }

    public int getPort() {return this.port;}
    public String getPath() {return this.path;}

}