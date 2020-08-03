package script;

import gui.MainControls;

import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class MouseAutomation implements Runnable
{
    private final int MOUSE_ZONE_BORDER = 80;

    private static Robot mouseRobot;
    private AtomicBoolean automationActive;
    private GraphicsDevice graphicsDevice;
    private int screenWidth;
    private int screenHeight;

    public MouseAutomation ()
    {
        //TODO implement interrupt-based stopping mechanism
        automationActive = new AtomicBoolean(false);

        graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
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

    public boolean getAutomationActive ()
    {
        return automationActive.get();
    }

    public void setAutomationActive (boolean value)
    {
        this.automationActive.set(value);
    }

    public void perform ()
    {
        System.out.println("Now move to remote desktop. You have 10 seconds...");

        mouseRobot.delay(10000);

        System.out.println("Beginning random mouse movements");

        while (automationActive.get())
        {
            int randWaitTime = ( int ) ( Math.random() * 60 );
            int randMoveDuration = ( int ) ( Math.random() * 5 );
            Point pointerInfo = MouseInfo.getPointerInfo().getLocation();

            int newMouseX = ( int ) ( pointerInfo.getX() + ( 100 * Math.random() - 50 ) );
            int newMouseY = ( int ) ( pointerInfo.getY() + ( 100 * Math.random() - 50 ) );

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

            System.out.println("Moving to - " + newMouseX + ", " + newMouseY);

            //TODO smooth mouse movements

            mouseRobot.mouseMove(newMouseX, newMouseY);

            //prevent a delay when clicking the stop button
            if (automationActive.get())
            {
                System.out.println("Sleeping for " + randWaitTime + " seconds...");
                mouseRobot.delay(randWaitTime * 1000);
            } else
            {
                break;
            }
        }

        System.out.println("Mouse automation stopped.");
    }

    @Override
    public void run ()
    {
        automationActive.set(true);
        perform();
    }

}
