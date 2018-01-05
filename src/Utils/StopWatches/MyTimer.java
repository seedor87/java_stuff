package Utils.StopWatches;

import java.awt.*;
import java.util.concurrent.*;
import java.util.concurrent.TimeUnit;

public class MyTimer {
    Toolkit toolkit;
    Future<String> future;
    ExecutorService executor;

    MyTimer() throws Exception{
        executor = Executors.newSingleThreadExecutor();
        future = executor.submit(() -> {
            while(true) {}
        });
        toolkit = Toolkit.getDefaultToolkit();
    }

    MyTimer(Callable call) {
        executor = Executors.newSingleThreadExecutor();
        future = executor.submit(call);
        toolkit = Toolkit.getDefaultToolkit();
    }

    public void exe(long seconds) {
        try {
            System.out.println("Started..");
            System.out.println(future.get(seconds, TimeUnit.SECONDS));
            System.out.println("Finished!");
        } catch (TimeoutException e) {
            future.cancel(true);
            System.out.println("Terminated!");
        } catch (ExecutionException e) {
            future.cancel(true);
            e.printStackTrace();
        } catch (InterruptedException e) {
            future.cancel(true);
            e.printStackTrace();
        } finally {
            toolkit.beep();
            executor.shutdownNow();
            System.exit(0);
        }
    }

    public static void main(String[] args) throws Exception {
        new MyTimer().exe(1);
        new MyTimer(new Callable() {
            @Override
            public Object call() throws Exception {
                return null;
            }
        });
    }
}

