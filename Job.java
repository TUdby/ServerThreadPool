import java.net.Socket;

public class Job {
    Socket socket;
    int client_num;

    public Job(Socket socket, int client_num) {
        this.socket = socket;
        this.client_num = client_num;
    }
}