class ThreadPool {
    int max_capacity = 40;
    int num_threads;
    WorkerThread[] pool;
    boolean stopped;

    SocketQueue queue;

    public ThreadPool() {
        this.pool = new WorkerThread[max_capacity];
        this.queue = new SocketQueue();
        
        // start 5 threads to begin with
        num_threads = 5;
        for (int i = 0; i < 5; i++)
            (pool[i] = new WorkerThread(queue)).start();
    }

    public void increaseThreads() {
        // ensure we do not exceed capacity
        if (num_threads == max_capacity)
            return;

        // increase amount and start new threads
        int amount = num_threads * 2;
        for (; num_threads < amount; num_threads++) {
            pool[num_threads] = new WorkerThread(queue);
            pool[num_threads].start();
        }
        System.out.println(num_threads + " threads (increase)");
    }

    public void decreaseThreads() {
        if (num_threads == 5) return;

        int amount = num_threads / 2;
        for(; num_threads > amount; num_threads--) {
            pool[num_threads - 1].interrupt();
            pool[num_threads - 1] = null;
        }
        System.out.println(num_threads + " threads (decrease)");
    }

    public int jobsAmount() {
        return queue.getSize();
    }

    public int threadsAmount() {
        return num_threads;
    }

    public SocketQueue getQueue() {
        return queue;
    }
}