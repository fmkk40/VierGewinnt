# README

## Inhaltsverzeichnis:

- *Präsentation und Beschreibung des Spiel VierGewinnt.*
- *Starten des Spiels in IntelliJ.*
- *Beispiel für Gamelogic.*
- *Implementierung eines Threads.*
- *Starten des Spiels in Jshell.*
- *JUnit-Tests.*
- *Verwendete Bibliotheken.*


## Darstellung des Spiels

Das Spiel wird auf einem senkrecht stehenden hohlen Spielbrett gespielt, in das die Spieler abwechselnd ihre Spielsteine fallen lassen. Das Spielbrett besteht aus sieben Spalten (senkrecht) und sechs Reihen (waagerecht). Jeder Spieler besitzt 21 gleichfarbige Spielsteine. Wenn ein Spieler einen Spielstein in eine Spalte fallen lässt, besetzt dieser den untersten freien Platz der Spalte. Gewinner ist, wer vier oder mehr seiner Spielsteine in eine waagerechte, senkrecht oder diagonale Linie bringt. Das Spiel endet unentschieden, wenn das Spielbrett komplett gefüllt ist, ohne dass ein Spieler eine Viererlinie gebildet hat.

## Starten des Spiels in IntelliJ

Um das Spiel zu starten muss man die main-Methode in View ausführen und nachdem es gemacht wurde, kommt auf dem Bildschirm ein Bild mit einigen Hinweise.
Diese Hinweise dienen dazu, dass man sich entscheiden kann :  
- *ob man mit jemandem spielen will, indem man auf "1" drückt;* 
 - *ob man mit dem Rechner spielen will, indem man auf "2" drückt*
 -  *ob man Hilfe braucht, indem man auf "3" drückt*
### Screenshot
<img src="Screenshot.png"> 