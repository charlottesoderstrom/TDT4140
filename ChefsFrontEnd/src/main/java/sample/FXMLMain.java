package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;

/**
 * Denne klassen blir kalt av Main og oppretter alle objekter som trengs i Front-end.
 */
public class FXMLMain extends Application{

    public static Scene mainPageScene;
    public static Scene recipePageScene;
    public static Scene logInPageScene;
    public static Scene signUpPage;
    public static Scene writeRecipePage;


    /**
     * Oppretter og initaliserer alle objekter som trengs i frontend. Passer på at alle objketer får satt
     * referanser til andre objekter.
     * @param primaryStage vinduet for applikasjonen.
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader mainPageLoader = new FXMLLoader(getClass().getClassLoader().getResource("mainPage.fxml"));
        Parent mainPageRoot = mainPageLoader.load();

        FXMLLoader recipePageLoader = new FXMLLoader(getClass().getClassLoader().getResource("recipe.fxml"));
        Parent recipePageRoot = recipePageLoader.load();

        FXMLLoader logInPageLoader = new FXMLLoader(getClass().getClassLoader().getResource("Login.fxml"));
        Parent logInPageRoot = logInPageLoader.load();

        FXMLLoader signUpPageLoader = new FXMLLoader(getClass().getClassLoader().getResource("signup.fxml"));
        Parent signUpPageRoot = signUpPageLoader.load();

        FXMLLoader writeRecipePageLoader = new FXMLLoader(getClass().getClassLoader().getResource("writeRecipe.fxml"));
        Parent writeRecipeRoot = writeRecipePageLoader.load();


        this.mainPageScene = new Scene(mainPageRoot);
        this.recipePageScene = new Scene(recipePageRoot);
        this.logInPageScene = new Scene(logInPageRoot);
        this.signUpPage = new Scene(signUpPageRoot);
        this.writeRecipePage = new Scene(writeRecipeRoot);



        /*
         * Gir primaryStage til kontrollere.
         * Kobler opp MainPageController til Content. RecipeController kobles opp til MainPageController.
         * LogIn- og SignUpController får MainPageController for å kunne gi beskjeder fra seg til den.
         */
        MainPageController mainPageController = mainPageLoader.getController();
        RecipeController recipeController = recipePageLoader.getController();
        LogInController logInController = logInPageLoader.getController();
        SignUpController signUpController = signUpPageLoader.getController();
        WriteRecipeController writeRecipeController = writeRecipePageLoader.getController();

        mainPageController.setStage(primaryStage);
        recipeController.setStage(primaryStage);
        logInController.setStage(primaryStage);
        signUpController.setStage(primaryStage);
        writeRecipeController.setStage(primaryStage);

        mainPageController.setRecipeController(recipeController);
        mainPageController.setWriteRecipeController(writeRecipeController);
        signUpController.setMainPageController(mainPageController);
        logInController.setMainPageController(mainPageController);
        writeRecipeController.setRecipeController(recipeController);
        recipeController.setMainPageController(mainPageController);

        mainPageController.hidePreviousPageButton();
        mainPageController.hideChefPreviousPageButton();

        Content content = new Content();
        signUpController.setContent(content);
        content.setMainPageController(mainPageController);

        // Laster inn data fra databasen
        content.loadRecipies();
        content.loadVerifiedRecipies();
        mainPageController.setContent(content);


        mainPageController.updateFxml();
        mainPageController.updateChefFxml();


        /*
         * Applikasjonen åpner opp mainPage når den startes. Man er foreløpig ikke logget inn og skal ikke
         * kunne få opp fanen for å legge til oppskrifter, og man skal heller ikke kunne slette oppskrifter.
         */
        primaryStage.setTitle("Chefs Apprentice");
        primaryStage.setScene(this.mainPageScene);
        mainPageController.hideRecipeTab();
        recipeController.hideDeleteButton();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
