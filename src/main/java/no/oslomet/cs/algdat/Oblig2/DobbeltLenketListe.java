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

        Objects.requireNonNull(a,"Tabellen a er null!");


        int i = 0; for(; i<a.length && a[i] == null; i++); // vil nå første indeks som ikke er null
        if(i<a.length){
            Node<T> p = hode = new Node<>(a[i],null,null); //den første noden
            antall = 1;

            for(i++; i<a.length; i++){  // finne neste noder
                if(a[i] != null){
                    p = p.neste = new Node<>(a[i],p,null);
                    antall++;
                }
            }
            hale = p;
        }

    }

    public Liste<T> subliste(int fra, int til) {

        fratilKontroll(antall,fra,til); //sjekke om indeksene fra og til er lovlige
        Liste<T> liste = new DobbeltLenketListe<>();    //ny objekt
        for (int i=fra; i<til; i++){
            T verdi = finnNode(i).verdi;       //ta vare på ønkede verdi
            liste.leggInn(verdi);           //legg den inn i liste
        }
        return liste;
    }

    private void fratilKontroll(int antall, int fra, int til){
        if(fra < 0 || til > antall)
            throw new IndexOutOfBoundsException("Indeksene er utenfor arrayet!");
        if(fra > til)
            throw new IllegalArgumentException("fra(" + fra + ") > til(" + til + ") - illegalt intervall!");
    }

    @Override
    public int antall() { return antall; }

    @Override
    public boolean tom() { return antall == 0; }

    @Override
    public boolean leggInn(T verdi) {

        Objects.requireNonNull(verdi,"Ikke tillatt med null-verdier!");

        if(antall == 0){    //Listen på forhånd er tom
            hode=hale = new Node<>(verdi,null,null);
        }else{              //Listen er ikke tom.
            hale = hale.neste = new Node<>(verdi,hale,null);    // legges bakerst
        }
        antall++;       // en mer i listen
        endringer++;    // øke endinger i listen

        return true;    // vellykket innlegging
    }

    @Override
    public void leggInn(int indeks, T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean inneholder(T verdi) {
        throw new UnsupportedOperationException();
    }

    private Node<T> finnNode(int indeks){

        Node<T> p;
        if(indeks < antall/2){
            p = hode;       //peker på hode
            for(int i = 0; i<indeks; i++) p = p.neste; //Start letingen fra hode mot høyere
        }else{
            p = hale;       //Peker på hale
            for(int i = antall-1; i>indeks; i--) p = p.forrige;  //Start letingen fra hale mot venstre
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
    public int indeksTil(T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T oppdater(int indeks, T nyverdi) {

        Objects.requireNonNull(nyverdi,"Ikke tillatt med null-verdier!");
        indeksKontroll(indeks, false);  //Sjekk indeks
        Node<T> p = finnNode(indeks);       // finne ønskede node
        T gammelVerdi = p.verdi;            //ta vare på verdien
        p.verdi = nyverdi;              //bytte gamle verdien med den nye
        endringer++;

        return gammelVerdi;
    }

    @Override
    public boolean fjern(T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T fjern(int indeks) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void nullstill() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();
        s.append('[');  // starter utsriften med [
        if(!tom()){
            Node<T> p = hode;   // starter å bygge fra hode
            s.append(p.verdi);  //så legger den inn i s
            p = p.neste;    //hode blir da neste.

            while (p != null){  // tar med resten hvis det er noe mer
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
        if(!tom()){
            Node<T> p = hale;   // starter å skrive verdier fra hale
            s.append(p.verdi);  //så legger den inn i s
            p = p.forrige;    //hale blir da forrige.

            while (p != null){  // tar med resten hvis det er noe mer
                s.append(',').append(' ').append(p.verdi);
                p = p.forrige;
            }
        }
        s.append(']');  // avslutter utsriften med ]
        return s.toString();
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException();
    }

    public Iterator<T> iterator(int indeks) {
        throw new UnsupportedOperationException();
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
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            return denne != null;
        }

        @Override
        public T next() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    } // class DobbeltLenketListeIterator

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        throw new UnsupportedOperationException();


    }

} // class DobbeltLenketListe


