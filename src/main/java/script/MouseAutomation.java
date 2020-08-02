package script;

import java.awt.*;

public class MouseAutomation
{
    public static void perform () throws AWTException
    {
        Robot robot = new Robot();

        System.out.println("Now move to remote desktop. You have 10 seconds...");

        robot.delay(10000);

        System.out.println("Beginning random mouse movements");

        while (true)
        {
            int randWaitTime = ( int ) ( Math.random() * 60 );
            int randMoveDuration = ( int ) ( Math.random() * 5 );
            Point pointerInfo = MouseInfo.getPointerInfo().getLocation();

            int newMouseX = ( int ) ( pointerInfo.getX() + ( 100 * Math.random() - 50 ) );
            int newMouseY = ( int ) ( pointerInfo.getY() + ( 100 * Math.random() - 50 ) );
        }
    }

    public static void stop ()
    {

    }
}
