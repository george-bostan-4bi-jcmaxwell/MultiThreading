/*
 * Con questo programma voglio illustrare i seguenti concetti:
 * 1. MAIN e' un thread come gli altri e quindi puo' terminare prima che gli altri
 * 2. THREADs vengono eseguiti allo stesso tempo
 * 3. THREADs possono essere interrotti e hanno la possibilita' di interrompersi in modo pulito
 * 4. THREADs possono essere definiti mediante una CLASSE che implementa un INTERFACCIA Runnable
 * 5. THREADs possono essere avviati in modo indipendente da quando sono stati definiti
 * 6. posso passare parametri al THREADs tramite il costruttore della classe Runnable
 */

//il codice è stato fatto completamente da me, ad eccezione dell'ultima parte, infatti ho chiesto aiuto a gerardo per le condizioni da mettere negli if
//se il codice è simile a qualcuno è perchè su github chiunque può vedere il codice degli altri pertanto può essere stato copiato.
//ripeto quindi che il codice è stato fatto da me tranne per i due if finali che ho fatto con l'aiuto di gerardo.
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
    // "main" e' il THREAD principale da cui vengono creati e avviati tutti gli altri THREADs
    // i vari THREADs poi evolvono indipendentemente dal "main" che puo' eventualmente terminare prima degli altri
    // nel main vengono creati e utilizzati 3 threads
    // essi partono contemporaneamente e dopo un tempo random stabilito nel run() essi scrivono su dusplai il conto alla rovescia che parte da 10
    // essendo random i threads scrivono i numeri su schermo in ordine casuale 
    //(nel senso che qualunque dei 3 threads potrebbe scrivere prima dell'altro e subito dopo potrebbe accadere il contrario)
    // viene infine mostrato il punteggio che rappresenta le volte dove il thread TOE capita prima di TAC
    public static void main(String[] args) {
        System.out.println("Main Thread iniziata...");
        Schermi x = new Schermi();
        
        Thread tic = new Thread (new TicTacToe("TIC", x)); //permette la creazione primo THREAD TIC
        
        Thread tac = new Thread(new TicTacToe("TAC", x));  //permette la creazione secondo THREAD TAC
        
        Thread toe = new Thread(new TicTacToe("TOE", x));  //permette la creazione terzo THREAD TOE
        
        tic.start();    // avvia il THREAD TIC
        tac.start();    // avvia il THREAD TAC
        toe.start();    // avvia il THREAD Toe
        //tutti i THREADS partono contemporaneamente
        try {
            tic.join(); //mette in attesa il programma principale finche il THREAD TIC non finisce l'esecuzione
            TimeUnit.MILLISECONDS.sleep(1111);  //tempo di attesa
        } catch (InterruptedException e) {}
        
        try {
            tac.join(); //mette in attesa il programma principale finche il THREAD TAC non finisce l'esecuzione
            TimeUnit.MILLISECONDS.sleep(1111);  //tempo di attesa
        } catch (InterruptedException e) {}
        
        try {
            toe.join(); //mette in attesa il programma principale finche il THREAD TOE non finisce l'esecuzione
            TimeUnit.MILLISECONDS.sleep(1111);  //tempo di attesa
        } catch (InterruptedException e) {}

        System.out.println("Il punteggio e: " +x.punteggio());  //mostra su schermo il punteggio finale calcolato nel run()
        System.out.println("Main Thread completata! tempo di esecuzione: " + (end - start) + "ms"); //indica il tempo impiegato dal programma
    }
    
}

class TicTacToe implements Runnable {
    private String t;
    private String msg;
    Schermi x;

    // Costruttore
    public TicTacToe (String s, Schermi x) {
        this.t = s;
        this.x = x;
    }
    class Schermi {

  String ultimoTHREAD = ""; // ultimo thread che ha scritto sullo schermo
  int punteggio = 0;

  public int punteggio() {  // fornisce il punteggio
    return this.punteggio;
  }

  public synchronized void scrivi(String thread, String msg) {
    int casuale=100+(int)(Math.random()*300); //numero casuale tra 100 e 400
    msg += ": " + casuale + " :";
    if( thread.equals("TOE") && ultimoTHREAD.equals("TAC")) {
        punteggio++;
        msg += "  <----------------";
    }
    try {
        TimeUnit.MILLISECONDS.sleep(casuale); //casuale ora diventa un numero rappresentante il tempo il MILLISECONDI
    } catch (InterruptedException e) {} //Richiamo eccezione    this.ultimoTHREAD = thread;
    System.out.println(msg);
    ultimoTHREAD = thread;
  }
}
    @Override // Annotazione per il compilatore
    // se facessimo un overloading invece di un override il copilatore ci segnalerebbe l'errore
    // per approfondimenti http://lancill.blogspot.it/2012/11/annotations-override.html
    // questa è la parte del codice che permette di calcolare sia il tempo random, sia il punteggio finale
    // esso utilizza poi il tempo creato per avviare i THREADS in modo casuale
    public void run() {
        for (int i = 10; i > 0; i--) {
            msg = "<" + t + "> " + t + ": " + i;
            x.scrivi(t, msg);
        }
    }
    
}
