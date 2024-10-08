import java.util.Random;

public class SecondThread implements Runnable {

    private int[] mas;

    public SecondThread(int[] mas) {
        this.mas = mas;
    }

    @Override
    public void run() {
        // 1. Сумма чисел на первых трёх чётных позициях
        int sumFirstEvenPositions = mas[0] + mas[2] + mas[4];
        System.out.println("Suma numerelor de pe primele trei poziții pare: " + sumFirstEvenPositions);

        // 2. Сумма чисел на позициях, кратных трём, начиная с последнего элемента
        int sumPositionsMultipleOfThree = 0;
        for (int i = mas.length - 1; i >= 0; i--) {
            if (i % 3 == 0) {
                sumPositionsMultipleOfThree += mas[i];
            }
        }
        System.out.println("Suma numerelor de pe poziții pare cât trei, începând cu ultimul element: " + sumPositionsMultipleOfThree);
    }

    public static void main(String[] args) {
        // Генерация массива из 100 случайных чисел от 1 до 100
        int[] mas = new int[100];
        Random random = new Random();
        for (int i = 0; i < mas.length; i++) {
            mas[i] = random.nextInt(100) + 1;
        }

        // Вывод сгенерированного массива
        System.out.println("Masivul generat:");
        for (int i = 0; i < mas.length; i++) {
            System.out.print(mas[i] + " ");
        }
        System.out.println();

        // Создание и запуск второго потока
        SecondThread th2 = new SecondThread(mas);
        Thread thread2 = new Thread(th2);
        thread2.start();
    }
}
