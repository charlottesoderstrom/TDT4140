package sample;

import java.util.HashMap;


/**
 * Tilstandsobjekt. Representerer en oppskrift.
 */
public class Recipe implements java.io.Serializable {
    private final String title;
    private final String instructions;
    private HashMap<String, String> ingredients;
    private final boolean verified;


    /**
     * Konstruktør.
     * @param title tittel på oppskriften.
     * @param instructions fremgangsmåten til oppskriften.
     * @param ingredients ingrediensene til oppskriften.
     */
    public Recipe(String title, String instructions, HashMap<String, String> ingredients) {

        /**
         * Tittel på oppskriften.
         */
        this.title = title;

        /**
         * Fremgangsmåten til oppskriften.
         */
        this.instructions = instructions;

        /**
         * Ingrediensene til oppskriften.
         */
        this.ingredients = ingredients;

        /**
         * Holder styr på om oppskriften er anbefalt, dvs. om oppskriften er skrevet av en "Chef".
         */
        this.verified = false;
    }


    /**
     * Konstruktør.
     * @param title tittel på oppskriften.
     * @param instructions fremgangsmåten til oppskriften.
     * @param ingredients ingrediensene til oppskriften.
     * @param verified true hvis oppskriften er anbefalt.
     */
    public Recipe(String title, String instructions, HashMap<String, String> ingredients, boolean verified) {
        this.title = title;
        this.instructions = instructions;
        this.ingredients = ingredients;
        this.verified = verified;
    }


    /**
     * @return tittelen til oppskriften.
     */
    public String getTitle() {
        return this.title;
    }


    /**
     * @return fremgangsmåten til oppskriften.
     */
    public String getInstructions() {
        return this.instructions;
    }


    /**
     * @return ingrediensene i oppskriften.
     */
    public HashMap<String, String> getIngredients() {
        return this.ingredients;
    }


    /**
     * @return true hvis oppskriften er anbefalt.
     */
    public boolean isVerified() {
        return verified;
    }


    /**
     * Printer oppskriften.
     */
    public void printContent() {
        System.out.println("Title: " + this.getTitle());
        System.out.println("Instructions: " + this.getInstructions());
        System.out.println("Ingredients:");
        this.ingredients.forEach((ingredient, amount) -> System.out.println(ingredient + " - " + amount));
    }

    public static Recipe makeTestRecipe() {
        HashMap<String, String> ingredients = new HashMap<>();
        ingredients.putIfAbsent("Egg", "5");
        ingredients.putIfAbsent("Flour", "0.5 kilos");
        ingredients.putIfAbsent("Sugar", "Lots");
        ingredients.putIfAbsent("Water", "100ml");
        ingredients.putIfAbsent("Dry Yeast", "50 grams");
        Recipe boller = new Recipe("Boller", "Crack eggs into bowl. Whisk everything. " +
                "Add lots of sugar. Cook for a couple of minutes.", ingredients, true);
        return boller;
    }

    public static void main(String[] args) {

    }
}
