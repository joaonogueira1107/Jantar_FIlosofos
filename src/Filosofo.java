import java.io.*;
import java.net.*;
import java.util.Random;

public class Filosofo {
    private static final String HOST = "localhost"; // Host do servidor
    private static final int PORT = 12345; // Porta do servidor

    private final Random random = new Random();

    public void iniciar() throws IOException, InterruptedException {
        try (Socket socket = new Socket(HOST, PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            out.println("HELLO"); // Inicia conexão com o servidor
            String response = in.readLine();
            System.out.println("Servidor: " + response);

            int id = Integer.parseInt(response.split(" ")[1]); // ID do filósofo

            while (true) {
                pensar();
                out.println("THINKING");
                in.readLine();

                out.println("REQUEST_FORKS");
                if ("GRANTED".equals(in.readLine())) {
                    comer();
                    out.println("RELEASE_FORKS");
                    in.readLine();
                }
            }
        }
    }

    private void pensar() throws InterruptedException {
        // Simula o tempo de pensamento
        int tempo = Math.max(0, (int) (random.nextGaussian() * 2 + 5));
        System.out.println("Pensando por " + tempo + "ms");
        Thread.sleep(tempo);
    }

    private void comer() throws InterruptedException {
        // Simula o tempo de comer
        System.out.println("Comendo...");
        Thread.sleep(3000);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        new Filosofo().iniciar(); // Inicia o filósofo
    }
}