package Client;

/**
 * Haichao Song
 * Description:
 */
public class Client {

    private String address;
    private int port;
    private ClientGUI clientGUI;

    public void main(String[] args) {
        Client client = new Client(args[0], Integer.parseInt(args[1]));
        client.create();
    }

    public Client(String address, int port) {
        this.address = address;
        this.port = port;
        clientGUI = null;
    }

    public void create() {
        this.clientGUI = new ClientGUI(this);
        clientGUI.getFrame().setVisible(true);
    }

}
