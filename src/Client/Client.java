package Client;

/**
 * Haichao Song
 * Description:
 */
public class Client {

    private String address;
    private int port;

    public void main(String[] args) {
        Client client = new Client(args[0], Integer.parseInt(args[1]));
        client.start();
    }

    public Client(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public void start() {

    }

}
