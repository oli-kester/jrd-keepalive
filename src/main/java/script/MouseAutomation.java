package script;

import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class MouseAutomation implements Runnable
{
    private static Robot mouseRobot;
    private AtomicBoolean automationActive;

    public MouseAutomation ()
    {
        automationActive = new AtomicBoolean(false);

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

            System.out.println("Mouse automation running...");

            mouseRobot.delay(randWaitTime * 1000);
        }

        System.out.println("Mouse automation stopped.");
    }

    @Override //multithreading support override
    public void run ()
    {
        automationActive.set(true);
        perform();
    }

}
