import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static final int BUFFER_SIZE = 5;
    private static final int NUM_PRODUCERS = 3;
    private static final int NUM_CONSUMERS = 4;
    private static final int TARGET_PER_CONSUMER = 2;

    public static class SharedBuffer {
        private Queue<Integer> buffer = new LinkedList<>();
        private int bufferSize;
        private AtomicInteger totalConsumed = new AtomicInteger(0); // безопасное отслеживание потребляемых элементов
        private int totalTarget;

        public SharedBuffer(int size, int totalTarget) {
            this.bufferSize = size;
            this.totalTarget = totalTarget;
        }

        public synchronized void produce(Thread producer) throws InterruptedException {
            while ( buffer.size() >= bufferSize|| totalConsumed.get() >= totalTarget) {
                if (totalConsumed.get() >= totalTarget) return;
                System.out.println(producer.getName() + " ожидает: Буфер полон");
                wait();
            }

            Random random = new Random();
            for (int i = 0; i < 2 && totalConsumed.get() < totalTarget; i++) {
                int oddNumber = random.nextInt(100) * 2 + 1;
                buffer.offer(oddNumber);
                System.out.println(producer.getName() + " произвел: " + oddNumber);
            }
            notifyAll();
        }

        public synchronized int consume(Thread consumer) throws InterruptedException {
            while (buffer.isEmpty() && totalConsumed.get() < totalTarget) {
                if (totalConsumed.get() >= totalTarget) return -1; // сигнал потребителю о завершении работы
                System.out.println(consumer.getName() + " ожидает: Буфер пуст");
                wait();
            }

            if (totalConsumed.get() >= totalTarget) return -1;

            //Извлечение числа из буфера
            int item = buffer.poll();
            totalConsumed.incrementAndGet();// доьавляет +1 и записывает это значение
            System.out.println(consumer.getName() + " потребил: " + item);
            notifyAll();
            return item; //возвращает извлечённое число потребителю
        }
    }

    public static class Producer implements Runnable {
        private SharedBuffer buffer;// хранит ссылку на общий буфер

        public Producer(SharedBuffer buffer) {
            this.buffer = buffer; //обеспечили доступ производителей к общему буферу
        }

        @Override
        public void run() {
            try {
                while (true) {
                    buffer.produce(Thread.currentThread());// позволяет методу produce понять какой производитель производит
                    if (Thread.currentThread().isInterrupted()) break;
                    Thread.sleep(50);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static class Consumer implements Runnable {
        private SharedBuffer buffer;

        public Consumer(SharedBuffer buffer) {
            this.buffer = buffer;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    int item = buffer.consume(Thread.currentThread()); // проверяем если цель достигнута
                    if (item == -1) break;
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        int totalTarget = NUM_CONSUMERS * TARGET_PER_CONSUMER;
        SharedBuffer buffer = new SharedBuffer(BUFFER_SIZE, totalTarget);

        Thread[] producers = new Thread[NUM_PRODUCERS];
        Thread[] consumers = new Thread[NUM_CONSUMERS];

        System.out.println("Начало работы системы производства и потребления нечетных чисел:");
        System.out.println("Количество производителей: " + NUM_PRODUCERS);
        System.out.println("Количество потребителей: " + NUM_CONSUMERS);
        System.out.println("Размер буфера: " + BUFFER_SIZE);
        System.out.println("Целевое количество для каждого потребителя: " + TARGET_PER_CONSUMER);
        System.out.println("-------------------------------------------");

        for (int i = 0; i < NUM_PRODUCERS; i++) {
            producers[i] = new Thread(new Producer(buffer), "Производитель-" + (i + 1));
            producers[i].start();
        }

        for (int i = 0; i < NUM_CONSUMERS; i++) { //цикл для создания и запуска потоков потребителей
            consumers[i] = new Thread(new Consumer(buffer), "Потребитель-" + (i + 1));
            consumers[i].start();
        }

        try {
            for (Thread consumer : consumers) { // цикл по массиву потоков потребителей
                consumer.join();
            }
            for (Thread producer : producers) {
                producer.interrupt();
                producer.join();
            }
            System.out.println("-------------------------------------------");
            System.out.println("Все потребители успешно получили требуемое количество нечетных чисел.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
