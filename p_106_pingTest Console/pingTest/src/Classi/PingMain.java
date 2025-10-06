package Classi;

// Importazione della classe Scanner per l'input da tastiera
import java.util.Scanner;

/**
 * Classe principale che gestisce l'interfaccia utente per il test di ping
 * Permette all'utente di inserire indirizzi IP da testare e di interrompere il test
 */
public class PingMain {
	/**
	 * Metodo main che avvia l'applicazione
	 * @param args argomenti da linea di comando (non utilizzati)
	 */
	public static void main(String[] args) {
		// Variabile per memorizzare l'input dell'utente
		String input;
		// Creazione di un oggetto Scanner per leggere l'input dell'utente
		Scanner sc = new Scanner(System.in);
		// Ciclo principale del programma
		do{
			// Richiesta all'utente di inserire un indirizzo IP
			System.out.println("Inserisci l'indirizzo IP da testare");
			// Lettura dell'indirizzo IP inserito
			input = sc.nextLine();
			// Creazione di un nuovo thread per eseguire il ping in background
			Thread pingThread = new Thread(new Ping(input));
			// Istruzioni per l'utente
			System.out.println("Ping avviato, premi Enter per cambiare IP, inserisci Q e premi Enter per uscire");
			// Avvio del thread di ping
			pingThread.start();
			// Attesa dell'input dell'utente per continuare o terminare
			input = sc.nextLine();
			// Interruzione del thread di ping quando l'utente inserisce un nuovo input
			pingThread.interrupt();
			try {
				// Attesa che il thread di ping termini completamente
				pingThread.join();
			} catch (InterruptedException e) {
				// Gestione dell'eccezione in caso di interruzione impropria
				System.out.println("Thread interrotto in modo errato");
			}
		// Continua il ciclo finché l'utente non inserisce 'Q'
		}while(!input.equals("Q")); 
		// Chiusura dello scanner per evitare memory leak
		sc.close();
		// Messaggio di conferma della terminazione del programma
		System.out.println("Processo terminato");
	}
}
