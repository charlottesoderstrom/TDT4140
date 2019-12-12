package sample;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


/**
 * Forsyner kontrollerne med oppskrifter. Inneholder metoder for å videreformidle spørringer (søk) fra brukere.
 */

public class Content {

    private MainPageController mainPageController;

    /**
     * Alle oppskriftene som vises på forsiden (mainPage.fxml).
     */
    protected List<Recipe> recipes;


    /**
     * Alle anbefalte oppskrifter som vises på forsiden (mainPage.fxml).
     */
    protected ArrayList<Recipe> verifiedRecipes;


    /**
     * Hjelpeteller for å holde styr på hvilke oppskfiter man har bladd til på forsiden (mainPage.fxml).
     */
    private int indexCounter = 0;


    /**
     * Hjelpeteller for å holde styr på hvilke anbefalte oppskrifter man har bladd til på forsiden (mainPage.fxml).
     */
    private int verifiedIndexCounter = 0;


    /**
     * Konstruktør.
     */
    public Content(){
        this.recipes = new ArrayList<>();
        this.verifiedRecipes = new ArrayList<>();
    }


    /**
     * @param mainPageController kontroller for mainPage.fxml.
     * @see MainPageController
     */
    public void setMainPageController(MainPageController mainPageController){
        this.mainPageController = mainPageController;
    }


    /**
     * Kaller på Client-klassen som søker etter oppskrifter på en eller flere ingredienser.
     * @param ingredients ingrediensene brukeren har søkt på.
     * @see Client
     */
    public void doIngredientSearch(String ingredients) {
        this.recipes = Client.getRecipeByIngredients(ingredients);
        this.indexCounter = 0;
    }


    /**
     * Kaller på Client-klassen som søker etter anbefalte oppskrifter på en eller flere ingredienser.
     * @param ingredients ingrediensene brukeren har søkt på.
     * @see Client
     */
    public void doVerifiedIngredientSearch(String ingredients) {
        this.verifiedRecipes = Client.getVerifiedRecipesByIngredients(ingredients);
        this.verifiedIndexCounter = 0;
    }


    /**
     * Kaller på Client for å laste inn oppskrifter fra databasen.
     * @see Client
     */
    public void loadRecipies(){
        this.recipes = Client.getRecipes();
        this.indexCounter = 0;
    }


    /**
     * Kaller på Client for å laste inn anbefalte oppskrifter fra databasen.
     * @see Client
     */
    public void loadVerifiedRecipies() {
        this.verifiedRecipes = Client.getVerifiedRecipes();
        this.verifiedIndexCounter = 0;
    }


    /**
     * Kaller på Client for å registrere en bruker.
     * @param user brukeren som skal registreres.
     * @return true hvis bruker ble registrert (dvs. at brukernavn ikke var opptatt)
     */
    public boolean registerUser(User user) {
        return Client.registerUser(user);
    }


    /**
     * @return oppskriftene som vises på forsiden (mainPage.fxml).
     */
    public List<Recipe> getRecipesList(){
        return this.recipes;
    } //returnerer alle oppskriftene


    /**
     * @return 4 eller mindre oppskrifter.
     */
    public List<Recipe> get4Recipies() {
        List<Recipe> recipes = new ArrayList<Recipe>();
        for (int i = this.indexCounter; i < this.indexCounter+4; i++){
            if (i < this.recipes.size()) {
                recipes.add(this.recipes.get(i));
            }
        }
        this.indexCounter += 4;
        System.out.println(this.recipes.size());
        System.out.println(this.indexCounter);

        if (this.recipes.size() <= 4){
            this.mainPageController.hideNextPageButton();
            this.mainPageController.hidePreviousPageButton();
        }
        else if (this.indexCounter >= this.recipes.size()){
            this.mainPageController.showPreviousPageButton();
            this.mainPageController.hideNextPageButton();
        }

        return recipes;
    }


    /**
     * @return 4 eller mindre verifiserte oppskrifter.
     */
    public List<Recipe> get4VerifiedRecipies() {
        List<Recipe> recipes = new ArrayList<Recipe>();
        for (int i = this.verifiedIndexCounter; i < this.verifiedIndexCounter+4; i++){
            if (i < this.verifiedRecipes.size()) {
                recipes.add(this.verifiedRecipes.get(i));
            }
        }
        this.verifiedIndexCounter += 4;
        System.out.println(this.verifiedRecipes.size());
        System.out.println(this.verifiedIndexCounter);

        if (this.verifiedRecipes.size() <=4){
            this.mainPageController.hideChefPreviousPageButton();
            this.mainPageController.hideChefNextPageButton();
        }

        else if (this.verifiedIndexCounter >= this.verifiedRecipes.size()){
            this.mainPageController.showChefPreviousPageButton();
            this.mainPageController.hideChefNextPageButton();
        }
        return recipes;
    }


    /**
     * Legger til en oppskrift.
     * @param recipe oppskiften som skal legges til.
     */
    public void addRecipe(Recipe recipe){
        this.recipes.add(recipe);
    }


    /**
     * Fjerner en oppskrift.
     * @param recipe oppskriften som skal fjernes.
     */
    public void removeRecipe(Recipe recipe){
        if(this.recipes.contains(recipe)) {
            this.recipes.remove(recipe);
        }
    }


    /**
     * Hjelpemetode for å sikre konistens på hvilken side man har bladd til blant oppskfitene på forsiden (mainPage.fxml).
     */
    public void previousContent(){
        if (this.indexCounter < 4){
            return ;
        }
        this.indexCounter -= 8;
    }


    /**
     * Hjelpemetode for å sikre konistens på hvilken side man har bladd til blant anbefalte oppskfitene på forsiden (mainPage.fxml).
     */
    public void previousVerifiedContent(){
        if (this.verifiedIndexCounter < 4){
            return ;
        }
        this.verifiedIndexCounter -= 8;
    }

}
