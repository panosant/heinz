package xj_adv.ch6_exceptions.exercise631;

import java.util.concurrent.*;

// Using exceptions ...
public class ThreadPool {
    private final ThreadGroup group = new ThreadGroup("thread pool");
    private final BlockingQueue<Runnable> runQueue = new LinkedBlockingQueue<>();
    public static final int MAXIMUM_POOL_SIZE = 1024;
    private volatile boolean running = true;

    /**
     * @param poolSize the pool size which should be between 1 and
     *                 the maximum allowed
     * @see #MAXIMUM_POOL_SIZE
     */
    public ThreadPool(int poolSize) throws IllegalArgumentException {

        if (poolSize <= 0 || poolSize > ThreadPool.MAXIMUM_POOL_SIZE) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < poolSize; i++) {
            Worker worker = new Worker(group, "Worker" + i);
            worker.setDaemon(false);
            worker.start();
        }
    }

    /**
     * @param job
     * @throws NullPointerException
     */
    public void submit(Runnable job) {
        if (job == null) {
            throw new NullPointerException();
        }

        boolean result = runQueue.offer(job); // will always be true
        assert result : "Job submit failed!";
    }

    private Runnable take() throws InterruptedException {
        Runnable runnable = runQueue.take(); // will never be null
        assert runnable != null : "Consumption from BlockingQueue failed!";

        return runnable;
    }

    public int getRunQueueLength() {
        return runQueue.size();
    }

    public void shutdown() {
        running = false;
        group.interrupt();
    }

    private class Worker extends Thread {
        public Worker(ThreadGroup group, String name) {
            super(group, name); // group and name cannot be null

            assert group != null : "Group was null!";
            assert name != null : "Name was null!";
        }

        public void run() {
            while (running && !isInterrupted()) {
                try {
                    take().run();
                } catch (InterruptedException e) {
                    interrupt();
                    break;
                }
            }
        }
    }
}
