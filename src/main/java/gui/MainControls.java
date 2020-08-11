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
    public TextArea txtAreaStatus;

    @FXML
    public void handleControlBtn ()
    {
        if (! automationActive)
        {
            mouseAutomation = new MouseAutomation();
            txtAreaStatus.textProperty().bind(mouseAutomation.messageProperty());

            mouseThread = new Thread(mouseAutomation);
            mouseThread.setDaemon(true);

            mouseThread.start();
            btnControl.setText("Stop");
            automationActive = true;

        } else
        {
            mouseAutomation.setAutomationActive(false);
            btnControl.setText("Start");
            automationActive = false;
        }
    }
}
