import java.util.Scanner;
import java.text.DecimalFormat;

public class CurrencyConverterApp {

    public static void main(String[] args) {
        System.out.println("Запуск основного приложения...");

        // Данные для первой конвертации: USD -> EUR
        double amount1 = 100;
        String fromCurrency1 = "USD";
        String toCurrency1 = "EUR";
        double exchangeRate1 = 0.85; // Пример курса

        // Данные для второй конвертации: USD -> MDL
        double amount2 = 100;
        String fromCurrency2 = "USD";
        String toCurrency2 = "MDL";
        double exchangeRate2 = 17.50; // Пример курса для MDL

        // Создаю два потока для конвертации валют
        Thread conversionThread1 = new Thread(new CurrencyConversionTask(amount1, fromCurrency1, toCurrency1, exchangeRate1));
        Thread conversionThread2 = new Thread(new CurrencyConversionTask(amount2, fromCurrency2, toCurrency2, exchangeRate2));

        // Запускаю потоки
        conversionThread1.start();
        conversionThread2.start();

        // Основное приложение может продолжать выполнять другие задачи
        System.out.println("Основное приложение выполняет другие задачи...");

        // Ожидаю завершения потоков
        try {
            conversionThread1.join();
            conversionThread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Основное приложение завершено.");
    }
}

// Класс, который реализует задачу конвертации валют
class CurrencyConversionTask implements Runnable {
    private double amount;
    private String fromCurrency;
    private String toCurrency;
    private double exchangeRate;

    public CurrencyConversionTask(double amount, String fromCurrency, String toCurrency, double exchangeRate) {
        this.amount = amount;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.exchangeRate = exchangeRate;
    }

    @Override
    public void run() {
        System.out.println("Конвертация валюты: " + amount + " " + fromCurrency + " в " + toCurrency + " по курсу " + exchangeRate);
        double convertedAmount = amount * exchangeRate;
        System.out.println("Результат: " + convertedAmount + " " + toCurrency);
    }
}
