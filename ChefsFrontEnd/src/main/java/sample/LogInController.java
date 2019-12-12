package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;


/**
 * Denne klassen tar seg av login-siden.
 */

public class LogInController extends Controll{


    private MainPageController mainPageController;

    @FXML
    private TextField textField;
    @FXML
    private Text loginStatus;


    /**
     * @param mainPageController kontroller for mainPage.fxml.
     * @see MainPageController
     */
    public void setMainPageController(MainPageController mainPageController) {
        this.mainPageController = mainPageController;
    }

    /**
     * Tar inn et brukernavn fra bruker og logger inn bruker hvis brukernavnet finnes i databasen,
     * hvis ikke får bruker tilbakemelding om at brukernavnet ikke finnes.
     * Etter at bruker er logget inn blir bruker sendt tilbake til forsiden.
     */
    @FXML
    public void handleLoginButton(){
        String username = this.textField.getText();
        User user = Client.getUser(username);
        if (user == null){
            this.loginStatus.setText("User does not exist");
            return ;
        }
        //Gå tilbake til forsiden og si fra til mainPageController om at bruker er logget inn.
        user.printUser();
        this.loginStatus.setText("");
        this.mainPageController.userLoggedIn(user);
        super.getStage().setScene(FXMLMain.mainPageScene);
    }

    /**
     * Sender bruker tilbake til forsiden (mainPage.fxml).
     */
    @FXML
    public void handleExitButton(){
        // Gå tilbake til forsiden
        super.getStage().setScene(FXMLMain.mainPageScene);
    }
}
