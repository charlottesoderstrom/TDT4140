//junit
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

//java
import java.util.HashMap;

//project
import sample.Recipe;

public class RecipeTest {
    @Test
    public void testRecipe() {
        HashMap<String, String> ingredients = new HashMap<>();
        ingredients.putIfAbsent("Egg", "3");
        ingredients.putIfAbsent("Salt", "a tablespoon");
        ingredients.putIfAbsent("Pepper", "a tablespoon");
        ingredients.putIfAbsent("Water", "100ml");
        ingredients.putIfAbsent("Sausage", "1");
        Recipe omelette = new Recipe("Omelette", "Crack eggs into bowl. Whisk everything. " +
                "Add seasoning to taste. Cook for a couple of minutes.", ingredients);
        assertEquals("Testing getTitle()", "Omelette", omelette.getTitle());
        assertEquals("Testing getInstructions()", "Crack eggs into bowl. Whisk everything. " +
                "Add seasoning to taste. Cook for a couple of minutes.", omelette.getInstructions());
    }
}
