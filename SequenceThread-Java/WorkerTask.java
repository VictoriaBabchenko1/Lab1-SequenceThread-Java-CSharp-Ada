import java.util.concurrent.atomic.AtomicBoolean;

public class WorkerTask implements Runnable {
    private final int taskId;
    private final double step;
    private final AtomicBoolean running = new AtomicBoolean(true);

    public WorkerTask(int taskId, double step) {
        this.taskId = taskId;
        this.step = step;
    }

    public void stop() {
        running.set(false);
    }

    @Override
    public void run() {
        synchronized (Main.START_LOCK) {
            while (!Main.started) {
                try {
                    Main.START_LOCK.wait();
                } catch (InterruptedException ignored) {
                }
            }
        }

        double sum = 0.0;
        int count = 0;

        for (double current = 0.0; running.get(); count++) {
            sum += current;
            current += step;
        }

        System.out.printf("[Thread %d] Finished. Sum: %.2f, Elements: %d%n", taskId, sum, count);
    }
}