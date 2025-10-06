import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class LineClient {
    private String ip;
    private int port;

    public LineClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public static void main(String[] args) {
        // Puoi cambiare IP e porta qui se necessario, ma 127.0.0.1 e 1337 sono corretti per il server
        LineClient client = new LineClient("127.0.0.1", 1337);
        try {
            client.startClient();
        } catch (IOException e) {
            System.err.println("Errore di connessione: " + e.getMessage());
            System.err.println("Assicurati che il server sia in esecuzione.");
        }
    }

    public void startClient() throws IOException {
        Socket socket = new Socket(ip, port);
        System.out.println("Connessione stabilita con il server.");
        Scanner socketIn = new Scanner(socket.getInputStream());
        PrintWriter socketOut = new PrintWriter(socket.getOutputStream());
        Scanner stdin = new Scanner(System.in);

        System.out.println("Inserisci un messaggio da inviare al server (scrivi 'quit' per chiudere il server, CTRL+D o CTRL+C per chiudere solo questo client).");

        try {
            while (true) {
                // Legge una riga dall'input della console
                String inputLine = stdin.nextLine();

                // Invia la riga al server
                socketOut.println(inputLine);
                socketOut.flush();

                // Se l'utente scrive "quit", il server chiuderà la connessione
                // e la riga successiva (socketIn.nextLine()) lancerà un'eccezione.
                if (inputLine.equals("quit")) {
                    System.out.println("Richiesta di chiusura inviata al server.");
                    break;
                }

                // Legge la risposta del server e la stampa
                String socketLine = socketIn.nextLine();
                System.out.println("Server: " + socketLine);
            }
        } catch (NoSuchElementException e) {
            // Questa eccezione viene lanciata quando lo stream di input si chiude
            // (es. il server chiude la connessione o si preme CTRL+D)
            System.out.println("Connessione chiusa.");
        } finally {
            // Chiude tutte le risorse
            stdin.close();
            socketIn.close();
            socketOut.close();
            socket.close();
        }
    }
}