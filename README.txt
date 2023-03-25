Tema2.java (Main)
Pentru a parcurge continutul fisierelor si a-l putea atribui in mod optim
thread-urilor, am salvat continutul de la orders.txt intr-o lista numita
order_list si pe cel de la order_products.txt in product_list, pe care le
trimit mai departe claselor.

Am creat, de asemenea, doua semafoare pe care le trimit mai departe claselor
semmy = semafor ce blocheaza un singur thread, se asigura ca maxim un thread
va printa intr-un fisier la un moment dat
semmy_p = semafor ce blocheaza p thread-uri, se asigura ca nu sunt mai mult de
p thread-uri active pe un nivel

Comanda.java
Folosind formulele din laboratorul 1 am delimitat "munca" pentru fiecare thread
cu un "start" si "end". Pentru fiecare comanda primita, despart string-ul si
dupa creez thread-uri pentru produse.

Produs.java
Folosesc din nou formula din primul laborator pentru a imparti continutul la
toate thread-urile. Dupa aceea, parcurg fisierul de produse cu fiecare thread
in parte si printez atunci cand comenzile corespund.