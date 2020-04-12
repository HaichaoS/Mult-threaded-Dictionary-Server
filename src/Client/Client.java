package Client;

/**
 * Haichao Song
 * Description:
 */
public class Client {

    private ClientGUI clientGUI;
    private RequestHandler requestHandler;

    public static void main(String[] args) {
        try {

            if (Integer.parseInt(args[1]) <= 1024 || Integer.parseInt(args[1]) >= 49151) {
                System.out.println("Invalid Port Number");
                System.exit(-1);
            } else {
                Client client = new Client(args[0], Integer.parseInt(args[1]));
                System.out.println(args[0]);
                client.create();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Client(String address, int port) {
        this.clientGUI = null;
        this.requestHandler = new RequestHandler(address, port);

    }

    public void create() {
        try {
            this.clientGUI = new ClientGUI(requestHandler);
            clientGUI.getFrame().setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
