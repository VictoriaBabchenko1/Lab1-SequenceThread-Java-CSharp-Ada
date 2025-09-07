import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static int THREAD_COUNT;
    public static double STEP;
    public static final Object START_LOCK = new Object();
    public static volatile boolean started = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введіть кількість потоків: ");
        THREAD_COUNT = scanner.nextInt();

        System.out.print("Введіть крок: ");
        STEP = scanner.nextDouble();

        scanner.close();

        List<WorkerTask> workerTasks = new ArrayList<>();
        List<Integer> taskDelays = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < THREAD_COUNT; i++) {
            int delay = random.nextInt(7001) + 3000;
            taskDelays.add(delay);
            workerTasks.add(new WorkerTask(i + 1, STEP));
        }

        workerTasks.forEach(worker -> new Thread(worker).start());

        synchronized (START_LOCK) {
            started = true;
            START_LOCK.notifyAll();
        }

        new ControllerThread(workerTasks, taskDelays).start();
    }
}