import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
// EchoServer.java - Server che riceve connessioni e fa eco dei messaggi
public class EchoServer {
    private int port;               // Porta su cui il server ascolta
    private ServerSocket serverSocket;

    public EchoServer(int port) {
        this.port = port;
    }

    // Metodo principale che crea e avvia il server
    public static void main(String[] args) {
        EchoServer server = new EchoServer(1337);
        try {
            server.startServer();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void startServer() throws IOException {
        // Crea un ServerSocket sulla porta specificata
        serverSocket = new ServerSocket(port);
        System.out.println("Server socket ready on port: " + port);

        // Attende una connessione da un client
        Socket socket = serverSocket.accept();
        System.out.println("Received client connection");

        // Inizializza gli stream di input/output per la comunicazione
        Scanner in = new Scanner(socket.getInputStream());
        PrintWriter out = new PrintWriter(socket.getOutputStream());

        // Loop principale del server
        while (true) {
            String line = in.nextLine();     // Legge messaggio dal client
            if (line.equals("quit")) {       // Se riceve "quit", termina
                break;
            } else {
                // Invia echo del messaggio ricevuto
                out.println("Received: " + line);
                out.flush();                 // Forza l'invio
            }
        }

        // Chiusura di tutti gli stream e socket
        System.out.println("Closing sockets");
        in.close();
        out.close();
        socket.close();
        serverSocket.close();
    }
}