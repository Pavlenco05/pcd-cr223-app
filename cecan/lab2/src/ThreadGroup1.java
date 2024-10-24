public class ThreadGroup1 {
    public static void main(String[] args) {
        ThreadGroup sys = Thread.currentThread().getThreadGroup();

        createAndStartThreads(sys, new int[]{8, 3});

        ThreadGroup gn = new ThreadGroup(sys, "gn");
        gn.setMaxPriority(6);

        createAndStartThreads(gn, new int[]{3});

        ThreadGroup gh = new ThreadGroup(gn, "gh");
        gh.setMaxPriority(6);

        createAndStartThreads(gh, new int[]{4, 3, 6, 3});

        ThreadGroup gm = new ThreadGroup(sys, "gm");
        gm.setMaxPriority(6);

        createAndStartThreads(gm, new int[]{2, 3, 3});

        System.out.println("Listing system threads and groups:");
        sys.list();
    }
    static void createAndStartThreads(ThreadGroup group, int[] priorities) {
        for (int i = 0; i < priorities.length; i++) {
            Thread t = new Thread(group, new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        // Daemon threads will exit when no other non-daemon threads are running
                        // Performing no operations keeps them alive as daemon threads
                    }
                }
            }, String.valueOf(i));
            t.setPriority(priorities[i]);
            t.setDaemon(true);
            t.start();
        }
    }
}
