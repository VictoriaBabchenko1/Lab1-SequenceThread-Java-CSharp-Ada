class SequenceThread extends Thread {
    private int step, threadId;
    private long sum = 0;
    private int count = 0;

    public SequenceThread(int step, int threadId) {
        this.step = step;
        this.threadId = threadId;
    }

    public void run() {
        long startTime = System.currentTimeMillis();
        int current = 0;
        while (System.currentTimeMillis() - startTime < 3000) {
            sum += current;
            current += step;
            count++;
            try { Thread.sleep(10); } catch (InterruptedException e) { break; }
        }
        System.out.println("Thread " + threadId + ": Sum = " + sum + ", Count = " + count);
    }
}

public class ThreadDemo {
    public static void main(String[] args) {
        int[] steps = {1, 2, 3};
        for (int i = 0; i < steps.length; i++) {
            new SequenceThread(steps[i], i + 1).start();
        }
    }
}
