package sample;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;


/**
 * Denne klassen tar seg av signup-siden.
 */

public class SignUpController extends Controll {

    private MainPageController mainPageController;
    private Content content;

    @FXML
    private TextField textField;
    @FXML
    private CheckBox checkBoxChef;
    @FXML
    private CheckBox checkBoxAdmin;
    @FXML
    private Text signupStatus;


    /**
     * @param mainPageController kontroller for mainPage.fxml.
     * @see MainPageController
     */
    public void setMainPageController(MainPageController mainPageController){
        this.mainPageController = mainPageController;
    }


    /**
     * Setter Content-objektet.
     * @param content Content.
     */
    public void setContent(Content content){
        this.content = content;
    }


    /**
     * Tar inn et brukernavn fra bruker og lager en bruker.
     * Etter at bruker er logget inn blir bruker sendt tilbake til forsiden.
     */
    @FXML
    public void handleSignupButton() {
        String username = this.textField.getText();

        // Gå tilbake til forsiden og si ifra til mainPageController om at bruker er logget inn.
        if (this.checkBoxAdmin.isSelected()) {
            User user = new User(username, "admin");
            this.handleSignup(user);
        }
        else if (this.checkBoxChef.isSelected()){
            User user = new User(username, "chef");
            this.handleSignup(user);
        }
        else {
            User user = new User(username, "user");
            this.handleSignup(user);
        }

        // Gå tilbake til forsiden.
        super.getStage().setScene(FXMLMain.mainPageScene);
    }


    /**
     * Hjelpemetode for handleSignupButton().
     * Sjekker om brukernavn allerede er tatt og gir i såfall bruker beskjed om dette.
     * Om brukernavn er ledig vil bruker bli opprettet i databasen, bruker blir automatisk logget inn
     * og sendt tilbake til forsiden (mainPage.fxml).
     * @param user brukerobjektet som blir opprettet i handleSignupButton().
     */
    private void handleSignup(User user){
        boolean userIsRegistered = this.content.registerUser(user);

        if (userIsRegistered){
            this.mainPageController.userLoggedIn(user); // boolean isAdmin.

            // Gå tilbake til forsiden.
            super.getStage().setScene(FXMLMain.mainPageScene);

            this.signupStatus.setText("");
            System.out.println(user.getUsername() + " registered");
            return;
        }
        this.signupStatus.setText("Username is taken");
        System.out.println("Username allready taken");

        // Gå tilbake til forsiden uten å være logget inn
        super.getStage().setScene(FXMLMain.mainPageScene);
    }


    /**
     * Sender bruker tilbake til forsiden (mainPage.fxml).
     */
    @FXML
    public void handleExitButton(){
        // Gå tilbake til forsiden.
        super.getStage().setScene(FXMLMain.mainPageScene);
    }
}
