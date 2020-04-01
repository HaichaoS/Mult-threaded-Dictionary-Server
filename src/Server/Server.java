package Server;

/**
 * Haichao Song
 * Description:
 */
public class Server {

    private String port, path;

    public static void main(String[] args) {
        try {

            Server server = new Server(args[0], args[1]);
            server.create();

        }
    }

    public Server(String port, String path) {
        this.port = port;
        this.path = path;
    }

    public void create() {
        
    }

}
