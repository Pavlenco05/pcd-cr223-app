class CurrencyConverterThread extends Thread {
    private double amount;
    private double exchangeRate;
    private boolean running = true;

    public CurrencyConverterThread(double amount, double exchangeRate) {
        this.amount = amount;
        this.exchangeRate = exchangeRate;
    }

    private double convert(double amount, double rate) {
        return amount * rate;
    }

    public void stopConverting() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            double convertedAmount = convert(amount, exchangeRate);
            System.out.println(Thread.currentThread().getName() + amount + " по курсу " + exchangeRate + " = " + convertedAmount);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println(Thread.currentThread().getName() + "Прерван");
                break;
            }

            stopConverting();
        }
    }
}