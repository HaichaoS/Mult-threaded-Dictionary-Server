package Client;

import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

/**
 * Team: W9-5
 * Description:
 */
public class RequestHandler {

    private String address;
    private int port;
    private ServerHandler serverHandler;

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

        int state = 0;
        printLog(command, word, meaning, synonym);

        try {

            serverHandler = new ServerHandler(address, port, command, word, meaning, synonym);
            serverHandler.start();
            serverHandler.join(5000);
            if (serverHandler.isAlive()) {
                serverHandler.interrupt();
                throw new TimeoutException();
            }
            ArrayList<String> request = serverHandler.getRequest();
            state = Integer.parseInt(request.get(0));
            meaning = request.get(1);
            synonym = request.get(2);

            System.out.println("Connect Success");
            System.out.println("State: " + request.get(0));

        } catch (TimeoutException e) {
            state = 2;
            meaning = "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        printResponse(state, meaning);
        String[] res = {String.valueOf(state), meaning, synonym};
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

    public void printLog(String command, String word, String meaning, String synonym) {
        System.out.println("Command: " + command);
        System.out.println("Word: " + word);
        System.out.println("Meaning: " + meaning);
        System.out.println("Synonym:" + synonym);
    }

}
