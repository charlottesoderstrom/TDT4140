package sample;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import jdk.jfr.StackTrace;

//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Arrays;
import java.util.*;


/**
 * Kontroller for siden hvor man oppretter nye oppskrifter. Oppretter nye Recipe-objketer og sender til backend.
 */

public class WriteRecipeController extends Controll{

    /**
     * Peker på kontrolleren for recipe.fxml.
     */
    private RecipeController recipeController;


    /**
     * Holder styr på alle ingrediensene en bruker har lagt inn i en oppskrift.
     */
    private HashMap<String, String> ingredientsList = new HashMap<>();


    /**
     * Holder styr på bruker-objektet som skriver en oppskrift.
     */
    private User user;


    @FXML
    private Button publishRecipe;
    @FXML
    private Button addIngredient;
    @FXML
    private TextArea ingredients; // Format: <String, String>
    @FXML
    private TextArea instructions;
    @FXML
    private TextArea addTitle;
    @FXML
    private ListView<String> ingredientsWindow;
    @FXML
    private Text warning;
    @FXML
    private Button deleteFromListViewBtn;




    public void setRecipeController(RecipeController controller) {
        this.recipeController = controller;
    }

    public void setUser(User user){
        this.user = user;
    }

    /**
    Tar inn en ingrediens (og hvor mye) fra bruker og lar klassen ta vare på det.
     */
    public void setAddIngredient(){
        try {
            List<String> inputList = new ArrayList<>(Arrays.asList(this.ingredients.getText().split(",")));
            this.ingredientsList.put(inputList.get(0), inputList.get(1));
           // System.out.println(this.ingredientsList);
        }
        catch (Exception e){
            e.printStackTrace();
            AlertBox.display("Warning!", "Ingredients must be added on the form: 'ingredient, amount'" );
        }
        this.ingredients.clear();
        this.updateIngredientsWindow();
    }


    /**
     * Passer på at ingrediens-vinduet i writeRecipe.fxml viser ingredienser som er lagt inn.
     */
    public void updateIngredientsWindow() {
        List windowList = new ArrayList<String>();
        ObservableList<String> observableWindowList = FXCollections.observableList(windowList);
        for (String key : this.ingredientsList.keySet()){
            windowList.add("" + key + ": " + this.ingredientsList.get(key));
        }
        this.ingredientsWindow.setItems(observableWindowList);
    }


    /**
     * Blir kalt når bruker trykker på "publish recipe"-knappen. Oppretter et Recipe-objekt og sender til databasen.
     * Passer på at oppskrift blir en anbefalt oppskrift om bruker er en "Chef".
     * Om man ikke har fyllt ut alle feltene vil man få et popup-vindu som sier at man må det før man kan publisere en oppskrift.
     */
    public void createRecipe(){
        String recipeTitle = this.addTitle.getText();
        String instructions = this.instructions.getText();
        HashMap<String, String> ingredients = this.ingredientsList;

        if(recipeTitle == "" || instructions == "" || this.ingredientsList.isEmpty()) {
            AlertBox.display("Warning", "Fill out all fields before submitting");
            return;
        }




        // Opprett nytt recipe-objekt, send objektet videre til backend
        Recipe recipe = new Recipe(recipeTitle, instructions, ingredients);
        if (user.getRole().equals("chef")){
            recipe = new Recipe(recipeTitle, instructions, ingredients, true);
        }
        Client.addRecipe(recipe);


        System.out.println(recipe.getIngredients());
        recipeController.updateRecipePage(recipe);

        // Gå tilbake til forsiden (mainPage.fxml).
        super.getStage().setScene(FXMLMain.recipePageScene);

        // Fjern all tekst fra alle felter hvor bruker har skrevet inn noe.
        this.addTitle.clear();
        this.ingredientsWindow.getItems().clear();
        this.ingredientsList.clear();
        this.instructions.clear();
    }

    /**
     * Tar bruker tilbake til forsiden (mainPage.fxml).
     */
    @FXML
    public void handleExitButton(){
        // Gå tilbake til forsiden (mainPage.fxml).
        super.getStage().setScene(FXMLMain.mainPageScene);
    }


    /**
     * Sletter markert ingrediens fra listview
     */
    public void deleteFromListView(){
        int index = ingredientsWindow.getSelectionModel().getSelectedIndex();
        if(index>=0){
            String ingredient = ingredientsWindow.getItems().toString();
            String key = ingredient.substring(1,ingredient.indexOf(":"));
            ingredientsWindow.getItems().remove(index);
            ingredientsList.remove(key);
        }
    }

}
