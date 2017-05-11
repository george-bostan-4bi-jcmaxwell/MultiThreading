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

import java.util.Random;
import java.util.concurrent.TimeUnit;
import static multithread.TicTacToe.punteggio;
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
        long start = System.currentTimeMillis();
        
        Thread tic = new Thread (new TicTacToe("TIC")); //permette la creazione primo THREAD TIC
        
        Thread tac = new Thread(new TicTacToe("TAC"));  //permette la creazione secondo THREAD TAC
        
        Thread toe = new Thread(new TicTacToe("TOE"));  //permette la creazione terzo THREAD TOE
        
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

        long end = System.currentTimeMillis();
        System.out.println("Il punteggio e: " +punteggio);  //mostra su schermo il punteggio finale calcolato nel run()
        System.out.println("Main Thread completata! tempo di esecuzione: " + (end - start) + "ms"); //indica il tempo impiegato dal programma
    }
    
}

class TicTacToe implements Runnable {
    public static int punteggio = 0;    //variabile utilizzata calcolare e stampare il punteggio finale
    public static boolean trovato = false;  //variabile utilizzata durante il calcolo del punteggio
    private String t;
    private String msg;

    // Costruttore
    public TicTacToe (String s) {
        this.t = s;
    }
    
    @Override // Annotazione per il compilatore
    // se facessimo un overloading invece di un override il copilatore ci segnalerebbe l'errore
    // per approfondimenti http://lancill.blogspot.it/2012/11/annotations-override.html
    // questa è la parte del codice che permette di calcolare sia il tempo random, sia il punteggio finale
    // esso utilizza poi il tempo creato per avviare i THREADS in modo casuale
    public void run() {
        Random random = new Random();   //serve per creare i valori random
        int j = 100;
        int n = 300-j;
        int k = random.nextInt(n)+j;    //i valori saranno compresi tra 100 e 300
        for (int i = 10; i > 0; i--) {
            if (t.equals("TAC"))    //controlla se è in corso il THREAD TAC
            {
                trovato = true; //se la condizione è vera trovato diventa true
            }
            msg = "<" + t + "> ";
            //System.out.print(msg);
            try {
                TimeUnit.MILLISECONDS.sleep(k);
            } catch (InterruptedException e) {
                System.out.println("THREAD " + t + " e' stata interrotta! bye bye...");
                return; //me ne vado = termino il THREAD
            }
            if ("TOE".equals(t) && trovato == true) //controlla se il THREAD TOE è in corso e se la prima condizione è = true
            {
                punteggio++;    //se la condizione si avvera il punteggio viene incrementato
            }
            else
            {
                trovato = false;    //altrimenti trovato diventa false e di conseguenza il punteggio rimane invariato
            }
            msg += t + ": " + i;
            System.out.println(msg);
         
        }
    }
    
}
