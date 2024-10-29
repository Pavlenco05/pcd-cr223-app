import java.util.Random;

public class ArrayGenerator {
    private final int[] mas;

    public ArrayGenerator(int size) {
        mas = new int[size];
    }

    public int[] generateRandomArray() {
        Random random = new Random();
        for (int i = 0; i < mas.length; i++) {
            mas[i] = random.nextInt(100) + 1;
        }
        return mas;
    }

    public void printArray() {
        System.out.println("Массив чисел:");
        for (int ma : mas) {
            System.out.print(ma + " ");
        }
        System.out.println();
    }
}

class SumFirstEvenPositionsTask implements Runnable {
    private final int[] mas;

    public SumFirstEvenPositionsTask(int[] mas) {
        this.mas = mas;
    }

    @Override
    public void run() {
        int sumFirstEvenPositions = mas[0] + mas[2] + mas[4];
        System.out.println("Сумма чисел на первых трёх чётных позициях: " + sumFirstEvenPositions);
    }
}

class SumEvenIndicesTask implements Runnable {
    private final int[] mas;

    public SumEvenIndicesTask(int[] mas) {
        this.mas = mas;
    }

    @Override
    public void run() {
        int sumPositionsMultipleOfTwo = 0;
        for (int i = mas.length - 1; i >= 0; i--) {
            if (i % 2 == 0) {
                sumPositionsMultipleOfTwo += mas[i];
            }
        }
        System.out.println("Сумма чисел на чётных индексах, начиная с последнего элемента: " + sumPositionsMultipleOfTwo);
    }
}

 class Main {
    public static void main(String[] args) {

        ArrayGenerator generator = new ArrayGenerator(100);
        int[] mas = generator.generateRandomArray();
        generator.printArray();

        SumFirstEvenPositionsTask sumTask1 = new SumFirstEvenPositionsTask(mas);
        Thread thread1 = new Thread(sumTask1);

        SumEvenIndicesTask sumTask2 = new SumEvenIndicesTask(mas);
        Thread thread2 = new Thread(sumTask2);

        thread1.start();
        thread2.start();
    }
}
