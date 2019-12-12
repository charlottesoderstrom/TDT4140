package sample;

import javafx.stage.Stage;


/**
 * Abstrakt klasse for alle kontrollere.
 */
public abstract class Controll {

    /**
     * Stage er vinduet til applikasjonen.
     */
    private Stage stage;

    /**
     * @param stage er den samme for alle kontrollere.
     */
    public void setStage(Stage stage){
        this.stage = stage;
    }


    public Stage getStage() {
        return stage;
    }
}
