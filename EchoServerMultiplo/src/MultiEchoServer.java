import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList; // Importiamo ArrayList
import java.util.List;      // E la sua interfaccia

public class MultiEchoServer { // Ho rinominato la classe per chiarezza
    private int port;

    public MultiEchoServer(int port) {
        this.port = port;
    }

    public void startServer() {
        // La nostra lista per tenere traccia di tutti i thread "operai"
        ArrayList<Thread> clientThreads = new ArrayList<>();
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server (manuale) pronto sulla porta: " + port);

            while (true) { // Il server non si ferma mai (in questo esempio semplice)
                System.out.println("In attesa di una nuova connessione...");
                Socket clientSocket = serverSocket.accept(); // Bloccante: aspetta un client
                System.out.println("Client connesso: " + clientSocket.getInetAddress());

                // Per ogni client, facciamo tutto a mano:
                // 1. Creiamo il gestore del client (il "compito" da eseguire)
                EchoServerClientHandler handler = new EchoServerClientHandler(clientSocket);

                // 2. Creiamo un nuovo "operaio" (Thread) e gli assegniamo il compito
                Thread clientThread = new Thread(handler);

                // 3. Aggiungiamo il nuovo operaio alla nostra lista per non perderlo
                clientThreads.add(clientThread);

                // 4. Diciamo all'operaio di iniziare a lavorare!
                clientThread.start();
            }
        } catch (IOException e) {
            System.err.println("Errore del server: " + e.getMessage());
        } finally {
            // Questo blocco verrebbe eseguito se uscissimo dal while (es. con un'eccezione)
            // Per chiudere tutto correttamente, dovremmo iterare sulla lista e "joinare" i thread.
            // È un processo complesso che ExecutorService.shutdown() fa per noi!
            try {
                if (serverSocket != null && !serverSocket.isClosed()) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        MultiEchoServer server = new MultiEchoServer(1337);
        server.startServer();
    }
}