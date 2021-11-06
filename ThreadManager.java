public class ThreadManager extends Thread {
    ThreadPool pool;

    public ThreadManager() {
        this.pool = new ThreadPool();
    }

    @Override
    public void run() {
        int jobs;
        int threads;

        // loop
        boolean cont = true;
        while (cont) {
            // poll the information
            jobs = pool.jobsAmount();
            threads = pool.threadsAmount();
            
            // if needs adjustment, adjust
            if (jobs > threads * 2)
                pool.increaseThreads();
            else if (threads != 5 && jobs < threads / 2)
                pool.decreaseThreads();

            // shut down an interrupted thread
            if (Thread.interrupted())
                cont = false;
        }
    }

    public SocketQueue getQueue() {
        return pool.getQueue();
    }
}