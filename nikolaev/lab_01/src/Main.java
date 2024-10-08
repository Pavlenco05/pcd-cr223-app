import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<String> currencyList;

        APIConverter apiConverter = new APIConverter();
        apiConverter.fetchCurrenciesFromApi();
        currencyList = new ArrayList<>(apiConverter.getAvailableCurrencies());

        System.out.println("Используются hardcoded данные.");

        if (currencyList.isEmpty()) {
            // if there are no available currencies from the API set hardcoded
            currencyList = new ArrayList<>(new HardcodedConverter().getAvailableCurrencies());
        }

        ConversionData obj = handleCurrencyConversion(scanner, currencyList);

        // API response will be always last
        Thread converterThread = new Thread(new APIConverter(obj.amount, obj.fromCurrency, obj.toCurrency));
        converterThread.start();

        Thread hardconverterThread = new Thread(new HardcodedConverter(obj.amount, obj.fromCurrency, obj.toCurrency));
        hardconverterThread.start();
    }

    private static ConversionData handleCurrencyConversion(Scanner scanner, List<String> currencyList) {
    System.out.println("Доступные валюты:");
    for (int i = 0; i < currencyList.size(); i++) {
        System.out.println((i + 1) + ". " + currencyList.get(i));
    }

    System.out.println("Выберите исходную валюту (введите номер):");
    String fromCurrency = currencyList.get(getChoice(scanner, currencyList.size()) - 1);

    double amount = 0;
    boolean validInput = false; // flag

    while (!validInput) {
        System.out.println("Введите сумму для конвертации:");
        if (scanner.hasNextDouble()) { // NaN handle
            amount = scanner.nextDouble();
            if (amount > 0) { // negative number handle
                validInput = true;
            } else {
                System.err.println("Ошибка: сумма должна быть положительным числом. Попробуйте снова.");
            }
        } else {
            System.err.println("Ошибка: введите число. Попробуйте снова.");
            scanner.next(); // buffer cleanup
        }
    }

    String toCurrency;
    while (true) {
        System.out.println("Выберите целевую валюту:");
        toCurrency = currencyList.get(getChoice(scanner, currencyList.size()) - 1);
        
        if (!fromCurrency.equals(toCurrency)) {
            break;
        } else {
            System.err.println("Ошибка: исходная и целевая валюты не могут совпадать. Попробуйте снова.");
        }
    }

    return new ConversionData(fromCurrency, toCurrency, amount);
}


    private static int getChoice(Scanner scanner, int max) {
        int choice;
        do {
            choice = scanner.nextInt();
        } while (choice < 1 || choice > max);
        return choice;
    }

    // Something like type in ts to store response
    private static class ConversionData {
        String fromCurrency;
        String toCurrency;
        double amount;

        ConversionData(String fromCurrency, String toCurrency, double amount) {
            this.fromCurrency = fromCurrency;
            this.toCurrency = toCurrency;
            this.amount = amount;
        }
    }
}

