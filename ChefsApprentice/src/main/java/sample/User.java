package sample;


import java.io.Serializable;

/**
 * Denne klassen brukes for å holde styr på brukerprivilegier.
 */

public class User implements Serializable {
    /**
     * Brukernavnet til brukeren.
     */
    private final String username;
    /**
     * Hvilken grad av systemprivilegier brukeren har.
     */
    private final String role;

    /**
     * Lager et bruker-objekt.
     * @param username  Brukernavnet til brukeren.
     * @param role  Graden av systemprivilegier som brukeren har.
     */
    public User(String username, String role) {
        this.username = username;
        this.role = role;
    }

    /**
     * Standard getter for brukernavn.
     * @return  returnerer brukernavnet til brukeren.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Standard getter for rolle.
     * @return  returnerer brukerprivilegiene til brukeren.
     */
    public String getRole() {
        return this.role;
    }

    /**
     * Denne metoden er en slags toString(). Printer oppskriftsklassen på et fint format.
     */
    public void printUser() {
        System.out.println("\nUsername: " + this.getUsername());
        System.out.println("Role: " + this.getRole());
    }

}
