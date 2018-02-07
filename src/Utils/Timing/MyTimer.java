package Utils.Timing;

import java.awt.Toolkit;
import java.util.concurrent.*;
import java.util.concurrent.TimeUnit;

import static Utils.Console.Printing.printrn;

public class MyTimer<T extends Callable> {
    private Future<T> future;
    private ExecutorService executor;
    private final Toolkit toolkit = Toolkit.getDefaultToolkit();

    public MyTimer() throws Exception{
        executor = Executors.newSingleThreadExecutor();
        future = executor.submit(() -> {
            while(true);
        });
    }

    public MyTimer(T call) throws Exception {
        executor = Executors.newSingleThreadExecutor();
        future = executor.submit(call);
    }

    public void run(long seconds) {
        try {
            System.out.println("Started...");
            future.get(seconds, TimeUnit.SECONDS);
            System.out.println("\nFinished!");
        } catch (TimeoutException e) {
            future.cancel(true);
            System.out.println("\nTerminated!");
        } catch (Exception e) {
            future.cancel(true);
            e.printStackTrace();
        } finally {
            toolkit.beep();
            future.cancel(true);
            executor.shutdownNow();
        }
    }

    public static void main(String[] args) throws Exception {
        new MyTimer<>(() -> new TestClass().call()).run(1);
        new MyTimer(new TestClass()::call).run(1);
        new MyTimer(new TestClass()).run(1);
        new MyTimer().run(1);
        System.exit(0);
    }
}

class TestClass implements Callable {
    @Override
    public Object call() throws Exception {
        int i = 0;
        while(i < 10000) {
            printrn(i);
            ++i;
        }
        return null;
    }
}

