public class ThreadGroupExample {
    public static void main(String[] args) {
        ThreadGroup mainGroup = Thread.currentThread().getThreadGroup();
        mainGroup.setMaxPriority(Thread.MAX_PRIORITY - 1);
        mainGroup.list();

        // Создаем и настраиваем группу G2 в mainGroup
        ThreadGroup g2 = new ThreadGroup(mainGroup, "G2");
        g2.setMaxPriority(Thread.MAX_PRIORITY);
        g2.list();

        // Создаем и настраиваем вложенную группу G1 в G2
        ThreadGroup g1 = new ThreadGroup(g2, "G1");
        startThreadInGroup(g1, "Tha", 1);
        startThreadInGroup(g1, "Thb", 3);
        startThreadInGroup(g1, "Thc", 8);
        startThreadInGroup(g1, "Thd", 3);
        g1.list();

        // Создаем поток в группе G2
        startThreadInGroup(g2, "ThA", 1);
        g2.list();

        // Создаем группу G3 в mainGroup
        ThreadGroup g3 = new ThreadGroup(mainGroup, "G3");
        startThreadInGroup(g3, "Th1", 4);
        startThreadInGroup(g3, "Th2", 3);
        startThreadInGroup(g3, "Th3", 5);
        g3.list();

        // Создаем потоки в главной группе mainGroup
        startThreadInGroup(mainGroup, "Th1", 3);
        startThreadInGroup(mainGroup, "Th2", 6);
        mainGroup.list();
    }

    // Метод для создания и запуска потока в заданной группе с указанным приоритетом
    public static void startThreadInGroup(ThreadGroup group, String name, int priority) {
        Thread thread = new Thread(group, name);
        thread.setPriority(priority);
        thread.start();
    }
}
