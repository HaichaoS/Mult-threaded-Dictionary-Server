package Client;

/**
 * Haichao Song
 * Description:
 */
public class Client {

    private String address;
    private int port;
    private ClientGUI clientGUI;

    public static void main(String[] args) {
        try {
            Client client = new Client(args[0], Integer.parseInt(args[1]));
            System.out.println(args[0]);
            client.create();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Client(String address, int port) {
        this.address = address;
        this.port = port;
        clientGUI = null;
    }

    public void create() {
        try {
            this.clientGUI = new ClientGUI(this);
            clientGUI.getFrame().setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
