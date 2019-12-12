package sample;

import java.io.IOException;
import java.util.ArrayList;

public class MainBackEnd {

    public static void start(){
        MultiServer server = new MultiServer();
        try {
        server.runServer();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        start();

        //System.out.println("Hello World!");

        //System.out.println("Commencing addRecipesTest...\n_____________________________");
        //DBConnector.addRecipe("Omelette", "Crack eggs, then cook. Season to taste and eat straight away.");
        //HashMap<String, String> ingredients = new HashMap<>();
        //ingredients.put("Egg","3");
        //ingredients.put("Salt","a tablespoon");
        //ingredients.put("Pepper","a tablespoon");
        //ingredients.put("Water","100ml");
        //ingredients.put("Sausage","1");
        //Recipe omelette = new Recipe("Omelette", "Crack eggs into bowl. Whisk everything. " +
                //"Add seasoning to taste. Cook for a couple of minutes.", ingredients);
        //DBConnector.addRecipe(omelette);

        //System.out.println("Commencing printRecipesTest...\n_____________________________");
        //DBConnector.printRecipes();

        //System.out.println("Commencing getRecipesTest\n_____________________________");
        //List<Recipe> recipes = DBConnector.getRecipes();
        //recipes.forEach(recipe -> recipe.printContent());

        //System.out.println("Commencing printRecipesByIngredientTest...\n_________________________________\n");
        //DBConnector.printRecipesByIngredient("Flour");
        //DBConnector.printRecipesByIngredient("Water");
        //DBConnector.printRecipesByIngredient("Salt");

        //System.out.println("Commencing printRecipesByNameTest\n_____________________________");
        //DBConnector.printRecipesByName("Pancakes");
        //DBConnector.printRecipesByName("Omelette");

        //System.out.println("Commencing getRecipesByNameTest\n_____________________________");
        //ArrayList<Recipe> recipes;
        //recipes = DBConnector.getRecipesByName("Omelette");
        //recipes.forEach(recipe -> recipe.printContent());

        //System.out.println("Commencing getRecipesByIngredientTest\n_____________________________");
        //ArrayList<String> ingredients = new ArrayList<>();
        //ingredients.add("Salt");
        //ingredients.add("Pepper");
        //ArrayList<Recipe> recipes;
        //recipes = DBConnector.getRecipesByIngredient("Egg");
        //recipes = DBConnector.getRecipesByIngredient(ingredients);
        //recipes.forEach(recipe -> recipe.printContent());
    }
}
