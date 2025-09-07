using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;

namespace SequenceThread_C_
{
    public class Program
    {
        internal static readonly object START_LOCK = new object(); 
        internal static bool started = false; 

         public static int THREAD_COUNT;
        public static double STEP; 

        public static void Main(string[] args)
        {
            Console.Write("Введіть кількість потоків: ");
            if (!int.TryParse(Console.ReadLine(), out THREAD_COUNT) || THREAD_COUNT <= 0)
            {
                Console.WriteLine("Невірне значення, використовується 3 потоки за замовчуванням.");
                THREAD_COUNT = 3;
            }

            Console.Write("Введіть крок: ");
            if (!double.TryParse(Console.ReadLine(), out STEP) || STEP <= 0)
            {
                Console.WriteLine("Невірне значення, використовується крок 0.5 за замовчуванням.");
                STEP = 0.5;
            }
            
            List<WorkerThread> workers = new List<WorkerThread>();
            List<int> delays = new List<int>();
            Random rand = new Random();

            for (int i = 0; i < THREAD_COUNT; i++)
            {
                int delay = rand.Next(3000, 10001); 
                delays.Add(delay);
                workers.Add(new WorkerThread(i + 1, STEP));
            }

            foreach (var worker in workers)
            {
                new Thread(worker.Run).Start();
            }

            lock (START_LOCK)
            {
                started = true;
                Monitor.PulseAll(START_LOCK);
            }

            new ControllerThread(workers, delays).Start();
        }
    }
}