/*
 * Con questo programma voglio illustrare i seguenti concetti:
 * 1. MAIN e' un thread come gli altri e quindi puo' terminare prima che gli altri
 * 2. THREADs vengono eseguiti allo stesso tempo
 * 3. THREADs possono essere interrotti e hanno la possibilita' di interrompersi in modo pulito
 * 4. THREADs possono essere definiti mediante una CLASSE che implementa un INTERFACCIA Runnable
 * 5. THREADs possono essere avviati in modo indipendente da quando sono stati definiti
 * 6. posso passare parametri al THREADs tramite il costruttore della classe Runnable
 */
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
    public static void main(String[] args) {
        System.out.println("Main Thread iniziata...");
        long start = System.currentTimeMillis();
        
        Thread tic = new Thread (new TicTacToe("TIC")); //creazione primo THREAD
        
        Thread tac = new Thread(new TicTacToe("TAC"));  //creazione secondo THREAD
        
        Thread toe = new Thread(new TicTacToe("TOE"));  //creazione terzo THREAD
        
        tic.start();    // avvio del primo THREAD
        tac.start();    // avvio del secondo THREAD
        toe.start();    // avvio del terzo THREAD
        
        try {
            tic.join(); //mette in attesa il thread tic in esecuzione fino a quando non termina
            TimeUnit.MILLISECONDS.sleep(1111);  //tempo di attesa
        } catch (InterruptedException e) {}
        
        try {
            tac.join(); //mette in attesa il thread tac in esecuzione fino a quando non termina
            TimeUnit.MILLISECONDS.sleep(1111);  //tempo di attesa
        } catch (InterruptedException e) {}
        
        try {
            toe.join(); //mette in attesa il thread toe in esecuzione fino a quando non termina
            TimeUnit.MILLISECONDS.sleep(1111);  //tempo di attesa
        } catch (InterruptedException e) {}

        long end = System.currentTimeMillis();
        System.out.println("Il punteggio e: " +punteggio);  //mostra il punteggio finale
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
    public void run() {
        Random random = new Random();   //serve per trovare i valori random
        int j = 100;
        int n = 300-j;
        int k = random.nextInt(n)+j;    //Valori compresi tra 100 e 300
        for (int i = 10; i > 0; i--) {
            if (t.equals("TAC"))    //controlla se è in corso il THREAD tac
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
            if ("TOE".equals(t) && trovato == true) //controlla se è in corso il THREAD toe e se la prima condizione è = true
            {
                punteggio++;    //se la condizione si avvera il punteggio viene incrementato
            }
            else
            {
                trovato = false;    //altrimenti trovato diventa false
            }
            msg += t + ": " + i;
            System.out.println(msg);
         
        }
    }
    
}
