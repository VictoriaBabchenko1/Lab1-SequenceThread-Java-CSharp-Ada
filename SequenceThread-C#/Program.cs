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
            try
            {
                Thread.Sleep(10);
            }
            catch (ThreadInterruptedException)
            {
                break;
            }
        }
        Console.WriteLine($"Thread {threadId}: Sum = {sum}, Count = {count}");
    }
}

class Program
{
    static void Main(string[] args)
    {
        int numberOfThreads = 0;

        if (args.Length > 0)
        {
            if (int.TryParse(args[0], out numberOfThreads))
            {
                if (numberOfThreads <= 0)
                {
                    Console.WriteLine("Кількість потоків повинна бути додатнім числом");
                    return;
                }
            }
            else
            {
                Console.WriteLine("Невірний формат");
                return;
            }
        }
        else
        {
            Console.WriteLine("Кількість поторків не вказана, використовуємо кількість за замовчуванням: 3");
            numberOfThreads = 3;
        }

        for (int i = 0; i < numberOfThreads; i++)
        {
            var threadObj = new SequenceThread(i + 1, i + 1);
            new Thread(new ThreadStart(threadObj.Run)).Start();
        }
    }
}