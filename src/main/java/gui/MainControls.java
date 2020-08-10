package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import script.MouseAutomation;

public class MainControls
{
    Thread mouseThread;
    MouseAutomation mouseAutomation;
    private boolean automationActive = false;
    @FXML
    public Button btnControl;
    @FXML
    public TextArea txtAreaStatus; //TODO append to this with status information from MSEAUT.java. Probably need to use an interface to keep the automation class generic.

    @SuppressWarnings ( "FieldMayBeFinal" )
    private GuiUpdater textFieldGuiUpdater = textToAppend ->
    {
        String currText = txtAreaStatus.getText();
        txtAreaStatus.setText(currText + "\n" + textToAppend);
    };

    @FXML
    public void handleControlBtn ()
    {
        if (! automationActive)
        {
            mouseAutomation = new MouseAutomation(textFieldGuiUpdater);
            mouseThread = new Thread(mouseAutomation);

            mouseThread.start();
            btnControl.setText("Stop");
            automationActive = true;

        } else
        {
            System.out.println("Stopping mouse automation...");
            mouseAutomation.setAutomationActive(false);
            btnControl.setText("Start");
            automationActive = false;
        }
    }

}
