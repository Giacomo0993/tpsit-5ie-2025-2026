package Classi;

// Importazione delle librerie necessarie
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Classe principale che gestisce l'applicazione di test ping multiplo
 * Permette di testare la connettività verso più indirizzi IP simultaneamente
 */
public class PingMain {
	// Lista dei thread per gestire i ping in parallelo
	private static ArrayList<Thread> threads;
	// Lista degli oggetti Ping per mantenere riferimenti a tutti i ping attivi
	private static ArrayList<Ping> pings;
	// Semaforo per sincronizzare i thread di ping
	private static Semaforo semaforo;
	
	/**
	 * Metodo principale che avvia l'applicazione
	 * @param args argomenti da linea di comando (non utilizzati)
	 */
	public static void main(String[] args) {
		// Variabile per memorizzare l'input dell'utente
		String input;
		// Scanner per leggere l'input dell'utente dalla console
		Scanner sc = new Scanner(System.in);
		// Inizializzazione del semaforo per la sincronizzazione
		semaforo = new Semaforo();
		// Inizializzazione delle liste per thread e ping
		threads = new ArrayList<Thread>();
		pings = new ArrayList<Ping>();
		
		// Ciclo principale dell'applicazione
		do{		
			// Ferma tutti i ping attivi quando si aggiunge un nuovo indirizzo
			fermaTutti();
			// Richiesta all'utente di inserire un indirizzo IP
			System.out.println("Inserisci l'indirizzo IP da testare");
			input = sc.nextLine();
			// Creazione di un nuovo oggetto Ping con l'indirizzo inserito
			Ping ping = new Ping(input,semaforo);
			// Creazione di un nuovo thread per eseguire il ping
			Thread pingThread = new Thread(ping);
			System.out.println("Ping avviato, premi Enter per agiungere un nuovo IP, inserisci Q e premi Enter per uscire");
			// Avvio del thread di ping
			pingThread.start();
			// Riavvio di tutti i ping precedentemente fermati
			avviatutti();
			// Aggiunta del nuovo thread e ping alle rispettive liste
			threads.add(pingThread);
			pings.add(ping);
			// Lettura dell'input per continuare o uscire
			input = sc.nextLine();
		}while(!input.equals("Q")); // Continua finché l'utente non inserisce 'Q'
		
		// Terminazione di tutti i thread quando l'utente decide di uscire
		for(Thread t:threads) {
			// Interrompe il thread
			t.interrupt();
			try {
				// Attende che il thread termini
				t.join();
			} catch (InterruptedException e) {
				System.out.println("Thread interrotto in modo errato");
			}
		}
		// Chiusura dello scanner
		sc.close();
		System.out.println("Processo terminato");
	}
	
	/**
	 * Metodo per fermare temporaneamente tutti i ping attivi
	 * Imposta il flag 'ferma' su tutti gli oggetti Ping
	 */
	public static void fermaTutti() {
		for(Ping p:pings) {
			p.ferma();		
		}
	}
	
	/**
	 * Metodo per riavviare tutti i ping precedentemente fermati
	 * Utilizza il semaforo per sbloccare i thread in attesa
	 */
	public static void avviatutti() {
		semaforo.avvia();
	}
}
