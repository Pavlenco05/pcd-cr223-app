package com.example.pcdlab1;
import java.util.Scanner;

public class PCDLab1 {
    // Создаем объект 'lock', который будет использоваться для синхронизации потоков
    private static final Object lock = new Object();

    public static void main(String[] args) {
        // Создаем два потока для конвертации валют:
        // Первый поток для конвертации из USD в EUR
        Thread usdToEurConverterThread = new Thread(new CurrencyConverter(0.91, "EUR"));
        usdToEurConverterThread.setName("Potok 1");
        // Второй поток для конвертации из USD в GBP
        Thread usdToGbpConverterThread = new Thread(new CurrencyConverter(0.77, "GBP"));
        usdToGbpConverterThread.setName("Potok 2");
        // Запускаем оба потока
        usdToEurConverterThread.start();
        usdToGbpConverterThread.start();

        // Ожидаем завершения выполнения обоих потоков
        try {
            usdToEurConverterThread.join(); // Ожидаем завершения первого потока
            usdToGbpConverterThread.join(); // Ожидаем завершения второго потока
        } catch (InterruptedException e) {
            e.printStackTrace(); // Обрабатываем возможное исключение, если поток был прерван
        }
    }

    // Класс, реализующий Runnable, для конвертации валют
    static class CurrencyConverter implements Runnable {
        private final double conversionRate; // Курс конвертации
        private final String currency; // Валюта, в которую будем конвертировать

        // Конструктор, который принимает курс конвертации и название валюты
        public CurrencyConverter(double conversionRate, String currency) {
            this.conversionRate = conversionRate;
            this.currency = currency;
        }

        // Метод run() содержит логику потока
        @Override
        public void run() {
            // Используем блок синхронизации для предотвращения одновременного ввода/вывода в нескольких потоках
            synchronized (lock) {
                // Создаем Scanner для ввода данных с клавиатуры
                Scanner scanner = new Scanner(System.in);

                // Запрашиваем у пользователя ввод суммы в USD для конвертации
                System.out.println(Thread.currentThread().getName());
                System.out.print("Введите сумму в USD для конвертации в " + currency + ": ");
                double usd = scanner.nextDouble(); // Получаем сумму в USD от пользователя

                // Выполняем конвертацию по заданному курсу
                double convertedAmount = usd * conversionRate;

                // Выводим результат конвертации на экран
                System.out.println("Конвертация валют: " + usd + " USD = " + convertedAmount + " " + currency);
            }
        }
    }
}
