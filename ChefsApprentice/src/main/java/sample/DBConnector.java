package sample;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

public class DBConnector {
    private static final String dbUrl = "jdbc:mysql://mysql.stud.ntnu.no/kaareob_recipes"; //TODO: Få Bort disse!!
    private static final String USERNAME = "kaareob_79";
    private static final String password = "gruppe79";
    private static final String driver = "com.mysql.cj.jdbc.Driver";


    public static void printRecipes(){
        try{
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(dbUrl, USERNAME, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Recipes");
            while(resultSet.next()){
                System.out.println("RecipeNr: " + resultSet.getInt(1) + "\nTitle: " +
                        resultSet.getString(2) + "\nInstructions: " + resultSet.getString(3));
                System.out.println("\n ----------------------------------------\n");
            }
            connection.close();
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public static ArrayList<Recipe> getRecipes(){
        ArrayList<Recipe> recipes = new ArrayList<>();
        try{
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(dbUrl, USERNAME, password);
            Statement statement = connection.createStatement();
            String query = "SELECT RecipeID, Title, Instructions, IngredientName, Quantity " +
                    "FROM (Recipes Natural Join IngredientsInRecipes) Natural Join Ingredients ";
            ResultSet resultSet = statement.executeQuery(query);
            System.out.println("Getting all recipes...");
            if(resultSet.first()){
                while(!resultSet.isAfterLast()){
                    HashMap<String, String> ingredients = new HashMap<>();
                    int recipeID = resultSet.getInt(1);
                    String title = resultSet.getString(2);
                    String instructions = resultSet.getString(3);
                    while (!resultSet.isAfterLast() && (resultSet.getInt(1) == recipeID)){
                        String ingredient = resultSet.getString(4);
                        String quantity = resultSet.getString(5);
                        ingredients.put(ingredient, quantity);
                        resultSet.next();
                    }
                    recipes.add(new Recipe(title, instructions, ingredients));
                }
            } else {
                System.out.println("The search returned no results...");
            }
            connection.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return recipes;
    }

    public static ArrayList<Recipe> getVerifiedRecipes(){
        ArrayList<Recipe> recipes = new ArrayList<>();
        try{
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(dbUrl, USERNAME, password);
            Statement statement = connection.createStatement();
            String query = "SELECT RecipeID, Title, Instructions, Verified, IngredientName, Quantity " +
                    "FROM (Recipes Natural Join IngredientsInRecipes) Natural Join Ingredients " +
                    "WHERE Verified = '1'";
            ResultSet resultSet = statement.executeQuery(query);
            System.out.println("Getting all verified recipes...");
            if(resultSet.first()){
                while(!resultSet.isAfterLast()){
                    HashMap<String, String> ingredients = new HashMap<>();
                    int recipeID = resultSet.getInt(1);
                    String title = resultSet.getString(2);
                    String instructions = resultSet.getString(3);
                    while (!resultSet.isAfterLast() && (resultSet.getInt(1) == recipeID)){
                        String ingredient = resultSet.getString(5);
                        String quantity = resultSet.getString(6);
                        ingredients.put(ingredient, quantity);
                        resultSet.next();
                    }
                    recipes.add(new Recipe(title, instructions, ingredients, true));
                }
            } else {
                System.out.println("The search returned no results...");
            }
            connection.close();
        } catch (Exception e){
            System.out.println(e);
        }
        return recipes;
    }

    public static void printRecipesByIngredient(String ingredient){
        try{
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(dbUrl, USERNAME, password);
            Statement statement = connection.createStatement();
            String query = "SELECT Title, Instructions, IngredientName " +
                    "FROM (Recipes Natural Join IngredientsInRecipes) Natural Join Ingredients " +
                    "WHERE Ingredients.IngredientName = " + "'" + ingredient + "'";
            ResultSet resultSet = statement.executeQuery(query);
            System.out.println("Your search was on ingredient: " + ingredient);
            while(resultSet.next()){
                System.out.println("Recipe: " + resultSet.getString(1) + "\nInstructions: " +
                        resultSet.getString(2) + "\nIngredient Search Match: " + resultSet.getString(3));
                System.out.println("\n ----------------------------------------\n");
            }
            connection.close();
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public static ArrayList<Recipe> getRecipesByIngredient(String ingredient){
        ArrayList<Integer> ids = new ArrayList<>();
        try{
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(dbUrl, USERNAME, password);
            Statement statement = connection.createStatement();
            String query = "SELECT RecipeID " +
                    "FROM (Recipes Natural Join IngredientsInRecipes) Natural Join Ingredients " +
                    "WHERE Ingredients.IngredientName = " + "'" + ingredient + "'";
            ResultSet resultSet = statement.executeQuery(query);
            System.out.println("Your search was on ingredient: " + ingredient);
            while(resultSet.next()){
                ids.add(resultSet.getInt(1));
            }
            connection.close();
        } catch (Exception e){
            System.out.println(e);
        }
        ArrayList<Recipe> recipes = new ArrayList<>();
        ids.forEach(id -> recipes.add(getRecipeByID(id)));
        return recipes;
    }

    public static ArrayList<Recipe> getRecipesByIngredient(ArrayList<String> ingredients){
        ArrayList<Integer> ids = new ArrayList<>();
        try{
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(dbUrl, USERNAME, password);
            String query = "SELECT RecipeID " +
                    "FROM (Recipes Natural Join IngredientsInRecipes) Natural Join Ingredients " +
                    "WHERE Ingredients.IngredientName = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            for (String ingredient : ingredients) {
                preparedStatement.setString(1, ingredient);
                ResultSet resultSet = preparedStatement.executeQuery();
                while(resultSet.next()){
                    ids.add(resultSet.getInt(1));
                }
            }
            connection.close();
        } catch (Exception e){
            System.out.println(e);
        }
        ArrayList<Recipe> recipes = new ArrayList<>();
        Map<Integer, Long> counts = ids.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        counts.forEach((id, count ) -> {
            System.out.println("ID: " + id + " - Occurences: " + count);
            if (count == ingredients.size()) {
                recipes.add(getRecipeByID(id));
            }
        });
        System.out.println("Your search was on ingredients: ");
        ingredients.forEach(ingredient -> System.out.println(ingredient));
        if (recipes.size() > 0){
            System.out.println("Search successful!");
        } else {
            System.out.println("No search results found containing all ingredients!");
        }

        return recipes;
    }

    public static ArrayList<Recipe> getVerifiedRecipesByIngredient(ArrayList<String> ingredients){
        ArrayList<Integer> ids = new ArrayList<>();
        try{
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(dbUrl, USERNAME, password);
            String query = "SELECT RecipeID " +
                    "FROM (Recipes Natural Join IngredientsInRecipes) Natural Join Ingredients " +
                    "WHERE Ingredients.IngredientName = ?" +
                    "AND Recipes.Verified = 1";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            for (String ingredient : ingredients) {
                preparedStatement.setString(1, ingredient);
                ResultSet resultSet = preparedStatement.executeQuery();
                while(resultSet.next()){
                    ids.add(resultSet.getInt(1));
                }
            }
            connection.close();
        } catch (Exception e){
            System.out.println(e);
        }
        ArrayList<Recipe> recipes = new ArrayList<>();
        Map<Integer, Long> counts = ids.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        counts.forEach((id, count ) -> {
            System.out.println("ID: " + id + " - Occurences: " + count);
            if (count == ingredients.size()) {
                recipes.add(getRecipeByID(id));
            }
        });
        System.out.println("Your search was on ingredients: ");
        ingredients.forEach(ingredient -> System.out.println(ingredient));
        if (recipes.size() > 0){
            System.out.println("Search successful!");
        } else {
            System.out.println("No search results found containing all ingredients!");
        }

        return recipes;
    }

    public static void printRecipesByName(String recipeName){
        try{
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(dbUrl, USERNAME, password);
            Statement statement = connection.createStatement();
            String query = "SELECT RecipeID, Title, Instructions, IngredientName, Quantity " +
                    "FROM (Recipes Natural Join IngredientsInRecipes) Natural Join Ingredients " +
                    "WHERE Recipes.Title = " + "'" + recipeName + "'";
            ResultSet resultSet = statement.executeQuery(query);
            System.out.println("Your search was on the keyword: " + recipeName);
            if(resultSet.first()){
                while(!resultSet.isAfterLast()){
                    int recipeID = resultSet.getInt(1);
                    String title = resultSet.getString(2);
                    String instructions = resultSet.getString(3);
                    ArrayList<String> ingredients = new ArrayList<>();
                    ArrayList<String> quantities = new ArrayList<>();
                    System.out.println("RecipeID: " + recipeID);
                    System.out.println("Title: " + title + "\nInstructions: " + instructions);
                    System.out.println("Ingredients and quantities:");
                    while (!resultSet.isAfterLast() && (resultSet.getInt(1) == recipeID)){
                        String ingredient = resultSet.getString(4);
                        ingredients.add(ingredient);
                        String quantity = resultSet.getString(5);
                        quantities.add(quantity);
                        System.out.println(ingredient + " - " + quantity);
                        resultSet.next();
                    }
                }
            } else {
                System.out.println("The search returned no results...");
            }
            connection.close();
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public static Recipe getRecipeByID(int recipeID){
        Recipe recipe = new Recipe("Not Found", "Not Found", new HashMap<String, String>());
        try{
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(dbUrl, USERNAME, password);
            Statement statement = connection.createStatement();
            String query = "SELECT RecipeID, Title, Instructions, IngredientName, Quantity, Verified " +
                    "FROM (Recipes Natural Join IngredientsInRecipes) Natural Join Ingredients " +
                    "WHERE Recipes.RecipeID = " + recipeID;
            ResultSet resultSet = statement.executeQuery(query);
            System.out.println("Your search was on the recipeID: " + recipeID);
            HashMap<String, String> ingredients = new HashMap<>();
            resultSet.first();
            int recipeIDresult = resultSet.getInt(1);
            String title = resultSet.getString(2);
            String instructions = resultSet.getString(3);
            int verifiedInt = resultSet.getInt(6);
            boolean verified = false;
            if(verifiedInt == 1) {
                verified = true;
            }
            while (!resultSet.isAfterLast() && (resultSet.getInt(1) == recipeIDresult)){
                String ingredient = resultSet.getString(4);
                String quantity = resultSet.getString(5);
                ingredients.put(ingredient, quantity);
                resultSet.next();
            }
            recipe = new Recipe(title, instructions, ingredients, verified);
            connection.close();
        } catch (Exception e){
            System.out.println(e);
        }
        return recipe;
    }

    public static ArrayList<Recipe> getRecipesByName(String recipeName){
        ArrayList<Recipe> recipes = new ArrayList<>();
        try{
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(dbUrl, USERNAME, password);
            Statement statement = connection.createStatement();
            String query = "SELECT RecipeID, Title, Instructions, IngredientName, Quantity " +
                    "FROM (Recipes Natural Join IngredientsInRecipes) Natural Join Ingredients " +
                    "WHERE Recipes.Title = " + "'" + recipeName + "'";
            ResultSet resultSet = statement.executeQuery(query);
            System.out.println("Your search was on the keyword: " + recipeName);
            if(resultSet.first()){
                while(!resultSet.isAfterLast()){
                    HashMap<String, String> ingredients = new HashMap<>();
                    int recipeID = resultSet.getInt(1);
                    String title = resultSet.getString(2);
                    String instructions = resultSet.getString(3);
                    while (!resultSet.isAfterLast() && (resultSet.getInt(1) == recipeID)){
                        String ingredient = resultSet.getString(4);
                        String quantity = resultSet.getString(5);
                        ingredients.put(ingredient, quantity);
                        resultSet.next();
                    }
                    recipes.add(new Recipe(title, instructions, ingredients));
                }
            } else {
                System.out.println("The search returned no results...");
            }
            connection.close();
        } catch (Exception e){
            System.out.println(e);
        }
        return recipes;
    }

    public static void addRecipe(Recipe recipe){
        try{
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(dbUrl, USERNAME, password);

            // the mysql insert statements
            // First one is for Recipes
            String recipesQuery = " INSERT INTO Recipes (title, instructions, Verified)"
                                + " VALUES (?, ?, ?)";
            // Second one is for Ingredients
            String ingredientsQuery = "INSERT INTO Ingredients VALUES (?)";
            // Third one is for mapping between Recipes and Ingredients.
            String ingredientsInRecipesQuery = "INSERT INTO IngredientsInRecipes VALUES(?, ?, ?)";

            // create the mysql insert preparedstatement
            PreparedStatement statement = connection.prepareStatement(recipesQuery);
            statement.setString(1, recipe.getTitle());
            statement.setString(2, recipe.getInstructions());
            if(recipe.isVerified()){
                statement.setInt(3, 1);
            } else {
                statement.setInt(3, 0);
            }

            // execute the preparedstatement
            System.out.println("Inserting following items into recipes:\n" + "title: "
                    + recipe.getTitle() + "\nInstructions: " + recipe.getInstructions());
            statement.execute();

            // Getting recipeDbId
            Statement idStatement = connection.createStatement();
            String idQuery = "SELECT RecipeID " +
                            "FROM Recipes " +
                            "WHERE Recipes.Title = " + "'" + recipe.getTitle() + "'";
            ResultSet resultSet = idStatement.executeQuery(idQuery);
            resultSet.next();
            int recipeDbId = resultSet.getInt(1);
            System.out.println("RecipeID: " + recipeDbId);


            // creating statements regarding ingredient mapping
            PreparedStatement ingrStatement = connection.prepareStatement(ingredientsQuery);
            PreparedStatement ingrToRecStatement = connection.prepareStatement(ingredientsInRecipesQuery);

            // Iterating through ingredient - quantity pairs and executing INSERT queries.
            HashMap<String, String> ingredients = recipe.getIngredients();
            Iterator<Map.Entry<String, String>> iterator = ingredients.entrySet().iterator();
            while(iterator.hasNext()) {
                Map.Entry<String, String> pair = iterator.next();
                System.out.println(pair.getKey() + " = " + pair.getValue());
                // Updating statements
                try{
                    //These need to be in their own try-block in case ingredient already exists in DB
                    System.out.println("Trying to add ingredient: " + pair.getKey());
                    ingrStatement.setString(1, pair.getKey());
                    ingrStatement.execute();
                } catch (SQLIntegrityConstraintViolationException e) {
                    System.out.println(e);
                }
                ingrToRecStatement.setInt(1, recipeDbId);
                ingrToRecStatement.setString(2, pair.getKey());
                ingrToRecStatement.setString(3, pair.getValue());
                System.out.println("Adding new ingredientconnection: " + pair.getKey() + pair.getValue() + "ID: " + recipeDbId);
                // Executing statements
                ingrToRecStatement.execute();
            }
            connection.close();
            System.out.println("Insert successful!\n__________________________________");
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public static int getRecipeID(Recipe recipe) {
        int recipeID = -1;
        try{
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(dbUrl, USERNAME, password);
            String recipeQuery = "SELECT RecipeID FROM Recipes WHERE Title = ?";
            PreparedStatement idStatement = connection.prepareStatement(recipeQuery);
            idStatement.setString(1, recipe.getTitle());
            ResultSet resultSet = idStatement.executeQuery();
            resultSet.next();
            if(!resultSet.isAfterLast()) {
            recipeID = resultSet.getInt(1);
            }
            connection.close();
        } catch (Exception e){
            System.out.println(e);
        }
        return recipeID;
    }

    public static void deleteRecipe(Recipe recipe){
        int recipeID = getRecipeID(recipe);
        if(recipeID > -1){
            try{
                Class.forName(driver);
                Connection connection = DriverManager.getConnection(dbUrl, USERNAME, password);
                //Deleting from IngredientsInRecipes
                String ingrInRecQuery = "DELETE FROM IngredientsInRecipes WHERE RecipeID = ?";
                PreparedStatement ingrInRecStmt = connection.prepareStatement(ingrInRecQuery);
                ingrInRecStmt.setInt(1, recipeID);
                ingrInRecStmt.executeUpdate();

                //Deleting from Recipes
                String recipeQuery = "DELETE FROM Recipes WHERE RecipeID = ?";
                PreparedStatement recipeStmt = connection.prepareStatement(recipeQuery);
                recipeStmt.setInt(1, recipeID);
                recipeStmt.executeUpdate();


                connection.close();
                System.out.println("Deletion successful!\n__________________________________");
            } catch (Exception e){
                System.out.println(e);
            }
        } else{
            System.out.println("Recipe not in database.");
        }
    }

    public static int getRecipeID(String title) {
        int recipeID = -1;
        try{
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(dbUrl, USERNAME, password);
            String recipeQuery = "SELECT RecipeID FROM Recipes WHERE Title = ?";
            PreparedStatement idStatement = connection.prepareStatement(recipeQuery);
            idStatement.setString(1, title);
            ResultSet resultSet = idStatement.executeQuery();
            resultSet.next();
            if(!resultSet.isAfterLast()) {
                recipeID = resultSet.getInt(1);
            }
            connection.close();
        } catch (Exception e){
            System.out.println(e);
        }
        return recipeID;
    }

    public static boolean deleteRecipe(String title){
        int recipeID = getRecipeID(title);
        if(recipeID > -1){
            try{
                Class.forName(driver);
                Connection connection = DriverManager.getConnection(dbUrl, USERNAME, password);
                //Deleting from IngredientsInRecipes
                String ingrInRecQuery = "DELETE FROM IngredientsInRecipes WHERE RecipeID = ?";
                PreparedStatement ingrInRecStmt = connection.prepareStatement(ingrInRecQuery);
                ingrInRecStmt.setInt(1, recipeID);
                ingrInRecStmt.executeUpdate();

                //Deleting from Recipes
                String recipeQuery = "DELETE FROM Recipes WHERE RecipeID = ?";
                PreparedStatement recipeStmt = connection.prepareStatement(recipeQuery);
                recipeStmt.setInt(1, recipeID);
                recipeStmt.executeUpdate();


                connection.close();
                System.out.println("Deletion successful!\n__________________________________");
                return true;
            } catch (Exception e){
                System.out.println(e);
            }
        } else{
            System.out.println("Recipe not in database.");
        }
        return false;
    }

    public static boolean addUser(User user){
        if(doesUserExist(user)) {
            System.out.println("User: " + user.getUsername() + " already exists..");
            return false;
        } else {
            try{
                Class.forName(driver);
                Connection connection = DriverManager.getConnection(dbUrl, USERNAME, password);

                // the mysql insert statement
                String query = "INSERT INTO Users (UserName, Role)"
                        + " VALUES (?, ?)";

                // create the mysql insert PreparedStatement
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, user.getUsername());
                statement.setString(2, user.getRole());

                // execute the PreparedStatement
                System.out.println("Inserting following items into users:\n" + "Username: "
                        + user.getUsername() + "\nRole: " + user.getRole());
                statement.execute();

                connection.close();
            } catch (Exception e){
                System.out.println(e);
            }
            return true;
        }
    }

    public static ArrayList<User> getUsers(){
        ArrayList<User> users = new ArrayList<>();
        try{
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(dbUrl, USERNAME, password);
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM Users";
            ResultSet resultSet = statement.executeQuery(query);
            System.out.println("Getting all users...");
            if(resultSet.first()){
                while(!resultSet.isAfterLast()){
                    String username = resultSet.getString(2);
                    String role = resultSet.getString(3);
                    users.add(new User(username, role));
                    resultSet.next();
                }
            } else {
                System.out.println("The search returned no results...");
            }
            connection.close();
        } catch (Exception e){
            System.out.println(e);
        }
        return users;
    }

    public static User getUser(String username) {
        User user = null;
        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(dbUrl, USERNAME, password);
            String query = "Select * FROM Users WHERE UserName = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.first()){
                String name = resultSet.getString(2);
                String role = resultSet.getString(3);
                user = new User(name, role);
            } else {
                System.out.println("The search returned no results...");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public static String getRoleByUserName(String name) {
        User user = getUser(name);
        return user.getRole();
    }

    public static boolean doesUserExist(String username) {
        User user = getUser(username);
        System.out.println("Searching for user: " + username);
        if(user == null){
            return false;
        } else {
            return true;
        }
    }

    public static boolean doesUserExist(User user) {
        User userresult = getUser(user.getUsername());
        System.out.println("Searching for user: " + user.getUsername());
        if(userresult == null){
            return false;
        } else {
            return true;
        }
    }

    public static void main(String[] args) {

        /*
        ArrayList<User> users = getUsers();
        users.forEach(user -> user.printUser());
        User user = getUser("admin");
        user.printUser();
        addUser("Bob", "Chef");
        System.out.println(doesUserExist("admin"));
        System.out.println(doesUserExist("Alice"));
        System.out.println(doesUserExist("Bob"));


        //Recipe boller = Recipe.makeTestRecipe();
        //addRecipe(boller);
        //deleteRecipe(boller);

        ArrayList<Recipe> recipes;
        recipes = getRecipes();
        recipes.forEach(recipe -> recipe.printContent());
        System.out.println("VERIFIED:");
        recipes = getVerifiedRecipes();
        recipes.forEach(recipe -> recipe.printContent());
        */
    }

}
