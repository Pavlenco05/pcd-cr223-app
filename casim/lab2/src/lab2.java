import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class lab2 {
    static class CustomDaemon extends Thread {
        private String message;

        public CustomDaemon(ThreadGroup group, String name, String message) {
            super(group, name);
            setDaemon(true);
            this.message = message;
        }

        @Override
        public void run() {
            // Здесь выполнение задач потока, если нужно
        }
    }

    public static void main(String[] args) {
        // Создание группы G2
        ThreadGroup groupG2 = new ThreadGroup("G2");

        // Создание группы G1 внутри G2
        ThreadGroup groupG1 = new ThreadGroup(groupG2, "G1");
        Thread tha = new CustomDaemon(groupG1, "Tha", "Tha in G1 is running");
        tha.setPriority(1);
        Thread thb = new CustomDaemon(groupG1, "Thb", "Thb in G1 is running");
        thb.setPriority(3);
        Thread thc = new CustomDaemon(groupG1, "Thc", "Thc in G1 is running");
        thc.setPriority(8);
        Thread thd = new CustomDaemon(groupG1, "Thd", "Thd in G1 is running");
        thd.setPriority(3);
        Thread thA = new CustomDaemon(groupG1, "ThA", "ThA in G2 is running");
        thA.setPriority(1);

        // Создание группы G3 внутри G2
        ThreadGroup groupG3 = new ThreadGroup(groupG2, "G3");
        Thread th1G3 = new CustomDaemon(groupG3, "Th1", "Th1 in G3 is running");
        th1G3.setPriority(4);
        Thread th2G3 = new CustomDaemon(groupG3, "Th2", "Th2 in G3 is running");
        th2G3.setPriority(3);
        Thread th3G3 = new CustomDaemon(groupG3, "Th3", "Th3 in G3 is running");
        th3G3.setPriority(5);

        // Потоки в группе G2
        Thread th1G2 = new CustomDaemon(groupG2, "Th1", "Th1 in G2 is running");
        th1G2.setPriority(3);
        Thread th2G2 = new CustomDaemon(groupG2, "Th2", "Th2 in G2 is running");
        th2G2.setPriority(6);

        // Запуск всех потоков через ExecutorService
        ExecutorService executor = Executors.newFixedThreadPool(8);
        Thread[] threads = { tha, thb, thc, thd, thA, th1G3, th2G3, th3G3, th1G2, th2G2 };

        for (Thread thread : threads) {
            executor.submit(thread);  // запуск потоков
        }

        // Ожидание завершения потоков
        try {
            th1G2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }

        // Вывод информации о потоках в группах
        System.out.println("\nThreads in group G2:");
        printThreadInfo(groupG2);
    }

    public static void printThreadInfo(ThreadGroup group) {
        int numThreads = group.activeCount();  // Получаем количество активных потоков в группе
        Thread[] threads = new Thread[numThreads];  // Массив для потоков
        group.enumerate(threads, true);  // Заполняем массив активными потоками

        for (Thread t : threads) {
            if (t != null && t.getThreadGroup() != null) {
                System.out.println("Thread name: " + t.getName() + ", Priority: " + t.getPriority() +
                        ", Group: " + t.getThreadGroup().getName());
            }
        }
    }
}
