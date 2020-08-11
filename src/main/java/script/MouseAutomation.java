package script;

import javafx.concurrent.Task;

import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class MouseAutomation extends Task<MouseAutomation>
{

    private static Robot mouseRobot;
    private final AtomicBoolean automationActive;
    private final int screenWidth;
    private final int screenHeight;
    private String statusMessage;

    public MouseAutomation ()
    {
        //TODO implement interrupt-based stopping mechanism
        statusMessage = "";
        automationActive = new AtomicBoolean(false);

        GraphicsDevice graphicsDevice =
                GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        screenWidth = graphicsDevice.getDisplayMode().getWidth();
        screenHeight = graphicsDevice.getDisplayMode().getHeight();

        try
        {
            mouseRobot = new Robot();
        } catch (AWTException e)
        {
            //TODO error box for non-awt support
            e.printStackTrace();
        }
    }

    public void setAutomationActive (boolean value)
    {
        appendToStatusMessage("Stopping mouse automation...");
        this.automationActive.set(value);
    }

    public void perform ()
    {
        appendToStatusMessage("Now move to remote desktop. You have 10 seconds...");

        mouseRobot.delay(10000);

        appendToStatusMessage("Beginning random mouse movements. ");

        while (automationActive.get())
        {
            int randWaitTime = ( int ) ( Math.random() * 60 );
//            int randMoveDuration = ( int ) ( Math.random() * 5 );
            Point pointerInfo = MouseInfo.getPointerInfo().getLocation();

            int newMouseX = ( int ) ( pointerInfo.getX() + ( 100 * Math.random() - 50 ) );
            int newMouseY = ( int ) ( pointerInfo.getY() + ( 100 * Math.random() - 50 ) );

            int MOUSE_ZONE_BORDER = 80;
            if (( screenWidth - newMouseX ) < MOUSE_ZONE_BORDER)
            {
                newMouseX = screenWidth - MOUSE_ZONE_BORDER;

            } else if (newMouseX < MOUSE_ZONE_BORDER)
            {
                newMouseX = MOUSE_ZONE_BORDER;
            }

            if (( screenHeight - newMouseY ) < MOUSE_ZONE_BORDER)
            {
                newMouseY = screenHeight - MOUSE_ZONE_BORDER;

            } else if (newMouseY < MOUSE_ZONE_BORDER)
            {
                newMouseY = MOUSE_ZONE_BORDER;
            }

            appendToStatusMessage("Moving to - " + newMouseX + ", " + newMouseY);

            //TODO smooth mouse movements

            mouseRobot.mouseMove(newMouseX, newMouseY);

            //prevent a delay when clicking the stop button
            if (automationActive.get())
            {
                appendToStatusMessage("Sleeping for " + randWaitTime + " seconds...");
                mouseRobot.delay(randWaitTime * 1000);
            } else
            {
                break;
            }
        }

        appendToStatusMessage("Mouse automation stopped.");
    }

    @Override
    protected MouseAutomation call ()
    {
        automationActive.set(true);
        perform();
        return null;
    }

    private void appendToStatusMessage (String message)
    {
        statusMessage = statusMessage + message + "\n";
        updateMessage(statusMessage);
    }
}
