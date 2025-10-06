package Classi;

// Importazione delle librerie necessarie
import java.io.IOException;
import java.net.InetAddress;
import java.util.GregorianCalendar;

/**
 * Classe che implementa la funzionalità di ping verso un indirizzo specifico
 * Implementa l'interfaccia Runnable per essere eseguita in un thread separato
 */
public class Ping implements Runnable{
    // Indirizzo IP o nome host da pingare
    private String indirizzo;
    // Intervallo di tempo (in millisecondi) tra un ping e l'altro
    private final int delay=5000;
    // Riferimento al semaforo per la sincronizzazione
    Semaforo semaforo;
    // Flag per indicare se il ping deve essere temporaneamente fermato
    boolean ferma=false; 
    
    /**
     * Costruttore della classe Ping
     * @param indirizzo l'indirizzo IP o nome host da pingare
     * @param semaforo il semaforo per la sincronizzazione
     */
    public Ping(String indirizzo, Semaforo semaforo){
    	this.semaforo = semaforo;
        this.indirizzo=indirizzo;
    }
    
    /**
     * Metodo eseguito dal thread
     * Esegue ping continui verso l'indirizzo specificato fino all'interruzione
     */
    public void run() { 
            try {
				// Continua finché il thread non viene interrotto
				while(!Thread.interrupted()){
				    // Risolve l'indirizzo IP
				    InetAddress address = InetAddress.getByName(indirizzo);			    
				    long finish = 0;
				    // Registra il tempo di inizio
				    long start = new GregorianCalendar().getTimeInMillis();			   
					// Verifica se l'indirizzo è raggiungibile entro 5 secondi
					if (address.isReachable(5000)){
						// Registra il tempo di fine
						finish = new GregorianCalendar().getTimeInMillis();				  
					  	System.out.println("Ping:" + address.getHostName() + " Time:" + (finish-start) + "ms");
					} else {
						System.out.println("Richiesta scaduta, indirizzo " + address.getHostName() + " non raggiungibile");
					}
					// Attende prima del prossimo ping
					Thread.sleep(delay);
					// Se il flag ferma è attivo, blocca il thread fino a nuovo avviso
					if(ferma) {semaforo.blocca();}
					// Resetta il flag ferma
					ferma=false;
				}
			} catch (InterruptedException e) {
				// Gestisce l'interruzione del thread
				System.out.println("Ping interrotto su: " + indirizzo);
            } catch (IOException e) {
                // Gestisce errori di rete o indirizzi non validi
                System.out.println("Errore di rete, controlla l'indirizzo inserito: " + indirizzo);
            }
    }
    
    /**
     * Metodo per impostare il flag di fermata
     * Quando chiamato, il thread si fermerà al prossimo ciclo
     */
    public void ferma() {ferma=true;}   
}
