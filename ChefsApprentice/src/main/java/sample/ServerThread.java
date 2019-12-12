package sample;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class ServerThread extends Thread{
    protected Socket socket;

    public ServerThread(Socket clientSocket){
        this.socket = clientSocket;
    }

    public void run() {
        ObjectInputStream objectInputStream = null;
        ObjectOutputStream objectOutputStream = null;

        try {
        objectInputStream = new ObjectInputStream(socket.getInputStream());
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

        } catch (IOException e){
            e.printStackTrace();
            return;
        }

        try {
        Object request = objectInputStream.readObject();
        if (request instanceof Recipe) {
            DBConnector.addRecipe((Recipe) request);
            System.out.println("Added recipe");
        } else if (request instanceof User) {
            boolean registeredUser = DBConnector.addUser((User) request);
            String response;
            if (registeredUser) {
                response = "true";
                System.out.println("Added user");
            } else {
                response = "false";
            }
            objectOutputStream.writeObject(response);
        } else if (request instanceof Integer) {
            ArrayList<Recipe> recipes = Serializer.getManyRecipes((Integer) request);
            recipes.forEach(recipe -> DBConnector.addRecipe(recipe));
        } else if (request instanceof String) {
                String[] result = String.valueOf(request).split(",");
                if(result[0].equals("0")) {
                    boolean deletedRecipe = DBConnector.deleteRecipe(result[1]);
                    String response;
                    if (deletedRecipe) {
                        response = "true";
                    } else {
                        response = "false";
                    }
                    objectOutputStream.writeObject(response);
                }
                else if (result[0].equals("1")) {
                    ArrayList<Recipe> response = new ArrayList<>();
                    response = DBConnector.getRecipes();
                    objectOutputStream.writeObject(response);
                } else if (result[0].equals("2")){
                    ArrayList<Recipe> response = new ArrayList<>();
                    response = DBConnector.getRecipesByName(result[1].trim());
                    objectOutputStream.writeObject(response);
                } else if (result[0].equals("3")){
                    ArrayList<Recipe> response = new ArrayList<>();
                    //response = sample.DBConnector.getRecipesByIngredient(result[1].trim());
                    ArrayList<String> ingredientList = new ArrayList<String>(Arrays.asList(result));
                    ingredientList.remove(0);
                    for (int i = 0; i < ingredientList.size(); i++) {
                        ingredientList.set(i, ingredientList.get(i).trim());
                    }
                    response = DBConnector.getRecipesByIngredient(ingredientList);
                    objectOutputStream.writeObject(response);
                } else if (result[0].equals("bye")){
                    System.exit(0);
                } else if  (result[0].equals("4")) {
                    User responseUser = null;
                    responseUser = DBConnector.getUser(result[1].trim());
                    objectOutputStream.writeObject(responseUser);
                } else if (result[0].equals("5")) {
                    ArrayList<Recipe> response = new ArrayList<>();
                    response = DBConnector.getVerifiedRecipes();
                    objectOutputStream.writeObject(response);
                }
            }

            socket.close();

        } catch (ClassNotFoundException ne) {
            ne.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }
}

