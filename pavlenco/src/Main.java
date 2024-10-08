import java.util.Scanner;
import java.text.DecimalFormat;

class CurrencyConverter implements Runnable {
    private double amount;
    private double conversionRate;

    public CurrencyConverter(double amount, double conversionRate) {
        this.amount = amount;
        this.conversionRate = conversionRate;
    }

    @Override
    public void run() {
        double convertedAmount = amount * conversionRate;
        DecimalFormat df = new DecimalFormat("#.##"); // Форматирование до двух знаков после запятой
        System.out.printf("Converted amount: %s%n", df.format(convertedAmount));
    }
}

class CurrencyConversionApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double amount = 0;
        double conversionRate = 0;

        try {
            // Ввод суммы
            System.out.print("Enter the amount to convert: ");
            if (scanner.hasNextDouble()) {
                amount = scanner.nextDouble();
            } else {
                System.out.println("Invalid input for amount. Please enter a valid number.");
                return; // Завершение программы при неверном вводе
            }

            // Ввод курса конвертации
            System.out.print("Enter the conversion rate: ");
            if (scanner.hasNextDouble()) {
                conversionRate = scanner.nextDouble();
            } else {
                System.out.println("Invalid input for conversion rate. Please enter a valid number.");
                return; // Завершение программы при неверном вводе
            }

            // Создание и запуск потока конвертации валюты
            CurrencyConverter converter = new CurrencyConverter(amount, conversionRate);
            Thread conversionThread = new Thread(converter);
            conversionThread.start();
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        } finally {
            // Закрытие сканера
            scanner.close();
        }
    }
}
