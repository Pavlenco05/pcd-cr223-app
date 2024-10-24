import java.util.Random;

public class lab1 {

    private static final String[] adjectives = {
            "quick", "lazy", "happy", "sad", "bright", "dark", "small", "large", "beautiful", "ugly"
    };

    private static final String[] nouns = {
            "cat", "dog", "car", "house", "tree", "river", "mountain", "book", "phone", "computer"
    };

    private static String adjective = "";
    private static String noun = "";

    private static final Object lock = new Object();

    static class AdjectiveGenerator implements Runnable {
        @Override
        public void run() {
            Random random = new Random();
            while (true) {
                synchronized (lock) {
                    adjective = Thread.currentThread().getName() +  adjectives[random.nextInt(adjectives.length)];
                    lock.notify(); // уведомляем другой поток
                    try {
                        lock.wait(); // ждать
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }

    static class NounGenerator implements Runnable {
        @Override
        public void run() {
            Random random = new Random();
            while (true) {
                synchronized (lock) {
                    noun = nouns[random.nextInt(nouns.length)];
                    System.out.println(adjective + " " + noun);
                    lock.notify();  // уведомляем другой поток
                    try {
                        lock.wait();  // ждём новоеприлаг и по кругу
                        Thread.sleep(2000);
                    } catch (InterruptedException e) { // обрабатывает исключения
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {

        Thread adjectiveThread = new Thread(new AdjectiveGenerator());
        Thread nounThread = new Thread(new NounGenerator());

        adjectiveThread.start();
        nounThread.start();

        try {
            adjectiveThread.join();
            nounThread.join(); // метод join заставляет главный поток подождать завершения других потоков
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}