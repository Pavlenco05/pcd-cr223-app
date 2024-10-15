import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.LinkedBlockingDeque;

class DiceFile implements Runnable {
    boolean dicecheck;
    static LinkedBlockingDeque<Integer> queue = new LinkedBlockingDeque<>();
    Random random = new Random();

    DiceFile(boolean dicecheck) {
        this.dicecheck = dicecheck;
    }

    public void run() {
        if (dicecheck) {
            while (true) {
                int dicevar = random.nextInt(6) + 1;
                try {
                    queue.put(dicevar);
                    System.out.println("Бросок кубика: " + dicevar);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try{
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try (FileWriter num = new FileWriter("Dice.txt", false)) {
                while (true) {
                    try {
                        int dicevar = queue.take();
                        num.write(dicevar + "\n");
                        num.flush();
                        System.out.println("Записано в файл: " + dicevar);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Thread randomnumth = new Thread(new DiceFile(true));
        Thread writenumth = new Thread(new DiceFile(false));
        randomnumth.start();
        writenumth.start();
    }
}
