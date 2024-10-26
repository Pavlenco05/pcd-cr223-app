import java.util.Scanner;
import java.text.DecimalFormat;

class CurrencyConverter implements Runnable {
    private double amount;
    private double conversionRate;
    private String fromCurrency;
    private String toCurrency;

    public CurrencyConverter(double amount, double conversionRate, String fromCurrency, String toCurrency) {
        this.amount = amount;
        this.conversionRate = conversionRate;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
    }

    @Override
    public void run() {
        double convertedAmount = amount * conversionRate;
        DecimalFormat df = new DecimalFormat("#.##"); // Форматирование до двух знаков после запятой
        System.out.printf("%s %s = %s %s%n", df.format(amount), fromCurrency, df.format(convertedAmount), toCurrency);
    }
}

 class CurrencyConversionApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double amount = 0;
        double conversionRate = 0;
        String fromCurrency = "";
        String toCurrency = "";

        try {
            System.out.print("Enter the amount to convert: ");
            if (scanner.hasNextDouble()) {
                amount = scanner.nextDouble();
            } else {
                System.out.println("Invalid input for amount. Please enter a valid number.");
                return; // Завершение программы при неверном вводе
            }

            System.out.print("Enter from currency (USD, EUR): ");
            fromCurrency = scanner.next().toUpperCase();
            System.out.print("Enter to currency (USD, EUR): ");
            toCurrency = scanner.next().toUpperCase();

            if (!isValidCurrency(fromCurrency) || !isValidCurrency(toCurrency)) {
                System.out.println("Invalid currency. Please enter 'USD' or 'EUR'.");
                return; // Завершение программы при неверном вводе валюты
            }

            // Выбор способа получения курса конвертации
            System.out.println("Choose conversion rate option:");
            System.out.println("1. Enter conversion rate manually");
            System.out.println("2. Use predefined conversion rate");
            int choice = scanner.nextInt();

            if (choice == 1) {
                System.out.print("Enter the conversion rate: ");
                if (scanner.hasNextDouble()) {
                    conversionRate = scanner.nextDouble();
                } else {
                    System.out.println("Invalid input for conversion rate. Please enter a valid number.");
                    return; // Завершение программы при неверном вводе
                }
            } else if (choice == 2) {
                conversionRate = getPredefinedConversionRate(fromCurrency, toCurrency);
                if (conversionRate == -1) {
                    System.out.println("Invalid currency conversion pair. Please enter valid currencies.");
                    return;
                }
            } else {
                System.out.println("Invalid choice. Please enter 1 or 2.");
                return; // Завершение программы при неверном выборе
            }

            CurrencyConverter converter = new CurrencyConverter(amount, conversionRate, fromCurrency, toCurrency);
            Thread conversionThread = new Thread(converter);
            conversionThread.start();
            System.out.println("Currency conversion is in progress...");
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        } finally {
            scanner.close(); // Закрытие сканера
        }
    }

    private static boolean isValidCurrency(String currency) {
        return currency.equals("USD") || currency.equals("EUR");
    }

    private static double getPredefinedConversionRate(String fromCurrency, String toCurrency) {
        if (fromCurrency.equals("USD") && toCurrency.equals("EUR")) {
            return 0.85; // Условный курс USD -> EUR
        } else if (fromCurrency.equals("EUR") && toCurrency.equals("USD")) {
            return 1.18; // Условный курс EUR -> USD
        } else {
            return -1; // Неверная пара валют
        }
    }
}
