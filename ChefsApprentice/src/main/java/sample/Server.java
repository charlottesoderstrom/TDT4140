package sample;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Server {

    public static final int PORT = 4444;

    public void runServer() throws IOException, ClassNotFoundException{
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server running...");
        Socket socket = serverSocket.accept();
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());


        Object request = objectInputStream.readObject();
        if (request instanceof Recipe) {
            DBConnector.addRecipe((Recipe) request);
            System.out.println("Added recipe");
        } else {
            if (request instanceof String) {
                ArrayList<Recipe> response = new ArrayList<>();
                String[] result = String.valueOf(request).split(",");
                if (result[0].equals("1")) {
                    response = DBConnector.getRecipes();
                } else if (result[0].equals("2")){
                    response = DBConnector.getRecipesByName(result[1].trim());
                } else if (result[0].equals("3")){
                    //response = DBConnector.getRecipesByIngredient(result[1].trim());
                    ArrayList<String> ingredientList = new ArrayList<String>(Arrays.asList(result));
                    ingredientList.remove(0);
                    for (int i = 0; i < ingredientList.size(); i++) {
                        ingredientList.set(i, ingredientList.get(i).trim());
                    }
                    response = DBConnector.getRecipesByIngredient(ingredientList);
                }
                objectOutputStream.writeObject(response);
            }
        }
        socket.close();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Server s = new Server();
        s.runServer();
    }
}
