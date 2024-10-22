import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    static class CustomDaemon extends Thread {
        private final String message;

        public CustomDaemon(ThreadGroup group, String name, String message) {
            super(group, name);
            setDaemon(true);
            this.message = message;
        }

        @Override
        public void run() {
            System.out.println(message);
        }
    }

    public static void main(String[] args) {
        // System (main)
        ThreadGroup mainGroup = Thread.currentThread().getThreadGroup();

        Thread thA = new CustomDaemon(mainGroup, "ThA", "ThA is running");
        thA.setPriority(3);

        ThreadGroup group2 = new ThreadGroup(mainGroup, "G2");

        Thread th1G2 = new CustomDaemon(group2, "Th1", "Th1 in G2 is running");
        th1G2.setPriority(5);

        Thread th2G2 = new CustomDaemon(group2, "Th2", "Th2 in G2 is running");
        th2G2.setPriority(3);

        Thread th33G2 = new CustomDaemon(group2, "Th33", "Th33 in G2 is running");
        th33G2.setPriority(7);

        Thread th11 = new CustomDaemon(mainGroup, "Th11", "Th11 is running");
        th11.setPriority(3);

        Thread th22 = new CustomDaemon(mainGroup, "Th22", "Th22 is running");
        th22.setPriority(3);

        ThreadGroup group1 = new ThreadGroup(mainGroup, "G1");

        ThreadGroup group3 = new ThreadGroup(group1, "G3");

        Thread thaaG3 = new CustomDaemon(group3, "Thaa", "Thaa in G3 is running");
        thaaG3.setPriority(2);

        Thread thbbG3 = new CustomDaemon(group3, "Thbb", "Thbb in G3 is running");
        thbbG3.setPriority(3);

        Thread thccG3 = new CustomDaemon(group3, "Thcc", "Thcc in G3 is running");
        thccG3.setPriority(8);

        Thread thddG3 = new CustomDaemon(group3, "Thdd", "Thdd in G3 is running");
        thddG3.setPriority(3);

        mainGroup.list();
        System.out.println("\nStarting all threads:");

        Thread[] threads = {
                thA, th1G2, th2G2, th33G2, th11, th22, thaaG3, thbbG3, thccG3, thddG3
        };

        ExecutorService executor = Executors.newFixedThreadPool(threads.length);

        System.out.println("\nStarting all threads via executor:");

        for (Thread thread : threads) {
            executor.submit(thread);
        }

        try {
            th11.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Interrupted: " + e.getMessage());
        } finally {
            executor.shutdown();
        }

        // System.out.println("\nAll threads have finished.");
    }
}
