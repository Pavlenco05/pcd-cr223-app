public class CurrencyApp {
    public static void main(String[] args) {
        double amount = 100; // сумма для конвертации
        double exchangeRate = 0.85; // курс


        CurrencyConverterThread converter = new CurrencyConverterThread(amount, exchangeRate);
        CurrencyConverterThread converter2 = new CurrencyConverterThread(amount, 0.5);
        converter.start();
        converter2.start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        converter.stopConverting();
        System.out.println("Конвертация завершена");
    }
}