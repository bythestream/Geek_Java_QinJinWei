import java.util.concurrent.CountDownLatch;

public class CountDownLatchThread  extends Thread{

    private CountDownLatch threadSignal;
    public CountDownLatchThread(CountDownLatch signal){
        this.threadSignal = signal;
    }

    @Override
    public void run(){
        System.out.println(Thread.currentThread().getName() + " started ...");
        threadSignal.countDown();
        System.out.println(Thread.currentThread().getName() + " finished. Num of signal threads left running= " + threadSignal.getCount());
        threadSignal.countDown();
    }
}