package sample;

import com.sun.tools.javac.Main;
import javafx.scene.control.TextArea;
import javafx.scene.text.TextFlow;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


/**
 * Denne klassen kontrollerer siden som viser en oppskrift.
 */

public class RecipeController extends Controll {

   private MainPageController mainPageController;

   @FXML
    private Text title;
   @FXML
    private TextArea instructions;
   @FXML
    private TextArea ingredients;
   @FXML
   private Button deleteButton;
   @FXML
    private Button exitButton;

    /**
     * Setter kontroller for mainPage.fxml.
     * @param controller mainPageController.
     */
   public void setMainPageController(MainPageController controller){
       this.mainPageController = controller;
   }

    /**
     * Initaliserer siden med all informasjon som trengs for at en bruker skal kunne lese oppskfiten:
     * Tittel, ingredienser og fremgangsmåte.
     * @param recipe oppskriften som skal leses.
     */
    public void updateRecipePage(Recipe recipe) {
        this.title.setText(recipe.getTitle());
        instructions.setWrapText(true);
        this.instructions.setText(recipe.getInstructions());

        HashMap<String,String> ingredientsList = recipe.getIngredients();
        List<String> ingredients = new ArrayList<String>(recipe.getIngredients().keySet());
        List<String> quantity = new ArrayList<String>(recipe.getIngredients().values());
        this.ingredients.clear();
        for(int i = 0;  i < ingredientsList.size(); i++) {
            this.ingredients.appendText(ingredients.get(i) + " - " + quantity.get(i) +"\n");
        }
    }

    public void hideDeleteButton(){
        this.deleteButton.setVisible(false);
    }

    public void showDeleteButton(){
        this.deleteButton.setVisible(true);
    }


    /**
     * Sletter oppskriften som vises når admin trykker på slett-knappen.
     */
    @FXML
    public void handleDeleteButton(){
        if (Client.deleteRecipe(this.title.getText())){
            this.mainPageController.handleReload();
            super.getStage().setScene(FXMLMain.mainPageScene);
        }
    }


    /**
     * Sender bruker tilbake til forsiden (mainPage.fxml).
     */
    @FXML
    public void handleExitButton() {
        super.getStage().setScene(FXMLMain.mainPageScene);
    }


}
