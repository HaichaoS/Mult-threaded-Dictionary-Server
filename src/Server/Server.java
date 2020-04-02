package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Haichao Song
 * Description:
 */
public class Server {

    private int port;
    private String path;
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
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                System.out.println("Assigning new thread for this client");

                // create a new thread object
                Thread t = new ClientHandler(s, dis, dos);

                // Invoking the start() method
                t.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getPort() {return this.port;}
    public String getPath() {return this.path;}

}
