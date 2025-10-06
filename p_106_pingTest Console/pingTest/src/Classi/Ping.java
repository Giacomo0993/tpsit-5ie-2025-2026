package Classi;

// Importazione delle classi necessarie per la gestione della rete e del tempo
import java.io.IOException;
import java.net.InetAddress;
import java.util.GregorianCalendar;

/**
 * Classe che implementa l'interfaccia Runnable per eseguire ping in un thread separato
 * Verifica la raggiungibilità di un indirizzo IP e misura il tempo di risposta
 */
public class Ping implements Runnable{
    // Indirizzo IP o nome host da testare
    private String indirizzo;
    // Intervallo di tempo (in millisecondi) tra un ping e l'altro
    private final int delay=2000; 
    
    /**
     * Costruttore che inizializza l'indirizzo da testare
     * @param indirizzo l'indirizzo IP o nome host da testare
     */
    public Ping(String indirizzo){
        this.indirizzo=indirizzo;
    }  
    
    /**
     * Metodo eseguito dal thread che effettua il ping continuo
     * fino a quando non viene interrotto
     */
    @Override
    public void run() { 
            try {
				// Continua finché il thread non viene interrotto
				while(!Thread.interrupted()){
				    // Risolve l'indirizzo IP o nome host
				    InetAddress address = InetAddress.getByName(indirizzo);
				    // Variabili per misurare il tempo di risposta
				    long finish = 0;
				    // Registra il tempo di inizio
				    long start = new GregorianCalendar().getTimeInMillis();
				    
					// Verifica se l'indirizzo è raggiungibile entro 5 secondi
					if (address.isReachable(5000)){
						// Registra il tempo di fine
						finish = new GregorianCalendar().getTimeInMillis();
					  	// Mostra il risultato del ping con il tempo di risposta
					  	System.out.println("Ping:" + address.getHostName() + " Time:" + (finish-start) + "ms");
					} else {
						// Mostra un messaggio se l'indirizzo non è raggiungibile
						System.out.println("Richiesta scaduta, indirizzo " + address.getHostName() + " non raggiungibile");
					}
					// Attende il tempo specificato prima del prossimo ping
					Thread.sleep(delay);
				}
			} catch (InterruptedException e) {
				// Gestione dell'interruzione del thread
				System.out.println("Ping interrotto");
			} catch (IOException e) {
				// Gestione degli errori di rete
				System.out.println("Errore di rete, controlla l'indirizzo inserito");
			}
    }
     
}



