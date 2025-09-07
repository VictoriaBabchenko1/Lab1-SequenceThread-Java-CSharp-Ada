import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ControllerThread {
    private final List<WorkerTask> workerTasks;
    private final List<Integer> stopTimes;

    public ControllerThread(List<WorkerTask> workerTasks, List<Integer> stopTimes) {
        this.workerTasks = workerTasks;
        this.stopTimes = stopTimes;
    }

    public void start() {
        new Thread(() -> {
            List<TaskDetails> taskDetailsList = new ArrayList<>();

            for (int i = 0; i < workerTasks.size(); i++) {
                taskDetailsList.add(new TaskDetails(i, stopTimes.get(i)));
            }

            taskDetailsList.sort(Comparator.comparingInt(t -> t.stopTime));
            int lastStopTime = 0;

            for (TaskDetails taskDetails : taskDetailsList) {
                int delay = taskDetails.stopTime - lastStopTime;

                try {
                    Thread.sleep(delay);
                } catch (InterruptedException ignored) {
                }

                workerTasks.get(taskDetails.index).stop();
                lastStopTime = taskDetails.stopTime;
            }
        }).start();
    }

    private static class TaskDetails {
        int index;
        int stopTime;

        TaskDetails(int index, int stopTime) {
            this.index = index;
            this.stopTime = stopTime;
        }
    }
}