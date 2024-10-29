import java.util.concurrent.CountDownLatch;

public class Main {
    static CountDownLatch latch = new CountDownLatch(4); // Synchronize end task
    static CountDownLatch startLatch34 = new CountDownLatch(2); // Wait for Th1 and Th2 to complete

    public static class Th1 extends Thread {
        @Override
        public void run() {
            System.out.println("Thread 1: Starting task for Condition 1 (Start Sums)");
            int result;
            for (int i = 2; i < 102; i += 4) {
                result = i + (i + 2);
                System.out.println("Th1 Sum: " + i + " + " + (i + 2) + " = " + result);
                Thread.yield(); // Yield to allow other threads to run
            }
            startLatch34.countDown(); // Signal that Th1 has completed
            latch.countDown(); // Signal task completion for overall program end
            delayedPrint("Prenume: Nichita");
        }
    }

    public static class Th2 extends Thread {
        @Override
        public void run() {
            System.out.println("Thread 2: Starting task for Condition 2 (End Sums)");
            int result;
            for (int i = 106; i > 6; i -= 4) {
                result = i + (i - 2);
                System.out.println("Th2 Position Sum: " + i + " + " + (i - 2) + " = " + result);
                Thread.yield(); // Yield to allow other threads to run
            }
            startLatch34.countDown(); // Signal that Th2 has completed
            latch.countDown(); // Signal task completion for overall program end
            delayedPrint("Nume: Cecan");
        }
    }

    public static class Th3 extends Thread {
        @Override
        public void run() {
            System.out.println("Thread 3: Initialized and waiting for Th1 and Th2 to complete.");
            try {
                startLatch34.await(); // Wait for Th1 and Th2 to complete
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread 3: Starting task for Condition 3 (Iterate Interval [0, 798])");
            for (int i = 0; i <= 798; i++) {
                System.out.print("(Th3)" + i + " ");
                Thread.yield();
            }
            System.out.println();
            latch.countDown(); // Signal task completion for overall program end
            delayedPrint("Disciplina: Programarea concurenta si distribuita");
        }
    }

    public static class Th4 extends Thread {
        @Override
        public void run() {
            System.out.println("Thread 4: Initialized and waiting for Th1 and Th2 to complete.");
            try {
                startLatch34.await(); // Wait for Th1 and Th2 to complete
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread 4: Starting task for Condition 4 (Iterate Interval [1456, 2111])");
            for (int i = 2111; i >= 1456; i--) {
                System.out.print("(Th4)" + i + " ");
                Thread.yield();
            }
            System.out.println();
            latch.countDown(); // Signal task completion for overall program end
            delayedPrint("Grupa:CR-223");
        }
    }

    static synchronized void delayedPrint(String text) {
        for (char ch : text.toCharArray()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print(ch);
        }
        System.out.println();
    }


    public static void main(String[] args) {
        // Instantiate and start threads
        Th1 th1 = new Th1();
        Th2 th2 = new Th2();
        Th3 th3 = new Th3();
        Th4 th4 = new Th4();

        th1.start();
        th2.start();
        th3.start();
        th4.start();

        // Wait for all threads to complete tasks
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
