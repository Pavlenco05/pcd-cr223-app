import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
// 1 
public class Main {
    private static final CountDownLatch mainLatch = new CountDownLatch(4);
    private static final CountDownLatch waitLatch = new CountDownLatch(2);
    public static class Th1 extends Thread {
        @Override
        public void run() {
            System.out.println("Thread 1 started");
            performCalculations(100, 200, 4, "[Th1]", "CALCULATIONS");
            waitLatch.countDown();
            mainLatch.countDown();
            printWithDelay("Surname: Nikolaev");
        }
    }
    public static class Th2 extends Thread {
        @Override
        public void run() {
            System.out.println("Thread 2 started");
            performCalculations(110, 10, -4, "[Th2]", "CALCULATIONS");
            waitLatch.countDown();
            mainLatch.countDown();
            printWithDelay("Name: Edward");
        }
    }
    public static class Th3 extends Thread {
        @Override
        public void run() {
            System.out.println("Thread 3 initialization");
            awaitLatch(waitLatch);
            System.out.println("Thread 3 started");
            performCalculations(100, 500, 1, "[Th3]", "RANGE");
            mainLatch.countDown();
            printWithDelay("Discipline: PCD");
        }
    }
    public static class Th4 extends Thread {
        @Override
        public void run() {
            System.out.println("Thread 4 initialization");
            awaitLatch(waitLatch);
            System.out.println("Thread 4 started");
            performCalculations(700, 300, -1, "[Th4]", "RANGE");
            mainLatch.countDown();
            printWithDelay("Group: CR-223");
        }
    }
    private static void performCalculations(int start, int end, int step, String threadLabel, String type) {
        if (Objects.equals(type, "CALCULATIONS")) {
            for (int i = start; (step > 0) ? i < end : i > end; i += step) {
                int result = i + (i + step / 2);
                System.out.println(threadLabel + " => " + i + " + " + (i + step / 2) + " = " + result);
                Thread.yield();
            }
        }
        if (Objects.equals(type, "RANGE")) {
            for (int i = start; (step > 0) ? i <= end : i >= end; i += step) {
                System.out.print(threadLabel + " => " + i + " ");
                Thread.yield();
            }
            System.out.println();
        }
    }

    /**
     * @deprecated use synchronizedPrint instead
     * synchronize prints for all threads
     */
    static synchronized void printWithDelay(String text) {
        for (char letter : text.toCharArray()) {
            try {
                Thread.sleep(100); // every letter after 100 mills
            } catch (InterruptedException e) {
                System.err.println("Error " + e.getMessage());
                Thread.currentThread().interrupt();
            }
            System.out.print(letter);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        startThreads();
        awaitLatch(mainLatch);
    }
    /**
     * Use this static method to start all threads
     */
    private static void startThreads() {
        List<Thread> threads = Arrays.asList(new Th1(), new Th2(), new Th3(), new Th4());
        // TS: threads.forEach((thread) => thread.start())
        threads.forEach(Thread::start); // Intellij replaced with lambda reference
    }
    /**
     * Awaiting 0 latch value to exit the program
     */
    private static void awaitLatch(CountDownLatch latch) {
        try {
            latch.await(); // wait for 0 value
        } catch (InterruptedException e) {
            System.err.println("Error while waiting: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
