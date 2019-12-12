package sample;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Client {

    public static String ip = "localhost";

    public Client() {
        this.ip = "localhost";
    }

    public Client(String ip) {
        this.ip = ip;
    }

    public static ArrayList<Recipe> getRecipes() {
        ArrayList<Recipe> response = new ArrayList<>();
        // Setup connection
        try {
            Socket socket = new Socket(Client.ip, 4444);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            // Test getRecipes() in DBConnector
            objectOutputStream.writeObject("1");
            // Read response
            response = (ArrayList<Recipe>) objectInputStream.readObject();
            //response.forEach(recipe -> recipe.printContent());
            socket.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public static ArrayList<Recipe> getVerifiedRecipes() {
        ArrayList<Recipe> response = new ArrayList<>();
        // Setup connection
        try {
            Socket socket = new Socket(Client.ip, 4444);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            // Test getRecipes() in DBConnector
            objectOutputStream.writeObject("5");
            // Read response
            response = (ArrayList<Recipe>) objectInputStream.readObject();
            //response.forEach(recipe -> recipe.printContent());
            socket.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public static void addRecipe(Recipe recipe) {
        // Setup connection
        try {
            Socket socket = new Socket(Client.ip, 4444);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            // Test getRecipes() in DBConnector
            objectOutputStream.writeObject(recipe);
            // Read response
            socket.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteRecipe(String title) {
        // Setup connection
        String response = "";
        try {
            Socket socket = new Socket(Client.ip, 4444);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            // Test getRecipes() in DBConnector
            objectOutputStream.writeObject("0," + title);
            response = (String) objectInputStream.readObject();
            socket.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        if (response.equals("true")) {

            return true;
        }
        return false;
    }

    public static ArrayList<Recipe> getRecipesByName(String name) {
        ArrayList<Recipe> response = new ArrayList<>();
        // Setup connection
        try {
            Socket socket = new Socket(Client.ip, 4444);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            // Test getRecipes() in DBConnector
            objectOutputStream.writeObject("2, " + name);
            // Read response
            response = (ArrayList<Recipe>) objectInputStream.readObject();
            //response.forEach(recipe -> recipe.printContent());
            socket.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return response;// Setup connection


    }

    public static ArrayList<Recipe> getRecipeByIngredients(String search) {
        ArrayList<Recipe> response = new ArrayList<>();
        // Setup connection
        try {
            Socket socket = new Socket(Client.ip, 4444);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            // Test getRecipes() in DBConnector
            objectOutputStream.writeObject("3, " + search);
            // Read response
            response = (ArrayList<Recipe>) objectInputStream.readObject();
            //response.forEach(recipe -> recipe.printContent());
            socket.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public static ArrayList<Recipe> getVerifiedRecipesByIngredients(String search) {
        ArrayList<Recipe> verifiedResponse = new ArrayList<>();
        try {
            ArrayList<Recipe> response = getRecipeByIngredients(search);
            for (Recipe recipe : response) {
                if (recipe.isVerified()) {
                    verifiedResponse.add(recipe);
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return verifiedResponse;
    }

    public static User getUser(String username) {
        User responseUser = null;
        // Setup connection
        try {
            Socket socket = new Socket(Client.ip, 4444);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            // Test getRecipes() in DBConnector
            objectOutputStream.writeObject("4, " + username);
            // Read response
            responseUser = (User) objectInputStream.readObject();
            //responseUser.forEach(user -> user.printUser());
            socket.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return responseUser;
    }

    public static boolean registerUser(User user){
        String response = "";
        // Setup connection
        try {
            Socket socket = new Socket(Client.ip, 4444);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            // Test getRecipes() in DBConnector
            objectOutputStream.writeObject(user);
            // Read response
            response = (String) objectInputStream.readObject();
            socket.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        if (response.equals("true")){
            return true;
        }
        return false;

    }

    public static void addManyRecipes(int numberOfRecipes) {
        // Setup connection
        try {
            Socket socket = new Socket(Client.ip, 4444);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream.writeObject(numberOfRecipes);
            socket.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }



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
        sample.Recipe newrecipe = new sample.Recipe("Croissant", "Crack eggs into bowl. Whisk everything. " +
                "Add seasoning to taste. Cook for a couple of minutes. Use a lot of butter.", ingredients);
        objectOutputStream.writeObject(newrecipe);
        */


    public static void main(String[] args) throws IOException, UnknownHostException, ClassNotFoundException{
        getVerifiedRecipes();

    }
}
