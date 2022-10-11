package no.oslomet.cs.algdat.Oblig2;


////////////////// class DobbeltLenketListe //////////////////////////////

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Iterator;
import java.util.Comparator;

public class DobbeltLenketListe<T> implements Liste<T> {

    /**
     * Node class
     *
     * @param <T>
     */
    private static final class Node<T> {
        private T verdi;                   // nodens verdi
        private Node<T> forrige, neste;    // pekere

        private Node(T verdi, Node<T> forrige, Node<T> neste) {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        private Node(T verdi) {
            this(verdi, null, null);
        }
    }

    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;         // antall endringer i listen

    public DobbeltLenketListe() {   // standardkonstruktør
        hode = hale = null;
        endringer = 0;
        antall = 0;
    }

    public DobbeltLenketListe(T[] a) {
        this();     // alle variabelene er nullet

        Objects.requireNonNull(a, "Tabellen a er null!");


        int i = 0;
        for (; i < a.length && a[i] == null; i++) ; // vil nå første indeks som ikke er null
        if (i < a.length) {
            Node<T> p = hode = new Node<>(a[i], null, null); //den første noden
            antall = 1;

            for (i++; i < a.length; i++) {  // finne neste noder
                if (a[i] != null) {
                    p = p.neste = new Node<>(a[i], p, null);
                    antall++;
                }
            }
            hale = p;
        }

    }

    public Liste<T> subliste(int fra, int til) {

        fratilKontroll(antall, fra, til); //sjekke om indeksene fra og til er lovlige
        Liste<T> liste = new DobbeltLenketListe<>();    //ny objekt
        for (int i = fra; i < til; i++) {
            T verdi = finnNode(i).verdi;       //ta vare på ønkede verdi
            liste.leggInn(verdi);           //legg den inn i liste
        }
        return liste;
    }

    private void fratilKontroll(int antall, int fra, int til) {
        if (fra < 0 || til > antall)
            throw new IndexOutOfBoundsException("Indeksene er utenfor arrayet!");
        if (fra > til)
            throw new IllegalArgumentException("fra(" + fra + ") > til(" + til + ") - illegalt intervall!");
    }

    @Override
    public int antall() {
        return antall;
    }

    @Override
    public boolean tom() {
        return antall == 0;
    }

    @Override
    public boolean leggInn(T verdi) {

        Objects.requireNonNull(verdi, "Ikke tillatt med null-verdier!");

        if (antall == 0) {    //Listen på forhånd er tom
            hode = hale = new Node<>(verdi, null, null);
        } else {              //Listen er ikke tom.
            hale = hale.neste = new Node<>(verdi, hale, null);    // legges bakerst
        }
        antall++;       // en mer i listen
        endringer++;    // øke endinger i listen

        return true;    // vellykket innlegging
    }

    @Override
    public void leggInn(int indeks, T verdi) {  //med utgangspunkt i Programkode 3.3.2 g) i kompendie

        Objects.requireNonNull(verdi, "Ikke tillatt med null-verdier!");

        indeksKontroll(indeks, true);  //antall er lovlig

        if (indeks == 0) {                     // ny verdi skal ligge først
            if (antall == 0) {
                hale = hode = new Node<>(verdi, null, null);   // hode og hale går til samme node
            } else
                hode = hode.forrige = new Node<>(verdi, null, hode);    // legges først
        } else if (indeks == antall) {           // ny verdi skal ligge bakerst

            hale = hale.neste = new Node<>(verdi, hale, null);  // legges bakerst
        } else {
            Node<T> p = finnNode(indeks);                  // finne hvor den nye noden skal legges

            p.forrige = p.forrige.neste = new Node<>(verdi, p.forrige, p);  // verdi settes inn i listen
        }
        antall++;
        endringer++;
    }

    @Override
    public boolean inneholder(T verdi) {     //ligner på oppgave2 avsnitt 3.3.3

        return indeksTil(verdi) != -1;
    }

    private Node<T> finnNode(int indeks) {

        Node<T> p;
        if (indeks < antall / 2) {
            p = hode;       //peker på hode
            for (int i = 0; i < indeks; i++) p = p.neste; //Start letingen fra hode mot høyere
        } else {
            p = hale;       //Peker på hale
            for (int i = antall - 1; i > indeks; i--) p = p.forrige;  //Start letingen fra hale mot venstre
        }
        return p;
    }

    @Override
    public T hent(int indeks) {

        indeksKontroll(indeks, false);  //Sjekk indeks
        Node<T> p = finnNode(indeks);   // finne indeksen til ønskede node
        return p.verdi;
    }

    @Override
    public int indeksTil(T verdi) {  //utgangspunkt i oppgave2 avsnitt 3.3.3
        if (verdi == null) return -1;

        Node<T> p = hode;
        for (int i = 0; i < antall; i++) {
            if (p.verdi.equals(verdi)) {
                return i;       // return indeksen
            }
            p = p.neste; // går til neste node
        }
        return -1;  //hvis verdien ikke finnes
    }

    @Override
    public T oppdater(int indeks, T nyverdi) {

        Objects.requireNonNull(nyverdi, "Ikke tillatt med null-verdier!");
        indeksKontroll(indeks, false);  //Sjekk indeks
        Node<T> p = finnNode(indeks);       // finne ønskede node
        T gammelVerdi = p.verdi;            //ta vare på verdien
        p.verdi = nyverdi;              //bytte gamle verdien med den nye
        endringer++;

        return gammelVerdi;
    }

    @Override
    public boolean fjern(T verdi) {     //ut fra oppgave3 avsnitt 3.3.3 i kompendie

        if (verdi == null) return false;

        Node<T> p = hode, q, r;

        for (int i = 0; i < antall; i++) {
            if (p.verdi.equals(verdi)) {    // prøver å finne verdien
                if (antall == 1) {       // Listen inneholder kun en verdi?
                    hode = hale = null;
                    break;
                }
                if (i == 0) {   // verdien kommer først
                    p = p.neste;
                    p.forrige = null;
                    hode = p;
                    break;
                } else if (i < antall - 1) {    // verdien kommer mellom to verdier
                    p = p.forrige;
                    q = p.neste;
                    r = q.neste;

                    p.neste = r;
                    r.forrige = p;
                    break;
                } else {        // verdien kommer sist
                    q = p.forrige;
                    q.neste = null;
                    hale = q;
                    break;
                }
            }
            if (i == antall - 1) {    // fant ikke verdien
                return false;
            }
            p = p.neste;
        }
        antall--;
        endringer++;

        return true;
    }

    @Override
    public T fjern(int indeks) {
        indeksKontroll(indeks, false);  //sjekke indeksen
        if (antall == 0)        // liste er tom
            throw new NoSuchElementException("Liste er tom");
        Node<T> temp = finnNode(indeks);    //finne ønskede node
        if (temp == null)
            return null;
        if (temp.forrige != null) {
            temp.forrige.neste = temp.neste;
        }
        if (temp.neste != null) {
            temp.neste.forrige = temp.forrige;
        }
        if (temp == hode && temp.neste != null) {
            hode = temp.neste;
        }
        if (temp == hale && temp.forrige != null) {
            hale = temp.forrige;
        }
        antall--;
        endringer++;
        return temp.verdi;
    }

    @Override
    public void nullstill() {
        throw new UnsupportedOperationException();

    }

    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();
        s.append('[');  // starter utsriften med [
        if (!tom()) {
            Node<T> p = hode;   // starter å bygge fra hode
            s.append(p.verdi);  //så legger den inn i s
            p = p.neste;    //hode blir da neste.

            while (p != null) {  // tar med resten hvis det er noe mer
                s.append(',').append(' ').append(p.verdi);
                p = p.neste;
            }
        }
        s.append(']');  // avslutter utsriften med ]
        return s.toString();
    }

    public String omvendtString() {

        StringBuilder s = new StringBuilder();
        s.append('[');  // starter utsriften med [
        if (!tom()) {
            Node<T> p = hale;   // starter å skrive verdier fra hale
            s.append(p.verdi);  //så legger den inn i s
            p = p.forrige;    //hale blir da forrige.

            while (p != null) {  // tar med resten hvis det er noe mer
                s.append(',').append(' ').append(p.verdi);
                p = p.forrige;
            }
        }
        s.append(']');  // avslutter utsriften med ]
        return s.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new DobbeltLenketListeIterator();
    }

    public Iterator<T> iterator(int indeks) {
        indeksKontroll(indeks, false);
        return new DobbeltLenketListeIterator(indeks);
    }

    private class DobbeltLenketListeIterator implements Iterator<T> {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator() {
            denne = hode;     // p starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks) {
            denne = finnNode(indeks);
            fjernOK = false;
            iteratorendringer = endringer;
        }

        @Override
        public boolean hasNext() {
            return denne != null;
        }

        @Override
        public T next() {   // ut fra Programkode 3.3.4 c) fra kompendie
            if (iteratorendringer != endringer) { //sjekke om iteratorendringer er lik endringer.
                throw new ConcurrentModificationException();
            } else if (!hasNext()) {    // ikke flere igjen i listen?
                throw new NoSuchElementException();
            }
            fjernOK = true;
            T verdi = denne.verdi;  // tar vare på verdien
            denne = denne.neste;    // flytter denne til neste node
            return verdi;
        }

        @Override
        public void remove() {
            if (!fjernOK) {   // ikke tillatt å kalle metoden
                throw new IllegalStateException("Ulovlig tilstand!");
            }
            if (iteratorendringer != endringer) { //endringer og iteratorendringer er forskjellige
                throw new ConcurrentModificationException();
            }
            fjernOK = false;    //remove() kan ikke kalles på nytt

            if (antall == 1) {    //den som skal fjernes er eneste verdi
                hode = null;
                hale = null;
            } else if (denne == null) {    //den siste skal fjernes
                hale = hale.forrige;
                hale.neste = null;
            } else if (denne.forrige == hode) {    //den første skal fjernes
                hode = denne;
                hode.forrige = null;
            } else {                      //Hvis en node inne i listen skal fjernes
                denne.forrige = denne.forrige.forrige;
                denne.forrige.neste = denne;
            }
            antall--;
            endringer++;
            iteratorendringer++;
        }

    } // class DobbeltLenketListeIterator

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        if (liste == null) {
            throw new NullPointerException("Ingen verdi i listen");
        }

        int n = liste.antall();
        for (int i = 0; i < n - 1; i++) {    //Finne indeksen til minste
            int min = i;
            for (int j = i + 1; j < n; j++) {
                if (c.compare(liste.hent(j), liste.hent(min)) < 0)
                    min = j;
            }

            T temp = liste.hent(min);
            liste.oppdater(min, liste.hent(i));
            liste.oppdater(i, temp);
        }
    }

} // class DobbeltLenketListe


