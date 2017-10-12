package xj_adv.ch6_exceptions.solution631;

import java.util.*;
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
     * @throws OutOfRangeException when the pool size is less than
     *                             1 or larger than the maximum
     *                             allowed
     * @see #MAXIMUM_POOL_SIZE
     */
    public ThreadPool(int poolSize) {
        if (poolSize < 1 || poolSize > MAXIMUM_POOL_SIZE) {
            throw new OutOfRangeException(poolSize, 1, MAXIMUM_POOL_SIZE);
        }
        for (int i = 0; i < poolSize; i++) {
            Worker worker = new Worker(group, "Worker" + i);
            worker.setDaemon(false);
            worker.start();
        }
        assert poolSize > 0 && poolSize <= MAXIMUM_POOL_SIZE;
    }

    /**
     * @param job
     * @throws NullPointerException if job is null
     */
    public void submit(Runnable job) {
        Objects.requireNonNull(job, "job may not be null");
        boolean added = runQueue.offer(job);
        assert added : "Could not modify the queue";
    }

    private Runnable take() throws InterruptedException {
        Runnable job = runQueue.take();
        assert job != null;
        return job;
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
            super(group, name);
            assert group != null;
            assert name != null;
        }

        public void run() {
            assert !isDaemon();
            while (running && !isInterrupted()) {
                try {
                    take().run();
                } catch (InterruptedException e) {
                    interrupt();
                    break;
                }
            }
            assert !running || isInterrupted();
        }
    }
}
