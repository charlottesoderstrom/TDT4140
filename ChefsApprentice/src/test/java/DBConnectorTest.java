//junit
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.instanceOf;
//java
import java.util.ArrayList;

//project
import sample.DBConnector;
import sample.Recipe;
import sample.User;


public class DBConnectorTest {
    Recipe testRecipe = Recipe.makeTestRecipe();
    User testUser = new User("testUser", "admin");

    @Test
    public void printTest(){
        try{
            DBConnector.addRecipe(testRecipe);
            DBConnector.printRecipes();
            DBConnector.printRecipesByIngredient("Flour");
            DBConnector.printRecipesByName(testRecipe.getTitle());
            DBConnector.deleteRecipe(testRecipe);
        }catch(Exception e){
            fail("Printing threw exception!");
        }
    }

    @Test
    public void getRecipesTest(){
        ArrayList<Recipe> recipes = DBConnector.getRecipes();
        recipes.forEach(recipe -> assertThat(recipe, instanceOf(Recipe.class)));
    }

    @Test
    public void addRecipeTest(){
        try{
            DBConnector.addRecipe(testRecipe);
            DBConnector.deleteRecipe(testRecipe);
        }catch (Exception e){
            fail("AddRecipe threw an exception!");
        }
    }

    @Test
    public void deleteRecipeTest(){
        try{
            DBConnector.addRecipe(testRecipe);
            DBConnector.deleteRecipe(testRecipe.getTitle());
        }catch(Exception e){
            fail("DeleteRecipe threw an exception!");
        }
    }

    @Test
    public void addUserTest(){
        try{
            DBConnector.addUser(testUser);
        } catch(Exception e){
            fail("AddUser threw an Exception!");
        }
        User result = DBConnector.getUser("testUser");
        assertEquals(testUser.getUsername(), result.getUsername());
        assertEquals(testUser.getRole(), result.getRole());
        assertEquals(testUser.getRole(), DBConnector.getRoleByUserName("testUser"));
        assertTrue(DBConnector.doesUserExist(testUser));
        assertTrue(DBConnector.doesUserExist("testUser"));
    }

    @Test
    public void getUsersTest(){
        try{
            ArrayList<User> users = DBConnector.getUsers();
            users.forEach(user -> assertThat(user, instanceOf(User.class)));
        }catch(Exception e){
            fail("Threw exception!");
        }
    }

    @Test
    public void getRecipeByIDandIDByNameTest(){
        try{
            int testID = 1;
            Recipe idRecipe = DBConnector.getRecipeByID(testID);
            assertEquals(testID, DBConnector.getRecipeID(idRecipe.getTitle()));
        } catch (Exception e){
            fail("Threw exception!");
        }
    }

    @Test
    public void getRecipesByNameTest(){
        DBConnector.addRecipe(testRecipe);
        ArrayList<Recipe> recipes = DBConnector.getRecipesByName(testRecipe.getTitle());
        DBConnector.deleteRecipe(testRecipe);
        recipes.forEach(recipe -> assertThat(recipe, instanceOf(Recipe.class)));
    }

    @Test
    public void getRecipesByIngredientsTest(){
        ArrayList<String> ingredients = new ArrayList<>();
        ingredients.add("Flour");
        ingredients.add("Sugar");
        ArrayList<Recipe> result = DBConnector.getRecipesByIngredient("Flour");
        result.forEach(recipe -> assertThat(recipe, instanceOf(Recipe.class)));
        result = DBConnector.getRecipesByIngredient(ingredients);
        result.forEach(recipe -> assertThat(recipe, instanceOf(Recipe.class)));
    }

    @Test
    public void getVerifiedRecipesandVerifiedSearchTest(){
        DBConnector.addRecipe(testRecipe);
        ArrayList<Recipe> result = DBConnector.getVerifiedRecipes();
        result.forEach(recipe -> assertTrue(recipe.isVerified()));
        ArrayList<String> ingredients = new ArrayList<>();
        ingredients.add("Flour");
        result = DBConnector.getVerifiedRecipesByIngredient(ingredients);
        result.forEach(recipe -> assertTrue(recipe.isVerified()));
        DBConnector.deleteRecipe(testRecipe);
    }
}
