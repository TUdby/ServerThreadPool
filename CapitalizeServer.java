import java.net.ServerSocket;

// End the server with Ctrl+c
public class CapitalizeServer {
        public static void main(String[] args) throws Exception {
        // Set up thread queue, threadpool, and manager
        ThreadManager manager = new ThreadManager();
        SocketQueue queue = manager.getQueue();
        manager.start();

        // Start server
        System.out.println("The capitalization server is running.");
        int clientNumber = 0;
        ServerSocket listener = new ServerSocket(9898);
        try {
            while (true) {
                queue.enqueue(listener.accept(), clientNumber++);
            }
        } finally {
            listener.close();
        }
    }

}
