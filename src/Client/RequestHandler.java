package Client;

import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

/**
 * Haichao Song
 * Description: the class that is responsible for making different requests according to users’ different operations
 * and get information from the responses get from server handler.
 */
public class RequestHandler {

    private String address;
    private int port;
    private ServerHandler serverHandler;
    private final int CONNECTION_FAIL = 2;
    private final int OPERATION_FAIL = 0;

    public RequestHandler(String address, int port) {
        this.address = address;
        this.port = port;
        this.serverHandler = null;
    }

    public String[] search(String word) {
        String[] request = execute("Search", word, "", "");
        return request;
    }

    public String[] add(String word, String meaning, String synonym) {
        String[] request = execute("Add", word, meaning, synonym);
        return request;
    }

    public String[] remove(String word) {
        String[] request = execute("Remove", word, "", "");
        return request;
    }

    public String[] execute(String command, String word, String meaning, String synonym) {

        int state = OPERATION_FAIL;
        printRequest(command, word, meaning, synonym);

        try {

            // create new server handler to send request
            serverHandler = new ServerHandler(address, port, command, word, meaning, synonym);
            serverHandler.start();
            serverHandler.join(5000);
            if (serverHandler.isAlive()) {
                serverHandler.interrupt();
                throw new TimeoutException();
            }

            // get and read response from server handler
            ArrayList<String> request = serverHandler.getRequest();
            state = Integer.parseInt(request.get(0));
            meaning = request.get(1);
            synonym = request.get(2);

            System.out.println("Connect Success");
            System.out.println("State: " + request.get(0));

        } catch (TimeoutException e) {
            state = CONNECTION_FAIL;
            meaning = "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] res = {String.valueOf(state), meaning, synonym};
        return res;

    }

    /* show information for client request */
    public void printRequest(String command, String word, String meaning, String synonym) {
        System.out.println("Command: " + command);
        System.out.println("Word: " + word);
        System.out.println("Meaning: " + meaning);
        System.out.println("Synonym:" + synonym);
    }

}
