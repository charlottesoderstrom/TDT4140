package sample;

import java.util.HashMap;


public class Recipe implements java.io.Serializable {
    private final String title;
    private final String instructions;
    private HashMap<String, String> ingredients;
    private final boolean verified;

    public Recipe(String title, String instructions, HashMap<String, String> ingredients) {
        this.title = title;
        this.instructions = instructions;
        this.ingredients = ingredients;
        this.verified = false;
    }

    public Recipe(String title, String instructions, HashMap<String, String> ingredients, boolean verified) {
        this.title = title;
        this.instructions = instructions;
        this.ingredients = ingredients;
        this.verified = verified;
    }

    public String getTitle() {
        return this.title;
    }

    public String getInstructions() {
        return this.instructions;
    }

    public HashMap<String, String> getIngredients() {
        return this.ingredients;
    }

    public boolean isVerified() {
        return verified;
    }

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
