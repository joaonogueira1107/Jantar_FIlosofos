import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    private static final int PORT = 12345; // Porta onde o servidor escutará
    private final List<Garfo> garfos = new ArrayList<>(); // Lista de garfos disponíveis
    private final Map<Integer, PhilosopherData> filosofos = new ConcurrentHashMap<>(); // Dados dos filósofos conectados
    private int currentId = 1; // ID atual para registro de filósofos

    public Server(int numFilosofos) {
        // Inicializa os garfos com base no número de filósofos
        for (int i = 0; i < numFilosofos; i++) {
            garfos.add(new Garfo());
        }
    }

    public void iniciar() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor iniciado na porta " + PORT);

            // Loop infinito para aceitar conexões de clientes
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new ClientHandler(clientSocket)).start(); // Cria uma thread para cada cliente
            }
        }
    }

    private synchronized int registrarFilosofo() {
        // Registra um filósofo atribuindo um ID único
        int id = currentId++;
        filosofos.put(id, new PhilosopherData(id));
        return id;
    }

    private class ClientHandler implements Runnable {
        private final Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket; // Conexão do cliente
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                // Primeira mensagem esperada do cliente
                String message = in.readLine();
                if ("HELLO".equals(message)) {
                    int id = registrarFilosofo(); // Registra o filósofo
                    out.println("HI " + id); // Responde com o ID
                    System.out.println("Filósofo registrado com ID " + id);

                    // Processa mensagens subsequentes do cliente
                    while ((message = in.readLine()) != null) {
                        processarMensagem(message, id, out);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace(); // Log de erro de conexão
            }
        }

        private void processarMensagem(String message, int id, PrintWriter out) {
            switch (message) {
                case "THINKING":
                    // Incrementa o contador de pensamentos
                    filosofos.get(id).incrementarPensamentos();
                    out.println("OK");
                    break;

                case "REQUEST_FORKS":
                    // Tenta pegar os garfos e responde se foi possível
                    if (tentarPegarGarfos(id)) {
                        out.println("GRANTED");
                    } else {
                        out.println("DENIED");
                    }
                    break;

                case "RELEASE_FORKS":
                    // Libera os garfos
                    liberarGarfos(id);
                    out.println("OK");
                    break;

                default:
                    out.println("UNKNOWN_COMMAND"); // Comando desconhecido
            }
        }

        private boolean tentarPegarGarfos(int id) {
            int left = id - 1; // Garfo à esquerda
            int right = id % garfos.size(); // Garfo à direita

            // Sincronização para garantir que dois filósofos não peguem o mesmo garfo
            synchronized (garfos.get(left)) {
                synchronized (garfos.get(right)) {
                    if (!garfos.get(left).pegar() || !garfos.get(right).pegar()) {
                        liberarGarfos(id); // Libera os garfos se não conseguiu pegar ambos
                        return false;
                    }
                    return true; // Pegou os dois garfos com sucesso
                }
            }
        }

        private void liberarGarfos(int id) {
            // Libera os garfos esquerdo e direito
            int left = id - 1;
            int right = id % garfos.size();
            garfos.get(left).liberar();
            garfos.get(right).liberar();
        }
    }

    private static class PhilosopherData {
        private final int id;
        private int pensamentos; // Número de vezes que o filósofo pensou
        private int refeicoes; // Número de refeições feitas

        public PhilosopherData(int id) {
            this.id = id;
            this.pensamentos = 0;
            this.refeicoes = 0;
        }

        public void incrementarPensamentos() {
            pensamentos++; // Incrementa pensamentos
        }

        public void incrementarRefeicoes() {
            refeicoes++; // Incrementa refeições
        }
    }

    public static void main(String[] args) throws IOException {
        Server servidor = new Server(5); // Cria o servidor para 5 filósofos
        servidor.iniciar();
    }
}