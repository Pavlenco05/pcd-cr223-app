public class Main {
    static class CustomThread extends Thread {
        public CustomThread(ThreadGroup group, String name) {
            super(group, name);
            setDaemon(true);
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                boolean isDaemon = Thread.currentThread().isDaemon();
                // never executable condition
                if (!isDaemon) System.out.println("Not daemon thread " + this.getName());
            }
        }
    }

    public static void main(String[] args) {
        // System (main)
        ThreadGroup mainGroup = Thread.currentThread().getThreadGroup();

        Thread thA = new CustomThread(mainGroup, "ThA");
        thA.setPriority(3);
        // thA.setDaemon(true);

        ThreadGroup group2 = new ThreadGroup(mainGroup, "G2");

        Thread th1G2 = new CustomThread(group2, "Th1");
        th1G2.setPriority(5);
        // th1G2.setDaemon(true);

        Thread th2G2 = new CustomThread(group2, "Th2");
        th2G2.setPriority(3);
        // th2G2.setDaemon(true);

        Thread th33G2 = new CustomThread(group2, "Th33");
        th33G2.setPriority(7);
        // th33G2.setDaemon(true);

        Thread th11 = new CustomThread(mainGroup, "Th11");
        th11.setPriority(3);
        // th11.setDaemon(true);

        Thread th22 = new CustomThread(mainGroup, "Th22");
        th22.setPriority(3);
        // th22.setDaemon(true);

        ThreadGroup group1 = new ThreadGroup(mainGroup, "G1");

        ThreadGroup group3 = new ThreadGroup(group1, "G3");

        Thread thaaG3 = new CustomThread(group3, "Thaa");
        thaaG3.setPriority(2);
        // thaaG3.setDaemon(true);

        Thread thbbG3 = new CustomThread(group3, "Thbb");
        thbbG3.setPriority(3);
        // thbbG3.setDaemon(true);

        Thread thccG3 = new CustomThread(group3, "Thcc");
        thccG3.setPriority(8);
        // thccG3.setDaemon(true);

        Thread thddG3 = new CustomThread(group3, "Thdd");
        thddG3.setPriority(3);
        // thddG3.setDaemon(true);

        // Startup
        Thread[] threads = {
                thA, th1G2, th2G2, th33G2, th11, th22, thaaG3, thbbG3, thccG3, thddG3
        };

        // Start all threads
        for (Thread thread : threads) {
            thread.start();
        }

        ThreadGroup[] threadGroups = {
                mainGroup, group1, group2, group3
        };

        for (ThreadGroup group : threadGroups) {
            System.out.println(group.getName()+":");
            group.list();
            System.out.println("\n--------------------------------");
        }

        System.out.println("\nFinished");
    }
}
