import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class ParallelClient {
	public static void main(String[] args) throws Exception {
		int amount = 500;
		Client[] clients = new Client[amount];

		for (int i = 0; i < amount; i++)
			(clients[i] = new Client()).start();

		for (int i = 0; i < amount; i++)
			clients[i].join();

		System.out.println("Parallel Tests Finished");
	}

	public static class Client extends Thread {
		private BufferedReader in;
		private PrintWriter out;
		Socket socket;
		Random random;
		String[] messages = { "capitalize me", "hope this works", "message", "poetry", "asdf",
							"buy", "sell", "thank you" };

		@Override
		public void run() {
			random = new Random();
			String response;
			int send_amount = random.nextInt(4);

			try {
				// Connect to the server
				socket = new Socket("localhost", 9898);
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream(), true);

				// Get and print the welcome message
				String welcome = in.readLine();
				System.out.print(welcome + "\n");

				for (int x = 0; x < send_amount; x++) {
					// send a message to be capitalized
					out.println(messages[random.nextInt(messages.length)]);

					// Read response
					try {
						response = in.readLine();
					} catch (IOException ex) {
						response = "Error: " + ex;
						System.out.println("" + response + "\n");
					}
					
					// If server side fails by sending null or empty string
					// print that this client attempt will terminate, though
					// this client will still try again for the remainder of 
					// the send amount
					if (response == null || response.equals("")) {
						System.out.println("client to terminate.");
					}

					// print response
					System.out.println(response);
				}
				// this will tell server side to close the connection
				out.println(".");

			} catch (IOException e) {}
		}
	}
}
