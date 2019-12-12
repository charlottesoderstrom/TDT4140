package sample;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Client {

    public static void main(String[] args) throws IOException, UnknownHostException, ClassNotFoundException{
        // Setup connection
        Socket socket = new Socket("localhost", 4444);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

        // Test getRecipes() in DBConnector
        //objectOutputStream.writeObject("1");

        // Test getRecipesByName()
        //objectOutputStream.writeObject("2, Omelette");

        // Test getRecipesByIngredient()
        //objectOutputStream.writeObject("3, Pepper, Salt");

        // Test addRecipe()
        /*
        HashMap<String, String> ingredients = new HashMap<>();
        ingredients.putIfAbsent("Egg","3");
        ingredients.putIfAbsent("Salt","a tablespoon");
        ingredients.putIfAbsent("Pepper","a tablespoon");
        ingredients.putIfAbsent("Water","100ml");
        ingredients.putIfAbsent("Sausage","1");
        ingredients.putIfAbsent("Milk","2");
        ingredients.putIfAbsent("Sugar","4");
        ingredients.putIfAbsent("Sugar","4");
        Recipe newrecipe = new Recipe("Croissant", "Crack eggs into bowl. Whisk everything. " +
                "Add seasoning to taste. Cook for a couple of minutes. Use a lot of butter.", ingredients);
        objectOutputStream.writeObject(newrecipe);
        */


        // Read response
        ArrayList<Recipe> response = (ArrayList<Recipe>) objectInputStream.readObject();
        response.forEach(recipe -> recipe.printContent());
        ArrayList<User> responseUser = (ArrayList<User>) objectInputStream.readObject();
        responseUser.forEach(user -> user.printUser());


        // Close connection
        socket.close();
    }

}
