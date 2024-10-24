public class ThreadGroup1 {
    public static void main(String[] args) {
        ThreadGroup mainGroup = new ThreadGroup("Main");
        ThreadGroup g4 = new ThreadGroup(mainGroup, "G4");
        ThreadGroup g3 = new ThreadGroup(g4, "G3");
        ThreadGroup g2 = new ThreadGroup(g3, "G2");

        Thread tha = new Thread(g3, () -> {
            System.out.println("Поток " + Thread.currentThread().getName() + " запущен.");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Поток " + Thread.currentThread().getName() + " завершён.");
        }, "Tha");
        tha.setPriority(2);
        tha.setDaemon(true);

        Thread thb = new Thread(g3, () -> {
            System.out.println("Поток " + Thread.currentThread().getName() + " запущен.");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Поток " + Thread.currentThread().getName() + " завершён.");
        }, "Thb");
        thb.setPriority(8);
        thb.setDaemon(true);

        Thread thc = new Thread(g3, () -> {
            System.out.println("Поток " + Thread.currentThread().getName() + " запущен.");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Поток " + Thread.currentThread().getName() + " завершён.");
        }, "Thc");
        thc.setPriority(3);
        thc.setDaemon(true);

        Thread thd = new Thread(g3, () -> {
            System.out.println("Поток " + Thread.currentThread().getName() + " запущен.");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Поток " + Thread.currentThread().getName() + " завершён.");
        }, "Thd");
        thd.setPriority(3);
        thd.setDaemon(true);

        Thread th1_g2 = new Thread(g2, () -> {
            System.out.println("Поток " + Thread.currentThread().getName() + " запущен.");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Поток " + Thread.currentThread().getName() + " завершён.");
        }, "Th1");
        th1_g2.setPriority(3);
        th1_g2.setDaemon(true);

        Thread th2_g2 = new Thread(g2, () -> {
            System.out.println("Поток " + Thread.currentThread().getName() + " запущен.");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Поток " + Thread.currentThread().getName() + " завершён.");
        }, "Th2");
        th2_g2.setPriority(3);
        th2_g2.setDaemon(true);

        Thread th3_g2 = new Thread(g2, () -> {
            System.out.println("Поток " + Thread.currentThread().getName() + " запущен.");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Поток " + Thread.currentThread().getName() + " завершён.");
        }, "Th3");
        th3_g2.setPriority(3);
        th3_g2.setDaemon(true);

        Thread thA_g2 = new Thread(g2, () -> {
            System.out.println("Поток " + Thread.currentThread().getName() + " запущен.");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Поток " + Thread.currentThread().getName() + " завершён.");
        }, "ThA");
        thA_g2.setPriority(3);
        thA_g2.setDaemon(true);

        Thread th1_g3 = new Thread(g3, () -> {
            System.out.println("Поток " + Thread.currentThread().getName() + " запущен.");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Поток " + Thread.currentThread().getName() + " завершён.");
        }, "Th1");
        th1_g3.setPriority(8);
        th1_g3.setDaemon(true);

        Thread th2_g3 = new Thread(g3, () -> {
            System.out.println("Поток " + Thread.currentThread().getName() + " запущен.");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Поток " + Thread.currentThread().getName() + " завершён.");
        }, "Th2");
        th2_g3.setPriority(3);
        th2_g3.setDaemon(true);

        tha.start();
        thb.start();
        thc.start();
        thd.start();
        th1_g2.start();
        th2_g2.start();
        th3_g2.start();
        thA_g2.start();
        th1_g3.start();
        th2_g3.start();

        System.out.println("Потоки в главной группе:");
        mainGroup.list();

        System.out.println("\nПотоки в группе G4:");
        g4.list();

        System.out.println("\nПотоки в группе G3:");
        g3.list();

        System.out.println("\nПотоки в группе G2:");
        g2.list();
    }
}
