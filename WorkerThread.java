import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class WorkerThread extends Thread {
    SocketQueue queue;
    
    public WorkerThread(SocketQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        Job job;
        Socket socket;
        int client_num = 0;
        boolean cont = true;
        while(cont) {
          try {
                job = queue.dequeue();
                socket = job.socket;
                client_num = job.client_num;

                // Get and Decorate Streams
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                // Send welcome message
                out.println("Hello, you are client #" + client_num + ".");

                // Get messages line by line, return capitalized
                while (true) {
                    String input = in.readLine();
                    // entering a period will quit
                    if (input == null || input.equals(".")) {
                        break;
                    }
                    out.println(input.toUpperCase());
                }

                in.close();
                out.close();
                socket.close();
           } catch (IOException e) {
               System.out.println("Error handling client #" + client_num + ": " + e);
               cont = false;
           } catch (InterruptedException e) {
               cont = false;
           }
        }
    }
}