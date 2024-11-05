import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Main {
    private static final CountDownLatch mainLatch = new CountDownLatch(4);
    private static final CountDownLatch waitLatch = new CountDownLatch(2);
    private static final ThreadGroup group = new ThreadGroup("MyThreadGroup");

    public static class Th1 implements Runnable {
        @Override
        public void run() {
            System.out.println("Поток 1 начал выполнение");
            calculateSumPairs(1, 100, "[Th1]");
            waitLatch.countDown();
            mainLatch.countDown();
            printWithDelay("Фамилия: Касым");
        }
    }

    public static class Th2 implements Runnable {
        @Override
        public void run() {
            System.out.println("Поток 2 начал выполнение");
            calculateSumPositionsFromEnd(6, 106, "[Th2]");
            waitLatch.countDown();
            mainLatch.countDown();
            printWithDelay("Имя: Анастасия");
        }
    }

    public static class Th3 implements Runnable {
        @Override
        public void run() {
            System.out.println("Инициализация потока 3");
            awaitLatch(waitLatch);
            System.out.println("Поток 3 начал выполнение");
            traverseFromStart(120, 690, "[Th3]");
            mainLatch.countDown();
            printWithDelay("Дисциплина: Компьютеры и сети");
        }
    }

    public static class Th4 implements Runnable {
        @Override
        public void run() {
            System.out.println("Инициализация потока 4");
            awaitLatch(waitLatch);
            System.out.println("Поток 4 начал выполнение");
            traverseFromEnd(1000, 1567, "[Th4]");
            mainLatch.countDown();
            printWithDelay("Группа: CR-223");
        }
    }

    private static void calculateSumPairs(int start, int end, String threadLabel) {
        for (int i = start; i < end; i += 2) {
            int result = i + (i + 2);
            System.out.println(threadLabel + " => Сумма парных чисел " + i + " и " + (i + 2) + " = " + result);
            Thread.yield();
        }
    }

    private static void calculateSumPositionsFromEnd(int start, int end, String threadLabel) {
        // Находим первое четное число в диапазоне
        int firstEven = (end % 2 == 0) ? end : end - 1;

        // Суммируем четные числа парами
        for (int i = firstEven; i >= start; i -= 2) {
            if (i - 2 >= start) {
                int result = i + (i - 2);
                System.out.println(threadLabel + " => Сумма позиций парных четных чисел " + i + " и " + (i - 2) + " = " + result);
            }
            Thread.yield();
        }
    }

    private static void traverseFromStart(int start, int end, String threadLabel) {
        for (int i = start; i <= end; i++) {
            System.out.print(threadLabel + " => " + i + " ");
            Thread.yield();
        }
        System.out.println();
    }

    private static void traverseFromEnd(int start, int end, String threadLabel) {
        for (int i = end; i >= start; i--) {
            System.out.print(threadLabel + " => " + i + " ");
            Thread.yield();
        }
        System.out.println();
    }

    private static synchronized void printWithDelay(String text) {
        for (char letter : text.toCharArray()) {
            try {
                Thread.sleep(100); // Задержка 100 миллисекунд
            } catch (InterruptedException e) {
                System.err.println("Ошибка " + e.getMessage());
                Thread.currentThread().interrupt();
            }
            System.out.print(letter);
        }
        System.out.println();
    }

    private static void startThreads() {
        List<Thread> threads = Arrays.asList(
                new Thread(group, new Th1(), "Th1"),
                new Thread(group, new Th2(), "Th2"),
                new Thread(group, new Th3(), "Th3"),
                new Thread(group, new Th4(), "Th4")
        );

        // Установка приоритетов для потоков
        threads.get(0).setPriority(Thread.MIN_PRIORITY);
        threads.get(1).setPriority(Thread.NORM_PRIORITY);
        threads.get(2).setPriority(Thread.MAX_PRIORITY);
        threads.get(3).setPriority(Thread.NORM_PRIORITY);

        // Запуск потоков
        threads.forEach(Thread::start);

        // Перечисление потоков в группе
        Thread[] threadArray = new Thread[group.activeCount()];
        group.enumerate(threadArray);
        for (Thread thread : threadArray) {
            System.out.println("Поток: " + thread.getName() + ", Приоритет: " + thread.getPriority());
        }

        // Список всех потоков в группе
        System.out.println("Список потоков в группе:");
        group.list();
    }

    private static void awaitLatch(CountDownLatch latch) {
        try {
            latch.await();
        } catch (InterruptedException e) {
            System.err.println("Ошибка ожидания: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        startThreads();
        awaitLatch(mainLatch);
    }
}
