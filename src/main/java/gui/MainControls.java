package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.*;
import script.MouseAutomation;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

public class MainControls
{
    public static final String GITHUB_ISSUES_URL = "https://github.com/oli-kester/jrd-keepalive/issues";
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
            try
            {
                mouseAutomation = new MouseAutomation();
                txtAreaStatus.textProperty().bind(mouseAutomation.messageProperty());
                //TODO make text area scroll down with output

                mouseThread = new Thread(mouseAutomation);
                mouseThread.setDaemon(true);

                mouseThread.start();
                btnControl.setText("Stop");
                automationActive = true;

            } catch (AWTException e)
            {
                Alert awtAlert = new Alert(Alert.AlertType.ERROR);
                ButtonType buttonTypeReport = new ButtonType("Report Error");
                ButtonType buttonTypeClose = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);
                awtAlert.getButtonTypes().setAll(buttonTypeReport, buttonTypeClose);

                awtAlert.setHeight(400);
                awtAlert.setWidth(400);
                awtAlert.setTitle("Error");
                awtAlert.setHeaderText("Automation Not Possible");
                awtAlert.setContentText("This platform configuration does not support low-level input control. " +
                        "Please report this error on GitHub. ");
                Optional<ButtonType> result = awtAlert.showAndWait();

                if (result.isPresent() && result.get() == buttonTypeReport)
                {
                    try
                    {
                        Desktop.getDesktop().browse(new URI(GITHUB_ISSUES_URL));
                    } catch (IOException | URISyntaxException ioException)
                    {
                        ioException.printStackTrace();
                    }
                }

            }

        } else
        {
            mouseAutomation.setAutomationActive(false);
            btnControl.setText("Start");
            automationActive = false;
        }
    }
}
