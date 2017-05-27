/*
 * Con questo programma voglio illustrare i seguenti concetti:
 * 1. MAIN e' un thread come gli altri e quindi puo' terminare prima che gli altri
 * 2. THREADs vengono eseguiti allo stesso tempo
 * 3. THREADs possono essere interrotti e hanno la possibilita' di interrompersi in modo pulito
 * 4. THREADs possono essere definiti mediante una CLASSE che implementa un INTERFACCIA Runnable
 * 5. THREADs possono essere avviati in modo indipendente da quando sono stati definiti
 * 6. posso passare parametri al THREADs tramite il costruttore della classe Runnable
 */

//questa variante del primo esercizio sostituisce le variabili static con i monitor che permettono di evitare i conflitti
package multithread;

import java.util.concurrent.TimeUnit;
/**
 *
 * @author George Bostan
 */
public class MultiThread {
    /**
     * @param args the command line arguments
     */
    //ESERCIZIO 1
    // "main" e' il THREAD principale da cui vengono creati e avviati tutti gli altri THREADs
    // i vari THREADs poi evolvono indipendentemente dal "main" che puo' eventualmente terminare prima degli altri
    // nel main vengono creati e utilizzati 3 threads
    // essi partono contemporaneamente e dopo un tempo random stabilito nel run() essi scrivono su display il conto alla rovescia che parte da 10
    // essendo random i threads scrivono i numeri su schermo in ordine casuale 
    //(nel senso che qualunque dei 3 threads potrebbe scrivere prima dell'altro e subito dopo potrebbe accadere il contrario)
    // viene infine mostrato il punteggio che rappresenta le volte dove il thread TOE capita prima di TAC
    //ESERCIZIO 2
    //questa volta viene implementato il monitor che permette di evitare i conflitti
    //il monitor è come un semaforo che attraverso la parola syncronized permette la gestione di una sola risorsa condivisa tra diversi THREADs
    //la risorsa viene bloccata evitando di far accedere altri THREADs
    public static void main(String[] args) {
        System.out.println("Main Thread iniziata...");  //output su video che indica l'avvio del programma
        Schermi x = new Schermi();  //creazione del monitor che controlla l'accesso alla risorsa condivisa
        long start = System.currentTimeMillis();    //indica il tempo di inizio del programma
        Thread tic = new Thread (new TicTacToe("TIC", x)); //permette la creazione primo THREAD TIC
        
        Thread tac = new Thread(new TicTacToe("TAC", x));  //permette la creazione secondo THREAD TAC
        
        Thread toe = new Thread(new TicTacToe("TOE", x));  //permette la creazione terzo THREAD TOE
        
        tic.start();    // avvia il THREAD TIC
        tac.start();    // avvia il THREAD TAC
        toe.start();    // avvia il THREAD Toe
        //tutti i THREADS partono contemporaneamente
        try {
            toe.join(); //mette in attesa il programma principale finche il THREAD TOE non finisce l'esecuzione
        } 
        catch (InterruptedException e) {
        }
        long end = System.currentTimeMillis();  //indica il tempo alla conclusione del programma
        System.out.println("Il punteggio e: " +x.punteggio());  //mostra su schermo il punteggio finale calcolato nel monitor
        System.out.println("Main Thread completata! tempo di esecuzione: " + (end - start) + "ms");
        //calcola il tempo impiegato dal programma per eseguire tutto il codice e lo stampa su schermo
    }
    
}

class TicTacToe implements Runnable {
    private String t;
    private String msg;
    Schermi x;

    // Costruttore, possiamo usarlo per passare dei parametri al THREAD
    public TicTacToe (String s, Schermi x) {
        this.t = s;
        this.x = x;
    }
    
    @Override // Annotazione per il compilatore
    // se facessimo un overloading invece di un override il copilatore ci segnalerebbe l'errore
    // per approfondimenti http://lancill.blogspot.it/2012/11/annotations-override.html
    public void run() {
        for (int i = 10; i > 0; i--) {
            msg = "<" + t + "> " + t + ": " + i;
            x.scrivi(t, msg);
        }
    }
    
}
class Schermi {     //questo è il monitor che permette di gestire le risorse condivise

  String ultimoTHREAD = ""; //contiene l'ultimo thread che ha scritto sullo schermo
  int punteggio = 0;    //variabile che permette di calcolare il punteggio finale

  public int punteggio() {  // fornisce il punteggio
    return this.punteggio;
  }

  public synchronized void scrivi(String thread, String msg) {      //implementando syncrhonized il programma evita di avere dei conflitti
      //creazione di numeri casuali tra 100 e 300
    int casuale=100+(int)(Math.random()*300); 
    msg += ": " + casuale + " :";
    if( thread.equals("TOE") && ultimoTHREAD.equals("TAC")) {   
        //condizione che controlla se il THREAD attualmente in uso è uguale a TOE e se l'ultimo THREAD salvato nella variabile è uguale a TAC
        punteggio++;    //se la condizione si avvera il punteggio viene incrementato
        msg += "  <----------------";
    }
    try {
        TimeUnit.MILLISECONDS.sleep(casuale); //casuale ora diventa un numero rappresentante il tempo il MILLISECONDI
    } catch (InterruptedException e) {} //Richiamo eccezione    this.ultimoTHREAD = thread;
    System.out.println(msg);
    ultimoTHREAD = thread;
  }
}
