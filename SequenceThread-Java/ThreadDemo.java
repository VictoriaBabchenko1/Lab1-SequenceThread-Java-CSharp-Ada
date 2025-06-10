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
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println("Thread " + threadId + ": Sum = " + sum + ", Count = " + count);
    }
}

public class ThreadDemo {
    public static void main(String[] args) {
        int numberOfThreads = 0;

        if (args.length > 0) {
            try {
                numberOfThreads = Integer.parseInt(args[0]);
                if (numberOfThreads <= 0) {
                    System.out.println("Кількість потоків повинна бути додатнім числом");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Невірний формат");
                return;
            }
        } else {
            System.out.println("Кількість поторків не вказана, використовуємо кількість за замовчуванням: 3");
            numberOfThreads = 3;
        }

        for (int i = 0; i < numberOfThreads; i++) {
            int stepForThread = i + 1;
            new SequenceThread(stepForThread, i + 1).start();
        }
    }
}