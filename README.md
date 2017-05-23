## Funzionamento Programma
# Esercizio 1
Il programma TicTacToe utilizza tre THREADs diversi:
- Tic
- Tac
- Toe
<br>
I seguenti THREADs stampano in sequenza i numeri da 10 a 1.
Tutti i THREADs partono contemporaneamente ma poi, grazie ad un tempo generato casualmente tra 100 e 300 millisecondi, ogni THREAD stampa il numero della sequenza aspettando un tempo casuale.
Si può dunque notare che il risultato finale sono la stampa di 3 sequenze di numeri sempre diverse dovute al tempo che essendo casuale cambia ogni volta.
Infine ogni volta che il THREAD Toe capita dopo quello Tac, un contatore si incrementa e, alla fine delle 3 sequenze di numeri, mostra quante volte questa condizione è avvenuta (punteggio). 
<br>
# Esercizio 2
