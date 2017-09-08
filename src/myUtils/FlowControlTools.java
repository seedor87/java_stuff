package myUtils;

import java.io.IOException;

public class FlowControlTools {

    public static void waitForKey() {
        System.out.print("Press Enter Key To Continue >>>");
        try {
            System.in.read();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("\rContinuing...");
    }
}
