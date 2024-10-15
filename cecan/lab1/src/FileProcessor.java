import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class FileProcessor {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String filePath = "src/resources/lab1.txt";

        System.out.print("Enter the word to count: ");
        String wordToCount = scanner.nextLine();

        Thread fileReaderThread = new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                System.out.println("Error reading file: " + e.getMessage());
            }
        });

        Thread wordCounterThread = new Thread(() -> {
            int count = 0;
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    count += countWordOccurrences(line, wordToCount);
                }
                System.out.println("The word '" + wordToCount + "' occurs " + count + " times in the file.");
            } catch (IOException e) {
                System.out.println("Error reading file: " + e.getMessage());
            }
        });

        fileReaderThread.start();
        wordCounterThread.start();
    }

    private static int countWordOccurrences(String line, String word) {
        int index = 0, count = 0;
        while ((index = line.indexOf(word, index)) != -1) {
            count++;
            index += word.length();
        }
        return count;
    }
}
