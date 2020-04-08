package Client;

import java.util.concurrent.TimeoutException;

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

    public String[] search(String word) {
        String[] request = execute("Search", word, "");
        return request;
    }

    public String[] add(String word, String meaning) {
        String[] request = execute("Add", word, meaning);
        return request;
    }

    public String[] remove(String word) {
        String[] request = execute("Remove", word, "");
        return request;
    }

    public String[] execute(String command, String word, String meaning) {

        int state = 0;
        printLog(command, word, meaning);

        try {

            ExecuteThread eThread = new ExecuteThread(address, port, command, word, meaning);
            eThread.start();
            eThread.join(2000);
            if (eThread.isAlive()) {
                eThread.interrupt();
                throw new TimeoutException();
            }
            String[] eThreadRequest = eThread.getRequest();
            state = Integer.parseInt(eThreadRequest[0]);
            meaning = eThreadRequest[1];

            System.out.println("Connect Success");
            System.out.println("State: " + eThreadRequest[0]);

        } catch (TimeoutException e) {
            state = 2;
            meaning = "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        printResponse(state, meaning);
        String[] res = {String.valueOf(state), meaning};
        return res;

    }

    public void printResponse(int state, String meaning) {
        System.out.println("  Response:");
        if (state == 0) {
            System.out.println("  State: FAIL");
        } else if (state == 1) {
            System.out.println("  State: SUCCESS");
        } else {
            System.out.println("  Error: Unknown State");
        }
        System.out.println("  Meaning: " + meaning);
    }

    public void printLog(String command, String word, String meaning) {
        System.out.println("Command: " + command);
        System.out.println("Word: " + word);
        System.out.println("Meaning: " + meaning);
    }

}
