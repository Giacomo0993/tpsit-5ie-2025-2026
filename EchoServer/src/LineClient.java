import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;
// LineClient.java - Client che si connette al server e invia linee di testo
public class LineClient {
    private String ip;      // Indirizzo IP del server
    private int port;       // Porta del server

    // Costruttore che inizializza IP e porta
    public LineClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    // Metodo principale che crea un client e lo avvia
    public static void main(String[] args) {
        LineClient client = new LineClient("127.0.0.1", 1337);
        try {
            client.startClient();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void startClient() throws IOException {
        // Crea una connessione socket verso il server
        Socket socket = new Socket(ip, port);
        System.out.println("Connection established");

        // Inizializza gli stream di input/output
        Scanner socketIn = new Scanner(socket.getInputStream());    // Per leggere dal socket
        PrintWriter socketOut = new PrintWriter(socket.getOutputStream()); // Per scrivere sul socket
        Scanner stdin = new Scanner(System.in);    // Per leggere dall'input utente

        try {
            while (true) {
                String inputLine = stdin.nextLine();    // Legge input utente
                socketOut.println(inputLine);           // Invia al server
                socketOut.flush();                      // Forza l'invio
                String socketLine = socketIn.nextLine(); // Legge risposta dal server
                System.out.println(socketLine);         // Mostra risposta
            }
        } catch (NoSuchElementException e) {
            System.out.println("Connection closed");
        } finally {
            // Chiusura di tutti gli stream e socket
            stdin.close();
            socketIn.close();
            socketOut.close();
            socket.close();
        }
    }
}