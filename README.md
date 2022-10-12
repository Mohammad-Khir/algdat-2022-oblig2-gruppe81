# Obligatorisk oppgave 2 i Algoritmer og Datastrukturer 

Denne oppgaven er en innlevering i Algoritmer og Datastrukturer. 
Oppgaven er levert av følgende studenter:
## Gruppe 81:
* Moustapha Aljundi, 5364546, s364546@oslomet.no
* Alan Ali, s356488, s356488@oslomet.no
* Mohammad Khir Khaled Almohammad, 343988, s343988@oslomet.no
* Ali Fariz Alghadban, s362111, s362111@oslomet.no

# Arbeidsfordeling

I oppgaven har vi hatt følgende arbeidsfordeling:
* Mohammad har hatt hovedansvar for oppgave 1, 2, og 3. 
* Ali har hatt hovedansvar for oppgave 4, 5, og 6. 
* Mustapha har hatt hovedansvar for oppgave 7 og 8. 
* Alan har hatt hovedansvar for oppgave 9 og 10.
* Vi diskuterte alle oppgavene i fellesskap.

# Oppgavebeskrivelse

### Oppgave 1
antall() returnerer antall noder i listen. tom() er true når antall = 0.
Konstruktøren bruker en for-løkke til å finne den første i tabellen a
som ikke er null. Tar og legger den første verdien i a som den første noden.
Bruker så en for-løkke for å finne alle veridene til indeksene i a
som ikke er null og legger dem som en ny node i listen. Siste verdi i a
blir lagt inn i hale. Vi tok idien fra oppgave3 avsnitt3.3.2 i kompendiet.

### Oppgave 2
For å løse denne oppgaven tok vi inspirasjon fra Kompendiet oppgave2 avsnitt 3.3.2.
Løste metoden toString() ved å bruke StringBuilder som setter hver node
som er i listen inn i en streng. Koden sjekker at listen ikke er tom.
Setter at første node peker på hode og går deretter til neste-peker.
Den tar alle nodene som ikke er null og legger dem etterhverandre i
strengen ved bruk av en while-løkke. omvendtString() bruker det samme
oppsett, bortsett fra at den starter fra halen og går til forrige-peker.

### Oppgave 3
finnNode() metode sjekker om indeks er riktig og looper gjennom listen til
den ønskede indeksen og returnerer noden, oppdater() bruker finnNode og
endrer verdien til node(sjekker om verdi er gyldig og indeks).
Subliste() bruker finnNode inne i en forloop.
Kan effektiviseres med to forløkker istede for finnNode kall hvor
hvert element.

### Oppgave 4
Vi løste oppgaven basert på oppgave2 avsnitt 3.3.3 i kompendiet.
indeksTil() sjekker først om verdien er null.
Går gjennom i en for-løkken å sjekker om verdi finnes i listen.
Hvis verdi finnes vil den returnere indeksen til den første like verdien.
inneholder() sjekker om indeksTil() ikke er lik -1 og vil
returnere true hvis listen inneholder verdi.


### Oppgave 5
Vi løste oppgaven basert på Programkode 3.3.2 g) i kompendiet.
leggInn(int indeks, T verdi) sjekker om indeks er 0, om listen er tom og om indeksen er det
samme som antall. Legger den nye verdien på valgt indeks og flytter den
nåværende noden til neste indeks.

### Oppgave 6
Ut fra oppgave3 avsnitt 3.3.3 og Programkode 3.3.3 c)i kompendiet så startet vi oppgaven med:
fjern(T verdi) legger inn en verdi som skal fjernes,
koden sjekker først om den verdien finnes deretter alle mulige posisjoner hvor den verdien ligger.
hode: flytter pekere til riktig pekere, hale: flytter pekere til riktig peker, eller i mellom.
Returnerer true hvis verdi finnes og false hvis verdi ikke finnes.
fjern(int indeks) gjør akkurat det samme men returer verdien til indeksen som ble fjernet.

### Oppgave 7
Denne oppgaven minner oss på  oppgave2 avsnitt 3.3.2 i kompendie.
nullstill() løper igjennom hele listen og nullstiller alle verdier.
Vi kodet den på de to måtene som beskevet i oppgaveteksten.
Vi så at den  mest effektive måte var måte-1.

### Oppgave 8
løste oppgaven etter at vi har lest hele avsnitt 3.3.4 og spesielt på
Programkode 3.3.4 b)c)e) fra kompendie, da ble det lettere å sette i gang med å kode den.
next() flytter den nåværende noden en frem, iterator() lager et iterator object
og returnere det, samme med iterator(indeks) bare at den starter ved en gitt indeks.

### Oppgave 9
remove() fjerner noden som er til venstre for nåværende noder og sjekker de
forskjellige tilfellene som kan oppstå(slette hode, hale, ingenting osv..).
Vi fikk litt inspirasjon for å starte av Programkode 3.3.4 d) i kompendiet.

### Oppgave 10
Sorter() sorterer listen, finner først minste tall, deretter bytter på de.
Bruker metodene liste.hent og liste.oppdater.


## Kommentar på testresultat:
I oppgave 6 testet vi tid1 og tid2 på to forskjellige pc-er.
Den ene fikk tid1: 759 og tid2: 661 og vil da passere testen.
Den andre fikk tid1: 1571 og tid2: 856. Den andre får feil på test 6zg
pga for stor differanse mellom tidene (dette er nok pga for-løkken i
første metode). Vi har testet flere ganger med på første pcen og får ikke
opp feilmeldingen på oppgave 6.

