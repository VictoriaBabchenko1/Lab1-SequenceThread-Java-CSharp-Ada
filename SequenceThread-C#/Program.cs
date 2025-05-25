using System;
using System.Threading;

class SequenceThread
{
    int step, threadId, count = 0;
    long sum = 0;

    public SequenceThread(int step, int threadId)
    {
        this.step = step;
        this.threadId = threadId;
    }

    public void Run()
    {
        int current = 0;
        var start = DateTime.Now;
        while ((DateTime.Now - start).TotalSeconds < 3)
        {
            sum += current;
            current += step;
            count++;
            Thread.Sleep(10);
        }
        Console.WriteLine($"Thread {threadId}: Sum = {sum}, Count = {count}");
    }
}

class Program
{
    static void Main()
    {
        int[] steps = { 1, 2, 3 };
        for (int i = 0; i < steps.Length; i++)
        {
            var threadObj = new SequenceThread(steps[i], i + 1);
            new Thread(new ThreadStart(threadObj.Run)).Start();
        }
    }
}
