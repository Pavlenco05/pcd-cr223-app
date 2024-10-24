public class Lab2 {
    public static void main(String[] args) {
        ThreadGroup main = new ThreadGroup("main");

        Thread th1 = new Thread(main, new sleep(),"Th1");
        th1.setPriority(4);
        th1.start();

        Thread th2 = new Thread(main, new sleep(),"Th2");
        th2.setPriority(3);
        th2.start();

        ThreadGroup g6 = new ThreadGroup(main,"G6");
        Thread thA = new Thread(g6, new sleep(),"ThA");
        thA.setPriority(3);
        thA.start();

        ThreadGroup g2 = new ThreadGroup(main,"G2");
        th1 = new Thread(g2, new sleep(),"Th1");
        th1.setPriority(2);
        th1.start();

        ThreadGroup g3 = new ThreadGroup(g2, "G3");
        Thread tha = new Thread(g3, new sleep(),"Tha");
        tha.setPriority(2);
        tha.start();
        Thread thb = new Thread(g3, new sleep(),"Thb");
        thb.setPriority(3);
        thb.start();
        Thread thc = new Thread(g3, new sleep(),"Thc");
        thc.setPriority(4);
        thc.start();
        Thread thd = new Thread(g3, new sleep(),"Thd");
        thd.setPriority(3);
        thd.start();

        th2 = new Thread(g2, new sleep(),"Th2");
        th2.setPriority(3);
        th2.start();
        Thread th3 = new Thread(g2, new sleep(),"Th3");
        th3.setPriority(3);
        th3.start();

        main.list();

    }

    static class sleep implements Runnable {
        public void run() {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
