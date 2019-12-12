package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import java.util.HashMap;
import java.util.List;

/**
 * Kontrollerer forsiden (mainPage.fxml).
 * Behandler alle knapper på forsiden.
 * Forsyner RecipeController med et og et recipe-objekt (én og én oppskrift).
 * Passer på at frontend har kontroll på en eventuell bruker og hvilke tilganger denne brukeren skal ha.
 */
public class MainPageController extends Controll {

    private Content content;

    /**
     * Holder styr på hvilken "side" man har bladd til blant oppskriftene på forsiden (mainPage.fxml).
     */
    private int pageCounter = 1;

    /**
     * Holder styr på hvilken "side" man har bladd til blant anbefalte oppskrifter på forsiden (mainPage.fxml).
     */
    private int verifiedPageCounter = 1;

    private RecipeController recipeController;

    private WriteRecipeController writeRecipeController;

    /**
     * Holder på de oppskriftene (Recipe-objektene) som til enhver tid vises i fanen
     * for alle oppskrifter på forsiden (mainPage.fxml).
     */
    private HashMap<Integer, Recipe> recipes = new HashMap<>();


    /**
     * Holder på de oppskriftene (Recipe-objektene) som til enhver tid vises i fanen
     * for anbefalte oppskrifter på forsiden (mainPage.fxml).
     */
    private HashMap<Integer, Recipe> verifiedrecipes = new HashMap<>();


    //FrontPage
    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Button button3;
    @FXML
    private Button button4;
    @FXML
    private TextField textFieldSearch;
    @FXML
    private Text textForSearchStatus;
    @FXML
    private Button nextPage;
    @FXML
    private Button previousPage;
    @FXML
    private Button reloadbutton;


    //ChefsRecommendations
    @FXML
    private Button chefButton1;
    @FXML
    private Button chefButton2;
    @FXML
    private Button chefButton3;
    @FXML
    private Button chefButton4;
    @FXML
    private TextField chefTextFieldSearch;
    @FXML
    private Text textForSearchStatusVerified;
    @FXML
    private Button chefNextPage;
    @FXML
    private Button chefPreviousPage;
    @FXML
    private Button chefReloadbutton;


    //Add recipe
    @FXML
    private Button signup;
    @FXML
    private Button login;
    @FXML
    private Text userStatus;
    @FXML
    private Tab addRecipeTab;
    @FXML
    private Button adminAddRecipes;
    @FXML
    private Text addRecipesFromAPI;
    @FXML
    private Text writeNumberBetween1and100;
    @FXML
    private TextField adminRecipeCount;


    /**
     * Setter Content-referanse.
     * @param content
     * @see Content
     */
    public void setContent(Content content){
        this.content = content;
    }


    /**
     * Setter RecipeController-referanse.
     * @param recipeController
     * @see RecipeController
     */
    public void setRecipeController(RecipeController recipeController) {
        this.recipeController = recipeController;
    }


    /**
     * Setter WriteRecipeController-referanse.
     * @param controller
     * @see WriteRecipeController
     */
    public void setWriteRecipeController(WriteRecipeController controller){
        this.writeRecipeController = controller;
    }


    /**
     * *Setter pageCounter.
     * @param counter
     */
    public void setPageCounter(int counter){
        this.pageCounter = counter;
    }


    /**
     * Henter 4 nye oppskrifter (Recipe) fra Content og putter de i en HashMap.
     * @see Recipe
     * @see Content
     */
    private void fetchContent(){
        List<Recipe> recipes = this.content.get4Recipies();
        this.recipes.clear();
        for (int i = 0; i < recipes.size(); i++){
            this.recipes.put(i+1, recipes.get(i)); //Hashmap = <ID, Recipe>
        }
    }


    /**
     * Oppdaterer fanen for alle oppskrifter på forsiden (mainPage.fxml).
     */
    public void updateFxml(){
        this.fetchContent();
        this.updateButton(this.button1, 1);
        this.updateButton(this.button2, 2);
        this.updateButton(this.button3, 3);
        this.updateButton(this.button4, 4);
    }


    /**
     * Oppdaterer en bestemt knapp på forsiden (mainPage.fxml). Skjuler knapper som ikke trengs.
     * @param button knappen som skal oppdateres.
     * @param buttonId ID på knappen som skal oppdateres.
     */
    private void updateButton(Button button, int buttonId){
        if (this.recipes.containsKey(buttonId)){
            button.setText(this.recipes.get(buttonId).getTitle());
            button.setVisible(true);
            return;
        }
        button.setVisible(false);
    }


    /**
     * Tar inn søketekst fra bruker og sender videre til Content.
     * @see Content
     */
    @FXML
    public void handleSearch(){
        String searchText = this.textFieldSearch.getText();

        // Gi søketeksten videre til content
        this.content.doIngredientSearch(searchText);
        System.out.println(searchText);

        this.pageCounter = 1;

        // Oppdater forsiden
        this.updateFxml();
        this.hidePreviousPageButton();
        if (this.content.recipes.size() == 0) {
            this.textForSearchStatus.setText("The search didn't return any results");
        } else {
            this.textForSearchStatus.setText("Showing results for: " + searchText);
        }
        this.textFieldSearch.clear();
        if (this.content.recipes.size() <= 4){
            this.hideNextPageButton();
        } else {
            this.showNextPageButton();
        }
    }


    /**
     * Sender bruker til siden for å registrere seg (signup.fxml).
     */
    @FXML
    public void handlesignupbutton(){
        super.getStage().setScene(FXMLMain.signUpPage);
    }


    /**
     * Behandler login/logout-knappen.
     * Dersom man er logget inn vil man bli logget ut, dvs. at logout-knappen blir til en login-knapp.
     * Dersom man ikke er logget inn vil man bli sendt til siden for å logge inn (Login.fxml).
     */
    @FXML
    public void handleloginbutton() {
        if (this.signup.isVisible()) {
            super.getStage().setScene(FXMLMain.logInPageScene);
            return;
        }
        this.login.setText("Login");
        this.signup.setVisible(true);
        this.userStatus.setText("");
        this.recipeController.hideDeleteButton();
        this.hideRecipeTab();
        this.writeRecipeController.setUser(null);

    }



    // Denne metoden blir også kalt når bruker har registrert seg (signup) for da blir man automatisk innlogget.

    /**
     * Behandler en innlogging for en bruker.
     * Login-knappen vil bli til en logout-knapp og signup-knappen vil forsvinne.
     * Gir brukeren en tilbakemelding på at man nå er logget inn.
     * Gir alle innloggede brukere tilgang til å legge til nye oppskrifter.
     * Gir admin-rettigheter hvis bruker er admin.
     * @param user bruker-objektet
     */
    public void userLoggedIn(User user){
        this.login.setText("Logout");
        this.signup.setVisible(false);
        this.userStatus.setText("Hei "+ user.getUsername() + "!");
        this.writeRecipeController.setUser(user);

        // Admin skal ha rettigheter til å slette oppskrifter og legge til mange oppskrifter fra API.
        if (user.getRole().equals("admin")){
            this.adminAddRecipes.setVisible(true);
            this.recipeController.showDeleteButton();
            this.addRecipesFromAPI.setText("Add more recipes");
            this.writeNumberBetween1and100.setText("Write a number between 1-100");
            this.adminRecipeCount.setVisible(true);
            this.adminAddRecipes.setVisible(true);
        }
        // Når man er logget inn skal man ha tilgang til fanen for å skrive recipes, uansett hvordan bruker man er.
        this.showRecipeTab();
    }


    /**
     * Nekter bruker tilgang til fanen for å legge til oppskrifter.
     */
    public void hideRecipeTab() {
        this.adminAddRecipes.setVisible(false); // Hver gang fanen skjules skal adminAddRecipes også skjules.
        this.addRecipesFromAPI.setText("");
        this.writeNumberBetween1and100.setText("");
        this.adminRecipeCount.setVisible(false);
        this.addRecipeTab.setDisable(true);
    }


    /**
     * Gir bruker tilgang til fane for å legge til oppskrifter.
     */
    public void showRecipeTab(){
        this.addRecipeTab.setDisable(false);
    }


    /**
     * Behandler klikk på oppskrift (Recipe).
     * Sender bruker til siden for en oppskrift.
     */
    @FXML
    public void handleButton1(){
        this.recipeController.updateRecipePage(this.recipes.get(1));
        super.getStage().setScene(FXMLMain.recipePageScene);
    }


    /**
     * Behandler klikk på oppskrift (Recipe).
     * Sender bruker til siden for en oppskrift.
     */
    @FXML
    public void handleButton2(){
        this.recipeController.updateRecipePage(this.recipes.get(2));
        this.setRecipeScene();
    }


    /**
     * Behandler klikk på oppskrift (Recipe).
     * Sender bruker til siden for en oppskrift.
     */
    @FXML
    public void handleButton3(){
        this.recipeController.updateRecipePage(this.recipes.get(3));
        this.setRecipeScene();
    }

    /**
     * Behandler klikk på oppskrift (Recipe).
     * Sender bruker til siden for en oppskrift.
     */
    @FXML
    public void handleButton4(){
        this.recipeController.updateRecipePage(this.recipes.get(4));
        this.setRecipeScene();
    }


    /**
     * Behandler knapp for å refreshe forsiden med oppskrifter (mainPage.fxml).
     */
    @FXML
    public void handleReload(){ //Laster inn alle nye oppskrifter på nytt.
        this.content.loadRecipies();
        this.textFieldSearch.clear();
        this.textForSearchStatus.setText("");
        this.pageCounter = 1;
        this.verifiedPageCounter = 1;
        this.hidePreviousPageButton();
        this.showNextPageButton();
        this.updateFxml();
    }



    /**
     * Laster inn 4 nye oppskrifter, eller 4 nye resultater på et søk.
     */
    @FXML
    public void handleNextPage(){
        this.showPreviousPageButton();
        this.updateFxml();
        this.pageCounter++;
        System.out.println(this.pageCounter);
    }


    /**
     * Laster inn de 4 forrige oppskriftene som ble vist, eventuelt de 4 forrige resultatene på et søk.s
     */
    @FXML
    public void handlePreviousPage(){
        this.content.previousContent();
        this.showNextPageButton();
        this.updateFxml();
        this.pageCounter--;
        System.out.println(this.pageCounter);
        if (this.pageCounter <= 1){
            this.hidePreviousPageButton();
        }
    }

    /**
     * Viser knapp for å gå til forrige side når man browser oppskrifter.
     */
    public void showPreviousPageButton(){
        this.previousPage.setVisible(true);
    }


    /**
     * Skjuler knapp for å gå til forrige side når man browser oppskrifter.
     */
    public void hidePreviousPageButton(){
        this.previousPage.setVisible(false);
    }


    /**
     * Viser knapp for å gå til neste side når man browser oppskrifter.
     */
    public void showNextPageButton(){
        this.nextPage.setVisible(true);
    }


    /**
     * Skjuler knapp for å gå til neste side når man browser oppskrifter.
     */
    public void hideNextPageButton(){
        this.nextPage.setVisible(false);
    }




    //ChefsRecommendations
    /**
     * Behandler klikk på oppskrift (Recipe).
     * Sender bruker til siden for en oppskrift.
     */
    @FXML
    public void handleChefButton1(){
        this.recipeController.updateRecipePage(this.verifiedrecipes.get(1));
        super.getStage().setScene(FXMLMain.recipePageScene);
    }


    /**
     * Behandler klikk på oppskrift (Recipe).
     * Sender bruker til siden for en oppskrift.
     */
    @FXML
    public void handleChefButton2(){
        this.recipeController.updateRecipePage(this.verifiedrecipes.get(2));
        this.setRecipeScene();
    }


    /**
     * Behandler klikk på oppskrift (Recipe).
     * Sender bruker til siden for en oppskrift.
     */
    @FXML
    public void handleChefButton3(){
        this.recipeController.updateRecipePage(this.verifiedrecipes.get(3));
        this.setRecipeScene();
    }

    /**
     * Behandler klikk på oppskrift (Recipe).
     * Sender bruker til siden for en oppskrift.
     */
    @FXML
    public void handleChefButton4(){
        this.recipeController.updateRecipePage(this.verifiedrecipes.get(4));
        this.setRecipeScene();
    }


    /**
     * Behandler knapp for å refreshe forsiden med oppskrifter (mainPage.fxml).
     */
    @FXML
    public void handleChefReload(){ //Laster inn alle nye oppskrifter på nytt.
        this.content.loadVerifiedRecipies();
        this.chefTextFieldSearch.clear();
        this.textForSearchStatusVerified.setText("");
        this.verifiedPageCounter = 1;
        this.hideChefPreviousPageButton();
        this.showChefNextPageButton();
        this.updateChefFxml();
    }


    /**
     * Tar inn søketekst fra bruker og sender videre til Content.
     * @see Content
     */
    @FXML
    public void handleChefSearch(){
        String searchText = this.chefTextFieldSearch.getText();

        // Gi søketeksten videre til content
        this.content.doVerifiedIngredientSearch(searchText);
        System.out.println(searchText);
        this.verifiedPageCounter = 1;
        // Oppdater Chefs Recommendations
        this.updateChefFxml();
        this.hideChefPreviousPageButton();
        if (this.content.verifiedRecipes.size() == 0) {
            this.textForSearchStatusVerified.setText("The search didn't return any results");
        } else {
            this.textForSearchStatusVerified.setText("Showing results for: " + searchText);
        }
        this.chefTextFieldSearch.clear();
        if (this.content.verifiedRecipes.size() <= 4){
            this.hideChefNextPageButton();
        } else {
        this.showChefNextPageButton();
        }
    }


    /**
     * Oppdaterer fanen for anbefalte oppskrifter på forsiden (mainPage.fxml).
     */
    public void updateChefFxml(){

        this.fetchVerifiedContent();
        this.updateVerifiedButton(this.chefButton1, 1);
        this.updateVerifiedButton(this.chefButton2, 2);
        this.updateVerifiedButton(this.chefButton3, 3);
        this.updateVerifiedButton(this.chefButton4, 4);
    }


    /**
     * Oppdaterer en bestemt knapp på forsiden (mainPage.fxml). Skjuler knapper som ikke trengs.
     * @param button
     * @param buttonId
     */
    private void updateVerifiedButton(Button button, int buttonId){
        if (this.verifiedrecipes.containsKey(buttonId)){
            button.setText(this.verifiedrecipes.get(buttonId).getTitle());
            button.setVisible(true);
            return;
        }
        button.setVisible(false);
    }


    /**
     * Henter 4 nye anbefalte oppskrifter (Recipe) fra Content og putter de i en HashMap.
     * @see Recipe
     * @see Content
     */
    private void fetchVerifiedContent(){
        List<Recipe> verifiedrecipes = this.content.get4VerifiedRecipies();
        this.verifiedrecipes.clear();
        for (int i = 0; i < verifiedrecipes.size(); i++){
            this.verifiedrecipes.put(i+1, verifiedrecipes.get(i)); //Hashmap = <ID, Recipe>
        }
    }


    /**
     * Laster inn 4 nye oppskrifter, eller 4 nye resultater på et søk.
     */
    @FXML
    public void handleChefNextPage(){
        this.showChefPreviousPageButton();
        this.updateChefFxml();
        this.verifiedPageCounter++;
        System.out.println("Verified Pageindex: " + this.verifiedPageCounter);
    }


    /**
     * Laster inn de 4 forrige oppskriftene som ble vist, eventuelt de 4 forrige resultatene på et søk.s
     */
    @FXML
    public void handleChefPreviousPage(){
        this.content.previousVerifiedContent();
        this.showChefNextPageButton();
        this.updateChefFxml();
        this.verifiedPageCounter--;
        System.out.println("Verified Pageindex: " + this.verifiedPageCounter);
        if (this.verifiedPageCounter <= 1){
            this.hideChefPreviousPageButton();
        }
    }


    /**
     * Viser knapp for å gå til forrige side når man browser oppskrifter.
     */
    public void showChefPreviousPageButton(){
        this.chefPreviousPage.setVisible(true);
    }


    /**
     * Skjuler knapp for å gå til forrige side når man browser oppskrifter.
     */
    public void hideChefPreviousPageButton(){
        this.chefPreviousPage.setVisible(false);
    }


    /**
     * Viser knapp for å gå til neste side når man browser oppskrifter.
     */
    public void showChefNextPageButton(){
        this.chefNextPage.setVisible(true);
    }


    /**
     * Skjuler knapp for å gå til neste side når man browser oppskrifter.
     */
    public void hideChefNextPageButton(){
        this.chefNextPage.setVisible(false);
    }


    // Hjelpemetode for handleButton1, 2, 3 og 4. Setter stage til recipe.fxml.
    private void setRecipeScene(){ super.getStage().setScene(FXMLMain.recipePageScene); }


    //Add recipe-fane

    /**
     * Sender bruker til side for å skrive nye oppskrifter.
     */
    @FXML
    private void handleWriteRecipeButton(){
        super.getStage().setScene(FXMLMain.writeRecipePage);
    }


    /**
     * Behandler knapp for å legge til mange oppskrifter fra API.
     * Tar inn et nummer n mellom 1 og 100 og legger til n oppskrifter til databasen.
     */
    @FXML
    private void handleAdminAddRecipeButton() {
        int num = Integer.parseInt(this.adminRecipeCount.getText());
        if(num<1 || num>100) {
            AlertBox.display("Warning", "Number of recipes must be between 1-100");
        }
        else{
            Client.addManyRecipes(num);
            AlertBox.display("Success!", num+" recipes was added");
        }
        this.adminRecipeCount.clear();
        System.out.println(num);

    }
}