import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SocketQueue {
  private int max = 50;
  Queue<Job> queue;

  private ReentrantLock lock = new ReentrantLock(true);
  private Condition notEmpty = lock.newCondition();
  private Condition notFull = lock.newCondition();

  public SocketQueue() {
    queue = new LinkedList<>();
  }

  public void enqueue(Socket s, int num) {
    lock.lock();
    try {
      while (queue.size() == max) {
        notFull.await();
      }

      queue.add(new Job(s, num));
      notEmpty.signalAll();
    } catch (InterruptedException e) {
    } finally {
      lock.unlock();
    }
  }

  public Job dequeue() throws InterruptedException {
    lock.lock();
    try {
      while (queue.size() == 0) {
        notEmpty.await();
      }

      Job job = queue.remove();
      notFull.signalAll();
      return job;
    } catch(InterruptedException e) {
      throw new InterruptedException();
    } finally {
      lock.unlock();
    }
  }

  public int getSize() {
    return queue.size();
  }
}