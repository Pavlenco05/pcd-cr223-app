import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

class FileReaderThread implements Runnable {
    String filePath;

    public FileReaderThread(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                System.out.println("Текст был выведен с помощью " + Thread.currentThread().getName());

            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}

class WordCounterThread implements Runnable {
    String filePath;
    String wordToCount;

    public WordCounterThread(String filePath, String wordToCount) {
        this.filePath = filePath;
        this.wordToCount = wordToCount;
    }

    @Override
    public void run() {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                count += countWordOccurrences(line, wordToCount);
            }
            System.out.println("The word '" + wordToCount + "' occurs " + count + " times in the file. Функция работает под " + Thread.currentThread().getName() + " потоком");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    int countWordOccurrences(String line, String word) {
        int index = 0, count = 0;
        while ((index = line.indexOf(word, index)) != -1) {
            count++;
            index += word.length();
        }
        return count;
    }
}

public class FileProcessor {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String filePath = "src/resources/lab1.txt";

        System.out.print("Enter the word to count: ");
        String wordToCount = scanner.nextLine();

        FileReaderThread fileReader = new FileReaderThread(filePath);
        WordCounterThread wordCounter = new WordCounterThread(filePath, wordToCount);

        Thread thread1 = new Thread(fileReader);
        Thread thread2 = new Thread(wordCounter);

        thread1.start();
        thread2.start();

    }
}



