package JUnit;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.Description;

public class JUnitTestRunner {

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(JUnitTestSuite.class);
        for (Failure failure : result.getFailures()) {
            Description description;
            String header;
            String trace;
            Throwable exception;
            description = failure.getDescription();
            exception = failure.getException();
            header = failure.getTestHeader();
            trace = failure.getTrace();

            if(!description.toString().equals("")) {
                System.out.print("Description: ");
                System.out.println(description);
            }
            if(!exception.toString().equals("Exception: ")) {
                System.out.print("Exception: ");
                System.out.println(failure.getException());
            }
            if(!header.equals("")) {
                System.out.print("TestHeader: ");
                System.out.println(failure.getTestHeader());
            }
            if(!trace.equals("")) {
                System.out.println("Trace: " );
                System.out.println(failure.getTrace());
            }
        }

        System.out.println("All Tests Passed");
    }
}
