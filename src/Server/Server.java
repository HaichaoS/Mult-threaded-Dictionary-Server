package Server;

/**
 * Haichao Song
 * Description:
 */
public class Server {

    private String port, path;
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
        this.port = port;
        this.path = path;
    }

    public void create() {
        try {
            this.serverGUI = new ServerGUI(this);
            serverGUI.getFrame().setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPort() {return this.port;}
    public String getPath() {return this.path;}

}
