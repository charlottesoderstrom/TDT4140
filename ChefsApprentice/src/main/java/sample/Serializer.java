package sample;

import java.net.*;
import java.io.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Serializer {

    /**
     *
     * @param numberOfRecipes
     * @return Returnerer en ArrayList bestående av numberOfRecipes unike oppskrifter
     * Metoden gjør et kall til en tilfeldig oppskrift, og sjekker at ID'en ikke allerede er hentet, før den legges
     * til i ArrayList'en
     * @see Recipe
     */
    public static ArrayList<Recipe> getManyRecipes(int numberOfRecipes) {
        ArrayList<Recipe> recipes = new ArrayList<>();
        ArrayList<String> usedRecipes = new ArrayList<>();
        while (recipes.size() < numberOfRecipes) {
            IdRecipePair idRecipePair = getRandomRecipe();
            if (! usedRecipes.contains(idRecipePair.getId())) {
                recipes.add(idRecipePair.getRecipe());
                usedRecipes.add(idRecipePair.getId());
            }
        }
        return recipes;
    }

    /**
     *
     * @return Returnerer en tilfeldig oppskrift fra tredjepartsm api'et som et IdRecipePair-objekt
     * Dette objektet har to attributter:
     *      - String id
     *      - Recipe recipe
     */
    private static IdRecipePair getRandomRecipe() {
        try {
            String result = callAPI("https://www.themealdb.com/api/json/v1/1/random.php");
            return convertJsonToRecipe(result, true);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @return Returnerer standard-oppskriften (ID=52772) fra API'et
     */
    public static Recipe getDefaultRecipe() {
        try {
            String result = callAPI("https://www.themealdb.com/api/json/v1/1/lookup.php?i=52772");
            return convertJsonToRecipe(result);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param JsonRecipe JSON-streng som skal konverteres til et Recipe-objekt
     * @param getID Dersom denne parameteren er true returnes et IdRecipePair-objekt, ellers returneres null
     * @return Returnerer et IdRecipePair-objekt
     * @throws IOException
     */
    private static IdRecipePair convertJsonToRecipe(String JsonRecipe, boolean getID) throws IOException {
        // Tar bort den ytterste verdien i JSON-objektet, slik at alt lett kan mappes.
        String result = JsonRecipe.substring(10, JsonRecipe.length()-2);

        // Mapper JSON-strengen til key-value-par
        //https://www.baeldung.com/jackson-object-mapper-tutorial
        // https://stackoverflow.com/questions/10308452/how-to-convert-the-following-json-string-to-java-object
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> map = mapper.readValue(result, Map.class);

        HashMap<String, String> ingredients = new HashMap<>();
        for (int i = 1; i < 21; i++) {
            String ingredient = (String) map.get("strIngredient" + i);
            String measure = (String) map.get("strMeasure" + i);
            if (ingredient != null && ! ingredient.equals("")) {
                ingredients.put(ingredient, measure);
            }
        }
        Recipe recipe = new Recipe((String) map.get("strMeal"), (String) map.get("strInstructions"), ingredients);
        if (getID == true) {
            IdRecipePair idRecipePair = new IdRecipePair();
            idRecipePair.setId((String) map.get("idMeal"));
            idRecipePair.setRecipe(recipe);
            return idRecipePair;
        } else {
            return null;
        }
    }

    /**
     *
     * @param JsonRecipe JSON-streng som skal konverteres til et Recipe-objekt
     * @return Returnerer et Recipe-objekt
     * @throws IOException
     */
    private static Recipe convertJsonToRecipe(String JsonRecipe) throws IOException {
        // Tar bort den ytterste verdien i JSON-objektet, slik at alt lett kan mappes.
        String result = JsonRecipe.substring(10, JsonRecipe.length()-2);

        // Mapper JSON-strengen til key-value-par
        //https://www.baeldung.com/jackson-object-mapper-tutorial
        // https://stackoverflow.com/questions/10308452/how-to-convert-the-following-json-string-to-java-object
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> map = mapper.readValue(result, Map.class);

        HashMap<String, String> ingredients = new HashMap<>();
        for (int i = 1; i < 21; i++) {
            String ingredient = (String) map.get("strIngredient" + i);
            String measure = (String) map.get("strMeasure" + i);
            if (ingredient != null && ! ingredient.equals("")) {
                ingredients.put(ingredient, measure);
            }
        }
        Recipe recipe = new Recipe((String) map.get("strMeal"), (String) map.get("strInstructions"), ingredients);
        return recipe;
    }

    /**
     *
     * @param targetURL URL'en som skal kalles
     * @return Returnerer en String med resultatet fra API-kallet
     * @throws IOException
     */
    private static String callAPI(String targetURL) throws IOException {
        // https://stackoverflow.com/questions/1485708/how-do-i-do-a-http-get-in-javax
        StringBuilder response = new StringBuilder();

        // Opprett ett connection-objekt (oppretter ikke en connection med targetURL)
        URL url = new URL(targetURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        // Henter info fra targetURL
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            response.append(line);
        }
        rd.close();

        // Result er en JSON-string
        String result = response.toString();

        return result;
    }

}

/**
 * Hjelpeklasse for å returnere Recipe-objekter med tilhørede ID
 */
class IdRecipePair {
    private String id;
    private Recipe recipe;

    public Recipe getRecipe() {
        return recipe;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}