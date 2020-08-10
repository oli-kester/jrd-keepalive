package script;

import gui.GuiUpdater;

import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class MouseAutomation implements Runnable
{

    private static Robot mouseRobot;
    private final AtomicBoolean automationActive;
    private final int screenWidth;
    private final int screenHeight;
    private final GuiUpdater guiUpdater;

    public MouseAutomation (GuiUpdater updateThis)
    {
        //TODO implement interrupt-based stopping mechanism
        automationActive = new AtomicBoolean(false);

        this.guiUpdater = updateThis;

        GraphicsDevice graphicsDevice =
                GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        screenWidth = graphicsDevice.getDisplayMode().getWidth();
        screenHeight = graphicsDevice.getDisplayMode().getHeight();

        try
        {
            mouseRobot = new Robot();
        } catch (AWTException e)
        {
            //TODO display dialog box error regarding AWT support
            e.printStackTrace();
        }
    }

    public void setAutomationActive (boolean value)
    {
        this.automationActive.set(value);
    }

    public void perform ()
    {
//        System.out.println("Now move to remote desktop. You have 10 seconds...");
        guiUpdater.appendText("Now move to remote desktop. You have 10 seconds...");

        mouseRobot.delay(10000);

//        System.out.println("Beginning random mouse movements");
        guiUpdater.appendText("Beginning random mouse movements. ");

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

//            System.out.println("Moving to - " + newMouseX + ", " + newMouseY);
            guiUpdater.appendText("Moving to - " + newMouseX + ", " + newMouseY);

            //TODO smooth mouse movements

            mouseRobot.mouseMove(newMouseX, newMouseY);

            //prevent a delay when clicking the stop button
            if (automationActive.get())
            {
//                System.out.println("Sleeping for " + randWaitTime + " seconds...");
                guiUpdater.appendText("Sleeping for " + randWaitTime + " seconds...");
                mouseRobot.delay(randWaitTime * 1000);
            } else
            {
                break;
            }
        }

//        System.out.println("Mouse automation stopped.");
        guiUpdater.appendText("Mouse automation stopped.");
    }

    @Override
    public void run ()
    {
        automationActive.set(true);
        perform();
    }

}
