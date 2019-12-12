import org.junit.Test;
import sample.Serializer;
import sample.Recipe;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;

//junit
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SerializerTest {
    @Test
    public void testSerializer(){
        ArrayList<Recipe> recipes = Serializer.getManyRecipes(10);
        recipes.forEach(recipe -> assertThat(recipe, instanceOf(Recipe.class)));
        assertEquals(10, recipes.size());
    }
}
