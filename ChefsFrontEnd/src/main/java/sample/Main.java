package sample;

/**
 * Hovedklasse for front-end applikasjon.
 * Det er denne som kjøres i den ferdigkompilerte jar-filen.
 */
public class Main {

    /**
     * Metoden kjører FXMLMain sin main-metode
     * Dette måtte gjøres for å unngå en exception som følge av JavaFX
     * og Maven.
     * @param args
     * @see FXMLMain#main(String[])
     */
    public static void main(String[] args) {
        FXMLMain.main(args);
    }
}
