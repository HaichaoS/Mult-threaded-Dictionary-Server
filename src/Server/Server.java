package Server;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Haichao Song
 * Description:
 */
public class Server {

    private int port;
    private String path;
    private Dict dict;
    private ServerGUI serverGUI;
    private ServerSocket serverSocket;

    public static void main(String[] args) {

        if (Integer.parseInt(args[0]) <= 1024 || Integer.parseInt(args[0]) >= 49151) {
            System.out.println("Invalid Port Number");
            System.exit(-1);
        } else {
            try {
                Server server = new Server(args[0], args[1]);
                server.create();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Server(String port, String path) {
        this.port = Integer.parseInt(port);
        this.path = path;
        this.dict = new Dict(path);
    }

    public void create() {

        try {

            serverSocket = new ServerSocket(this.port);
            this.serverGUI = new ServerGUI(this);
            serverGUI.getFrame().setVisible(true);

            while(true) {

                // socket object to receive incoming client requests
                Socket s = serverSocket.accept();
                printLog("A new client is connected : " + s);
                printLog("Assigning new thread for this client");

                // create a new thread object
                ClientHandler t = new ClientHandler(s, this, dict);

                // Invoking the start() method
                t.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void printLog(String s) {
        System.out.println(s);
        if (serverGUI != null) serverGUI.getTextArea().append(s + '\n');
    }

    public int getPort() {return this.port;}
    public String getPath() {return this.path;}

}