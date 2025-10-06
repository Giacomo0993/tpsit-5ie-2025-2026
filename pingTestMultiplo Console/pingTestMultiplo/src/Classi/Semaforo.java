package Classi;

/**
 * Classe che implementa un semplice meccanismo di sincronizzazione (semaforo)
 * Utilizzata per coordinare i thread di ping
 */
public class Semaforo {
	/**
	 * Costruttore della classe Semaforo
	 */
	public Semaforo() {
	}
	
	/**
	 * Metodo sincronizzato per bloccare un thread
	 * Il thread che chiama questo metodo si mette in attesa
	 * @throws InterruptedException se il thread viene interrotto mentre è in attesa
	 */
	public synchronized void blocca() throws InterruptedException {
		wait();
	}
	
	/**
	 * Metodo sincronizzato per risvegliare tutti i thread in attesa
	 * Sblocca tutti i thread che hanno chiamato il metodo blocca()
	 */
	public synchronized void avvia() {
		notifyAll();
	}	
}
